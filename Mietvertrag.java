package defaults;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

public class Mietvertrag {
    private int id;
    private int userId;
    private int fahrzeugId;
    private int extrasId;
    private LocalDate startdatum;
    private LocalDate enddatum;
    private double gesamtpreis;
    private Fahrzeug fahrzeug;
    private List<Extras> extrasList;
    
    public Mietvertrag(int id, int userId, int fahrzeugId, int extrasId, LocalDate startdatum, LocalDate enddatum, double gesamtpreis) {
        this.id = id;
        this.userId = userId;
        this.fahrzeugId = fahrzeugId;
        this.extrasId = extrasId;
        this.startdatum = startdatum;
        this.enddatum = enddatum;
        this.gesamtpreis = gesamtpreis;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getUserId() { return userId; }
    public void setUserId(int userId) { this.userId = userId; }

    public int getFahrzeugId() { return fahrzeugId; }
    public void setFahrzeugId(int fahrzeugId) { this.fahrzeugId = fahrzeugId; }

    public int getExtrasId() { return extrasId; }
    public void setExtrasId(int extrasId) { this.extrasId = extrasId; }

    public LocalDate getStartdatum() { return startdatum; }
    public void setStartdatum(LocalDate startdatum) { this.startdatum = startdatum; }

    public LocalDate getEnddatum() { return enddatum; }
    public void setEnddatum(LocalDate enddatum) { this.enddatum = enddatum; }

    public double getGesamtpreis() {
        return new Mietvertragmethoden(this).berechneGesamtpreis();
    }
    public void setGesamtpreis(double gesamtpreis) { this.gesamtpreis = gesamtpreis; }
    
    public Fahrzeug getFahrzeug() {
        return fahrzeug;
    }

    public List<Extras> getExtrasList() {
        return extrasList;
    }

    public void setFahrzeug(Fahrzeug fahrzeug) {
        this.fahrzeug = fahrzeug;
    }

    public void setExtrasList(List<Extras> extrasList) {
        this.extrasList = extrasList;
    }

    public long getDauerInTagen() {
        return ChronoUnit.DAYS.between(startdatum, enddatum);
    }

    public boolean ueberschneidet(Mietvertrag anderer) {
        return !(this.enddatum.isBefore(anderer.startdatum) || this.startdatum.isAfter(anderer.enddatum));
    }

    @Override
    public String toString() {
        return "Mietvertrag [ID: " + id + ", Kunde: " + userId + ", Fahrzeug: " + fahrzeugId + ", Extras: " + extrasId +
               ", Von: " + startdatum + ", Bis: " + enddatum + ", Preis: " + gesamtpreis + " EUR]";
    }
}
