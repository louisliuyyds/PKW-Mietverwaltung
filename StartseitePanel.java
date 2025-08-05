package ui;

import defaults.Fahrzeug;
import service.MietvertragService;
import defaults.Mietvertragmethoden;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.time.LocalDate;
import java.util.List;

public class StartseitePanel extends JPanel {

    private final MietvertragService mietvertragService;
    private final Mietvertragmethoden mietvertragmethoden;

    private final JTextField startDatumFeld;
    private final JTextField endDatumFeld;
    private final JPanel fahrzeugListePanel;

    private final KundenUIElternController controller; // zur Navigation (Übergabe im Konstruktor)

    public StartseitePanel(MietvertragService service, Mietvertragmethoden methoden, KundenUIElternController controller) {
        this.mietvertragService = service;
        this.mietvertragmethoden = methoden;
        this.controller = controller;

        setLayout(new BorderLayout(10, 10));
        setBorder(new EmptyBorder(15, 15, 15, 15));

        JPanel datumsPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        startDatumFeld = new JTextField(10);
        endDatumFeld = new JTextField(10);
        JButton anzeigenBtn = new JButton("Verfügbare Fahrzeuge anzeigen");

        datumsPanel.add(new JLabel("Startdatum (YYYY-MM-DD):"));
        datumsPanel.add(startDatumFeld);
        datumsPanel.add(new JLabel("Enddatum (YYYY-MM-DD):"));
        datumsPanel.add(endDatumFeld);
        datumsPanel.add(anzeigenBtn);

        fahrzeugListePanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        JScrollPane scrollPane = new JScrollPane(fahrzeugListePanel);

        add(datumsPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);

        anzeigenBtn.addActionListener(this::ladeVerfuegbareFahrzeuge);
    }

    private void ladeVerfuegbareFahrzeuge(ActionEvent e) {
        try {
            LocalDate start = LocalDate.parse(startDatumFeld.getText());
            LocalDate ende = LocalDate.parse(endDatumFeld.getText());

            if (!mietvertragmethoden.istDatumGueltig(start, ende)) {
                JOptionPane.showMessageDialog(this, "Ungültige Datumsangabe.");
                return;
            }

            List<Fahrzeug> verfuegbar = mietvertragService.getVerfuegbareFahrzeuge(start, ende);

            fahrzeugListePanel.removeAll();
            for (Fahrzeug f : verfuegbar) {
                FahrzeugKarte karte = new FahrzeugKarte(f, () -> {
                    controller.zeigeFahrzeugDetails(f, start, ende);
                });
                fahrzeugListePanel.add(karte);
            }
            fahrzeugListePanel.revalidate();
            fahrzeugListePanel.repaint();

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Bitte gültige Daten im Format YYYY-MM-DD eingeben.");
        }
    }
}
