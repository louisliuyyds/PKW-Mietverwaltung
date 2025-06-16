package adapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import datenbank.ZugriffExtras;

public class ZugriffExtrasAdapter implements ZugriffInterface {

    private final ZugriffExtras zugriff = new ZugriffExtras();

    @Override
    public List<Object[]> getAlleDaten() {
        List<Object[]> extras = new ArrayList<>();
        String sql = "SELECT id_extras, kategorie, beschreibung, preis FROM extras";

        try (var conn = datenbank.Supabaseverbindung.connect();
             var stmt = conn.prepareStatement(sql);
             var rs = stmt.executeQuery()) {

            while (rs.next()) {
                Object[] row = new Object[4];
                row[0] = rs.getInt("id_extras");
                row[1] = rs.getString("kategorie");
                row[2] = rs.getString("beschreibung");
                row[3] = rs.getDouble("preis");
                extras.add(row);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return extras;
    }

    @Override
    public void update(int id, Map<String, String> daten) {
        try {
            String kategorie = daten.get("Kategorie").trim();
            String beschreibung = daten.get("Beschreibung").trim();
            double preis = Double.parseDouble(daten.get("Preis").trim());

            zugriff.updateExtras(id, kategorie, beschreibung, preis);

        } catch (Exception e) {
            throw new RuntimeException("Fehler beim Aktualisieren des Extras-Datensatzes", e);
        }
    }

    @Override
    public void delete(int id) {
        zugriff.deleteExtra(id);
    }

    @Override
    public void add(Map<String, String> daten) {
        try {
            int id = Integer.parseInt(daten.get("ID").trim());
            String kategorie = daten.get("Kategorie").trim();
            String beschreibung = daten.get("Beschreibung").trim();
            double preis = Double.parseDouble(daten.get("Preis").trim());

            zugriff.addExtras(id, kategorie, beschreibung, preis);

        } catch (Exception e) {
            throw new RuntimeException("Fehler beim Hinzufügen des Extras-Datensatzes", e);
        }
    }

    @Override
    public String[] getSpaltennamen() {
        return new String[] {
            "ID", "Kategorie", "Beschreibung", "Preis"
        };
    }
}
