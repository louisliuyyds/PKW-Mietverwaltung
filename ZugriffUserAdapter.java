package adapter;

import datenbank.ZugriffUser;
import defaults.User;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ZugriffUserAdapter implements ZugriffInterface<User> {

    private final ZugriffUser zugriff = new ZugriffUser();

    @Override
    public List<User> getAlleDaten() {
        return zugriff.getAllUsers();
    }

    @Override
    public void update(User user) {
        zugriff.updateUser(user);
    }

    @Override
    public void delete(int id) {
        zugriff.deleteUser(id);
    }

    @Override
    public void add(User user) {
        zugriff.addUser(user, user.getPassworthash());
    }

    @Override
    public String[] getSpaltennamen() {
        return new String[]{
                "ID", "Name", "Vorname", "Adresse", "Geburtstag", "Führerscheininformation", "Status", "Email", "Passwort"
        };
    }

    @Override
    public User getByID(int id) {
        return zugriff.getUserById(id);
    }

    @Override
    public List<User> getByName(String vorname, String name) {
        return zugriff.getUsersByName(vorname, name);
    }
    
    public User getUserByEmail(String email) {
        return zugriff.getUserByEmail(email);
    }


    public User fromMap(Map<String, String> daten) {
        try {
            String name = daten.getOrDefault("Name", "").trim();
            String vorname = daten.getOrDefault("Vorname", "").trim();
            String adresse = daten.getOrDefault("Adresse", "").trim();
            String geburtstagStr = daten.getOrDefault("Geburtstag", "").trim();
            String fuehrerschein = daten.getOrDefault("Führerscheininformation", "").trim();
            boolean status = daten.getOrDefault("Status", "false").equalsIgnoreCase("true");
            String email = daten.getOrDefault("Email", "").trim();
            String passwort = daten.getOrDefault("Passwort", "").trim();

            SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy");
            Date geburtstag = new Date(formatter.parse(geburtstagStr).getTime());

            int id = daten.containsKey("ID") ? Integer.parseInt(daten.get("ID")) : 0;

            return new User(id, name, vorname, adresse, geburtstag.toLocalDate(), fuehrerschein, status, email, passwort);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
} 
