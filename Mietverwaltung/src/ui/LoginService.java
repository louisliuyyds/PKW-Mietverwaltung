package ui;

import javax.swing.*;

public class LoginService {
    public boolean login() {
        String email = JOptionPane.showInputDialog("E-Mail:");
        String passwort = JOptionPane.showInputDialog("Passwort:");

        // Dummy-Login (ersetze mit echter DB-Abfrage)
        if (email.equals("root") && passwort.equals("root")) {
            MitarbeiterUI mn = new MitarbeiterUI();
            mn.start();
            return true;
        }

        if (email.equals("kunde") && passwort.equals("1234")) {
            Session.setUserId(1);
            new KundeUI().start();
            return true;
        }

        JOptionPane.showMessageDialog(null, "Login fehlgeschlagen.");
        return false;
    }
}
