package defaults;

public class Extras {
    private int extrasID;
    private String name;
    private double preis;
    private String beschreibung;

    // Konstruktor
    public Extras(int extrasID, String name, double preis, String beschreibung) {
        this.extrasID = extrasID;
        this.name = name;
        this.preis = preis;
        this.beschreibung = beschreibung;
    }

    public String toString() {
        return "Extras{" +
                "extrasID=" + extrasID +
                ", name='" + name + '\'' +
                ", preis=" + preis +
                ", beschreibung='" + beschreibung + '\'' +
                '}';
    }

    // Getter und Setter
    public int getExtrasID() {
        return extrasID;
    }

    public void setExtrasID(int extrasID) {
        this.extrasID = extrasID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPreis() {
        return preis;
    }

    public void setPreis(double preis) {
        this.preis = preis;
    }

    public String getBeschreibung() {
        return beschreibung;
    }

    public void setBeschreibung(String beschreibung) {
        this.beschreibung = beschreibung;
    }
}
