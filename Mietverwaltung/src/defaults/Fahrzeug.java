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

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getKategorie() {
		return kategorie;
	}

	public void setKategorie(String kategorie) {
		this.kategorie = kategorie;
	}

	public String getFahrzeug() {
		return fahrzeug;
	}

	public void setFahrzeug(String fahrzeug) {
		this.fahrzeug = fahrzeug;
	}

	public String getKennzeichen() {
		return kennzeichen;
	}

	public void setKennzeichen(String kennzeichen) {
		this.kennzeichen = kennzeichen;
	}

	public String getGetriebe() {
		return getriebe;
	}

	public void setGetriebe(String getriebe) {
		this.getriebe = getriebe;
	}

	public int getAnzahlSitze() {
		return anzahlSitze;
	}

	public void setAnzahlSitze(int anzahlSitze) {
		this.anzahlSitze = anzahlSitze;
	}

	public double getPreis() {
		return preis;
	}

	public void setPreis(double preis) {
		this.preis = preis;
	}

	public boolean isVerfuegbar() {
		return verfuegbar;
	}

	public void setVerfuegbar(boolean verfuegbar) {
		this.verfuegbar = verfuegbar;
	}
}
