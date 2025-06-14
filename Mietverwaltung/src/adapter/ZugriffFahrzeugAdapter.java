package adapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import datenbank.ZugriffFuhrpark;

public class ZugriffFahrzeugAdapter implements ZugriffInterface {

    private final ZugriffFuhrpark zugriff = new ZugriffFuhrpark();

    @Override
    public List<Object[]> getAlleDaten() {
        List<Object[]> fahrzeuge = new ArrayList<>();
        String sql = "SELECT id_fuhrpark, kategorie, fahrzeug, kennzeichen, getriebe, anzahl_sitze, preis, verfuegbarkeit FROM fuhrpark";

        try (var conn = datenbank.Supabaseverbindung.connect();
             var stmt = conn.prepareStatement(sql);
             var rs = stmt.executeQuery()) {

            while (rs.next()) {
                Object[] row = new Object[8];
                row[0] = rs.getInt("id_fuhrpark");
                row[1] = rs.getString("kategorie");
                row[2] = rs.getString("fahrzeug");
                row[3] = rs.getString("kennzeichen");
                row[4] = rs.getString("getriebe");
                row[5] = rs.getInt("anzahl_sitze");
                row[6] = rs.getDouble("preis");
                row[7] = rs.getBoolean("verfuegbarkeit") ? "Ja" : "Nein";
                fahrzeuge.add(row);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return fahrzeuge;
    }

    @Override
    public void update(int id, Map<String, String> daten) {
        try {
            String kategorie = daten.get("Kategorie").trim();
            String fahrzeug = daten.get("Fahrzeug").trim();
            String kennzeichen = daten.get("Kennzeichen").trim();
            String getriebe = daten.get("Getriebe").trim();
            int sitze = Integer.parseInt(daten.get("Anzahl Sitze").trim());
            double preis = Double.parseDouble(daten.get("Preis").trim());
            boolean verfuegbar = daten.get("Verfügbarkeit").equalsIgnoreCase("ja");

            zugriff.updateFahrzeug(id, kategorie, fahrzeug, kennzeichen, getriebe, sitze, preis, verfuegbar);

        } catch (Exception e) {
            throw new RuntimeException("Fehler beim Aktualisieren des Fahrzeugs", e);
        }
    }

    @Override
    public void delete(int id) {
        zugriff.deleteFahrzeug(id);
    }

    @Override
    public void add(Map<String, String> daten) {
        try {
            int id = Integer.parseInt(daten.get("ID").trim());
            String kategorie = daten.get("Kategorie").trim();
            String fahrzeug = daten.get("Fahrzeug").trim();
            String kennzeichen = daten.get("Kennzeichen").trim();
            String getriebe = daten.get("Getriebe").trim();
            int sitze = Integer.parseInt(daten.get("Anzahl Sitze").trim());
            double preis = Double.parseDouble(daten.get("Preis").trim());
            boolean verfuegbar = daten.get("Verfügbarkeit").equalsIgnoreCase("ja");

            zugriff.addFahrzeug(id, kategorie, fahrzeug, kennzeichen, getriebe, sitze, preis, verfuegbar);

        } catch (Exception e) {
            throw new RuntimeException("Fehler beim Hinzufügen des Fahrzeugs", e);
        }
    }

    @Override
    public String[] getSpaltennamen() {
        return new String[] {
            "ID", "Kategorie", "Fahrzeug", "Kennzeichen", "Getriebe", "Anzahl Sitze", "Preis", "Verfügbarkeit"
        };
    }
}

