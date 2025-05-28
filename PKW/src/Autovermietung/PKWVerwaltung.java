package defaults;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Random;
import java.util.Scanner;

public class PKWVerwaltung {
	static HashMap<String, PKW> pkwMap = new HashMap<>();
	static Scanner scanner = new Scanner(System.in);
	
	public static void main(String[] args) throws SQLException {
		Connector.connect();
		loadFromFile();
		while(true) {
			System.out.println("Gebe einen Befehl ein! Weitere Informationen unter /help.:");
			
			String befehl = scanner.nextLine();
			if (befehl.equalsIgnoreCase("/exit")) {
				System.out.println("Programm wird beendet.");
				saveToFile();
				break;
			}
			befehlabfrage(befehl);
		}
		scanner.close();
	}
	
	public static void befehlabfrage(String befehl) {
		if (befehl.equalsIgnoreCase("/help")) {
			System.out.println("Alle Befehle die Ausgeführt werden können: \n\t /exit      : programm beenden \n\t /createcar : erstellen eines neuen Fahrzeuges \n\t /listall   : alle Fahrzeuge auflisten \n\t /listcar   : spezifisches Fahrzeug aufrufen \n\t /editcar   : Fahrzeug bearbeiten \n\t /removecar : Fahrzeug entfernen \n");
		}
		if (befehl.equalsIgnoreCase("/createcar")) {
			System.out.println("Erstelle ein neues Fahrzeug:");
			createcar();
		}
		if (befehl.equalsIgnoreCase("/listall")) {
			if (pkwMap.isEmpty()) {
				System.out.println("Die Fahrzeugliste ist leer!");
			} else {
				System.out.println("Fagrzeugliste:");
				for (String name : pkwMap.keySet()) {
					PKW fahrzeug = pkwMap.get(name);
					fahrzeug.display();
				}
			}
		}
		if (befehl.equalsIgnoreCase("/listcar")) {
			System.out.println("Gebe den Namen des Farzeuges ein, das du suchen möchtest:");
			String name = scanner.nextLine();
			
			PKW fahrzeug = pkwMap.get(name);
			if (fahrzeug != null) {
				fahrzeug.display();
			} else {
				System.out.println("Fahrzeug mit dem angegebenen Namen " + name + " nicht gefunden.");
			}
		}
		if (befehl.equalsIgnoreCase("/editcar")) {
			System.out.println("Gebe den Namen des Farzeuges ein, das du suchen möchtest:");
			String name = scanner.nextLine();
			
			PKW fahrzeug = pkwMap.get(name);
			if (fahrzeug != null) {
				System.out.println("Gebe ein was du verändern möchtest (Kennzeichen, Status, Preis, Marke, Kraftstoff, Modell, Details):");
				String objekt = scanner.nextLine();
				
				editcar(fahrzeug, objekt);
			} else {
				System.out.println("Fahrzeug mit dem angegebenen Namen " + name + " nicht gefunden.");
			}	
		}
		if (befehl.equalsIgnoreCase("/removecar")) {
			System.out.println("Gebe den Namen des Farzeuges ein, welches entfernt werden soll:");
			String name = scanner.nextLine();
			
			if (pkwMap.containsKey(name)) {
				pkwMap.remove(name);
				System.out.println("Fahrzeug wurde Erfolgreich entfernt.");
			} else {
				System.out.println("Fahrzeug nicht vorhanden.");
			}
		}
	}
	
	
	public static void saveToFile() {
		try {
			ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("fahrzeuge.dat"));
			out.writeObject(pkwMap);
			out.close();
			System.out.println("Fahrzeug wurde erfolgreich gespeichert.");
		} catch (Exception e) {
			System.out.println("Fehler: " + e.getMessage());
		}
		
	}
	
	public static void loadFromFile() {
		 File file = new File("fahrzeuge.dat");
		 if (!file.exists()) {
		        System.out.println("Keine gespeicherte Datei gefunden. Es wird eine neue Fahrzeugliste erstellt.");
		        return;
		 }
		
		try {
			ObjectInputStream in = new ObjectInputStream(new FileInputStream(file));
			Object obj = in.readObject();
			if (obj instanceof HashMap) {
	            pkwMap = (HashMap<String, PKW>) obj;
	            System.out.println("Fahrzeuge erfolgreich geladen!");
	        } else {
	            System.out.println("Dateiinhalt ist ungültig.");
	        }
		} catch (Exception e) {
			System.out.println("Fehler: " + e.getMessage());
		}
	}
	
	public static void editcar(PKW fahrzeug, String objekt) {
		if (objekt.equalsIgnoreCase("Kennzeichen")) {
			System.out.println("Gebe den neuen Inhalt an:");
			String name = scanner.nextLine();
			
			fahrzeug.setKennzeichen(name);
		}
		if (objekt.equalsIgnoreCase("Status")) {
			System.out.println("Wähle den Status des Fahrzeuges:");
			PKW.Status[] statusWert = PKW.Status.values();
			for (int i = 0; i < statusWert.length; i++) {
				System.out.println((i + 1) + ". " + statusWert[i]);
			}
			int statusIndex = Integer.parseInt(scanner.nextLine()) - 1;
			PKW.Status status = statusWert[statusIndex];
			
			fahrzeug.setStatus(status);
		}
		if (objekt.equalsIgnoreCase("Preis")) {
			System.out.println("Gebe den neuen Inhalt an:");
			String name = scanner.nextLine();
			int preis_umgewandelt = Integer.parseInt(name);
			
			fahrzeug.setPreis(preis_umgewandelt);
		}
		if (objekt.equalsIgnoreCase("Marke")) {
			System.out.println("Gebe den neuen Inhalt an:");
			String name = scanner.nextLine();
			
			fahrzeug.setMarke(name);
		}
		if (objekt.equalsIgnoreCase("Kraftstoff")) {
			System.out.println("Wähle den Kraftstoff des Fahrzeuges:");
			PKW.Kraftstoff[] kraftstoffWert = PKW.Kraftstoff.values();
			for (int i = 0; i < kraftstoffWert.length; i++) {
				System.out.println((i + 1) + ". " + kraftstoffWert[i]);
			}
			int kraftstoffIndex = Integer.parseInt(scanner.nextLine()) - 1;
			PKW.Kraftstoff kraftstoff = kraftstoffWert[kraftstoffIndex];
			
			fahrzeug.setKraftstoff(kraftstoff);
		}
		if (objekt.equalsIgnoreCase("Modell")) {
			System.out.println("Gebe den neuen Inhalt an:");
			String name = scanner.nextLine();
			
			fahrzeug.setModell(name);
		}
		if (objekt.equalsIgnoreCase("Details")) {
			System.out.println("Gebe den neuen Inhalt an:");
			String name = scanner.nextLine();
			
			fahrzeug.setDetails(name);
		}
	}
	
	public static void createcar() {
		System.out.println("Gebe das Kennzeichen ein:");
		String kennzeichen = scanner.nextLine();
		
		System.out.println("Gebe den Preis ein:");
		String preis = scanner.nextLine();
		int preis_umgewandelt = Integer.parseInt(preis);
		
		System.out.println("Gebe die Marke ein:");
		String marke = scanner.nextLine();
		
		System.out.println("Wähle den Kraftstoff des Fahrzeuges:");
		PKW.Kraftstoff[] kraftstoffWert = PKW.Kraftstoff.values();
		for (int i = 0; i < kraftstoffWert.length; i++) {
			System.out.println((i + 1) + ". " + kraftstoffWert[i]);
		}
		int kraftstoffIndex = Integer.parseInt(scanner.nextLine()) - 1;
		PKW.Kraftstoff kraftstoff = kraftstoffWert[kraftstoffIndex];
		
		System.out.println("Gebe das Modell ein:");
		String modell = scanner.nextLine();
		
		System.out.println("Gebe weitere Details an:");
		String details = scanner.nextLine();
		
		System.out.println("Wähle den Status des Fahrzeuges:");
		PKW.Status[] statusWert = PKW.Status.values();
		for (int i = 0; i < statusWert.length; i++) {
			System.out.println((i + 1) + ". " + statusWert[i]);
		}
		int statusIndex = Integer.parseInt(scanner.nextLine()) - 1;
		PKW.Status status = statusWert[statusIndex];
		
		String ID = createID(marke, modell);
		System.out.println("Das Fahrzeug trägt die ID: " + ID);
		
		PKW fahrzeug = new PKW(ID, kennzeichen, status , preis_umgewandelt, marke, kraftstoff, modell, details);
		pkwMap.put(ID, fahrzeug);
		
		System.out.println("Fahrzeug erstellt! Hier sind alle Informationen:");
		fahrzeug.display();

	}
	
	public static String createID(String marke, String modell) {
		Random random = new Random();
		String number = "";
		
		number = marke + modell + "-";
		
		String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
		
		for(int i = 0; i < 5; i++) {
			int index = random.nextInt(characters.length());
			number += characters.charAt(index);
		}
		return number;
	}
}
