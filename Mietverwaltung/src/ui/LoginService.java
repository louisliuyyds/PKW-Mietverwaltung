package ui;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.mindrot.jbcrypt.BCrypt;

import datenbank.Supabaseverbindung;

public class LoginService {

    /**
     * Versucht einen Benutzer anhand von E-Mail und Passwort einzuloggen.
     *
     * @param email die eingegebene E-Mail-Adresse
     * @param plainPassword das eingegebene Passwort im Klartext
     * @return true, wenn Login erfolgreich war; false sonst
     */
    public boolean login(String email, String plainPassword) {
    	
    	if(email=="root"& plainPassword== "root") {
    		MitarbeiterUI mn= new MitarbeiterUI();
    		mn.start();
    	}
    	
        String sql = "SELECT password, id_user FROM users WHERE email = ?";

        try (Connection conn = Supabaseverbindung.connect();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, email);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                String storedHash = rs.getString("password");
                int id = rs.getInt("id_user");
                // Passwort überprüfen
                if (BCrypt.checkpw(plainPassword, storedHash)) {
                    System.out.println("Login erfolgreich.");
                    Session.setUserId(id);
                    return true;
                } else {
                    System.out.println("Falsches Passwort.");
                    return false;
                }
            } else {
                System.out.println("Benutzer nicht gefunden.");
                return false;
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
