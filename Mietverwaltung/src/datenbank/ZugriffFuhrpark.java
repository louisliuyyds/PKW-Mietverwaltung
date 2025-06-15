package datenbank;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import defaults.Fahrzeug;

public class ZugriffFuhrpark {

    // 1. Datensatz hinzufügen
    public void addFahrzeug(int id_fuhrpark, String kategorie, String fahrzeug, String kennzeichen, String getriebe, int anzahl_sitze, double preis, boolean verfuegbarkeit) {
        String sql = "INSERT INTO fuhrpark (id_fuhrpark, kategorie, fahrzeug, kennzeichen, getriebe, anzahl_sitze, preis, verfuegbarkeit) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = Supabaseverbindung.connect(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id_fuhrpark);
            stmt.setString(2, kategorie);
            stmt.setString(3, fahrzeug);
            stmt.setString(4, kennzeichen);
            stmt.setString(5, getriebe);
            stmt.setInt(6, anzahl_sitze);
            stmt.setDouble(7, preis);
            stmt.setBoolean(8, verfuegbarkeit);
            stmt.executeUpdate();
            System.out.println("Fahrzeug hinzugefügt.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // 2. Datensatz löschen
    public void deleteFahrzeug(int id_fuhrpark) {
        String sql = "DELETE FROM fuhrpark WHERE id_fuhrpark = ?";

        try (Connection conn = Supabaseverbindung.connect(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id_fuhrpark);
            int rows = stmt.executeUpdate();
            if (rows > 0) {
                System.out.println("Fahrzeug gelöscht.");
            } else {
                System.out.println("Kein Fahrzeug mit dieser ID gefunden.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // 3. Datensatz bearbeiten
    public void updateFahrzeug(int id_fuhrpark, String kategorie, String fahrzeug, String kennzeichen, String getriebe, int anzahl_sitze, double preis, boolean verfuegbarkeit) {
        String sql = "UPDATE fuhrpark SET kategorie = ?, fahrzeug = ?, kennzeichen = ?, getriebe = ?, anzahl_sitze = ?, preis = ?, verfuegbarkeit = ? WHERE id_fuhrpark = ?";

        try (Connection conn = Supabaseverbindung.connect(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, kategorie);
            stmt.setString(2, fahrzeug);
            stmt.setString(3, kennzeichen);
            stmt.setString(4, getriebe);
            stmt.setInt(5, anzahl_sitze);
            stmt.setDouble(6, preis);
            stmt.setBoolean(7, verfuegbarkeit);
            stmt.setInt(8, id_fuhrpark);
            int rows = stmt.executeUpdate();
            if (rows > 0) {
                System.out.println("Fahrzeug aktualisiert.");
            } else {
                System.out.println("Kein Fahrzeug mit dieser ID gefunden.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // 4. Alle Datensätze ausgeben
    public void getAlleFahrzeuge() {
        String sql = "SELECT id_fuhrpark, kategorie, fahrzeug, kennzeichen, getriebe, anzahl_sitze, preis, verfuegbarkeit FROM fuhrpark";

        try (Connection conn = Supabaseverbindung.connect();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                int id = rs.getInt("id_fuhrpark");
                String kategorie = rs.getString("kategorie");
                String fahrzeug = rs.getString("fahrzeug");
                String kennzeichen = rs.getString("kennzeichen");
                String getriebe = rs.getString("getriebe");
                int sitze = rs.getInt("anzahl_sitze");
                double preis = rs.getDouble("preis");
                boolean verfuegbar = rs.getBoolean("verfuegbarkeit");

                System.out.println("ID: " + id);
                System.out.println("Kategorie: " + kategorie);
                System.out.println("Fahrzeug: " + fahrzeug);
                System.out.println("Kennzeichen: " + kennzeichen);
                System.out.println("Getriebe: " + getriebe);
                System.out.println("Sitze: " + sitze);
                System.out.println("Preis: " + preis + " EUR");
                System.out.println("Verfügbar: " + (verfuegbar ? "Ja" : "Nein"));
                System.out.println("----------------------------------------");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // 5. Datensatz anhand Fahrzeug-ID ausgeben
    public void getFahrzeug(int id) {
        String sql = "SELECT * FROM fuhrpark WHERE id_fuhrpark = ?";

        try (Connection conn = Supabaseverbindung.connect();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);

            try (ResultSet rs = stmt.executeQuery()) {
                boolean found = false;
                while (rs.next()) {
                    found = true;
                    String kategorie = rs.getString("kategorie");
                    String fahrzeug = rs.getString("fahrzeug");
                    String kennzeichen = rs.getString("kennzeichen");
                    String getriebe = rs.getString("getriebe");
                    int sitze = rs.getInt("anzahl_sitze");
                    double preis = rs.getDouble("preis");
                    boolean verfuegbar = rs.getBoolean("verfuegbarkeit");

                    System.out.println("ID: " + id);
                    System.out.println("Kategorie: " + kategorie);
                    System.out.println("Fahrzeug: " + fahrzeug);
                    System.out.println("Kennzeichen: " + kennzeichen);
                    System.out.println("Getriebe: " + getriebe);
                    System.out.println("Sitze: " + sitze);
                    System.out.println("Preis: " + preis + " EUR");
                    System.out.println("Verfügbar: " + (verfuegbar ? "Ja" : "Nein"));
                    System.out.println("----------------------------------------");
                }

                if (!found) {
                    System.out.println("Kein Fahrzeug mit der ID \"" + id + "\" gefunden.");
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Fahrzeug getFahrzeugObjekt(int id) {
        String sql = "SELECT * FROM fuhrpark WHERE id_fuhrpark = ?";
        try (Connection conn = Supabaseverbindung.connect();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new Fahrzeug(
                    rs.getInt("id_fuhrpark"),
                    rs.getString("kategorie"),
                    rs.getString("fahrzeug"),
                    rs.getString("kennzeichen"),
                    rs.getString("getriebe"),
                    rs.getInt("anzahl_sitze"),
                    rs.getDouble("preis"),
                    rs.getBoolean("verfuegbarkeit")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
