package ui;

import adapter.ZugriffFahrzeugAdapter;
import adapter.ZugriffVertraegeAdapter;
import defaults.Fahrzeug;
import defaults.Mietvertrag;
import defaults.User;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.net.URL;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class KundenUI extends JFrame {

    public static void start(User user) {
        SwingUtilities.invokeLater(() -> {
            KundenUI ui = new KundenUI(user);
            ui.setVisible(true);
        });
    }

    public KundenUI(User user) {
        setTitle("Kundenportal – " + user.getVorname());
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel sidebar = new JPanel();
        sidebar.add(Box.createVerticalStrut(10));
        try {
            URL url = new URL("https://www.borgmann-krefeld.de/fileadmin/allgemein/logos/teaser-logo-rent-a-car-borgmann-krefeld.png");
            Image image = ImageIO.read(url);
            Image scaled = image.getScaledInstance(150, 100, Image.SCALE_SMOOTH);
            JLabel imagelabel = new JLabel(new ImageIcon(scaled));
            imagelabel.setAlignmentX(Component.CENTER_ALIGNMENT);
            sidebar.add(imagelabel);
        } catch (IOException e) {
            e.printStackTrace();
        }

        Color normalColor = new Color(37, 37, 37);
        Color hoverColor = new Color(44, 44, 44);
        Color foregroundColor = new Color(204, 204, 204);

        sidebar.setLayout(new BoxLayout(sidebar, BoxLayout.Y_AXIS));
        sidebar.setPreferredSize(new Dimension(180, 0));
        sidebar.setBackground(normalColor);
        sidebar.setForeground(foregroundColor);

        JButton startBtn = new JButton("Startseite");
        styleSidebarButton(startBtn, normalColor, hoverColor, foregroundColor);

        JButton profilBtn = new JButton("Profil");
        styleSidebarButton(profilBtn, normalColor, hoverColor, foregroundColor);

        JButton buchBtn = new JButton("Meine Buchungen");
        styleSidebarButton(buchBtn, normalColor, hoverColor, foregroundColor);

        sidebar.add(Box.createVerticalStrut(20));
        sidebar.add(startBtn);
        sidebar.add(Box.createVerticalStrut(5));
        sidebar.add(profilBtn);
        sidebar.add(Box.createVerticalStrut(5));
        sidebar.add(buchBtn);
        sidebar.add(Box.createVerticalGlue());

        JPanel cards = new JPanel(new CardLayout());
        cards.add(buildHomePanel(), "home");
        cards.add(buildProfilPanel(user), "profil");
        JPanel buchungenPanel = buildBuchungenPanel(user);
buchungenPanel.setName("buchungen");
cards.add(buchungenPanel, "buchungen");

        CardLayout cl = (CardLayout) cards.getLayout();
        startBtn.addActionListener(e -> cl.show(cards, "home"));
        profilBtn.addActionListener(e -> cl.show(cards, "profil"));
        buchBtn.addActionListener(e -> cl.show(cards, "buchungen"));

        add(sidebar, BorderLayout.WEST);
        add(cards, BorderLayout.CENTER);
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
                400,
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

    private JPanel buildBuchungenPanel(User user) {
    	int user1=user.getId();
        JPanel listPanel = new JPanel();
        listPanel.setLayout(new BoxLayout(listPanel, BoxLayout.Y_AXIS));
        listPanel.setBackground(Color.DARK_GRAY);
        listPanel.setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40));

        ZugriffVertraegeAdapter adapter = new ZugriffVertraegeAdapter();
        List<Mietvertrag> vertraege = adapter.getVertraegeByUser(user1);

        for (Mietvertrag v : vertraege) {
            JPanel itemPanel = new JPanel(new BorderLayout());
            itemPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
            itemPanel.setBackground(new Color(44, 44, 44));

            JLabel label = new JLabel(String.format("Fahrzeug: %s %s | Start: %s | Ende: %s",
                    v.getFahrzeug().getMarke(),
                    v.getFahrzeug().getModell(),
                    v.getStartdatum().format(DateTimeFormatter.ofPattern("dd.MM.yyyy")),
                    v.getEnddatum().format(DateTimeFormatter.ofPattern("dd.MM.yyyy"))));
            label.setForeground(new Color(204, 204, 204));

            JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
            buttonPanel.setBackground(new Color(44, 44, 44));

            JButton detailsBtn = new JButton("Details anzeigen");
            JButton stornierenBtn = new JButton("Stornieren");

            // Einheitliches Button-Styling
            Font btnFont = new Font("Arial", Font.PLAIN, 12);
            Color btnBg = new Color(60, 63, 65);
            Color btnFg = new Color(220, 220, 220);

            for (JButton btn : new JButton[]{detailsBtn, stornierenBtn}) {
                btn.setBackground(btnBg);
                btn.setForeground(btnFg);
                btn.setFocusPainted(false);
                btn.setFont(btnFont);
            }

            detailsBtn.addActionListener(e -> JOptionPane.showMessageDialog(this, v.toString(), "Details", JOptionPane.INFORMATION_MESSAGE));

            
stornierenBtn.addActionListener(e -> {
    int bestaetigung = JOptionPane.showConfirmDialog(
        this,
        "Möchten Sie diese Buchung wirklich stornieren?",
        "Buchung stornieren",
        JOptionPane.OK_CANCEL_OPTION,
        JOptionPane.WARNING_MESSAGE
    );

    if (bestaetigung == JOptionPane.OK_OPTION) {
        ZugriffVertraegeAdapter adapters = new ZugriffVertraegeAdapter();
        adapters.delete(v.getId());

        JOptionPane.showMessageDialog(
            this,
            "Buchung wurde erfolgreich storniert.",
            "Erfolg",
            JOptionPane.INFORMATION_MESSAGE
        );

        // Buchungspanel aktualisieren
        Container content = this.getContentPane();
        for (Component comp : content.getComponents()) {
            if (comp instanceof JPanel && ((JPanel) comp).getLayout() instanceof CardLayout) {
                JPanel cards = (JPanel) comp;

                // Alte "buchungen"-Komponente entfernen
                for (Component c : cards.getComponents()) {
                    if ("buchungen".equals(c.getName())) {
                        cards.remove(c);
                        break;
                    }
                }

                JPanel newBuchungen = buildBuchungenPanel(user);
                newBuchungen.setName("buchungen");
                cards.add(newBuchungen, "buchungen");

                ((CardLayout) cards.getLayout()).show(cards, "buchungen");
                break;
            }
        }
    }
});


            buttonPanel.add(detailsBtn);
            buttonPanel.add(stornierenBtn);

            itemPanel.add(label, BorderLayout.CENTER);
            itemPanel.add(buttonPanel, BorderLayout.EAST);

            listPanel.add(itemPanel);
            listPanel.add(Box.createVerticalStrut(10));
        }

        JScrollPane scrollPane = new JScrollPane(listPanel);
        scrollPane.setBorder(null);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);

        JPanel wrapper = new JPanel(new BorderLayout());
        wrapper.setBackground(Color.DARK_GRAY);
        wrapper.add(scrollPane, BorderLayout.CENTER);

        return wrapper;
    }

    private JPanel createCarCard(String title, String subtitle, String gearType, int seats, String fuel, int reach, String pricePerDay, String totalPrice, String imagePath) {
        int width = 350, height = 400;
        JPanel card = new RoundedPanel(20);
        card.setPreferredSize(new Dimension(width, height));
        card.setBackground(Color.WHITE);
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

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

    Color background = Color.DARK_GRAY;
    Color panelColor = new Color(44, 44, 44);
    Color headlineColor = Color.LIGHT_GRAY;
    Color textColor = new Color(204, 204, 204);

    private JPanel buildProfilPanel(User user) {
        int minWidth = 100;
        int maxWidth = 200;

        JPanel p = new JPanel();
        p.setBackground(background);
        p.setLayout(new BoxLayout(p, BoxLayout.Y_AXIS));

        JPanel roundedPanel = new RoundedPanel(20);

        try {
            URL url = new URL("https://www.windows-faq.de/wp-content/uploads/2022/09/user.png");
            Image image = ImageIO.read(url);
            Image scaled = image.getScaledInstance(100, 100, Image.SCALE_SMOOTH);
            JLabel imagelabel = new JLabel(new ImageIcon(scaled));
            imagelabel.setAlignmentX(Component.CENTER_ALIGNMENT);
            roundedPanel.add(imagelabel);
        } catch (IOException e) {
            e.printStackTrace();
        }

        roundedPanel.add(Box.createVerticalStrut(30));
        roundedPanel.setLayout(new BoxLayout(roundedPanel, BoxLayout.Y_AXIS));
        roundedPanel.setBackground(panelColor);
        roundedPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        roundedPanel.setAlignmentX(Component.LEFT_ALIGNMENT);

        Font labelFont = new Font("Arial", Font.BOLD, 12);
        Font valueFont = new Font("Arial", Font.PLAIN, 14);

        roundedPanel.add(createLabeledField("Vorname", user.getVorname(), labelFont, valueFont));
        roundedPanel.add(Box.createVerticalStrut(10));
        roundedPanel.add(createLabeledField("Nachname", user.getName(), labelFont, valueFont));
        roundedPanel.add(Box.createVerticalStrut(10));
        roundedPanel.add(createLabeledField("Adresse", user.getAdresse(), labelFont, valueFont));
        roundedPanel.add(Box.createVerticalStrut(10));
        roundedPanel.add(createLabeledField("Geburtstag", user.getGeburtstag().format(DateTimeFormatter.ofPattern("dd.MM.yyyy")), labelFont, valueFont));
        roundedPanel.add(Box.createVerticalStrut(10));
        roundedPanel.add(createLabeledField("Führerschein", user.getFuehrerscheininformation(), labelFont, valueFont));
        roundedPanel.add(Box.createVerticalStrut(10));
        roundedPanel.add(createLabeledField("E-Mail", user.getEmail(), labelFont, valueFont));
        roundedPanel.add(Box.createVerticalGlue());

        Dimension preferred = roundedPanel.getPreferredSize();
        int height = preferred.height;

        roundedPanel.setMinimumSize(new Dimension(minWidth, height));
        roundedPanel.setMaximumSize(new Dimension(maxWidth, height));
        roundedPanel.setPreferredSize(new Dimension(Math.min(maxWidth, preferred.width), height));

        p.add(Box.createVerticalStrut(20));
        p.add(roundedPanel);

        return p;
    }

    private JPanel createLabeledField(String labelText, String valueText, Font labelFont, Font valueFont) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(panelColor);

        JLabel label = new JLabel(labelText);
        label.setFont(labelFont);
        label.setForeground(headlineColor);

        JLabel value = new JLabel(valueText);
        value.setFont(valueFont);
        value.setForeground(textColor);

        panel.add(label);
        panel.add(value);

        return panel;
    }
}
