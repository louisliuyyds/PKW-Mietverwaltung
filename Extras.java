package defaults;

public class Extras {
    private int id;
    private String bezeichnung;
    private double preis;

    public Extras(int id, String bezeichnung, double preis) {
        this.id = id;
        this.bezeichnung = bezeichnung;
        this.preis = preis;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getBezeichnung() { return bezeichnung; }
    public void setBezeichnung(String bezeichnung) { this.bezeichnung = bezeichnung; }

    public double getPreis() { return preis; }
    public void setPreis(double preis) { this.preis = preis; }

    @Override
    public String toString() {
        return "Extras [ID: " + id + ", Bezeichnung: " + bezeichnung + ", Preis: " + preis + " EUR]";
    }
}
