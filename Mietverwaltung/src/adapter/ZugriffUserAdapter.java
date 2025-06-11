package adapter;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;

import datenbank.ZugriffUser;
import ui.ZugriffInterface;

public class ZugriffUserAdapter implements ZugriffInterface {

    private final ZugriffUser zugriff = new ZugriffUser();

    @Override
    public List<Object[]> getAlleDaten() {
        return zugriff.getAllUsersData();
    }

    @Override
    public void update(int id, Map<String, String> daten) {
        try {
            String name = daten.get("Name").trim();
            String vorname = daten.get("Vorname").trim();
            String adresse = daten.get("Adresse").trim();
            String geburtstagStr = daten.get("Geburtstag").trim();
            String fuehrerschein = daten.get("Führerscheininformation").trim();
            boolean status = daten.get("Status").equalsIgnoreCase("frei");

            Date geburtstag = new Date(new SimpleDateFormat("dd.MM.yyyy").parse(geburtstagStr).getTime());
            zugriff.updateUser(id, name, vorname, adresse, geburtstag, fuehrerschein, status);
        } catch (Exception e) {
            throw new RuntimeException("Fehler beim Update", e);
        }
    }

    @Override
    public void delete(int id) {
        zugriff.deleteUser(id);
    }

    @Override
    public void add(Map<String, String> daten) {
        try {
            int id = Integer.parseInt(daten.get("ID").trim());
            String name = daten.get("Name").trim();
            String vorname = daten.get("Vorname").trim();
            String adresse = daten.get("Adresse").trim();
            String geburtstagStr = daten.get("Geburtstag").trim();
            String fuehrerschein = daten.get("Führerscheininformation").trim();
            boolean status = daten.get("Status").equalsIgnoreCase("frei");
            String email = daten.get("Email").trim();
            String passwort = daten.get("Passwort").trim();

            Date geburtstag = new Date(new SimpleDateFormat("dd.MM.yyyy").parse(geburtstagStr).getTime());
            zugriff.addUser(id, name, vorname, adresse, geburtstag, fuehrerschein, status, email, passwort);
        } catch (Exception e) {
            throw new RuntimeException("Fehler beim Hinzufügen", e);
        }
    }

    @Override
    public String[] getSpaltennamen() {
        return new String[]{
                "ID", "Name", "Vorname", "Adresse", "Geburtstag", "Führerscheininformation", "Status", "Email", "Passwort"
        };
    }
}
