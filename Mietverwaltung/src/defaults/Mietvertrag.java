package defaults;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import datenbank.Supabaseverbindung;
import datenbank.ZugriffFuhrpark;

public class Mietvertrag {

    private int vertragID;
    private LocalDate startdatum;
    private LocalDate enddatum;
    private Fahrzeug fahrzeug;
    private List<Extras> extrasList = new ArrayList<>();
    // SQL-Konstanten


    public Mietvertrag(int vertragID, LocalDate startdatum, LocalDate enddatum, Fahrzeug fahrzeug) {
        this.vertragID = vertragID;
        this.startdatum = startdatum;
        this.enddatum = enddatum;
        this.fahrzeug = fahrzeug;
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
