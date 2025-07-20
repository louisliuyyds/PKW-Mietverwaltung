package ui;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.net.URL;

import adapter.ZugriffUserAdapter;
import adapter.ZugriffFahrzeugAdapter;
import adapter.ZugriffExtrasAdapter;
import adapter.ZugriffVertraegeAdapter;

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
        navPanel.setBackground(new Color(37, 37, 37));
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
        
        //Buttons
        Color normalColor = new Color(37, 37, 37);
        Color hoverColor = new Color(44, 44, 44);
        
        JButton btnStartseite = new JButton("Startseite");
        btnStartseite.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnStartseite.setBackground(new Color(37, 37, 37));
        btnStartseite.setForeground(new Color(204, 204, 204));
        btnStartseite.setFocusPainted(false);
        btnStartseite.setBorderPainted(false);
        btnStartseite.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        btnStartseite.setBorder(BorderFactory.createEmptyBorder(5, 15, 5, 0));
        btnStartseite.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnStartseite.addActionListener(e -> showCard("home"));
        btnStartseite.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt) {
            	btnStartseite.setBackground(hoverColor);
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent evt) {
            	btnStartseite.setBackground(normalColor);
            }
        });

        JButton btnKunden = new JButton("Kunden");
        btnKunden.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnKunden.setBackground(new Color(37, 37, 37));
        btnKunden.setForeground(new Color(204, 204, 204));
        btnKunden.setFocusPainted(false);
        btnKunden.setBorderPainted(false);
        btnKunden.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        btnKunden.setBorder(BorderFactory.createEmptyBorder(5, 15, 5, 0));
        btnKunden.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnKunden.addActionListener(e -> showCard("kunden"));
        btnKunden.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt) {
            	btnKunden.setBackground(hoverColor);
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent evt) {
            	btnKunden.setBackground(normalColor);
            }
        });

        JButton btnFahrzeuge = new JButton("Fahrzeuge");
        btnFahrzeuge.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnFahrzeuge.setBackground(new Color(37, 37, 37));
        btnFahrzeuge.setForeground(new Color(204, 204, 204));
        btnFahrzeuge.setFocusPainted(false);
        btnFahrzeuge.setBorderPainted(false);
        btnFahrzeuge.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        btnFahrzeuge.setBorder(BorderFactory.createEmptyBorder(5, 15, 5, 0));
        btnFahrzeuge.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnFahrzeuge.addActionListener(e -> showCard("fahrzeuge"));
        btnFahrzeuge.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt) {
            	btnFahrzeuge.setBackground(hoverColor);
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent evt) {
            	btnFahrzeuge.setBackground(normalColor);
            }
        });
            

        JButton btnExtras = new JButton("Extras");
        btnExtras.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnExtras.setBackground(new Color(37, 37, 37));
        btnExtras.setForeground(new Color(204, 204, 204));
        btnExtras.setFocusPainted(false);
        btnExtras.setBorderPainted(false);
        btnExtras.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        btnExtras.setBorder(BorderFactory.createEmptyBorder(5, 15, 5, 0));
        btnExtras.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnExtras.addActionListener(e -> showCard("extras"));
        btnExtras.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt) {
            	btnExtras.setBackground(hoverColor);
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent evt) {
            	btnExtras.setBackground(normalColor);
            }
        });

        JButton btnVertraege = new JButton("Verträge");
        btnVertraege.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnVertraege.setBackground(new Color(37, 37, 37));
        btnVertraege.setForeground(new Color(204, 204, 204));
        btnVertraege.setFocusPainted(false);
        btnVertraege.setBorderPainted(false);
        btnVertraege.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        btnVertraege.setBorder(BorderFactory.createEmptyBorder(5, 15, 5, 0));
        btnVertraege.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnVertraege.addActionListener(e -> showCard("vertraege"));
        btnVertraege.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt) {
            	btnVertraege.setBackground(hoverColor);
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent evt) {
            	btnVertraege.setBackground(normalColor);
            }
        });

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

        // Startseite
        JPanel homePanel = new JPanel(new BorderLayout());
        JLabel lblHome = new JLabel("Willkommen", SwingConstants.CENTER);
        lblHome.setFont(new Font("Arial", Font.BOLD, 24));
        homePanel.add(lblHome, BorderLayout.CENTER);

        // Generische Panels
        JPanel kundenPanel = new DatenverwaltungsPanel(new ZugriffUserAdapter());
        JPanel fahrzeugePanel = new DatenverwaltungsPanel(new ZugriffFahrzeugAdapter());
        JPanel extrasPanel = new DatenverwaltungsPanel(new ZugriffExtrasAdapter());
        JPanel vertraegePanel = new DatenverwaltungsPanel(new ZugriffVertraegeAdapter());

        // Karten registrieren
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

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ignored) { }

        SwingUtilities.invokeLater(() -> {
            MitarbeiterUI frame = new MitarbeiterUI();
            frame.setVisible(true);
        });
    }

    public static void start() {
        main(new String[]{});
    }
}
