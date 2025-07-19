package ui;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import adapter.ZugriffInterface;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

public class DatenverwaltungsPanel<T> extends JPanel {

    private final ZugriffInterface<T> zugriff;
    private final Function<Map<String, String>, T> fromMapConverter;
    private final Function<T, Object[]> toRowConverter;
    private JTable table;
    private DefaultTableModel model;

    public DatenverwaltungsPanel(ZugriffInterface<T> zugriff,
                                 Function<Map<String, String>, T> fromMapConverter,
                                 Function<T, Object[]> toRowConverter) {
        this.zugriff = zugriff;
        this.fromMapConverter = fromMapConverter;
        this.toRowConverter = toRowConverter;
        initializeUI();
        loadData();
    }

    private void initializeUI() {
        setLayout(new BorderLayout());
        add(createTopPanel(), BorderLayout.NORTH);
        add(createTablePanel(), BorderLayout.CENTER);
    }

    private JPanel createTopPanel() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT));

        JButton btnAdd = new JButton("Hinzufügen");
        JButton btnUpdate = new JButton("Bearbeiten");
        JButton btnDelete = new JButton("Löschen");

        btnAdd.addActionListener(this::onAdd);
        btnUpdate.addActionListener(this::onUpdate);
        btnDelete.addActionListener(this::onDelete);

        panel.add(btnAdd);
        panel.add(btnUpdate);
        panel.add(btnDelete);

        return panel;
    }

    private JScrollPane createTablePanel() {
        model = new DefaultTableModel(zugriff.getSpaltennamen(), 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        table = new JTable(model);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        return new JScrollPane(table);
    }

    private void loadData() {
        model.setRowCount(0);
        List<T> daten = zugriff.getAlleDaten();
        for (T obj : daten) {
            model.addRow(toRowConverter.apply(obj));
        }
    }

    private void onAdd(ActionEvent e) {
        String[] spalten = zugriff.getSpaltennamen();
        String[] ohneIdUndPreis = java.util.Arrays.stream(spalten)
        	    .filter(s -> !s.equalsIgnoreCase("ID"))
        	    .filter(s -> !s.equalsIgnoreCase("Gesamtpreis"))
        	    .toArray(String[]::new);


        Map<String, String> daten = EingabeFormular.showForm(ohneIdUndPreis);
        if (daten != null) {
            T objekt = fromMapConverter.apply(daten);
            zugriff.add(objekt);
            loadData();
        }
    }

    private void onUpdate(ActionEvent e) {
        int row = table.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Bitte einen Datensatz auswählen.");
            return;
        }

        String[] spalten = zugriff.getSpaltennamen();
        String[] ohneIdUndPreis = java.util.Arrays.stream(spalten)
        	    .filter(s -> !s.equalsIgnoreCase("ID"))
        	    .filter(s -> !s.equalsIgnoreCase("Gesamtpreis"))
        	    .toArray(String[]::new);


        Map<String, String> daten = EingabeFormular.showForm(ohneIdUndPreis);
        if (daten != null) {
            daten.put("ID", table.getValueAt(row, 0).toString());
            T objekt = fromMapConverter.apply(daten);
            zugriff.update(objekt);
            loadData();
        }
    }

    private void onDelete(ActionEvent e) {
        int row = table.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Bitte einen Datensatz auswählen.");
            return;
        }

        int id = Integer.parseInt(table.getValueAt(row, 0).toString());
        int confirm = JOptionPane.showConfirmDialog(this, "Wirklich löschen?", "Bestätigung", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            zugriff.delete(id);
            loadData();
        }
    }
} 