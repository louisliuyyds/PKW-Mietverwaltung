package ui;

import ui.Session;
import ui.MitarbeiterUI;
import datenbank.Supabaseverbindung;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import org.mindrot.jbcrypt.BCrypt;

public class LoginService {

    /**
     * Versucht einen Benutzer anhand von E-Mail und Passwort einzuloggen.
     *
     * @param email die eingegebene E-Mail-Adresse
     * @param plainPassword das eingegebene Passwort im Klartext
     * @return true, wenn Login erfolgreich war; false sonst
     */
    public boolean login(String email, String plainPassword) {
        // Admin-Bypass
        if ("root".equals(email) && "root".equals(plainPassword)) {
            new MitarbeiterUI().start();
            return true;
        }

        String sql = "SELECT password, id_user FROM users WHERE email = ?";

        try (Connection conn = Supabaseverbindung.connect();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, email);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                String storedHash = rs.getString("password");
                int id = rs.getInt("id_user");

                if (BCrypt.checkpw(plainPassword, storedHash)) {
                    Session.setUserId(id);
                    System.out.println("Login erfolgreich. Benutzer-ID: " + id);

                       // new KundeUI().start();
                    

                    return true;
                } else {
                    JOptionPane.showMessageDialog(null, "Falsches Passwort.");
                    return false;
                }
            } else {
                JOptionPane.showMessageDialog(null, "Benutzer nicht gefunden.");
                return false;
            }

        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Verbindungsfehler zur Datenbank.");
            return false;
        }
    }
}
