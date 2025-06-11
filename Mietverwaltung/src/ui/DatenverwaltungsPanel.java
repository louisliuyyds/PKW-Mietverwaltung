package ui;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.List;
import java.util.Map;

public class DatenverwaltungsPanel extends JPanel {

    private final ZugriffInterface zugriff;
    private JTable table;
    private DefaultTableModel model;

    public DatenverwaltungsPanel(ZugriffInterface zugriff) {
        this.zugriff = zugriff;
        setLayout(new BorderLayout());

        add(createTopPanel(), BorderLayout.NORTH);
        add(createTablePanel(), BorderLayout.CENTER);

        loadData();
    }

    private JPanel createTopPanel() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT));

        JButton btnAdd = new JButton("Hinzufügen");
        btnAdd.addActionListener(this::onAdd);

        JButton btnUpdate = new JButton("Aktualisieren");
        btnUpdate.addActionListener(this::onUpdate);

        JButton btnDelete = new JButton("Löschen");
        btnDelete.addActionListener(this::onDelete);

        panel.add(btnAdd);
        panel.add(btnUpdate);
        panel.add(btnDelete);

        return panel;
    }

    private JScrollPane createTablePanel() {
        model = new DefaultTableModel(zugriff.getSpaltennamen(), 0) {
            public boolean isCellEditable(int row, int col) {
                return false;
            }
        };
        table = new JTable(model);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        return new JScrollPane(table);
    }

    private void loadData() {
        model.setRowCount(0);
        List<Object[]> daten = zugriff.getAlleDaten();
        for (Object[] row : daten) {
            model.addRow(row);
        }
    }

    private void onAdd(ActionEvent e) {
        Map<String, String> daten = EingabeFormular.showForm(zugriff.getSpaltennamen());
        if (daten != null) {
            zugriff.add(daten);
            loadData();
        }
    }

    private void onUpdate(ActionEvent e) {
        int row = table.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Bitte Datensatz auswählen.");
            return;
        }

        int id = Integer.parseInt(model.getValueAt(row, 0).toString());
        Map<String, String> daten = EingabeFormular.showForm(zugriff.getSpaltennamen());
        if (daten != null) {
            zugriff.update(id, daten);
            loadData();
        }
    }

    private void onDelete(ActionEvent e) {
        int row = table.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Bitte Datensatz auswählen.");
            return;
        }

        int id = Integer.parseInt(model.getValueAt(row, 0).toString());
        int confirm = JOptionPane.showConfirmDialog(this, "Wirklich löschen?", "Bestätigung", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            zugriff.delete(id);
            loadData();
        }
    }
}
