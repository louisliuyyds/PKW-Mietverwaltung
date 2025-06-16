package tests;

import defaults.*;

public class Usertest {
    public static void main(String[] args) {
        // Create a new user
        // Assuming the User constructor expects the date of birth as a String in "dd-MM-yyyy" format
        User user = new User(3, "Müller", "Thomas", "Friedrichsfelde 60", java.sql.Date.valueOf("1988-09-29"), "null", false, "null", "123");
        System.out.println("User created");
        System.out.println(user.toString());
        System.out.println("Modify user data");
        user.setAdresse("Friedrichsfelde 61");
        user.setGeburtstag(java.sql.Date.valueOf("1988-09-30"));
        System.out.println(user.toString());
    }
}
