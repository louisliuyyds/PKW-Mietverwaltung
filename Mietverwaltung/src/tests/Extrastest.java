package tests;
import defaults.Extras;
public class Extrastest {
    public static void main(String[] args) {
        Extras extra1 = new Extras(1, "GPS Navigation", 5.99, "Inklusive Kartenmaterial");
        Extras extra2 = new Extras(2, "Kindersitz", 3.50, "Für Kinder bis 12 Jahre");
        Extras extra3 = new Extras(1, "GPS Navigation", 5.99, "Inklusive Kartenmaterial");
        System.out.println("Vor Modifikation:");
        System.out.println(extra1.toString());
        System.out.println(extra2.toString());
        System.out.println(extra3.toString());
        System.out.println("extra1 gleich extra3? " + extra1.equals(extra3));
        System.out.println("extra1 gleich extra2? " + extra1.equals(extra2));
        // Modifikationen
        extra1.setPreis(6.99);
        extra2.setBeschreibung("Für Kinder bis 12 Jahre, sicher und bequem");
        
        System.out.println("Nach Modifikation:");
        System.out.println(extra1.toString());
        System.out.println(extra2.toString());
    }

    
}
