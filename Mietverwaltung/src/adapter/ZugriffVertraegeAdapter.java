package adapter;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import datenbank.ZugriffVertraege;

public class ZugriffVertraegeAdapter implements ZugriffInterface {

    private final ZugriffVertraege zugriff = new ZugriffVertraege();

    @Override
    public List<Object[]> getAlleDaten() {
        List<Object[]> vertraege = new ArrayList<>();
        String sql = "SELECT id, startdatum, enddatum, id_users, id_fuhrparks, id_extra, gesamtpreis FROM mietvertraege";

        try (var conn = datenbank.Supabaseverbindung.connect();
             var stmt = conn.prepareStatement(sql);
             var rs = stmt.executeQuery()) {

            while (rs.next()) {
                Object[] row = new Object[7];
                row[0] = rs.getInt("id");
                row[1] = rs.getDate("startdatum");
                row[2] = rs.getDate("enddatum");
                row[3] = rs.getInt("id_users");
                row[4] = rs.getInt("id_fuhrparks");
                row[5] = rs.getInt("id_extra");
                row[6] = rs.getDouble("gesamtpreis");
                vertraege.add(row);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return vertraege;
    }

    @Override
    public void update(int id, Map<String, String> daten) {
        try {
            Date start = parseDate(daten.get("Startdatum"));
            Date end = parseDate(daten.get("Enddatum"));
            int userId = Integer.parseInt(daten.get("User-ID").trim());
            int fahrzeugId = Integer.parseInt(daten.get("Fahrzeug-ID").trim());
            int extrasId = Integer.parseInt(daten.get("Extras-ID").trim());
            double gesamtpreis = Double.parseDouble(daten.get("Gesamtpreis").trim());

            zugriff.updateVertrag(id, start, end, userId, fahrzeugId, extrasId, gesamtpreis);

        } catch (Exception e) {
            throw new RuntimeException("Fehler beim Aktualisieren des Vertrags", e);
        }
    }

    @Override
    public void delete(int id) {
        zugriff.deleteVertrag(id);
    }

    @Override
    public void add(Map<String, String> daten) {
        try {
            int id = Integer.parseInt(daten.get("ID").trim());
            Date start = parseDate(daten.get("Startdatum"));
            Date end = parseDate(daten.get("Enddatum"));
            int userId = Integer.parseInt(daten.get("User-ID").trim());
            int fahrzeugId = Integer.parseInt(daten.get("Fahrzeug-ID").trim());
            int extrasId = Integer.parseInt(daten.get("Extras-ID").trim());
            double gesamtpreis = Double.parseDouble(daten.get("Gesamtpreis").trim());

            zugriff.addVertrag(id, start, end, userId, fahrzeugId, extrasId, gesamtpreis);

        } catch (Exception e) {
            throw new RuntimeException("Fehler beim Hinzufügen des Vertrags", e);
        }
    }

    @Override
    public String[] getSpaltennamen() {
        return new String[] {
            "ID", "Startdatum", "Enddatum", "User-ID", "Fahrzeug-ID", "Extras-ID", "Gesamtpreis"
        };
    }

    private Date parseDate(String input) throws Exception {
        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
        return new Date(sdf.parse(input.trim()).getTime());
    }
}

