package defaults;

import java.time.LocalDate;

public class User {
    private int id;
    private String name;
    private String vorname;
    private String adresse;
    private LocalDate geburtstag;
    private String fuehrerscheininformation;
    private boolean status;
    private String email;
    private String passworthash;

    public User() { }          // ← leerer Default-Konstruktor

    public User(int id, String name, String vorname, String adresse, LocalDate geburtstag,
                String fuehrerscheininformation, boolean status, String email, String passworthash) {
        this.id = id;
        this.name = name;
        this.vorname = vorname;
        this.adresse = adresse;
        this.geburtstag = geburtstag;
        this.fuehrerscheininformation = fuehrerscheininformation;
        this.status = status;
        this.email = email;
        this.passworthash = passworthash;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getVorname() { return vorname; }
    public void setVorname(String vorname) { this.vorname = vorname; }

    public String getAdresse() { return adresse; }
    public void setAdresse(String adresse) { this.adresse = adresse; }

    public LocalDate getGeburtstag() { return geburtstag; }
    public void setGeburtstag(LocalDate geburtstag) { this.geburtstag = geburtstag; }

    public String getFuehrerscheininformation() { return fuehrerscheininformation; }
    public void setFuehrerscheininformation(String fuehrerscheininformation) { this.fuehrerscheininformation = fuehrerscheininformation; }

    public boolean isStatus() { return status; }
    public void setStatus(boolean status) { this.status = status; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPassworthash() { return passworthash; }
    public void setPassworthash(String passworthash) { this.passworthash = passworthash; }

    @Override
    public String toString() {
        return "User [ID: " + id + ", Name: " + name + ", Vorname: " + vorname + ", Email: " + email + "]";
    }
}
