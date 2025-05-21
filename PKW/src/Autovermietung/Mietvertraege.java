package Autovermietung;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Mietvertraege {

    // 1. Datensatz hinzufügen
	public void addVertrag(int id, Date startdatum, Date enddatum, int id_user, int id_fuhrpark, int id_extras, double gesamtpreis) {
	    String sql = "INSERT INTO mietvertraege (id, startdatum, enddatum, id_user, id_fuhrpark, id_extras, gesamtpreis) VALUES (?, ?, ?, ?, ?, ?, ?)";

	    try (Connection conn = Connector.connect(); PreparedStatement stmt = conn.prepareStatement(sql)) {
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

        try (Connection conn = Connector.connect(); PreparedStatement stmt = conn.prepareStatement(sql)) {
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
        String sql = "UPDATE mietvertraege SET startdatum = ?, enddatum = ?, id_user = ?, id_fuhrpark=?, id_extras=?,gesamtpreis=? WHERE id = ?";

        try (Connection conn = Connector.connect(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setDate(1, startdatum);
            stmt.setDate(2, enddatum);
            stmt.setInt(3, id_user);
            stmt.setInt(3, id_fuhrpark);
            stmt.setInt(3, id_extras);
            stmt.setDouble(3, gesamtpreis);
            stmt.setInt(4, id);
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
    public void getUserById(int id) {
        String sql = "SELECT * FROM mietvertrage WHERE id = ?";

        try (Connection conn = Connector.connect(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);

            var rs = stmt.executeQuery();

            if (rs.next()) {
                int userId = rs.getInt("id");
                String name = rs.getString("name");
                String vorname = rs.getString("vorname");
                int age = rs.getInt("age");

                System.out.println("Benutzer gefunden:");
                System.out.println("ID: " + userId);
                System.out.println("Name: " + name);
                System.out.println("Vorname: " + vorname);
                System.out.println("Alter: " + age);
            } else {
                System.out.println("Kein Benutzer mit ID " + id + " gefunden.");
            }

            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
