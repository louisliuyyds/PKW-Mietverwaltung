package tests;

import defaults.Mietvertrag;
import defaults.Extras;
import defaults.Fahrzeug;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Mietvertragstest {

    public static void main(String[] args) {
        // Testdaten erstellen
        Fahrzeug fahrzeug = new Fahrzeug(1, "Toyota", "Corolla", null, null, 0, 50.0, true);
        LocalDate startdatum = LocalDate.of(2025, 3, 24);
        LocalDate enddatum = LocalDate.of(2025, 3, 30);
        Mietvertrag mietvertrag = new Mietvertrag(1, startdatum, enddatum, fahrzeug);

        // Extras hinzufügen
        Extras extra1 = new Extras(1, "GPS Navigation", 5.99, "Inklusive Kartenmaterial");
        Extras extra2 = new Extras(2, "Kindersitz", 3.50, "Für Kinder bis 12 Jahre");
        mietvertrag.addExtra(extra1);
        mietvertrag.addExtra(extra2);

        // Mietvertrag ausgeben
        System.out.println(mietvertrag.toString());

        // Gesamtpreis berechnen
        double gesamtpreis = mietvertrag.gesamtPreis();
        System.out.println("Gesamtpreis: " + gesamtpreis);
    }
}
