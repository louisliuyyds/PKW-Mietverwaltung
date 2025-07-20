package defaults;

import java.util.Objects;

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
    
    @Override
	public int hashCode() {
		return Objects.hash(beschreibung, extrasID, name, preis);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Extras other = (Extras) obj;
		return Objects.equals(beschreibung, other.beschreibung) && extrasID == other.extrasID
				&& Objects.equals(name, other.name)
				&& Double.doubleToLongBits(preis) == Double.doubleToLongBits(other.preis);
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
