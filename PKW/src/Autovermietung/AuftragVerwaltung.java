package Autovermietung;
import java.util.HashMap;
import java.util.Scanner;

public class AuftragVerwaltung {
    static HashMap<String, Auftrag> auftragMap = new HashMap<>();

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("Gebe einen Befehl ein! Weitere Informationen unter /help.:");

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
            System.out.println(
                    "Alle Befehle die Ausgeführt werden können: \n\t /exit    : programm beenden \n\t /new     : neuen Autrag erstellen \n\t /list    : zeigt infos zu einem Auftrag \n\t /listall : zeigt alle Aufträge \n\t /edit    : bearbeiten eines Auftrags \n\t /remove  : Auftrag entfernen");
        }
        if (befehl.equalsIgnoreCase("/new")) {
            System.out.println("Erstelle neuen Auftrag:");
            neuerAuftrag();
        }
        if (befehl.equalsIgnoreCase("/list")) {
            Scanner scanner = new Scanner(System.in);
            System.out.println("Gebe den Namen des Auftrags ein, den du suchen möchtest:");
            String name = scanner.nextLine();

            Auftrag auftrag = auftragMap.get(name);
            if (auftrag != null) {
                auftrag.display();
            } else {
                System.out.println("Auftrag mit dem angegebenen Namen " + name + " nicht gefunden.");
            }
        }
        if (befehl.equalsIgnoreCase("/listall")) {
            if (auftragMap.isEmpty()) {
                System.out.println("Die Auftragliste ist leer!");
            } else {
                System.out.println("Fagrzeugliste:");
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
            Scanner scanner = new Scanner(System.in);
            System.out.println("Gebe den Namen des Auftrags ein, welcher entfernt werden soll:");
            String name = scanner.nextLine();

            auftragMap.remove(name);
        }

    }

    public static void neuerAuftrag() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Gebe den Familiennamen an:");
        String familienname = scanner.nextLine();

        System.out.println("Gebe das Anfangsdatum an:");
        String von = scanner.nextLine();

        System.out.println("Gebe das Abgabedatum an:");
        String bis = scanner.nextLine();

        System.out.println("Gebe das Abgabedatum an:");
        String extrapaket = scanner.nextLine();

        System.out.println("Gebe an welches Fahrzeug du mieten möchtest:");
        String name = scanner.nextLine();

        //Liste aller Fahrzeuge anzeigen

        System.out.println("Gebe an welches Fahrzeug du mieten möchtest:");
        String auftragname = scanner.nextLine();

        //Gesamtpreis setzen
        String seriennummer = "";

        Auftrag auftrag = new Auftrag(familienname, von, bis, null, extrapaket, name, seriennummer);
        auftragMap.put(auftragname, auftrag);

        System.out.println("Auftrag erstellt! Hier sind alle Informationen:");
        auftrag.display();
    }

}
