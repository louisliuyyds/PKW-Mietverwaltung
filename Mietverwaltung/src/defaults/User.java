package defaults;

import java.sql.Date;

public class User {
    int userID;
    String name;
    String vorname;
    String adresse;
    Date geburtstag;
    String fuehrerscheininformation;
    boolean status;
    String email;
    String plainPassword;

    public User(int userID, String name, String vorname, String adresse, Date geburtstag, String fuehrerscheininformation, boolean status, String email, String plainPassword) {
        this.userID = userID;
        this.name = name;
        this.vorname = vorname;
        this.adresse = adresse;
        this.geburtstag = geburtstag;
        this.fuehrerscheininformation = fuehrerscheininformation;
        this.status = status;
        this.email = email;
        this.plainPassword = plainPassword;
    }

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
    public void setGeburtstag(Date geburtstag) {
        this.geburtstag = geburtstag;
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
    @Override
    public String toString() {
        return "User{" +
                "userID=" + userID +
                ", name='" + name + '\'' +
                ", vorname='" + vorname + '\'' +
                ", adresse='" + adresse + '\'' +
                ", geburtstag=" + geburtstag +
                ", fuehrerscheininformation='" + fuehrerscheininformation + '\'' +
                ", status=" + status +
                ", email='" + email + '\'' +
                ", plainPassword='" + plainPassword + '\'' +
                '}';
    }

}
