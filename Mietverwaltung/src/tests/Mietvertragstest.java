package tests;

import defaults.Mietvertrag;
import defaults.User;
import defaults.Extras;
import defaults.Fahrzeug;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Mietvertragstest {

    public static void main(String[] args) {
        /*
    	// Testdaten erstellen
        Fahrzeug fahrzeug = new Fahrzeug(1, "Toyota", "Corolla", null, null, 0, 50.0, true);
        LocalDate startdatum = LocalDate.of(2025, 3, 24);
        LocalDate enddatum = LocalDate.of(2025, 3, 30);
        User user = new User(1, "Mustermann", "Max", "Musterstraße 1", "123456789", java.sql.Date.valueOf("1990-01-01"), "Klasse B", true, "max@mustermann.de", "passwort123");
        Mietvertrag mietvertrag = new Mietvertrag(1, startdatum, enddatum, fahrzeug, user);

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
        */
    	// Testdaten erstellen
        Fahrzeug fahrzeug = new Fahrzeug(1, "Toyota", "Corolla", null, null, 0, 50.0, true);
        User user = new User(1, "Mustermann", "Max", "Musterstraße 1", "123456",
                            java.sql.Date.valueOf("1990-01-01"), "Klasse B", true, 
                            "max@mustermann.de", "passwort123");
        
        // Extras definieren
        Extras extra1 = new Extras(1, "GPS Navigation", 5.99, "Inklusive Kartenmaterial");
        Extras extra2 = new Extras(2, "Kindersitz", 3.50, "Für Kinder bis 12 Jahre");
        
        System.out.println("===== POSITIVTEST: Korrekte Buchung =====");
        testValidBooking(fahrzeug, user, extra1, extra2);
        
        System.out.println("\n===== NEGATIVTESTS: Geschäftsregel-Verletzungen =====");
        testPastDateBooking(fahrzeug, user);
        testTooLongBooking(fahrzeug, user);
        
        System.out.println("\n===== PREISKALKULATIONSTEST =====");
        testPriceCalculation(fahrzeug, user, extra1, extra2);
    }

    private static void testValidBooking(Fahrzeug fahrzeug, User user, Extras extra1, Extras extra2) {
        try {
            LocalDate start = LocalDate.now().plusDays(1); // Morgen
            LocalDate end = start.plusDays(5); // 5 Tage Mietdauer
            
            Mietvertrag mietvertrag = new Mietvertrag(1, start, end, fahrzeug, user);
            mietvertrag.addExtra(extra1);
            mietvertrag.addExtra(extra2);
            
            System.out.println("Erfolgreiche Buchung:");
            System.out.println(mietvertrag);
            System.out.println("Gesamtpreis: " + mietvertrag.gesamtPreis());
        } catch (Exception e) {
            System.err.println("Fehler bei korrekter Buchung: " + e.getMessage());
        }
    }

    private static void testPastDateBooking(Fahrzeug fahrzeug, User user) {
        try {
            LocalDate pastDate = LocalDate.now().minusDays(1); // Gestern
            LocalDate endDate = pastDate.plusDays(3);
            
            System.out.println("Versuche Buchung in der Vergangenheit...");
            new Mietvertrag(2, pastDate, endDate, fahrzeug, user);
            System.err.println("FEHLER: Buchung in Vergangenheit sollte nicht möglich sein!");
        } catch (IllegalArgumentException e) {
            System.out.println("Erwarteter Fehler: " + e.getMessage());
        }
    }

    private static void testTooLongBooking(Fahrzeug fahrzeug, User user) {
        try {
            LocalDate start = LocalDate.now().plusDays(1);
            LocalDate end = start.plusDays(15); // 15 Tage (>14)
            
            System.out.println("Versuche zu lange Mietdauer...");
            new Mietvertrag(3, start, end, fahrzeug, user);
            System.err.println("FEHLER: Mietdauer >14 Tage sollte nicht möglich sein!");
        } catch (IllegalArgumentException e) {
            System.out.println("Erwarteter Fehler: " + e.getMessage());
        }
    }

    private static void testPriceCalculation(Fahrzeug fahrzeug, User user, Extras extra1, Extras extra2) {
        try {
            LocalDate start = LocalDate.of(2025, 8, 24);
            LocalDate end = LocalDate.of(2025, 8, 30); // 6 Tage
            int tage = (int) (end.toEpochDay() - start.toEpochDay());
            
            Mietvertrag mietvertrag = new Mietvertrag(4, start, end, fahrzeug, user);
            mietvertrag.addExtra(extra1);
            mietvertrag.addExtra(extra2);
            
            double expected = (fahrzeug.getPreis() * tage) + extra1.getPreis() + extra2.getPreis();
            double actual = mietvertrag.gesamtPreis();
            
            System.out.println("Berechneter Preis: " + actual);
            System.out.println("Erwarteter Preis: " + expected);
            
            if (Math.abs(expected - actual) < 0.01) {
                System.out.println("Preiskalkulation KORREKT");
            } else {
                System.err.println("FEHLER in Preiskalkulation!");
            }
        } catch (Exception e) {
            System.err.println("Fehler bei Preistest: " + e.getMessage());
        }
    }
}
