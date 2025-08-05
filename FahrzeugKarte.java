package ui;

import defaults.Fahrzeug;

import javax.swing.*;
import java.awt.*;
import java.net.URL;

public class FahrzeugKarte extends JPanel {

    public FahrzeugKarte(Fahrzeug fahrzeug, Runnable onClick) {
        setLayout(new BorderLayout());
        setPreferredSize(new Dimension(220, 260));
        setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));
        setCursor(new Cursor(Cursor.HAND_CURSOR));

        JLabel imageLabel = new JLabel();
        try {
            URL url = new URL(fahrzeug.getUrl());
            ImageIcon icon = new ImageIcon(url);
            Image scaled = icon.getImage().getScaledInstance(200, 120, Image.SCALE_SMOOTH);
            imageLabel.setIcon(new ImageIcon(scaled));
        } catch (Exception e) {
            imageLabel.setText("Kein Bild");
        }

        JLabel titel = new JLabel(fahrzeug.getMarke() + " " + fahrzeug.getModell(), SwingConstants.CENTER);
        JLabel preis = new JLabel("Preis/Tag: " + fahrzeug.getPreis() + "€", SwingConstants.CENTER);

        add(imageLabel, BorderLayout.NORTH);
        add(titel, BorderLayout.CENTER);
        add(preis, BorderLayout.SOUTH);

        addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                onClick.run();
            }
        });
    }
}
