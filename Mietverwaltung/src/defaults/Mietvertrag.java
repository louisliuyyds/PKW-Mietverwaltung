package defaults;

import java.sql.*;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import datenbank.Supabaseverbindung;
import datenbank.ZugriffFuhrpark;

public class Mietvertrag {

    private int vertragID;
    private LocalDate startdatum;
    private LocalDate enddatum;
    private Fahrzeug fahrzeug;
    private List<Extras> extrasList = new ArrayList<>();
    private User user; // Optional: User, der den Vertrag erstellt hat
    // Konstruktoren, Getter und Setter
    // SQL-Konstanten


    public Mietvertrag(int vertragID, LocalDate startdatum, LocalDate enddatum, Fahrzeug fahrzeug, User user) {
    	validateDatum(startdatum, enddatum);
        validateMietdauer(startdatum, enddatum);
        
        if (hatKundeAktiveBuchung(user.getUserID())) {
            throw new IllegalStateException("Kunde hat bereits eine aktive Buchung");
        }
    	this.vertragID = vertragID;
        this.startdatum = startdatum;
        this.enddatum = enddatum;
        this.fahrzeug = fahrzeug;
        this.user = user;
    }
    
 // Geschäftsregel 1: Keine Buchung in der Vergangenheit
    private void validateDatum(LocalDate start, LocalDate end) {
        if (start.isBefore(LocalDate.now())) {
            throw new IllegalArgumentException("Keine Buchungen in der Vergangenheit möglich");
        }
        if (end.isBefore(start)) {
            throw new IllegalArgumentException("Enddatum muss nach Startdatum liegen");
        }
    }
    
 // Geschäftsregel 2: Maximale Mietdauer 14 Tage
    private void validateMietdauer(LocalDate start, LocalDate end) {
        long tage = ChronoUnit.DAYS.between(start, end);
        if (tage > 14) {
            throw new IllegalArgumentException("Maximale Mietdauer beträgt 14 Tage");
        }
    }

    // Geschäftsregel 3: Nur eine aktive Buchung pro Kunde
    public static boolean hatKundeAktiveBuchung(int userID) {
        String sql = "SELECT COUNT(*) AS anzahl FROM mietvertraege " +
                     "WHERE mietvertraege.id_users = ? AND enddatum >= CURRENT_DATE";

        try (Connection conn = Supabaseverbindung.connect();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, userID);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                return rs.getInt("anzahl") > 0;
            }
        } catch (SQLException e) {
            System.err.println("Fehler bei Kundenbuchungsprüfung: " + e.getMessage());
        }
        return false;
    }

    @Override
	public int hashCode() {
		return Objects.hash(enddatum, extrasList, fahrzeug, startdatum, user, vertragID);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Mietvertrag other = (Mietvertrag) obj;
		return Objects.equals(enddatum, other.enddatum) && Objects.equals(extrasList, other.extrasList)
				&& Objects.equals(fahrzeug, other.fahrzeug) && Objects.equals(startdatum, other.startdatum)
				&& Objects.equals(user, other.user) && vertragID == other.vertragID;
	}

	public int getVertragID() {
        return vertragID;
    }

    public LocalDate getStartdatum() {
        return startdatum;
    }

    public LocalDate getEnddatum() {
        return enddatum;
    }

    public Fahrzeug getFahrzeug() {
        return fahrzeug;
    }
    public void setId(int id) {
        this.vertragID = id;
    }
    public void setStartdatum(LocalDate startdatum) {
        this.startdatum = startdatum;
    }
    public void setEnddatum(LocalDate enddatum) {
        this.enddatum = enddatum;
    }
    public void setFahrzeug(Fahrzeug fahrzeug) {
        this.fahrzeug = fahrzeug;
    }
    public User getUser() {
        return user;
    }
    public void setUser(User user) {
        this.user = user;
    }
    public List<Extras> getExtrasList() {
        return extrasList;
    }
    public void setExtrasList(List<Extras> extrasList) {
        this.extrasList = extrasList;
    }

	//Gesamtpreisberechnung
	
	public double gesamtPreis() {
		double ergebnis=0;
        for (Extras extra : extrasList) {
            ergebnis += extra.getPreis();
        }
        return ergebnis + fahrzeug.getPreis() * (enddatum.toEpochDay() - startdatum.toEpochDay());
	}

    public String toString() {
        return "Mietvertrag{" +
                "vertragID=" + vertragID +
                ", startdatum=" + startdatum +
                ", enddatum=" + enddatum +
                ", fahrzeug=" + fahrzeug +
                ", extrasList=" + extrasList +
                ", user=" + user +
                ", price=" + gesamtPreis() +
                '}';
    }
    public void addExtra(Extras extra) {
        extrasList.add(extra);
    }
	
	//Verfügbarkeitsprüfer
    /**
     * Gibt alle Fahrzeuge zurück, die im angegebenen Zeitraum verfügbar sind.
     *
     * @param startDatum Startdatum des gewünschten Zeitraums
     * @param endDatum Enddatum des gewünschten Zeitraums
     * @return Liste verfügbarer Fahrzeuge mit vollständigen Fahrzeugdaten
     */
    public static List<Fahrzeug> getVerfuegbareFahrzeuge(LocalDate startDatum, LocalDate endDatum) {
        List<Fahrzeug> verfuegbareFahrzeuge = new ArrayList<>();

        String sql = "SELECT id_fuhrpark FROM fuhrpark " +
                     "WHERE id_fuhrpark NOT IN (" +
                     "  SELECT id_fuhrpark FROM mietvertraege " +
                     "  WHERE (startdatum <= ? AND enddatum >= ?)" +
                     ")";

        try (Connection conn = Supabaseverbindung.connect();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setDate(1, Date.valueOf(endDatum));
            stmt.setDate(2, Date.valueOf(startDatum));

            ResultSet rs = stmt.executeQuery();
            ZugriffFuhrpark db = new ZugriffFuhrpark();

            while (rs.next()) {
                int id = rs.getInt("id_fuhrpark");
                Fahrzeug fahrzeug = db.getFahrzeugObjekt(id);
                if (fahrzeug != null) {
                    verfuegbareFahrzeuge.add(fahrzeug);
                }
            }

        } catch (SQLException e) {
            System.err.println("Fehler bei der Abfrage verfügbarer Fahrzeuge: " + e.getMessage());
            e.printStackTrace();
        }

        return verfuegbareFahrzeuge;
    }

    /**
     * Testmethode zum manuellen Ausführen.
     */
    public static void main(String[] args) {
        LocalDate von = LocalDate.of(2025, 3, 24);
        LocalDate bis = LocalDate.of(2025, 10, 1);

        List<Fahrzeug> verfuegbar = getVerfuegbareFahrzeuge(von, bis);
        System.out.println("Verfügbare Fahrzeuge im Zeitraum:");
        for (Fahrzeug f : verfuegbar) {
            System.out.println(f);
        }
    }

}
