package ui;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import datenbank.ZugriffUser;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;
import java.util.Vector;

public class MitarbeiterUI extends JFrame {
    private CardLayout cardLayout;
    private JPanel contentPanel;
    private ZugriffUser zugriffUser = new ZugriffUser();

    private JTable userTable;
    private DefaultTableModel userTableModel;
    private JTextField filterField;

    public MitarbeiterUI() {
        setTitle("Mitarbeiter");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(900, 600);
        setLocationRelativeTo(null);

        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(createNavigationPanel(), BorderLayout.WEST);
        getContentPane().add(createContentPanel(), BorderLayout.CENTER);
    }

    private JPanel createNavigationPanel() {
        JPanel navPanel = new JPanel();
        navPanel.setLayout(new BoxLayout(navPanel, BoxLayout.Y_AXIS));
        navPanel.setBackground(new Color(230, 230, 230));
        navPanel.setPreferredSize(new Dimension(150, 0));

        JButton btnStartseite = new JButton("Startseite");
        btnStartseite.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnStartseite.addActionListener(e -> showCard("home"));

        JButton btnKunden = new JButton("Kunden");
        btnKunden.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnKunden.addActionListener(e -> {
            showCard("kunden");
            loadAllUsers();
        });

        JButton btnFahrzeuge = new JButton("Fahrzeuge");
        btnFahrzeuge.setAlignmentX(Component.CENTER_ALIGNMENT);
        // btnFahrzeuge.addActionListener(…);

        JButton btnExtras = new JButton("Extras");
        btnExtras.setAlignmentX(Component.CENTER_ALIGNMENT);
        // btnExtras.addActionListener(…);

        JButton btnVertraege = new JButton("Verträge");
        btnVertraege.setAlignmentX(Component.CENTER_ALIGNMENT);
        // btnVertraege.addActionListener(…);

        navPanel.add(Box.createVerticalStrut(20));
        navPanel.add(btnStartseite);
        navPanel.add(Box.createVerticalStrut(10));
        navPanel.add(btnKunden);
        navPanel.add(Box.createVerticalStrut(10));
        navPanel.add(btnFahrzeuge);
        navPanel.add(Box.createVerticalStrut(10));
        navPanel.add(btnExtras);
        navPanel.add(Box.createVerticalStrut(10));
        navPanel.add(btnVertraege);
        navPanel.add(Box.createVerticalGlue());

        return navPanel;
    }

    private JPanel createContentPanel() {
        contentPanel = new JPanel();
        cardLayout = new CardLayout();
        contentPanel.setLayout(cardLayout);

        // Home-Panel
        JPanel homePanel = new JPanel(new BorderLayout());
        JLabel lblHome = new JLabel("Willkommen auf der Startseite", SwingConstants.CENTER);
        lblHome.setFont(new Font("Arial", Font.BOLD, 24));
        homePanel.add(lblHome, BorderLayout.CENTER);

        // Kunden-Panel
        JPanel kundenPanel = new JPanel(new BorderLayout());
        kundenPanel.add(createKundenTopPanel(), BorderLayout.NORTH);
        kundenPanel.add(createUserTablePanel(), BorderLayout.CENTER);

        contentPanel.add(homePanel, "home");
        contentPanel.add(kundenPanel, "kunden");

        cardLayout.show(contentPanel, "home");
        return contentPanel;
    }

