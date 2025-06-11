package defaults;

public class Fahrzeug {
    private int id;
    private String kategorie;
    private String fahrzeug;
    private String kennzeichen;
    private String getriebe;
    private int anzahlSitze;
    private double preis;
    private boolean verfuegbar;

    // Konstruktor
    public Fahrzeug(int id, String kategorie, String fahrzeug, String kennzeichen,
                    String getriebe, int anzahlSitze, double preis, boolean verfuegbar) {
        this.id = id;
        this.kategorie = kategorie;
        this.fahrzeug = fahrzeug;
        this.kennzeichen = kennzeichen;
        this.getriebe = getriebe;
        this.anzahlSitze = anzahlSitze;
        this.preis = preis;
        this.verfuegbar = verfuegbar;
    }

    @Override
    public String toString() {
        return "ID: " + id + ", Kategorie: " + kategorie + ", Fahrzeug: " + fahrzeug +
               ", Kennzeichen: " + kennzeichen + ", Getriebe: " + getriebe +
               ", Sitze: " + anzahlSitze + ", Preis: " + preis + " EUR, Verfügbar: " + (verfuegbar ? "Ja" : "Nein");
    }
}
