package datenbank;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ZugriffExtras {

    // 1. Datensatz hinzufügen
    public void addExtras(int id_extras, String kategorie, String beschreibung, double preis) {
        String sql = "INSERT INTO extras (id_extras, kategorie, beschreibung, preis) VALUES (?, ?, ?, ?)";

        try (Connection conn = Supabaseverbindung.connect(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id_extras);
            stmt.setString(2, kategorie);
            stmt.setString(3, beschreibung);
            stmt.setDouble(4, preis);
            stmt.executeUpdate();
            System.out.println("Extras hinzugefügt.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // 2. Datensatz löschen
    public void deleteExtra(int id_extras) {
        String sql = "DELETE FROM extras WHERE id_extras = ?";

        try (Connection conn = Supabaseverbindung.connect(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id_extras);
            int rows = stmt.executeUpdate();
            if (rows > 0) {
                System.out.println("Extras gelöscht.");
            } else {
                System.out.println("Keine Extras mit dieser ID gefunden.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // 3. Datensatz bearbeiten
    public void updateExtras(int id_extras, String kategorie, String beschreibung, double preis) {
        String sql = "UPDATE extras SET kategorie = ?, beschreibung = ?, preis = ? WHERE id_extras = ?";

        try (Connection conn = Supabaseverbindung.connect(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, kategorie);
            stmt.setString(2, beschreibung);
            stmt.setDouble(3, preis);
            stmt.setInt(4, id_extras);
            int rows = stmt.executeUpdate();
            if (rows > 0) {
                System.out.println("Extras aktualisiert.");
            } else {
                System.out.println("Keine Extras mit dieser ID gefunden.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // 4. Alle Datensätze ausgeben
    public void getAllExtras() {
        String sql = "SELECT id_extras, kategorie, beschreibung, preis FROM extras";

        try (Connection conn = Supabaseverbindung.connect();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                int id = rs.getInt("id_extras");
                String kategorie = rs.getString("kategorie");
                String beschreibung = rs.getString("beschreibung");
                double preis = rs.getDouble("preis");

                System.out.println("ID: " + id);
                System.out.println("Kategorie: " + kategorie);
                System.out.println("Beschreibung: " + beschreibung);
                System.out.println("Preis: " + preis + " EUR");
                System.out.println("----------------------------------------");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // 5. Datensätze anhand der Kategorie und Beschreibung ausgeben
    public void getExtras(String kategorie, String beschreibung) {
        String sql = "SELECT id_extras, kategorie, beschreibung, preis FROM extras WHERE kategorie = ? AND beschreibung = ?";

        try (Connection conn = Supabaseverbindung.connect();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, kategorie);
            stmt.setString(2, beschreibung);

            try (ResultSet rs = stmt.executeQuery()) {
                boolean found = false;
                while (rs.next()) {
                    found = true;
                    int id = rs.getInt("id_extras");
                    double preis = rs.getDouble("preis");

                    System.out.println("ID: " + id);
                    System.out.println("Kategorie: " + kategorie);
                    System.out.println("Beschreibung: " + beschreibung);
                    System.out.println("Preis: " + preis + " EUR");
                    System.out.println("----------------------------------------");
                }

                if (!found) {
                    System.out.println("Kein Extra mit Kategorie \"" + kategorie + "\" und Beschreibung \"" + beschreibung + "\" gefunden.");
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