    private JPanel createKundenTopPanel() {
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));

        JButton btnHinzufuegen = new JButton("Hinzufügen");
        btnHinzufuegen.addActionListener(this::onAddUser);

        JButton btnAktualisieren = new JButton("Aktualisieren");
        btnAktualisieren.addActionListener(this::onUpdateUser);

        JButton btnLoeschen = new JButton("Löschen");
        btnLoeschen.addActionListener(this::onDeleteUser);

        JButton btnStartseite = new JButton("Startseite");
        btnStartseite.addActionListener(e -> showCard("home"));

        filterField = new JTextField(20);
        JButton btnFiltern = new JButton("Filtern");
        btnFiltern.addActionListener(this::onFilterUsers);

        topPanel.add(btnHinzufuegen);
        topPanel.add(btnAktualisieren);
        topPanel.add(btnLoeschen);
        topPanel.add(Box.createHorizontalStrut(20));
        topPanel.add(new JLabel("Filter (Name oder Vorname):"));
        topPanel.add(filterField);
        topPanel.add(btnFiltern);
        topPanel.add(Box.createHorizontalStrut(20));
        topPanel.add(btnStartseite);

        return topPanel;
    }

    private JScrollPane createUserTablePanel() {
        String[] columnNames = {
                "ID", "Name", "Vorname", "Adresse", "Geburtstag", "Führerschein", "Status", "Email"
        };
        userTableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        userTable = new JTable(userTableModel);
        userTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        return new JScrollPane(userTable);
    }

    /**
     * Lädt alle User aus der DB (via ZugriffUser#getAllUsersData()) und füllt die Tabelle.
     */
    private void loadAllUsers() {
        userTableModel.setRowCount(0);
        List<Object[]> alleDaten = zugriffUser.getAllUsersData();

        for (Object[] zeile : alleDaten) {
            Vector<Object> rowVector = new Vector<>();
            for (Object feld : zeile) {
                rowVector.add(feld);
            }
            userTableModel.addRow(rowVector);
        }
    }

    /**
     * „Hinzufügen“-Button:
     * - Öffnet das Eingabeformular für alle User‐Felder
     * - Ruft dann EingabeFormulare.verarbeiteDaten(...) auf, das intern addUser(...) ausführt
     * - Lädt anschließend die Tabelle neu
     */
    private void onAddUser(ActionEvent e) {
        // 1. Hole alle Feldnamen für USER
        List<String> feldNamen = EingabeFormulare.getFelderForTyp(EingabeTypen.USER);

        // 2. Zeige das vereinfachte Eingabeformular an und erhält eine Map mit den eingegebenen Werten
        Map<String, String> daten = EingabeFormulare.einfacheEingabe(feldNamen);
        if (daten == null) {
            // Benutzer hat im Dialog auf „Abbrechen“ geklickt
            return;
        }

        // 3. Verarbeite die Daten: das ruft intern zugriffUser.addUser(...) auf
        EingabeFormulare.verarbeiteDaten(EingabeTypen.USER, daten);

        // 4. Tabelle neu laden
        loadAllUsers();
    }

    /**
     * „Aktualisieren“-Button:
     * - Liest ID des ausgewählten Users aus der Tabelle
     * - Öffnet ebenfalls das Eingabeformular und aktualisiert anschließend über zugriffUser.updateUser(...)
     */
    private void onUpdateUser(ActionEvent e) {
        int selectedRow = userTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this,
                    "Bitte wählen Sie zuerst einen Benutzer aus der Liste aus.",
                    "Keine Auswahl", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // ID des ausgewählten Datensatzes auslesen
        int id = (int) userTableModel.getValueAt(selectedRow, 0);

        // 1. Hole alle Feldnamen für USER
        List<String> feldNamen = EingabeFormulare.getFelderForTyp(EingabeTypen.USER);

        // 2. Zeige das Eingabeformular an und erhalte eine Map mit Eingaben
        Map<String, String> daten = EingabeFormulare.einfacheEingabe(feldNamen);
        if (daten == null) {
            // Abgebrochen
            return;
        }

        try {
            // 3. Werte parsen wie in verarbeiteDaten, aber hier selbst updateUser aufrufen
            String name = daten.get("Name").trim();
            String vorname = daten.get("Vorname").trim();
            String adresse = daten.get("Adresse").trim();

            // Geburtstag parsen (Format „dd.MM.yyyy“)
            String geburtstagStr = daten.get("Geburtstag").trim();
            SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
            sdf.setLenient(false);
            java.util.Date parsedDate = sdf.parse(geburtstagStr);
            Date sqlDate = new Date(parsedDate.getTime());

            String fuehrerschein = daten.get("Führerscheininformation").trim();
            boolean status = daten.get("Status").equalsIgnoreCase("frei");

            // 4. Aufruf von updateUser(...) mit allen Parametern
            zugriffUser.updateUser(id, name, vorname, adresse, sqlDate, fuehrerschein, status);

            // 5. Tabelle neu laden und Feedback
            loadAllUsers();
            JOptionPane.showMessageDialog(this,
                    "Benutzer mit ID " + id + " wurde erfolgreich aktualisiert.",
                    "Erfolg", JOptionPane.INFORMATION_MESSAGE);

        } catch (ParseException pe) {
            JOptionPane.showMessageDialog(this,
                    "Ungültiges Datum. Bitte verwende das Format dd.MM.yyyy.",
                    "Fehler", JOptionPane.ERROR_MESSAGE);
        } catch (NumberFormatException nfe) {
            JOptionPane.showMessageDialog(this,
                    "Fehlerhafte Eingabe bei numerischen Feldern.",
                    "Fehler", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * „Löschen“-Button:
     * - Liest die ID des ausgewählten Users
     * - Fragt eine Bestätigung ab und ruft zugriffUser.deleteUser(id) auf
     */
    private void onDeleteUser(ActionEvent e) {
        int selectedRow = userTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this,
                    "Bitte wählen Sie zuerst einen Benutzer aus der Liste aus.",
                    "Keine Auswahl", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int id = (int) userTableModel.getValueAt(selectedRow, 0);
        int confirm = JOptionPane.showConfirmDialog(this,
                "Sind Sie sicher, dass Sie den Benutzer mit der ID " + id + " löschen möchten?",
                "Löschen bestätigen", JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            zugriffUser.deleteUser(id);
            loadAllUsers();
            JOptionPane.showMessageDialog(this,
                    "Benutzer mit ID " + id + " wurde gelöscht.",
                    "Gelöscht", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private void onFilterUsers(ActionEvent e) {
        String filterText = filterField.getText().trim();
        if (filterText.isEmpty()) {
            loadAllUsers();
            return;
        }
        // Für einen echten Filter bräuchtest du z.B. eine Methode, die List<Object[]> zurückgibt.
        // Hier zeigen wir nur ein Beispiel, wie getUsersByName(...) aufgerufen wird:
        String[] tokens = filterText.split("\\s+");
        if (tokens.length >= 2) {
            String vorname = tokens[0];
            String name = tokens[1];
            zugriffUser.getUsersByName(vorname, name);
            // Achtung: getUsersByName(...) gibt nur in der Konsole aus. 
            // Für eine tatsächliche Filter‐Tabelle müsstest du eine Methode bauen, 
            // die List<Object[]> liefert und dann die Tabelle befüllt.
        } else {
            JOptionPane.showMessageDialog(this,
                    "Bitte gib Vorname und Name durch Leerzeichen getrennt ein.",
                    "Fehler", JOptionPane.WARNING_MESSAGE);
        }
    }

    private void showCard(String name) {
        cardLayout.show(contentPanel, name);
    }

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ignored) { }

        SwingUtilities.invokeLater(() -> {
            MitarbeiterUI frame = new MitarbeiterUI();
            frame.setVisible(true);
        });
    }
    
    public static void start() {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ignored) { }

        SwingUtilities.invokeLater(() -> {
            MitarbeiterUI frame = new MitarbeiterUI();
            frame.setVisible(true);
        });
    }
}
