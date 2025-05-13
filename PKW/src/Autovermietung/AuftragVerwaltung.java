package PKWVermietungsverwaltung;

import java.util.HashMap;
import java.util.Random;
import java.util.Scanner;

public class AuftragVerwaltung {
	static HashMap<String, Auftrag> auftragMap = new HashMap<>();
	static Scanner scanner = new Scanner(System.in);

	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		while (true) {
			System.out.println("Gebe einen Befehl ein! Weitere Informationen unter /help.");

			String befehl = scanner.nextLine();
			if (befehl.equalsIgnoreCase("/exit")) {
				System.out.println("Programm wird beendet.");
				break;
			}
			befehlabfrage(befehl);
		}
		scanner.close();
	}

	public static void befehlabfrage(String befehl) {
		if (befehl.equalsIgnoreCase("/help")) {
			System.out.println("Alle Befehle die Ausgeführt werden können \n\t exit    : programm beenden \n\t new     : neuen Autrag erstellen \n\t list    : zeigt infos zu einem Auftrag \n\t listall : zeigt alle Aufträge \n\t edit    : bearbeiten eines Auftrags \n\t remove  : Auftrag entfernen");
		}
		if (befehl.equalsIgnoreCase("/new")) {
			System.out.println("Erstelle neuen Auftrag:");
			neuerAuftrag();
		}
		if (befehl.equalsIgnoreCase("/list")) {
			System.out.println("Gebe den Namen des Auftrags ein, den du suchen möchtest:");
			String name = scanner.nextLine();

			Auftrag auftrag = auftragMap.get("name");
			if (auftrag != null) {
				auftrag.display();
			} else {
				System.out.println("Auftrag mit dem angegebenen Namen  + name +  nicht gefunden.");
			}
		}
		if (befehl.equalsIgnoreCase("/listall")) {
			if (auftragMap.isEmpty()) {
				System.out.println("Die Auftragliste ist leer!");
			} else {
				System.out.println("/Auftragliste");
				for (String name : auftragMap.keySet()) {
					Auftrag auftrag = auftragMap.get(name);
					auftrag.display();
				}
			}
		}
		if (befehl.equalsIgnoreCase("/edit")) {
			System.out.println("Bearbeite Autrag...");
		}
		if (befehl.equalsIgnoreCase("/remove")) {
			System.out.println("Gebe den Namen des Auftrags ein, welcher entfernt werden soll:");
			String name = scanner.nextLine();

			auftragMap.remove(name);
		}

	}

	public static void neuerAuftrag() {
		System.out.println("Gebe das Anfangsdatum an");
		String startdatum = scanner.nextLine();

		System.out.println("Gebe das Abgabedatum an");
		String enddatum = scanner.nextLine();
		
		System.out.println("Wähle ein Fahrzeug");
		String fahrzeugid = scanner.nextLine();
		
		String kundenid = null;
		String extraid = null;
		String gesamtpreis = null;
		
		String ID = createID(kundenid, fahrzeugid);
		
		Auftrag auftrag = new Auftrag(ID, startdatum, enddatum, fahrzeugid, kundenid, extraid, gesamtpreis);
		auftragMap.put(ID, auftrag);

		System.out.println("Auftrag erstellt! Hier sind alle Informationen:");
		auftrag.display();
	}
	
	public static String createID(String Kunde, String Fahrzeug) {
		Random random = new Random();
		String number = "";
		
		number = Kunde + Fahrzeug + "-";
		
		String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
		
		for(int i = 0; i < 5; i++) {
			int index = random.nextInt(characters.length());
			number += characters.charAt(index);
		}
		return number;
	}

}
