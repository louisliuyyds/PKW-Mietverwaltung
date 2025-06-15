package tests;

import defaults.Fahrzeug;

public class FahrzeugsTest {

	public static void main(String[] args) {
		Fahrzeug car1 = new Fahrzeug(1, "Limousine", "VW Polo", "ABC123", "Manuell", 5, 66.33, true);
		Fahrzeug car2 = new Fahrzeug(2, "SUV", "VW T-Roc", "DEF456", "Manuell", 5, 72.99, true);
		System.out.println(car1.toString());
		System.out.println(car2.toString());
		car1.setPreis(70);
		car2.setVerfuegbar(false);
		System.out.println("Nach Modifizierung:");
		System.out.println(car1.toString());
		System.out.println(car2.toString());
	}

}
