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

        Color normalColor = new Color(66, 66, 66);
        Color hoverColor = new Color(44, 44, 44);
        Color foregroundColor = new Color(204, 204, 204);

        // Buttons erstellen
        JButton loginBtn = new JButton("Login");
        styleSidebarButton(loginBtn, normalColor, hoverColor, foregroundColor);
        JButton regBtn = new JButton("Registrieren");
        styleSidebarButton(regBtn, normalColor, hoverColor, foregroundColor);

        // Titel-Label
        JLabel willkommen = new JLabel("Willkommen bei der Fahrzeugvermietung");
        willkommen.setForeground(foregroundColor);
        willkommen.setAlignmentX(Component.CENTER_ALIGNMENT);

        // ActionListener
        loginBtn.addActionListener(e -> {
            dispose();
            new LoginService();
        });

        regBtn.addActionListener(e -> {
            dispose();
            RegisterDialog.showRegisterDialog();
        });

        // Inhaltspanel mit vertikalem Layout
        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        contentPanel.setBackground(normalColor);
        contentPanel.setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40));

        contentPanel.add(willkommen);
        contentPanel.add(Box.createVerticalStrut(20));
        contentPanel.add(loginBtn);
        contentPanel.add(Box.createVerticalStrut(10));
        contentPanel.add(regBtn);

        // Wrapper-Panel zum Zentrieren
        JPanel centerPanel = new JPanel(new GridBagLayout());
        centerPanel.setBackground(normalColor);
        centerPanel.add(contentPanel);

        add(centerPanel, BorderLayout.CENTER);
    }

    private void styleSidebarButton(JButton button, Color normalColor, Color hoverColor, Color foregroundColor) {
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        button.setBackground(normalColor);
        button.setForeground(foregroundColor);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        button.setBorder(BorderFactory.createEmptyBorder(5, 15, 5, 0));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(hoverColor);
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(normalColor);
            }
        });
    }
}
