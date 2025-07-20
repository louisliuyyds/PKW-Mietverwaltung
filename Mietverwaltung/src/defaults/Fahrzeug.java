package defaults;

import java.util.Objects;

public class Fahrzeug {
    private int id;
    private String kategorie;
    private String Modell;
    private String kennzeichen;
    private String getriebe;
    private int anzahlSitze;
    private double preis;
    private boolean verfuegbar;

    // Konstruktor
    public Fahrzeug(int id, String kategorie, String Modell, String kennzeichen,
                    String getriebe, int anzahlSitze, double preis, boolean verfuegbar) {
        this.id = id;
        this.kategorie = kategorie;
        this.Modell = Modell;
        this.kennzeichen = kennzeichen;
        this.getriebe = getriebe;
        this.anzahlSitze = anzahlSitze;
        this.preis = preis;
        this.verfuegbar = verfuegbar;
    }
    // toString()-Methode
    @Override
    public String toString() {
        return "ID: " + id + ", Kategorie: " + kategorie + ", Modell: " + Modell +
               ", Kennzeichen: " + kennzeichen + ", Getriebe: " + getriebe +
               ", Sitze: " + anzahlSitze + ", Preis: " + preis + " EUR, Verfügbar: " + (verfuegbar ? "Ja" : "Nein");
    }
 
    // Getters und Setters
    
	public int getId() {
		return id;
	}

	@Override
	public int hashCode() {
		return Objects.hash(Modell, anzahlSitze, getriebe, id, kategorie, kennzeichen, preis, verfuegbar);
	}
	
	// Vergleich der Fachobjekte equals()-Methode
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Fahrzeug other = (Fahrzeug) obj;
		return Objects.equals(Modell, other.Modell) && anzahlSitze == other.anzahlSitze
				&& Objects.equals(getriebe, other.getriebe) && id == other.id
				&& Objects.equals(kategorie, other.kategorie) && Objects.equals(kennzeichen, other.kennzeichen)
				&& Double.doubleToLongBits(preis) == Double.doubleToLongBits(other.preis)
				&& verfuegbar == other.verfuegbar;
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

	public String getModell() {
		return Modell;
	}

	public void setFahrzeug(String fahrzeug) {
		this.Modell = fahrzeug;
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
