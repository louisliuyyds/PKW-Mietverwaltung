package ui;
import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class EingabeFormular {

    public static Map<String, String> showForm(String[] felder) {
        JPanel panel = new JPanel(new GridLayout(felder.length, 2, 8, 4));
        Map<String, JTextField> inputs = new HashMap<>();

        for (String feld : felder) {
            panel.add(new JLabel(feld + ":"));
            JTextField textField = new JTextField(20);
            panel.add(textField);
            inputs.put(feld, textField);
        }

        int result = JOptionPane.showConfirmDialog(null, panel, "Eingabeformular", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            Map<String, String> daten = new HashMap<>();
            for (String feld : felder) {
                daten.put(feld, inputs.get(feld).getText().trim());
            }
            return daten;
        }

        return null;
    }
}
