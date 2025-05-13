package Autovermietung;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.Scanner;

public class PKWVerwaltung {
    static HashMap<String, PKW> pkwMap = new HashMap<>();

    public static void main(String[] args) {
        loadFromFile();
        Scanner scanner = new Scanner(System.in);
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
            Scanner scanner = new Scanner(System.in);
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
            Scanner scanner = new Scanner(System.in);
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
            Scanner scanner = new Scanner(System.in);
            System.out.println("Gebe den Namen des Farzeuges ein, welches entfernt werden soll:");
            String name = scanner.nextLine();

            pkwMap.remove(name);
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
        Scanner scanner = new Scanner(System.in);
        if (objekt.equalsIgnoreCase("Kennzeichen")) {
            System.out.println("Gebe den neuen Inhalt an:");
            String name = scanner.nextLine();

            fahrzeug.setKennzeichen(name);
        }
        if (objekt.equalsIgnoreCase("Status")) {
            System.out.println("Gebe den neuen Inhalt an:");
            String name = scanner.nextLine();

            fahrzeug.setStatus(name);
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
            System.out.println("Gebe den neuen Inhalt an:");
            String name = scanner.nextLine();

            fahrzeug.setKraftstoff(name);
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
        Scanner scanner = new Scanner(System.in);

        System.out.println("Gebe das Kennzeichen ein:");
        String kennzeichen = scanner.nextLine();

        System.out.println("Gebe den Preis ein:");
        String preis = scanner.nextLine();
        int preis_umgewandelt = Integer.parseInt(preis);

        System.out.println("Gebe die Marke ein:");
        String marke = scanner.nextLine();

        System.out.println("Gebe die Treibstoffart ein:");
        String treibstoff = scanner.nextLine();

        System.out.println("Gebe das Modell ein:");
        String modell = scanner.nextLine();

        System.out.println("Gebe weitere Details an:");
        String details = scanner.nextLine();

        System.out.println("Gebe dem PKW ein Aufrufnamen:");
        String name = scanner.nextLine();

        PKW fahrzeug = new PKW(null, kennzeichen, "Verfügbar" , preis_umgewandelt, marke, treibstoff, modell, details, name);
        pkwMap.put(name, fahrzeug);

        System.out.println("Fahrzeug erstellt! Hier sind alle Informationen:");
        fahrzeug.display();

    }
}
