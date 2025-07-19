package defaults;

public class Fahrzeug {
    private int id;
    private String kategorie;
    private String marke;
    private String modell;
    private String kennzeichen;
    private String getriebe;
    private int anzahlSitze;
    private String treibstoff;
    private double preis;
    private boolean verfuegbar;

    public Fahrzeug(int id, String kategorie, String marke, String modell, String kennzeichen,
                    String getriebe, int anzahlSitze, String treibstoff, double preis, boolean verfuegbar) {
        this.id = id;
        this.kategorie = kategorie;
        this.marke = marke;
        this.modell = modell;
        this.kennzeichen = kennzeichen;
        this.getriebe = getriebe;
        this.anzahlSitze = anzahlSitze;
        this.treibstoff = treibstoff;
        this.preis = preis;
        this.verfuegbar = verfuegbar;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getKategorie() { return kategorie; }
    public void setKategorie(String kategorie) { this.kategorie = kategorie; }

    public String getMarke() { return marke; }
    public void setMarke(String marke) { this.marke = marke; }

    public String getModell() { return modell; }
    public void setModell(String modell) { this.modell = modell; }

    public String getKennzeichen() { return kennzeichen; }
    public void setKennzeichen(String kennzeichen) { this.kennzeichen = kennzeichen; }

    public String getGetriebe() { return getriebe; }
    public void setGetriebe(String getriebe) { this.getriebe = getriebe; }

    public int getAnzahlSitze() { return anzahlSitze; }
    public void setAnzahlSitze(int anzahlSitze) { this.anzahlSitze = anzahlSitze; }

    public String getTreibstoff() { return treibstoff; }
    public void setTreibstoff(String treibstoff) { this.treibstoff = treibstoff; }

    public double getPreis() { return preis; }
    public void setPreis(double preis) { this.preis = preis; }

    public boolean isVerfuegbar() { return verfuegbar; }
    public void setVerfuegbar(boolean verfuegbar) { this.verfuegbar = verfuegbar; }

    @Override
    public String toString() {
        return "ID: " + id + ", Kategorie: " + kategorie + ", Marke: " + marke + ", Modell: " + modell +
               ", Kennzeichen: " + kennzeichen + ", Getriebe: " + getriebe +
               ", Sitze: " + anzahlSitze + ", Treibstoff: " + treibstoff +
               ", Preis: " + preis + " EUR, Verfügbar: " + (verfuegbar ? "Ja" : "Nein");
    }
} 
