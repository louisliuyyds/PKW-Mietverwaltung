package ui;

import service.LoginService;
import service.RegisterDialog;

import javax.swing.*;
import java.awt.*;

/**
 * Großes Startfenster mit den Optionen Login oder Registrieren.
 */
public class StartUI extends JFrame {

    public StartUI() {
        setTitle("Fahrzeugvermietung – Willkommen");
        setSize(450, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        JButton loginBtn = new JButton("Login");
        JButton regBtn   = new JButton("Registrieren");

        loginBtn.addActionListener(e -> {
            // Fenster schließen, LoginService als eigenes Fenster öffnen
            dispose();
            new LoginService();
        });

        regBtn.addActionListener(e -> {
            dispose();
            RegisterDialog.showRegisterDialog();
        });

        JPanel p = new JPanel(new GridLayout(3, 1, 10, 10));
        p.setBorder(BorderFactory.createEmptyBorder(30, 40, 30, 40));
        p.add(new JLabel("Willkommen bei der Fahrzeugvermietung", SwingConstants.CENTER));
        p.add(loginBtn);
        p.add(regBtn);

        add(p, BorderLayout.CENTER);
    }
}
