package datenbank;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ZugriffVertraege {

    // 1. Datensatz hinzufügen
    public void addVertrag(int id, Date startdatum, Date enddatum, int id_user, int id_fuhrpark, int id_extras, double gesamtpreis) {
        String sql = "INSERT INTO mietvertraege (id, startdatum, enddatum, id_users, id_fuhrparks, id_extra, gesamtpreis) VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = Supabaseverbindung.connect(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.setDate(2, startdatum);
            stmt.setDate(3, enddatum);
            stmt.setInt(4, id_user);
            stmt.setInt(5, id_fuhrpark);
            stmt.setInt(6, id_extras);
            stmt.setDouble(7, gesamtpreis);

            stmt.executeUpdate();
            System.out.println("Vertrag erfolgreich hinzugefügt.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // 2. Datensatz löschen
    public void deleteVertrag(int id) {
        String sql = "DELETE FROM mietvertraege WHERE id = ?";

        try (Connection conn = Supabaseverbindung.connect(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            int rows = stmt.executeUpdate();
            if (rows > 0) {
                System.out.println("Vertrag gelöscht.");
            } else {
                System.out.println("Kein Vertrag mit dieser ID gefunden.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // 3. Datensatz bearbeiten
    public void updateVertrag(int id, Date startdatum, Date enddatum, int id_user, int id_fuhrpark, int id_extras, double gesamtpreis) {
        String sql = "UPDATE mietvertraege SET startdatum = ?, enddatum = ?, id_users = ?, id_fuhrparks = ?, id_extra = ?, gesamtpreis = ? WHERE id = ?";

        try (Connection conn = Supabaseverbindung.connect(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setDate(1, startdatum);
            stmt.setDate(2, enddatum);
            stmt.setInt(3, id_user);
            stmt.setInt(4, id_fuhrpark);
            stmt.setInt(5, id_extras);
            stmt.setDouble(6, gesamtpreis);
            stmt.setInt(7, id);
            int rows = stmt.executeUpdate();
            if (rows > 0) {
                System.out.println("Vertrag aktualisiert.");
            } else {
                System.out.println("Kein Vertrag mit dieser ID gefunden.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void getVertragById(int id) {
        String sql = "SELECT * FROM mietvertraege WHERE id = ?";

        try (Connection conn = Supabaseverbindung.connect(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);

            var rs = stmt.executeQuery();

            if (rs.next()) {
                int userId = rs.getInt("id");
                // Hier: Spalten "name", "vorname", "age" existieren vermutlich nicht in mietvertraege
                // Wenn du Nutzerinfos aus einer anderen Tabelle brauchst, musst du JOINs verwenden
                System.out.println("Vertrag gefunden:");
                System.out.println("ID: " + userId);
                // Beispiel-Ausgabe, falls passende Spalten da wären:
                // System.out.println("Name: " + rs.getString("name"));
                // System.out.println("Vorname: " + rs.getString("vorname"));
                // System.out.println("Alter: " + rs.getInt("age"));
            } else {
                System.out.println("Kein Vertrag mit ID " + id + " gefunden.");
            }

            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
