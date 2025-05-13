package PKWVermietungsverwaltung;

public class Auftrag {
	
	private String ID;
	private String AnfangsDatum;
	private String EndDatum;
	private String FahrzeugID;
	private String KundenID;
	private String ExtraID;
	private String Gesamtpreis;

	public Auftrag(String ID, String AnfangsDatum, String EndDatum,  String FahrzeugID, String KundenID, String ExtraID,  String Gesamtpreis) {
		this.ID = ID;
		this.AnfangsDatum = AnfangsDatum;
		this.EndDatum = EndDatum;
		this.FahrzeugID = FahrzeugID;
		this.KundenID = KundenID;
		this.ExtraID = ExtraID;
		this.Gesamtpreis = Gesamtpreis;
	}

	public void display() {
		System.out.println("-----===== Informationen Auftrag =====-----");
		System.out.println("ID: " + ID);
		System.out.println("Start Datum: " + AnfangsDatum);
		System.out.println("End Datum: " + EndDatum);
		System.out.println("FahrzeugID: " + FahrzeugID);
		System.out.println("KundenID: " + KundenID);
		System.out.println("ExtraID: " + ExtraID);
		System.out.println("Gesamtpreis: " + Gesamtpreis);
		System.out.println("-----=================================-----");
		System.out.println("");
	}

	public String getID() {
		return ID;
	}

	public void setID(String iD) {
		ID = iD;
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

	public String getFahrzeugID() {
		return FahrzeugID;
	}

	public void setFahrzeugID(String fahrzeugID) {
		FahrzeugID = fahrzeugID;
	}

	public String getKundenID() {
		return KundenID;
	}

	public void setKundenID(String kundenID) {
		KundenID = kundenID;
	}

	public String getExtraID() {
		return ExtraID;
	}

	public void setExtraID(String extraID) {
		ExtraID = extraID;
	}

	public String getGesamtpreis() {
		return Gesamtpreis;
	}

	public void setGesamtpreis(String gesamtpreis) {
		Gesamtpreis = gesamtpreis;
	}
	
	

}
