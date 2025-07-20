package defaults;

import java.sql.Date;
import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.util.Objects;

public class User {
    private int userID;
    private String name;
    private String vorname;
    private String adresse;
    private String telefonnummer; 
    private Date geburtstag;
    private String fuehrerscheininformation;
    private boolean status;
    private String email;
    private String plainPassword;
    
    
 // Konstruktor mit Altersvalidierung
    public User(int userID, String name, String vorname, String adresse, String telefonnummer, 
               Date geburtstag, String fuehrerschein, boolean status, 
               String email, String plainPassword) {
        
    	// Führerschein-Validierung
        validateFuehrerschein(fuehrerschein);
    	
        // Altersprüfung (mind. 18 Jahre)
        if (geburtstag != null && !isAtLeast18YearsOld(geburtstag)) {
            throw new IllegalArgumentException("Benutzer muss mindestens 18 Jahre alt sein");
        }
        
        this.userID = userID;
        this.name = name;
        this.vorname = vorname;
        this.adresse = adresse;
        this.telefonnummer = telefonnummer;
        this.geburtstag = geburtstag;
        this.fuehrerscheininformation = fuehrerschein;
        this.status = status;
        this.email = email;
        this.plainPassword = plainPassword;
    }
    // Validierung der Führerscheininformation
    private void validateFuehrerschein(String fuehrerschein) {
    	if (fuehrerschein == null || fuehrerschein.isBlank()) {
            throw new IllegalArgumentException("Führerscheininformation darf nicht null oder leer sein");
    	}
    }

	// Hilfsmethode zur Altersprüfung mit java.sql.Date
    private boolean isAtLeast18YearsOld(Date geburtstag) {
        // Konvertierung java.sql.Date -> java.time.LocalDate
        LocalDate birthDate = geburtstag.toLocalDate();
        LocalDate currentDate = LocalDate.now();
        Period age = Period.between(birthDate, currentDate);
        return age.getYears() >= 18;
    }

    // toString aktualisiert
    @Override
    public String toString() {
        return "User{" +
                "userID=" + userID +
                ", name='" + name + '\'' +
                ", vorname='" + vorname + '\'' +
                ", adresse='" + adresse + '\'' +
                ", telefonnummer='" + telefonnummer + '\'' +
                ", geburtstag=" + geburtstag +
                ", fuehrerscheininformation='" + fuehrerscheininformation + '\'' +
                ", status=" + status +
                ", email='" + email + '\'' +
                '}';  // Passwort wird nicht ausgegeben
    }

    // Getter/Setter für Telefonnummer
    public String getTelefonnummer() {
        return telefonnummer;
    }

    @Override
	public int hashCode() {
		return Objects.hash(adresse, email, fuehrerscheininformation, geburtstag, name, plainPassword, status,
				telefonnummer, userID, vorname);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		User other = (User) obj;
		return Objects.equals(adresse, other.adresse) && Objects.equals(email, other.email)
				&& Objects.equals(fuehrerscheininformation, other.fuehrerscheininformation)
				&& Objects.equals(geburtstag, other.geburtstag) && Objects.equals(name, other.name)
				&& Objects.equals(plainPassword, other.plainPassword) && status == other.status
				&& Objects.equals(telefonnummer, other.telefonnummer) && userID == other.userID
				&& Objects.equals(vorname, other.vorname);
	}
	public void setTelefonnummer(String telefonnummer) {
        this.telefonnummer = telefonnummer;
    }

    // Altersprüfung im Geburtstag-Setter
    public void setGeburtstag(Date geburtstag) {
        if (geburtstag != null && !isAtLeast18YearsOld(geburtstag)) {
            throw new IllegalArgumentException("Benutzer muss mindestens 18 Jahre alt sein");
        }
        this.geburtstag = geburtstag;
    }

    // Vorhandene Getter/Setter (unverändert)
    public int getUserID() {
        return userID;
    }
    public void setUserID(int userID) {
        this.userID = userID;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getVorname() {
        return vorname;
    }
    public void setVorname(String vorname) {
        this.vorname = vorname;
    }
    public String getAdresse() {
        return adresse;
    }
    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }
    public Date getGeburtstag() {
        return geburtstag;
    }
    public String getFuehrerscheininformation() {
        return fuehrerscheininformation;
    }
    public void setFuehrerscheininformation(String fuehrerscheininformation) {
        this.fuehrerscheininformation = fuehrerscheininformation;
    }
    public boolean isStatus() {
        return status;
    }
    public void setStatus(boolean status) {
        this.status = status;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getPlainPassword() {
        return plainPassword;
    }
    public void setPlainPassword(String plainPassword) {
        this.plainPassword = plainPassword;
    }

}
