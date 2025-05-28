package defaults;

import java.io.Serializable;

public class PKW implements Serializable{
	private static final long serialVersionUID = 1L;
	
	public enum Status {Verfügbar, Belegt, Reperatur}
	public enum Kraftstoff {Benzin_E5, Benzin_E10, Benzin_E85, Diesel_B7, Diesel_XTL,Wasserstoff, Elektro, Hybrid}
	
	private String ID;
	private String Kennzeichen;
	private Status Status;
	private int Preis;
	private String Marke;
	private Kraftstoff Kraftstoff;
	private String Modell;
	private String Details;
	
	public PKW(String Seriennummer, String Kennzeichen, Status Status , int Preis, String Marke, Kraftstoff Kraftstoff, String Modell, String Details) {
		this.ID = Seriennummer;
		this.Kennzeichen = Kennzeichen;
		this.Status = Status;
		this.Preis = Preis;
		this.Marke = Marke;
		this.Kraftstoff = Kraftstoff;
		this.Modell = Modell;
		this.Details = Details;
	}
	
	public void display() {
		System.out.println("-----===== Informationen =====-----");
		System.out.println("ID: " + ID);
		System.out.println("Kennzeichen: " + Kennzeichen);
		System.out.println("Status: " + Status);
		System.out.println("Preis: " + Preis);
		System.out.println("Marke: " + Marke);
		System.out.println("Kraftstoff: " + Kraftstoff);
		System.out.println("Modell: " + Modell);
		System.out.println("Details: " + Details);
		System.out.println("-----=========================-----");
		System.out.println("");
	}

	public String getID() {
		return ID;
	}

	public void setID(String iD) {
		ID = iD;
	}

	public String getKennzeichen() {
		return Kennzeichen;
	}

	public void setKennzeichen(String kennzeichen) {
		Kennzeichen = kennzeichen;
	}

	public Status getStatus() {
		return Status;
	}

	public void setStatus(Status status) {
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

	public Kraftstoff getKraftstoff() {
		return Kraftstoff;
	}

	public void setKraftstoff(Kraftstoff kraftstoff) {
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