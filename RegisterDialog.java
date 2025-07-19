package service;
import javax.swing.*;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

import adapter.ZugriffUserAdapter;
import defaults.User;
import ui.StartUI;

public class RegisterDialog {

    public static void showRegisterDialog() {
        JTextField vornameField = new JTextField();
        JTextField nameField    = new JTextField();
        JTextField adrField     = new JTextField();
        JTextField gebField     = new JTextField("yyyy-mm-dd");
        JTextField fsInfoField  = new JTextField();
        JTextField emailField   = new JTextField();
        JPasswordField pwField  = new JPasswordField();

        Object[] msg = {
                "Vorname:", vornameField,
                "Nachname:", nameField,
                "Adresse:", adrField,
                "Geburtstag (yyyy-mm-dd):", gebField,
                "Führerscheininformation:", fsInfoField,
                "E-Mail:", emailField,
                "Passwort:", pwField
        };

        int res = JOptionPane.showConfirmDialog(null, msg,
                "Registrieren", JOptionPane.OK_CANCEL_OPTION);

        if (res != JOptionPane.OK_OPTION) {
            new StartUI().setVisible(true);
            return;
        }

        // ► einfache Plausibilitätschecks
        List<String> fehler = new ArrayList<>();
        if (vornameField.getText().isBlank()) fehler.add("Vorname fehlt");
        if (nameField.getText().isBlank())    fehler.add("Nachname fehlt");
        if (!emailField.getText().contains("@")) fehler.add("E-Mail ungültig");
        LocalDate geburtstag = null;
        try { geburtstag = LocalDate.parse(gebField.getText().trim()); }
        catch (DateTimeParseException ex) { fehler.add("Geburtsdatum ungültig"); }

        if (!fehler.isEmpty()) {
            JOptionPane.showMessageDialog(null,
                    String.join("\n", fehler), "Eingabefehler",
                    JOptionPane.ERROR_MESSAGE);
            new StartUI().setVisible(true);
            return;
        }

        char[] pwChars = pwField.getPassword();
        String plainPw = new String(pwChars);
        // ► neuen User anlegen
        User neu = new User();
        neu.setVorname(vornameField.getText().trim());
        neu.setName(nameField.getText().trim());
        neu.setAdresse(adrField.getText().trim());
        neu.setGeburtstag(geburtstag);
        neu.setFuehrerscheininformation(fsInfoField.getText().trim());
        neu.setEmail(emailField.getText().trim());
        neu.setPassworthash(plainPw);      // in User wird gehasht

        ZugriffUserAdapter db = new ZugriffUserAdapter();
        db.add(neu);

        // ► automatisch einloggen und KundenUI öffnen
        ui.KundenUI.start(neu);
    }
}
