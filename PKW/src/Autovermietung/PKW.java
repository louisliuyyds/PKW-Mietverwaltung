package Autovermietung;
import java.io.Serializable;
import java.util.Random;

public class PKW implements Serializable{
    private static final long serialVersionUID = 1L;

    private String Seriennummer;
    private String Kennzeichen;
    private String Status;
    private int Preis;
    private String Marke;
    private String Kraftstoff;
    private String Modell;
    private String Details;
    private String PKWName;

    public PKW(String Seriennummer, String Kennzeichen, String Status , int Preis, String Marke, String Kraftstoff, String Modell, String Details, String PKWName) {
        this.Seriennummer = createSeriennummer();
        this.Kennzeichen = Kennzeichen;
        this.Status = Status;
        this.Preis = Preis;
        this.Marke = Marke;
        this.Kraftstoff = Kraftstoff;
        this.Modell = Modell;
        this.Details = Details;
        this.PKWName = PKWName;
    }

    public String createSeriennummer() {
        Random random = new Random();
        String serialnumber = "";

        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";

        for(int i = 0; i < 25; i++) {
            int index = random.nextInt(characters.length());
            serialnumber += characters.charAt(index);
        }
        return serialnumber;
    }

    public void display() {
        System.out.println("-----===== Informationen =====-----");
        System.out.println("Seriennummer: " + Seriennummer);
        System.out.println("Kennzeichen: " + Kennzeichen);
        System.out.println("Status: " + Status);
        System.out.println("Preis: " + Preis);
        System.out.println("Marke: " + Marke);
        System.out.println("Kraftstoff: " + Kraftstoff);
        System.out.println("Modell: " + Modell);
        System.out.println("Details: " + Details);
        System.out.println("PKWName: " + PKWName);
        System.out.println("-----=========================-----");
        System.out.println("");
    }

    public String getSeriennummer() {
        return Seriennummer;
    }

    public void setSeriennummer(String seriennummer) {
        Seriennummer = seriennummer;
    }

    public String getKennzeichen() {
        return Kennzeichen;
    }

    public void setKennzeichen(String kennzeichen) {
        Kennzeichen = kennzeichen;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }

    public int getPreis() {
        return Preis;
    }

    public void setPreis(int preis) {
        Preis = preis;
    }

    public String getMarke() {
        return Marke;
    }

    public void setMarke(String marke) {
        Marke = marke;
    }

    public String getKraftstoff() {
        return Kraftstoff;
    }

    public void setKraftstoff(String kraftstoff) {
        Kraftstoff = kraftstoff;
    }

    public String getModell() {
        return Modell;
    }

    public void setModell(String modell) {
        Modell = modell;
    }

    public String getDetails() {
        return Details;
    }

    public void setDetails(String details) {
        Details = details;
    }

}
