package ui;

import javax.swing.*;
import java.awt.*;
import defaults.Fahrzeug;

public class CarDetailsPanel extends JPanel {
    public CarDetailsPanel(Fahrzeug f) {
        setLayout(new BorderLayout());

        JLabel titel = new JLabel("Details zu: " + f.getFahrzeug(), SwingConstants.CENTER);
        titel.setFont(new Font("Arial", Font.BOLD, 20));
        add(titel, BorderLayout.NORTH);

        JLabel image = new JLabel();
        try {
            ImageIcon icon = new ImageIcon(new java.net.URL("https://via.placeholder.com/400x200?text=" + f.getFahrzeug()));
            Image img = icon.getImage().getScaledInstance(400, 200, Image.SCALE_SMOOTH);
            image.setIcon(new ImageIcon(img));
        } catch (Exception e) {
            image.setText("Kein Bild");
        }

        JPanel info = new JPanel(new GridLayout(0, 1));
        info.add(new JLabel("Kategorie: " + f.getKategorie()));
        info.add(new JLabel("Getriebe: " + f.getGetriebe()));
        info.add(new JLabel("Sitze: " + f.getAnzahlSitze()));
        info.add(new JLabel("Preis/Tag: " + f.getPreis() + " €"));

        JButton back = new JButton("Zurück");
        back.addActionListener(e -> {
            JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(this);
            frame.setContentPane(new CustomerHomePanel());
            frame.revalidate();
        });

        add(image, BorderLayout.WEST);
        add(info, BorderLayout.CENTER);
        add(back, BorderLayout.SOUTH);
    }
}