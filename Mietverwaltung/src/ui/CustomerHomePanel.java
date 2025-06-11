package ui;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import defaults.Fahrzeug;
import defaults.Mietvertrag;

public class CustomerHomePanel extends JPanel {
    private JTextField startField = new JTextField("2025-09-24", 10);
    private JTextField endField = new JTextField("2025-10-01", 10);
    private JPanel resultsPanel = new JPanel(new GridLayout(0, 2, 10, 10));

    public CustomerHomePanel() {
        setLayout(new BorderLayout());
        JPanel top = new JPanel();
        JButton search = new JButton("Verfügbare Autos anzeigen");

        top.add(new JLabel("Abholdatum:"));
        top.add(startField);
        top.add(new JLabel("Rückgabedatum:"));
        top.add(endField);
        top.add(search);

        search.addActionListener(e -> ladeVerfuegbareAutos());

        add(top, BorderLayout.NORTH);
        add(new JScrollPane(resultsPanel), BorderLayout.CENTER);
    }

    private void ladeVerfuegbareAutos() {
        resultsPanel.removeAll();
        LocalDate start = LocalDate.parse(startField.getText(), DateTimeFormatter.ISO_DATE);
        LocalDate end = LocalDate.parse(endField.getText(), DateTimeFormatter.ISO_DATE);

        List<Fahrzeug> autos = Mietvertrag.getVerfuegbareFahrzeuge(start, end);

        for (Fahrzeug f : autos) {
            CarCard karte = new CarCard(f, fahrzeug -> {
                JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(this);
                frame.setContentPane(new CarDetailsPanel(fahrzeug));
                frame.revalidate();
            });
            resultsPanel.add(karte);
        }

        resultsPanel.revalidate();
        resultsPanel.repaint();
    }
}