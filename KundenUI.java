package ui;

import defaults.User;

import javax.swing.*;
import java.awt.*;

/**
 * Einfaches Grundgerüst für das Kunden-Portal.
 * Die konkreten Layout-Elemente solltest du gemäß deiner Skizzen erweitern.
 */
public class KundenUI extends JFrame {

    public static void start(User user) {
        SwingUtilities.invokeLater(() -> new KundenUI(user).setVisible(true));
    }

    public KundenUI(User user) {
        setTitle("Kundenportal – " + user.getVorname());
        setSize(1000, 650);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Sidebar
        JPanel sidebar = new JPanel();
        sidebar.setLayout(new BoxLayout(sidebar, BoxLayout.Y_AXIS));
        sidebar.setPreferredSize(new Dimension(180, 0));

        JButton startBtn   = new JButton("Startseite");
        JButton profilBtn  = new JButton("Profil");
        JButton buchBtn    = new JButton("Meine Buchungen");

        sidebar.add(Box.createVerticalStrut(20));
        sidebar.add(startBtn);
        sidebar.add(Box.createVerticalStrut(5));
        sidebar.add(profilBtn);
        sidebar.add(Box.createVerticalStrut(5));
        sidebar.add(buchBtn);
        sidebar.add(Box.createVerticalGlue());

        // Card-Layout
        JPanel cards = new JPanel(new CardLayout());
        cards.add(buildHomePanel(), "home");
        cards.add(buildProfilPanel(user), "profil");
        cards.add(buildBuchungenPanel(), "buchungen");

        CardLayout cl = (CardLayout) cards.getLayout();
        startBtn.addActionListener(e -> cl.show(cards, "home"));
        profilBtn.addActionListener(e -> cl.show(cards, "profil"));
        buchBtn.addActionListener(e -> cl.show(cards, "buchungen"));

        add(sidebar, BorderLayout.WEST);
        add(cards, BorderLayout.CENTER);
    }

    private JPanel buildHomePanel() {
        JPanel p = new JPanel(new BorderLayout());
        p.add(new JLabel("[FAHRZEUG-GRID GEMÄß SKIZZE]", SwingConstants.CENTER), BorderLayout.CENTER);
        return p;
    }

    private JPanel buildProfilPanel(User user) {
        JPanel p = new JPanel(new GridLayout(0,2,8,4));
        p.add(new JLabel("Vorname:")); p.add(new JLabel(user.getVorname()));
        p.add(new JLabel("Nachname:")); p.add(new JLabel(user.getName()));
        p.add(new JLabel("Adresse:")); p.add(new JLabel(user.getAdresse()));
        p.add(new JLabel("Geburtstag:")); p.add(new JLabel(user.getGeburtstag().toString()));
        p.add(new JLabel("Führerschein:")); p.add(new JLabel(user.getFuehrerscheininformation()));
        p.add(new JLabel("E-Mail:")); p.add(new JLabel(user.getEmail()));
        return p;
    }

    private JPanel buildBuchungenPanel() {
        JPanel p = new JPanel(new BorderLayout());
        p.add(new JLabel("[Buchungs-Übersicht gem. Skizze]", SwingConstants.CENTER), BorderLayout.CENTER);
        return p;
    }
}
