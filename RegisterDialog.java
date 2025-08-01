package service;

import adapter.ZugriffUserAdapter;
import defaults.User;
import ui.KundenUI;
import ui.StartUI;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

public class RegisterDialog extends JDialog {

    private final Color backgroundColor = new Color(66, 66, 66);
    private final Color foregroundColor = new Color(204, 204, 204);

    private final JTextField vornameField = new JTextField();
    private final JTextField nameField    = new JTextField();
    private final JTextField adrField     = new JTextField();
    private final JTextField gebField     = new JTextField();
    private final JTextField fsInfoField  = new JTextField();
    private final JTextField emailField   = new JTextField();
    private final JPasswordField pwField  = new JPasswordField();

    public static void showRegisterDialog() {
        RegisterDialog dialog = new RegisterDialog();
        dialog.setVisible(true);
    }

    public RegisterDialog() {
        setTitle("Registrierung");
        setModal(true);
        setSize(600, 700);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        JPanel outerPanel = new JPanel(new GridBagLayout());  // zentriert
        outerPanel.setBackground(backgroundColor);

        JPanel content = new JPanel();
        content.setLayout(new BoxLayout(content, BoxLayout.Y_AXIS));
        content.setBackground(backgroundColor);
        content.setBorder(BorderFactory.createEmptyBorder(30, 60, 30, 60));

        JLabel titleLabel = new JLabel("Registrierung");
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        titleLabel.setForeground(foregroundColor);
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 22));

        content.add(titleLabel);
        content.add(Box.createVerticalStrut(30));
        addLabeledField(content, "Vorname", vornameField);
        addLabeledField(content, "Nachname", nameField);
        addLabeledField(content, "Adresse", adrField);
        addLabeledField(content, "Geburtstag (dd.MM.yyyy)", gebField);
        addLabeledField(content, "Führerscheininformation", fsInfoField);
        addLabeledField(content, "E-Mail", emailField);
        addLabeledField(content, "Passwort", pwField);
        content.add(Box.createVerticalStrut(30));
        content.add(createButtonPanel());

        outerPanel.add(content);  // alles zentriert
        setContentPane(outerPanel);
    }

    private void addLabeledField(JPanel panel, String labelText, JComponent inputField) {
        JLabel label = new JLabel(labelText);
        label.setForeground(foregroundColor);
        label.setAlignmentX(Component.CENTER_ALIGNMENT);

        if (inputField instanceof JTextField) {
            formatField((JTextField) inputField);
        }

        inputField.setAlignmentX(Component.CENTER_ALIGNMENT);

        JPanel wrapper = new JPanel();
        wrapper.setLayout(new BoxLayout(wrapper, BoxLayout.Y_AXIS));
        wrapper.setBackground(backgroundColor);
        wrapper.setAlignmentX(Component.CENTER_ALIGNMENT);
        wrapper.add(label);
        wrapper.add(Box.createVerticalStrut(5));
        wrapper.add(inputField);
        wrapper.add(Box.createVerticalStrut(15));

        panel.add(wrapper);
    }

    private void formatField(JTextField field) {
        field.setMaximumSize(new Dimension(300, 35));
        field.setPreferredSize(new Dimension(300, 35));
        field.setBackground(Color.WHITE);
        field.setForeground(Color.BLACK);
        field.setCaretColor(Color.BLACK);
        field.setFont(new Font("SansSerif", Font.PLAIN, 14));
        field.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.GRAY),
                BorderFactory.createEmptyBorder(5, 8, 5, 8)
        ));
    }

    private JPanel createButtonPanel() {
        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(backgroundColor);
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER));

        JButton okBtn = new JButton("Registrieren");
        JButton cancelBtn = new JButton("Abbrechen");

        okBtn.addActionListener(e -> handleRegister());
        cancelBtn.addActionListener(e -> {
            dispose();
            new StartUI().setVisible(true);
        });

        buttonPanel.add(okBtn);
        buttonPanel.add(cancelBtn);
        buttonPanel.setAlignmentX(Component.CENTER_ALIGNMENT);

        return buttonPanel;
    }

    private void handleRegister() {
        List<String> fehler = new ArrayList<>();
        if (vornameField.getText().isBlank()) fehler.add("Vorname fehlt");
        if (nameField.getText().isBlank())    fehler.add("Nachname fehlt");
        if (!emailField.getText().contains("@")) fehler.add("E-Mail ungültig");

        LocalDate geburtstag = null;
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
            geburtstag = LocalDate.parse(gebField.getText().trim(), formatter);
        } catch (DateTimeParseException e) {
            fehler.add("Geburtsdatum ungültig (Format: dd.MM.yyyy)");
        }

        if (!fehler.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    String.join("\n", fehler), "Eingabefehler",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        String plainPw = new String(pwField.getPassword());

        User neu = new User();
        neu.setVorname(vornameField.getText().trim());
        neu.setName(nameField.getText().trim());
        neu.setAdresse(adrField.getText().trim());
        neu.setGeburtstag(geburtstag);
        neu.setFuehrerscheininformation(fsInfoField.getText().trim());
        neu.setEmail(emailField.getText().trim());
        neu.setPassworthash(plainPw);

        new ZugriffUserAdapter().add(neu);

        dispose();
        KundenUI.start(neu);
    }
}
