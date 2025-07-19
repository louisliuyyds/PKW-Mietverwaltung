package service;

import adapter.ZugriffUserAdapter;
import defaults.User;
import ui.KundenUI;
import ui.MitarbeiterUI;

import javax.swing.*;
import java.awt.*;

public class LoginService extends JFrame {

    private final ZugriffUserAdapter zugriff = new ZugriffUserAdapter();

    private JTextField emailField;
    private JPasswordField passwordField;

    public LoginService() {
        setTitle("Login");
        setSize(400, 250);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        add(createLoginPanel(), BorderLayout.CENTER);
        setVisible(true);
    }

    private JPanel createLoginPanel() {
        JPanel panel = new JPanel(new GridLayout(3, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));

        emailField = new JTextField();
        passwordField = new JPasswordField();

        JButton loginBtn = new JButton("Login");
        JButton registerBtn = new JButton("Registrieren");

        loginBtn.addActionListener(e -> handleLogin());
        registerBtn.addActionListener(e -> openRegisterDialog());

        panel.add(new JLabel("E-Mail:"));
        panel.add(emailField);

        panel.add(new JLabel("Passwort:"));
        panel.add(passwordField);

        panel.add(loginBtn);
        panel.add(registerBtn);

        return panel;
    }

    private void handleLogin() {
        String email = emailField.getText().trim();
        String passwort = new String(passwordField.getPassword());

        if (authenticate(email, passwort)) {
            User user = Session.getUser();

            // Fenster schließen
            dispose();

            // Weiterleitung: root -> Mitarbeiter, sonst -> Kunde
            if ("root".equalsIgnoreCase(user.getEmail())) {
                SwingUtilities.invokeLater(MitarbeiterUI::start);
            } else {   
                SwingUtilities.invokeLater(() -> new KundenUI(user).setVisible(true));
            }

        } else {
            JOptionPane.showMessageDialog(this, "Login fehlgeschlagen!", "Fehler", JOptionPane.ERROR_MESSAGE);
        }
    }

    private boolean authenticate(String email, String plainPassword) {
        User user = zugriff.getUserByEmail(email);
        if (user == null) return false;

        if (!org.mindrot.jbcrypt.BCrypt.checkpw(plainPassword, user.getPassworthash())) return false;

        Session.setUser(user);
        return true;
    }

    private void openRegisterDialog() {
        new RegisterDialog();
    }
}
