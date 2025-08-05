package ui.panel;

import defaults.Extras;
import defaults.Fahrzeug;
import service.Mietvertragmethoden;
import datenbank.adapter.ZugriffExtrasAdapter;

import javax.swing.*;
import java.awt.*;
import java.net.URL;
import java.time.LocalDate;
import java.util.List;

public class FahrzeugDetailsPanel extends JPanel {

    private final KundenUIElternController controller;
    private final Fahrzeug fahrzeug;
    private final LocalDate startDatum;
    private final LocalDate endDatum;
    private final JComboBox<Extras> versicherungsComboBox;

    public FahrzeugDetailsPanel(Fahrzeug fahrzeug, LocalDate start, LocalDate ende,
                                 KundenUIElternController controller) {
        this.fahrzeug = fahrzeug;
        this.startDatum = start;
        this.endDatum = ende;
        this.controller = controller;

        setLayout(new BorderLayout(10, 10));
        JPanel main = new JPanel(new BorderLayout(10, 10));
        main.setBorder(BorderFactory.createTitledBorder("Fahrzeugdetails"));

        // Fahrzeugbild
        JLabel bildLabel = new JLabel();
        try {
            URL url = new URL(fahrzeug.getBildUrl());
            ImageIcon icon = new ImageIcon(url);
            Image scaled = icon.getImage().getScaledInstance(400, 200, Image.SCALE_SMOOTH);
            bildLabel.setIcon(new ImageIcon(scaled));
        } catch (Exception e) {
            bildLabel.setText("Kein Bild verfügbar");
        }

        // Fahrzeuginfo
        JTextArea info = new JTextArea();
        info.setEditable(false);
        info.setText(String.format(
                "Marke: %s\nModell: %s\nPreis/Tag: %.2f€\nLeistung: %s PS\nTüren: %d\nGetriebe: %s",
                fahrzeug.getMarke(), fahrzeug.getModell(), fahrzeug.getPreis(),
                fahrzeug.getLeistung(), fahrzeug.getTueren(), fahrzeug.getGetriebe()
        ));

        // Versicherungsauswahl
        List<Extras> versicherungen = new ZugriffExtrasAdapter().getExtrasByKategorie("Versicherung");
        versicherungsComboBox = new JComboBox<>(versicherungen.toArray(new Extras[0]));
        JPanel versicherungsPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        versicherungsPanel.add(new JLabel("Versicherung auswählen:"));
        versicherungsPanel.add(versicherungsComboBox);

        JButton weiterButton = new JButton("Weiter zur Zubehör-Auswahl");
        weiterButton.addActionListener(e -> {
            Extras versicherung = (Extras) versicherungsComboBox.getSelectedItem();
            controller.zeigeZubehoerAuswahl(fahrzeug, versicherung, startDatum, endDatum);
        });

        main.add(bildLabel, BorderLayout.NORTH);
        main.add(new JScrollPane(info), BorderLayout.CENTER);
        main.add(versicherungsPanel, BorderLayout.SOUTH);

        add(main, BorderLayout.CENTER);
        add(weiterButton, BorderLayout.SOUTH);
    }
}
