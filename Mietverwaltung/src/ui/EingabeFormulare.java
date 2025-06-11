package ui;

import java.text.SimpleDateFormat;
import java.util.*;

import javax.swing.*;

import datenbank.ZugriffExtras;
import datenbank.ZugriffFuhrpark;
import datenbank.ZugriffUser;
import datenbank.ZugriffVertraege;

import java.util.Date;

public class EingabeFormulare {

	public static List<String> getFelderForTyp(EingabeTypen typ) {
	    return switch (typ) {
	        case USER -> List.of("ID", "Name", "Vorname", "Adresse", "Geburtstag", "Führerscheininformation", "Status", "Email", "Passwort");
	        case FAHRZEUG -> List.of("Fahrzeug-ID", "Kategorie", "Fahrzeug", "Kennzeichen", "Getriebe", "Anzahl-Sitze","Preis","Status");
	        case EXTRAS -> List.of("Extras-ID", "Bezeichnung", "Beschreibung", "Preis");
	        case MIETVERTRAG -> List.of("Vertrags-ID","Startdatum","Enddatum","Kunden-ID","Fahrzeug-ID","Extras-ID","Gesamtpreis");
	    };
	}

	public static Map<String, String> einfacheEingabe(List<String> feldNamen) {
	    Map<String, String> eingaben = new LinkedHashMap<>();
	    List<Object> message = new ArrayList<>();
	    Map<String, JComponent> komponenten = new LinkedHashMap<>();

	    for (String feld : feldNamen) {
	        message.add(feld + ":");

	        if (feld.equalsIgnoreCase("Status")) {
	            JComboBox<String> comboBox = new JComboBox<>(new String[]{"frei", "gesperrt"});
	            komponenten.put(feld, comboBox);
	            message.add(comboBox);
	        } else if(feld.equalsIgnoreCase("Bezeichnung")) {
	        	JComboBox<String> comboBox = new JComboBox<>(new String[]{"Versicherung", "Zubehör"});
	            komponenten.put(feld, comboBox);
	            message.add(comboBox);
	        }
	        else {
	            JTextField textField = new JTextField();
	            komponenten.put(feld, textField);
	            message.add(textField);
	        }
	    }

	    int option = JOptionPane.showConfirmDialog(null, message.toArray(), "Eingabeformular", JOptionPane.OK_CANCEL_OPTION);

	    if (option == JOptionPane.OK_OPTION) {
	        for (Map.Entry<String, JComponent> entry : komponenten.entrySet()) {
	            if (entry.getValue() instanceof JTextField tf) {
	                eingaben.put(entry.getKey(), tf.getText());
	            } else if (entry.getValue() instanceof JComboBox cb) {
	                eingaben.put(entry.getKey(), (String) cb.getSelectedItem());
	            }
	        }
	        return eingaben;
	    } else {
	        JOptionPane.showMessageDialog(null, "Eingabe abgebrochen.");
	        return null;
	    }
	}

	public static void verarbeiteDaten(EingabeTypen typ, Map<String, String> daten) {
	    ZugriffUser db = new ZugriffUser(); // ggf. anpassen, je nach Struktur
	    ZugriffFuhrpark db2= new ZugriffFuhrpark();
	    ZugriffExtras db3= new ZugriffExtras();
	    ZugriffVertraege db4= new ZugriffVertraege();

	    try {
	        switch (typ) {
	            case USER -> {
	                int id = Integer.parseInt(daten.get("ID"));
	                String name = daten.get("Name");
	                String vorname = daten.get("Vorname");
	                String adresse = daten.get("Adresse");

	                // Geburtstag
	                String geburtstag = daten.get("Geburtstag");
	                SimpleDateFormat format =new SimpleDateFormat("dd.MM.yyyy");
	                String dateString=format.format(new Date());
	                Date date = format.parse(geburtstag);
	                java.sql.Date sqlDate= new java.sql.Date(date.getTime());
	                String fuehrerschein= daten.get("Führerscheininformation");
	                
	                boolean istFrei = daten.get("Status").equalsIgnoreCase("frei");
	                String email= daten.get("Email");
	                String password= daten.get("Passwort");

	                // DB-Aufruf
	                db.addUser(id, name, vorname, adresse, sqlDate, fuehrerschein, istFrei,email, password);
	                JOptionPane.showMessageDialog(null, "User wurde erfolgreich gespeichert.");
	            }

	            case FAHRZEUG -> {
	                int fahrzeugId = Integer.parseInt(daten.get("Fahrzeug-ID"));
	                String kategorie = daten.get("Kategorie");
	                String fahrzeug = daten.get("Fahrzeug");
	                String kennzeichen = daten.get("Kennzeichen");
	                String getriebe = daten.get("Getriebe");
	                int anzahlSitze = Integer.parseInt(daten.get("Anzahl-Sitze"));
	                double preis = Double.parseDouble(daten.get("Preis").replace(",", ".")); // falls Komma statt Punkt
	                boolean istFrei = daten.get("Status").equalsIgnoreCase("frei");

	                db2.addFahrzeug(fahrzeugId, kategorie, fahrzeug, kennzeichen, getriebe, anzahlSitze, preis, istFrei);
	                JOptionPane.showMessageDialog(null, "Fahrzeug wurde erfolgreich gespeichert.");
	            }
	            case EXTRAS -> {
	                int extrasId = Integer.parseInt(daten.get("Extras-ID"));
	                String bezeichnung = daten.get("Bezeichnung");
	                String beschreibung = daten.get("Beschreibung");
	                double preis = Double.parseDouble(daten.get("Preis").replace(",", ".")); // falls Komma statt Punkt

	                db3.addExtras(extrasId, bezeichnung, beschreibung, preis);
	                JOptionPane.showMessageDialog(null, "Extras wurden erfolgreich gespeichert.");
	            }
	            case MIETVERTRAG -> {
	                int vertragsId = Integer.parseInt(daten.get("Vertrags-ID"));
	                
	             // Startdatum
	                String startdatum = daten.get("Startdatum");
	                SimpleDateFormat format =new SimpleDateFormat("dd.MM.yyyy");
	                String dateString=format.format(new Date());
	                Date date = format.parse(startdatum);
	                java.sql.Date sqlDate= new java.sql.Date(date.getTime());
	                
	             // Enddatum
	                String enddatum = daten.get("Enddatum");
	                SimpleDateFormat format2 =new SimpleDateFormat("dd.MM.yyyy");
	                String dateString1=format2.format(new Date());
	                Date date2 = format2.parse(enddatum);
	                java.sql.Date sqlDate2= new java.sql.Date(date2.getTime());
	                
	                int id_user = Integer.parseInt(daten.get("Kunden-ID"));
	                int id_fuhrpark = Integer.parseInt(daten.get("Fahrzeug-ID"));
	                int id_extras = Integer.parseInt(daten.get("Extras-ID"));
	                double gesamtpreis = Double.parseDouble(daten.get("Gesamtpreis").replace(",", ".")); // falls Komma statt Punkt
	                
	                db4.addVertrag(vertragsId, sqlDate, sqlDate2, id_user, id_fuhrpark, id_extras, gesamtpreis);
	                JOptionPane.showMessageDialog(null, "Mietvertrag wurde erfolgreich gespeichert.");
	            }
	            // Weitere Fälle bei Bedarf
	            default -> {
	                JOptionPane.showMessageDialog(null, "Verarbeitung für " + typ + " ist noch nicht implementiert.");
	            }
	        }
	    } catch (Exception e) {
	        JOptionPane.showMessageDialog(null, "Fehler bei der Verarbeitung: " + e.getMessage());
	    }
	}

	
	
	
}
