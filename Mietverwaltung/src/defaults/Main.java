package defaults;
import javax.swing.*;
import ui.LoginService;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            String email = JOptionPane.showInputDialog("Bitte E-Mail eingeben:");
            String passwort = JOptionPane.showInputDialog("Bitte Passwort eingeben:");

            LoginService loginService = new LoginService();
            boolean success = loginService.login(email, passwort);

            if (!success) {
                JOptionPane.showMessageDialog(null, "Login fehlgeschlagen. Programm wird beendet.");
                System.exit(0);
            }
        });
    }
}
