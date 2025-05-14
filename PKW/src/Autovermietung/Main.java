package defaults;

import java.sql.Date;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import javax.swing.JOptionPane;

public class Main {

	public static void main(String[] args) throws SQLException {
		// TODO Auto-generated method stub

		Connector.connect();
		start();
	
		
	}

	public static void start() {
    	EingabeTypen[] optionen = EingabeTypen.values();
        EingabeTypen typ = (EingabeTypen) JOptionPane.showInputDialog(
            null,
            "Welches Formular möchtest du öffnen?",
            "Formularauswahl",
            JOptionPane.QUESTION_MESSAGE,
            null,
            optionen,
            optionen[0]
        );

        // Schritt 2: Wenn Auswahl getroffen, Felder ermitteln und Eingabe starten
        if (typ != null) {
            List<String> felder = EingabeFormulare.getFelderForTyp(typ);                 // <<< HIER
            Map<String, String> daten = EingabeFormulare.einfacheEingabe(felder);       // <<< UND HIER

            // Schritt 3: Wenn Eingabe erfolgreich, Daten verarbeiten
            if (daten != null) {
                EingabeFormulare.verarbeiteDaten(typ, daten);                           // <<< UND HIER
            }
        } else {
            JOptionPane.showMessageDialog(null, "Keine Auswahl getroffen – Programm wird beendet.");
        }
    }
}
