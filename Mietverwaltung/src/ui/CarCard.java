package ui;

import javax.swing.*;
import java.awt.*;
import java.util.function.Consumer;
import defaults.Fahrzeug;

public class CarCard extends JPanel {
    public CarCard(Fahrzeug f, Consumer<Fahrzeug> onClick) {
        setLayout(new BorderLayout(5, 5));
        setBorder(BorderFactory.createLineBorder(Color.GRAY));
        setPreferredSize(new Dimension(350, 200));

        JLabel image = new JLabel();
        try {
            ImageIcon icon = new ImageIcon(new java.net.URL("https://via.placeholder.com/300x150?text=" + f.getFahrzeug()));
            Image img = icon.getImage().getScaledInstance(320, 150, Image.SCALE_SMOOTH);
            image.setIcon(new ImageIcon(img));
        } catch (Exception e) {
            image.setText("Kein Bild");
        }

        JPanel info = new JPanel(new GridLayout(0, 1));
        info.add(new JLabel("Modell: " + f.getFahrzeug()));
        info.add(new JLabel("Kategorie: " + f.getKategorie()));
        info.add(new JLabel("Preis/Tag: " + f.getPreis() + " €"));
        JButton details = new JButton("Details");
        details.addActionListener(e -> onClick.accept(f));
        info.add(details);

        add(image, BorderLayout.CENTER);
        add(info, BorderLayout.SOUTH);
    }
}