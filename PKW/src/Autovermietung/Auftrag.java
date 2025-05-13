package Autovermietung;

public class Auftrag {
    private String Familienname;
    private String AnfangsDatum;
    private String EndDatum;
    private String Tagespreis;
    private String ExtraPaket;
    private String Fahrzeug;
    private String Seriennummer;

    public Auftrag(String Familienname, String AnfangsDatum, String EndDatum, String Tagespreis, String ExtraPaket,
                   String Fahrzeug, String Seriennummer) {
        this.Familienname = Familienname;
        this.AnfangsDatum = AnfangsDatum;
        this.EndDatum = EndDatum;
        this.Tagespreis = Tagespreis;
        this.ExtraPaket = ExtraPaket;
        this.Fahrzeug = Fahrzeug;
        this.Seriennummer = Seriennummer;
    }

    public void display() {
        System.out.println("-----===== Informationen Auftrag =====-----");
        System.out.println("Familienname: " + Familienname);
        System.out.println("Start Datum: " + AnfangsDatum);
        System.out.println("End Datum: " + EndDatum);
        System.out.println("Tagespreis: " + Tagespreis);
        System.out.println("ExtraPaket: " + ExtraPaket);
        System.out.println("Fahrzeug: " + Fahrzeug);
        System.out.println("Fahrzeug Seriennummer: " + Seriennummer);
        System.out.println("-----=================================-----");
        System.out.println("");
    }

    public String getAnfangsDatum() {
        return AnfangsDatum;
    }

    public void setAnfangsDatum(String anfangsDatum) {
        AnfangsDatum = anfangsDatum;
    }

    public String getEndDatum() {
        return EndDatum;
    }

    public void setEndDatum(String endDatum) {
        EndDatum = endDatum;
    }

    public String getTagespreis() {
        return Tagespreis;
    }

    public void setTagespreis(String tagespreis) {
        Tagespreis = tagespreis;
    }

    public String getExtraPaket() {
        return ExtraPaket;
    }

    public void setExtraPaket(String extraPaket) {
        ExtraPaket = extraPaket;
    }

    public String getFahrzeug() {
        return Fahrzeug;
    }

    public void setFahrzeug(String fahrzeug) {
        Fahrzeug = fahrzeug;
    }

    public String getSeriennummer() {
        return Seriennummer;
    }

    public void setSeriennummer(String seriennummer) {
        Seriennummer = seriennummer;
    }
}
