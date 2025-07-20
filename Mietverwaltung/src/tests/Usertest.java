package tests;

import java.sql.Date;

import defaults.*;

public class Usertest {
    public static void main(String[] args) {
        // Create new users
        // Assuming the User constructor expects the date of birth as a String in "dd-MM-yyyy" format
        User user1 = new User(3, "Müller", "Thomas", "Friedrichsfelde 60", "040-123456", java.sql.Date.valueOf("1988-09-29"), "B123", false, "th", "123");
        User user2 = new User(2, "Schmidt", "Anna", "Hamburg", "040-123456", 
        		java.sql.Date.valueOf("2005-03-20"), "B456", true, 
                "anna@mail.de", "passwort");
        User user3 = new User(3, "Müller", "Thomas", "Friedrichsfelde 60", "040-123456", java.sql.Date.valueOf("1988-09-29"), "B123", false, "th", "123");
        System.out.println("Users created");
        
        System.out.println("user1 equals user2? " + user1.equals(user2));
        System.out.println("user1 equals user3? " + user1.equals(user3));
        
        System.out.println(user1.toString());
        System.out.println("Modify user data");
        user1.setAdresse("Friedrichsfelde 61");
        user1.setGeburtstag(java.sql.Date.valueOf("1988-09-30"));
        System.out.println(user1.toString());
        
        try {
            User invalidUser = new User(2, "Schmidt", "Anna", "Hamburg", "040-123456",
            		java.sql.Date.valueOf("1985-08-22"), null, true, 
                                      "anna@mail.de", "passwort");
        } catch (IllegalArgumentException e) {
            System.out.println("Fehler: " + e.getMessage()); 
            // Ausgabe: Fehler: Führerscheininformation darf nicht null oder leer sein
        }
    }
    
    
}
