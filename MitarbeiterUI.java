package ui;

import javax.swing.*;
import java.awt.*;

import adapter.*;
import defaults.*;

public class MitarbeiterUI extends JFrame {

    private CardLayout cardLayout;
    private JPanel contentPanel;

    public MitarbeiterUI() {
        setTitle("Mitarbeiterverwaltung");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000, 600);
        setLocationRelativeTo(null);

        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(createNavigationPanel(), BorderLayout.WEST);
        getContentPane().add(createContentPanel(), BorderLayout.CENTER);
    }

    private JPanel createNavigationPanel() {
        JPanel navPanel = new JPanel();
        navPanel.setLayout(new BoxLayout(navPanel, BoxLayout.Y_AXIS));
        navPanel.setBackground(new Color(230, 230, 230));
        navPanel.setPreferredSize(new Dimension(150, 0));

        JButton btnStartseite = new JButton("Startseite");
        btnStartseite.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnStartseite.addActionListener(e -> showCard("home"));

        JButton btnKunden = new JButton("Kunden");
        btnKunden.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnKunden.addActionListener(e -> showCard("kunden"));

        JButton btnFahrzeuge = new JButton("Fahrzeuge");
        btnFahrzeuge.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnFahrzeuge.addActionListener(e -> showCard("fahrzeuge"));

        JButton btnExtras = new JButton("Extras");
        btnExtras.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnExtras.addActionListener(e -> showCard("extras"));

        JButton btnVertraege = new JButton("Verträge");
        btnVertraege.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnVertraege.addActionListener(e -> showCard("vertraege"));

        navPanel.add(Box.createVerticalStrut(20));
        navPanel.add(btnStartseite);
        navPanel.add(Box.createVerticalStrut(10));
        navPanel.add(btnKunden);
        navPanel.add(Box.createVerticalStrut(10));
        navPanel.add(btnFahrzeuge);
        navPanel.add(Box.createVerticalStrut(10));
        navPanel.add(btnExtras);
        navPanel.add(Box.createVerticalStrut(10));
        navPanel.add(btnVertraege);
        navPanel.add(Box.createVerticalGlue());

        return navPanel;
    }

    private JPanel createContentPanel() {
        contentPanel = new JPanel();
        cardLayout = new CardLayout();
        contentPanel.setLayout(cardLayout);

        JPanel homePanel = new JPanel(new BorderLayout());
        JLabel lblHome = new JLabel("Willkommen", SwingConstants.CENTER);
        lblHome.setFont(new Font("Arial", Font.BOLD, 24));
        homePanel.add(lblHome, BorderLayout.CENTER);

        ZugriffUserAdapter userAdapter = new ZugriffUserAdapter();
        ZugriffFahrzeugAdapter fahrzeugAdapter = new ZugriffFahrzeugAdapter();
        ZugriffExtrasAdapter extrasAdapter = new ZugriffExtrasAdapter();
        ZugriffVertraegeAdapter vertragAdapter = new ZugriffVertraegeAdapter();

        JPanel kundenPanel = new DatenverwaltungsPanel<User>(
            userAdapter,
            userAdapter::fromMap,
            u -> new Object[]{ u.getId(), u.getName(), u.getVorname(), u.getAdresse(), u.getGeburtstag(), u.getFuehrerscheininformation(), u.isStatus(), u.getEmail(), u.getPassworthash() }
        );

        JPanel fahrzeugePanel = new DatenverwaltungsPanel<Fahrzeug>(
            fahrzeugAdapter,
            fahrzeugAdapter::fromMap,
            f -> new Object[]{ f.getId(), f.getKategorie(), f.getMarke(), f.getModell(), f.getKennzeichen(), f.getGetriebe(), f.getAnzahlSitze(), f.getTreibstoff(), f.getPreis(), f.isVerfuegbar() }
        );

        JPanel extrasPanel = new DatenverwaltungsPanel<Extras>(
            extrasAdapter,
            extrasAdapter::fromMap,
            x -> new Object[]{ x.getId(), x.getBezeichnung(), x.getPreis() }
        );

        JPanel vertraegePanel = new DatenverwaltungsPanel<Mietvertrag>(
            vertragAdapter,
            vertragAdapter::fromMap,
            v -> new Object[]{ v.getId(), v.getStartdatum(), v.getEnddatum(), v.getUserId(), v.getFahrzeugId(), v.getExtrasId(), v.getGesamtpreis() }
        );

        contentPanel.add(homePanel, "home");
        contentPanel.add(kundenPanel, "kunden");
        contentPanel.add(fahrzeugePanel, "fahrzeuge");
        contentPanel.add(extrasPanel, "extras");
        contentPanel.add(vertraegePanel, "vertraege");

        cardLayout.show(contentPanel, "home");
        return contentPanel;
    }

    private void showCard(String name) {
        cardLayout.show(contentPanel, name);
    }

    public static void start() {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ignored) { }

        SwingUtilities.invokeLater(() -> {
            MitarbeiterUI frame = new MitarbeiterUI();
            frame.setVisible(true);
        });
    }
}