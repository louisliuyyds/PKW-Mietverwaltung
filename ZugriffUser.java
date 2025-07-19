package datenbank;

import org.mindrot.jbcrypt.BCrypt;

import defaults.User;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ZugriffUser {

    public void addUser(User user, String plainPassword) {
        String sql = "INSERT INTO users (name, vorname, adresse, geburtstag, fuehrerscheininformation, status, email, passwort) " +
                     "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = Supabaseverbindung.connect();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            String hashed = BCrypt.hashpw(plainPassword, BCrypt.gensalt());

            stmt.setString(1, user.getName());
            stmt.setString(2, user.getVorname());
            stmt.setString(3, user.getAdresse());
            stmt.setDate(4, Date.valueOf(user.getGeburtstag()));
            stmt.setString(5, user.getFuehrerscheininformation());
            stmt.setBoolean(6, user.isStatus());
            stmt.setString(7, user.getEmail());
            stmt.setString(8, hashed);

            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteUser(int id) {
        String sql = "DELETE FROM users WHERE id_user = ?";
        try (Connection conn = Supabaseverbindung.connect();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateUser(User user) {
        String sql = "UPDATE users SET name = ?, vorname = ?, adresse = ?, geburtstag = ?, fuehrerscheininformation = ?, status = ?, email = ? WHERE id_user = ?";
        try (Connection conn = Supabaseverbindung.connect();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, user.getName());
            stmt.setString(2, user.getVorname());
            stmt.setString(3, user.getAdresse());
            stmt.setDate(4, Date.valueOf(user.getGeburtstag()));
            stmt.setString(5, user.getFuehrerscheininformation());
            stmt.setBoolean(6, user.isStatus());
            stmt.setString(7, user.getEmail());
            stmt.setInt(8, user.getId());

            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        String sql = "SELECT * FROM users";

        try (Connection conn = Supabaseverbindung.connect();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                users.add(mapResultSetToUser(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return users;
    }

    public User getUserById(int id) {
        String sql = "SELECT * FROM users WHERE id_user = ?";
        try (Connection conn = Supabaseverbindung.connect();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return mapResultSetToUser(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<User> getUsersByName(String vorname, String name) {
        List<User> users = new ArrayList<>();
        String sql = "SELECT * FROM users WHERE LOWER(name) LIKE ? AND LOWER(vorname) LIKE ?";

        try (Connection conn = Supabaseverbindung.connect();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, "%" + name.toLowerCase() + "%");
            stmt.setString(2, "%" + vorname.toLowerCase() + "%");

            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                users.add(mapResultSetToUser(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return users;
    }
    
    public User getUserByEmail(String email) {
        String sql = "SELECT * FROM users WHERE email = ?";
        try (Connection conn = Supabaseverbindung.connect();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, email);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return new User(
                    rs.getInt("id_user"),
                    rs.getString("name"),
                    rs.getString("vorname"),
                    rs.getString("adresse"),
                    rs.getDate("geburtstag").toLocalDate(),
                    rs.getString("fuehrerscheininformation"),
                    rs.getBoolean("status"),
                    rs.getString("email"),
                    rs.getString("passwort")
                );
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    private User mapResultSetToUser(ResultSet rs) throws SQLException {
        return new User(
            rs.getInt("id_user"),
            rs.getString("name"),
            rs.getString("vorname"),
            rs.getString("adresse"),
            rs.getDate("geburtstag").toLocalDate(),
            rs.getString("fuehrerscheininformation"),
            rs.getBoolean("status"),
            rs.getString("email"),
            rs.getString("passwort")
        );
    }
    
    public void upgradePasswortHashesFallsNoetig() {
        String selectSQL = "SELECT id_user, passwort FROM users";
        String updateSQL = "UPDATE users SET passwort = ? WHERE id_user = ?";

        try (Connection conn = Supabaseverbindung.connect();
             PreparedStatement selectStmt = conn.prepareStatement(selectSQL);
             PreparedStatement updateStmt = conn.prepareStatement(updateSQL);
             ResultSet rs = selectStmt.executeQuery()) {

            while (rs.next()) {
                int id = rs.getInt("id_user");
                String passwort = rs.getString("passwort");

                // Prüfe, ob es sich bereits um einen gültigen BCrypt-Hash handelt
                if (!passwort.startsWith("$2a$")) {
                    // Hash erzeugen und aktualisieren
                    String hashed = org.mindrot.jbcrypt.BCrypt.hashpw(passwort, org.mindrot.jbcrypt.BCrypt.gensalt());

                    updateStmt.setString(1, hashed);
                    updateStmt.setInt(2, id);
                    updateStmt.executeUpdate();

                    System.out.println("Passwort von User-ID " + id + " wurde aktualisiert.");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

} 
