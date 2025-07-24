package ui;

import defaults.Fahrzeug;
import defaults.User;

import javax.imageio.ImageIO;
import javax.swing.*;

import java.util.List;
import adapter.ZugriffFahrzeugAdapter;

import java.awt.*;
import java.io.IOException;
import java.net.URL;

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
        
        // Hinzufügen des Logos
        sidebar.add(Box.createVerticalStrut(10));
        try {
        	URL url = new URL("https://www.borgmann-krefeld.de/fileadmin/allgemein/logos/teaser-logo-rent-a-car-borgmann-krefeld.png");
            Image image = ImageIO.read(url);
            Image scaled = image.getScaledInstance(150, 100, Image.SCALE_SMOOTH);
            JLabel imagelabel = new JLabel(new ImageIcon(scaled));
            imagelabel.setAlignmentX(Component.CENTER_ALIGNMENT);
            sidebar.add(imagelabel);
        } catch (IOException e ) {
        	e.printStackTrace();
        }
        
        //Colors
        Color normalColor = new Color(37, 37, 37);
        Color hoverColor = new Color(44, 44, 44);
        Color foregroundColor = new Color(204, 204, 204);
        
        sidebar.setLayout(new BoxLayout(sidebar, BoxLayout.Y_AXIS));
        sidebar.setPreferredSize(new Dimension(180, 0));
        sidebar.setBackground(normalColor);
        sidebar.setForeground(foregroundColor);
        
        JButton startBtn   = new JButton("Startseite");
        startBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        startBtn.setBackground(normalColor);
        startBtn.setForeground(foregroundColor);
        startBtn.setFocusPainted(false);
        startBtn.setBorderPainted(false);
        startBtn.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        startBtn.setBorder(BorderFactory.createEmptyBorder(5, 15, 5, 0));
        startBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        startBtn.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt) {
            	startBtn.setBackground(hoverColor);
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent evt) {
            	startBtn.setBackground(normalColor);
            }
        });
        
        JButton profilBtn  = new JButton("Profil");
        profilBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        profilBtn.setBackground(normalColor);
        profilBtn.setForeground(foregroundColor);
        profilBtn.setFocusPainted(false);
        profilBtn.setBorderPainted(false);
        profilBtn.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        profilBtn.setBorder(BorderFactory.createEmptyBorder(5, 15, 5, 0));
        profilBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        profilBtn.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt) {
            	profilBtn.setBackground(hoverColor);
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent evt) {
            	profilBtn.setBackground(normalColor);
            }
        });
        
        JButton buchBtn    = new JButton("Meine Buchungen");
        buchBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        buchBtn.setBackground(normalColor);
        buchBtn.setForeground(foregroundColor);
        buchBtn.setFocusPainted(false);
        buchBtn.setBorderPainted(false);
        buchBtn.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        buchBtn.setBorder(BorderFactory.createEmptyBorder(5, 15, 5, 0));
        buchBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        buchBtn.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt) {
            	buchBtn.setBackground(hoverColor);
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent evt) {
            	buchBtn.setBackground(normalColor);
            }
        });

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
    
    //-----------------------[Car Home Panel]--------------------------------------------------------------------------------------------------
    private JPanel buildHomePanel() {
        JPanel gridPanel = new JPanel(new GridLayout(0, 3, 20, 20));
        gridPanel.setBackground(Color.DARK_GRAY);
        gridPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        ZugriffFahrzeugAdapter adapter = new ZugriffFahrzeugAdapter();
        List<Fahrzeug> fahrzeuge = adapter.getAlleDaten();

        for (Fahrzeug f : fahrzeuge) {
            String imagePath = "images/" + f.getMarke().toLowerCase() + "_" + f.getModell().toLowerCase() + ".png";

            JPanel card = createCarCard(
                f.getMarke() + " " + f.getModell(),
                f.getKategorie(),
                f.getGetriebe(),
                f.getAnzahlSitze(),
                f.getTreibstoff(),
                400, // Beispiel: Reichweite
                String.format("%.2f €", f.getPreis()),
                String.format("%.2f €", f.getPreis() * 3),
                imagePath
            );

            gridPanel.add(card);
        }

        JScrollPane scrollPane = new JScrollPane(gridPanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);

        JPanel wrapper = new JPanel(new BorderLayout());
        wrapper.add(scrollPane, BorderLayout.CENTER);
        return wrapper;
    }

    
    private JPanel createCarCard(String title, String subtitle, String gearType, int seats, String fuel, int reach, String pricePerDay, String totalPrice, String imagePath) {
        int width = 350, height = 400;
        JPanel card = new RoundedPanel(20);
        card.setPreferredSize(new Dimension(width, height));
        card.setMaximumSize(new Dimension(width, height));
        card.setMinimumSize(new Dimension(width, height));
        card.setBackground(Color.WHITE);
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Labels
        JLabel titleLabel = new JLabel(title);
        titleLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 16));
        card.add(titleLabel);

        JLabel subtitleLabel = new JLabel(subtitle);
        subtitleLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        card.add(subtitleLabel);

        JPanel iconsPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        iconsPanel.setBackground(Color.WHITE);
        iconsPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        iconsPanel.add(new JLabel("👤 " + seats));
        iconsPanel.add(new JLabel("⛽ " + fuel));
        iconsPanel.add(new JLabel("≈ " + reach + "km"));
        iconsPanel.add(new JLabel("⚙️ " + gearType));
        card.add(iconsPanel);

        try {
            ImageIcon carImage = new ImageIcon(imagePath);
            Image scaledImage = carImage.getImage().getScaledInstance(300, 120, Image.SCALE_SMOOTH);
            card.add(new JLabel(new ImageIcon(scaledImage)));
        } catch (Exception e) {
            card.add(new JLabel("(Bild nicht gefunden)"));
        }

        JLabel priceLabel = new JLabel(pricePerDay + " / Tag");
        priceLabel.setFont(new Font("Arial", Font.BOLD, 16));
        card.add(priceLabel);

        JLabel totalLabel = new JLabel(totalPrice + " Gesamtpreis");
        card.add(totalLabel);
        
        //Colors
        Color defaultColor = Color.WHITE;
        Color hoverBorder = new Color(100, 150, 255);
        Color clickColor = new Color(230, 230, 230);

        card.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                card.setBorder(BorderFactory.createLineBorder(hoverBorder, 10));
                card.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent evt) {
            	card.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
                card.setCursor(Cursor.getDefaultCursor());
            }

            @Override
            public void mousePressed(java.awt.event.MouseEvent evt) {
                card.setBackground(clickColor);
                iconsPanel.setBackground(clickColor);
            }

            @Override
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                card.setBackground(defaultColor);
                iconsPanel.setBackground(defaultColor);
                JOptionPane.showMessageDialog(card, "Du hast " + title + " gewählt!");
            }
        });

        return card;
    }

    class RoundedPanel extends JPanel {
        private final int cornerRadius;

        public RoundedPanel(int radius) {
            this.cornerRadius = radius;
            setOpaque(false);
        }

        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(getBackground());
            g2.fillRoundRect(0, 0, getWidth(), getHeight(), cornerRadius, cornerRadius);
            g2.dispose();
            super.paintComponent(g);
        }
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
