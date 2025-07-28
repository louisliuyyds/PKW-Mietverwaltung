package ui;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.URL;

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
        
        //Colors
        Color normalColor = new Color(37, 37, 37);
        Color hoverColor = new Color(44, 44, 44);
        Color foregroundColor = new Color(204, 204, 204);
        
        navPanel.setBackground(normalColor);
        navPanel.setLayout(new BoxLayout(navPanel, BoxLayout.Y_AXIS));
        navPanel.setPreferredSize(new Dimension(150, 0));
        
        // Hinzufügen des Logos
        navPanel.add(Box.createVerticalStrut(10));
        try {
        	URL url = new URL("https://www.borgmann-krefeld.de/fileadmin/allgemein/logos/teaser-logo-rent-a-car-borgmann-krefeld.png");
            Image image = ImageIO.read(url);
            Image scaled = image.getScaledInstance(150, 100, Image.SCALE_SMOOTH);
            JLabel imagelabel = new JLabel(new ImageIcon(scaled));
            imagelabel.setAlignmentX(Component.CENTER_ALIGNMENT);
            navPanel.add(imagelabel);
        } catch (IOException e ) {
        	e.printStackTrace();
        }
        navPanel.add(Box.createVerticalStrut(20));
        
        
        
        JButton btnStartseite = new JButton("Startseite");
        styleSidebarButton(btnStartseite, normalColor, hoverColor, foregroundColor, e -> showCard("home"));


        JButton btnKunden = new JButton("Kunden");
        styleSidebarButton(btnKunden, normalColor, hoverColor, foregroundColor, e -> showCard("kunden"));

        JButton btnFahrzeuge = new JButton("Fahrzeuge");
        styleSidebarButton(btnFahrzeuge, normalColor, hoverColor, foregroundColor, e -> showCard("fahrzeuge"));
            

        JButton btnExtras = new JButton("Extras");
        styleSidebarButton(btnExtras, normalColor, hoverColor, foregroundColor, e -> showCard("extras"));

        JButton btnVertraege = new JButton("Verträge");
        styleSidebarButton(btnVertraege, normalColor, hoverColor, foregroundColor, e -> showCard("vertraege"));

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
    
    private void styleSidebarButton(JButton button, Color normalColor, Color hoverColor, Color foregroundColor, ActionListener action) {
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        button.setBackground(normalColor);
        button.setForeground(foregroundColor);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        button.setBorder(BorderFactory.createEmptyBorder(5, 15, 5, 0));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.addActionListener(action);

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
            f -> new Object[]{ f.getId(), f.getKategorie(), f.getMarke(), f.getModell(), f.getKennzeichen(), f.getGetriebe(), f.getAnzahlSitze(), f.getTreibstoff(), f.getPreis(), f.isVerfuegbar(), f.getReichweite(), f.getUrl() }
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
