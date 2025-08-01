package service;

import adapter.ZugriffUserAdapter;
import defaults.User;
import ui.KundenUI;
import ui.MitarbeiterUI;

import javax.swing.*;

public class LoginService {

    private final ZugriffUserAdapter zugriff = new ZugriffUserAdapter();

    public boolean authenticate(String email, String plainPassword) {
        User user = zugriff.getUserByEmail(email);
        if (user == null) return false;

        if (!org.mindrot.jbcrypt.BCrypt.checkpw(plainPassword, user.getPassworthash())) return false;

        Session.setUser(user);
        return true;
    }

    public void proceedAfterLogin(JFrame frame, String email) {
        User user = Session.getUser();
        frame.dispose();

        if ("root".equalsIgnoreCase(email)) {
            SwingUtilities.invokeLater(MitarbeiterUI::start);
        } else {
            SwingUtilities.invokeLater(() -> new KundenUI(user).setVisible(true));
        }
    }
}
