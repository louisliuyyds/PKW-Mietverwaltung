package ui;

import java.awt.*;

import javax.print.DocFlavor.URL;
import javax.swing.*;

import defaults.Fahrzeug;

public class FahrzeugKarte extends JPanel {
    public FahrzeugKarte(Fahrzeug fahrzeug, double gesamtpreis) {
        setLayout(new BorderLayout());
        setPreferredSize(new Dimension(300, 380));
        setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1));
        setBackground(Color.WHITE);

        // Bild laden
        try {
            URL url = new URL(fahrzeug.getBildUrl());  // Beispiel: image URL in Fahrzeug gespeichert
            ImageIcon imageIcon = new ImageIcon(url);
            JLabel imageLabel = new JLabel(new ImageIcon(imageIcon.getImage().getScaledInstance(300, 150, Image.SCALE_SMOOTH)));
            add(imageLabel, BorderLayout.NORTH);
        } catch (Exception e) {
            add(new JLabel("[Kein Bild]", SwingConstants.CENTER), BorderLayout.NORTH);
        }

        // Fahrzeugdaten
        String titel = fahrzeug.getMarke() + " " + fahrzeug.getModell();
        JLabel nameLabel = new JLabel("<html><b>" + titel + "</b> oder ähnlich<br>"
                + fahrzeug.getKlasse() + " " + fahrzeug.getTyp() + " " + fahrzeug.getGetriebe() + "</html>");
        nameLabel.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));

        // Icons (Personen/Koffer/Getriebe)
        JLabel icons = new JLabel("👤 " + fahrzeug.getSitze() + "   🧳 " + fahrzeug.getKofferraum() + "   ⚙ " + fahrzeug.getGetriebe());
        icons.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));

        // Preis
        JLabel preisLabel = new JLabel("<html><b>" + String.format("%.2f € / Tag", fahrzeug.getPreis()) + "</b><br>"
                + String.format("%.2f € Gesamtpreis", gesamtpreis) + "</html>");
        preisLabel.setBorder(BorderFactory.createEmptyBorder(5, 10, 10, 10));

        // Layout innen
        JPanel center = new JPanel();
        center.setLayout(new BoxLayout(center, BoxLayout.Y_AXIS));
        center.setBackground(Color.WHITE);
        center.add(nameLabel);
        center.add(icons);
        center.add(preisLabel);

        add(center, BorderLayout.CENTER);
    }
}
