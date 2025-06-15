package datenbank;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.mindrot.jbcrypt.BCrypt;

public class ZugriffUser {

    // 1. Datensatz hinzufügen
    public void addUser(int id, String name, String vorname, String adresse, Date geburtstag, String fuehrerscheininformation, boolean status, String email, String plainPassword) {
        String sql = "INSERT INTO users ( name, vorname, adresse, geburtstag, fuehrerscheininformation, status, email, password) VALUES ( ?, ?, ?, ?, ?, ?,?,?)";
        
        try (Connection conn = Supabaseverbindung.connect(); 
             PreparedStatement stmt = conn.prepareStatement(sql)) {
        	String hashed = BCrypt.hashpw(plainPassword, BCrypt.gensalt());
           
            stmt.setString(1, name);
            stmt.setString(2, vorname);
            stmt.setString(3, adresse);
            stmt.setDate(4,geburtstag);
            stmt.setString(5, fuehrerscheininformation);
            stmt.setBoolean(6, status);
            stmt.setString(7, email);
            stmt.setString(8, hashed);

            stmt.executeUpdate();
            System.out.println("Benutzer erfolgreich hinzugefügt.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // 2. Datensatz löschen
    public void deleteUser(int id) {
        String sql = "DELETE FROM users WHERE id_user = ?";

        try (Connection conn = Supabaseverbindung.connect(); 
             PreparedStatement stmt = conn.prepareStatement(sql)) {
             
            stmt.setInt(1, id);
            int rows = stmt.executeUpdate();
            if (rows > 0) {
                System.out.println("Benutzer gelöscht.");
            } else {
                System.out.println("Kein Benutzer mit dieser ID gefunden.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // 3. Datensatz bearbeiten
    public void updateUser(int id, String name, String vorname, String adresse, Date geburtstag, String fuehrerscheininformation, boolean status) {
        String sql = "UPDATE users SET name = ?, vorname = ?, adresse = ?, geburtstag = ?, fuehrerscheininformation = ?, status = ? WHERE id_user = ?";

        try (Connection conn = Supabaseverbindung.connect(); 
             PreparedStatement stmt = conn.prepareStatement(sql)) {
             
            stmt.setString(1, name);
            stmt.setString(2, vorname);
            stmt.setString(3, adresse);
            stmt.setDate(4, geburtstag);
            stmt.setString(5, fuehrerscheininformation);
            stmt.setBoolean(6, status);
            stmt.setInt(7, id);

            int rows = stmt.executeUpdate();
            if (rows > 0) {
                System.out.println("Benutzer aktualisiert.");
            } else {
                System.out.println("Kein Benutzer mit dieser ID gefunden.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // 4. Alle Datensätze ausgeben
    public void getAllUsers() {
        String sql = "SELECT id_user, name, vorname, adresse, geburtstag, fuehrerscheininformation, status FROM users";

        try (Connection conn = Supabaseverbindung.connect(); 
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                int id = rs.getInt("id_user");
                String name = rs.getString("name");
                String vorname = rs.getString("vorname");
                String adresse = rs.getString("adresse");
                Date geburtstag = rs.getDate("geburtstag");
                String fuehrerschein = rs.getString("fuehrerscheininformation");
                boolean status = rs.getBoolean("status");

                System.out.println("ID: " + id);
                System.out.println("Name: " + name);
                System.out.println("Vorname: " + vorname);
                System.out.println("Adresse: " + adresse);
                System.out.println("Geburtstag: " + geburtstag);
                System.out.println("Führerschein: " + fuehrerschein);
                System.out.println("Status: " + (status ? "Aktiv" : "Inaktiv"));
                System.out.println("----------------------------------------");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // 5. Datensatz anhand des Namens ausgeben
    public void getUsersByName(String vorname, String name) {
        String sql = "SELECT id_user, name, vorname, adresse, geburtstag, fuehrerscheininformation, status FROM users WHERE name = ? AND vorname = ?";

        try (Connection conn = Supabaseverbindung.connect(); 
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, name);
            stmt.setString(2, vorname);

            try (ResultSet rs = stmt.executeQuery()) {
                boolean found = false;
                while (rs.next()) {
                    found = true;
                    int id = rs.getInt("id_user");
                    String adresse = rs.getString("adresse");
                    Date geburtstag = rs.getDate("geburtstag");
                    String fuehrerschein = rs.getString("fuehrerscheininformation");
                    boolean status = rs.getBoolean("status");

                    System.out.println("ID: " + id);
                    System.out.println("Name: " + name);
                    System.out.println("Vorname: " + vorname);
                    System.out.println("Adresse: " + adresse);
                    System.out.println("Geburtstag: " + geburtstag);
                    System.out.println("Führerschein: " + fuehrerschein);
                    System.out.println("Status: " + (status ? "Aktiv" : "Inaktiv"));
                    System.out.println("----------------------------------------");
                }

                if (!found) {
                    System.out.println("Kein Benutzer mit dem Namen \"" + name + "\" gefunden.");
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public List<Object[]> getAllUsersData() {
        List<Object[]> resultList = new ArrayList<>();
        String sql = "SELECT id_user, name, vorname, adresse, geburtstag, fuehrerscheininformation, status, email, password FROM users";

        try (Connection conn = Supabaseverbindung.connect();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Object[] row = new Object[9];
                row[0] = rs.getInt("id_user");
                row[1] = rs.getString("name");
                row[2] = rs.getString("vorname");
                row[3] = rs.getString("adresse");
                row[4] = rs.getDate("geburtstag");
                row[5] = rs.getString("fuehrerscheininformation");
                row[6] = rs.getBoolean("status") ? "Aktiv" : "Inaktiv";
                row[7] = rs.getString("email");
                row[8] = rs.getString("password");
                resultList.add(row);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return resultList;
    }
}
