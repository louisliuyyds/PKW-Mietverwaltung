package tests;

import defaults.Fahrzeug;

public class FahrzeugsTest {

	public static void main(String[] args) {
		Fahrzeug car1 = new Fahrzeug(1, "Limousine", "VW Polo", "ABC123", "Manuell", 5, 66.33, true);
		Fahrzeug car2 = new Fahrzeug(2, "SUV", "VW T-Roc", "DEF456", "Manuell", 5, 72.99, true);
		Fahrzeug car3 = new Fahrzeug(1, "Limousine", "VW Polo", "ABC123", "Manuell", 5, 66.33, true);
		//Test toString 
		System.out.println(car1.toString());
		System.out.println(car2.toString());
		System.out.println(car3.toString());
		//Test für Vergleich der Fachobjekte
		
		System.out.println("car1 gleich car2? " + car1.equals(car2));
		System.out.println("car1 gleich car3? " + car1.equals(car3));
		
		//Test für Getters und Setters
		System.out.println("ursprünglicher Tagespreis von car1: " + car1.getPreis());
		car1.setPreis(70);
		System.out.println("neuer Tagespreis von car1: " + car1.getPreis());
		System.out.println("car2 verfügbar? " + car2.isVerfuegbar());
		car2.setVerfuegbar(false);
		System.out.println("car2 noch verfügbar? " + car2.isVerfuegbar());
		
	}

}
