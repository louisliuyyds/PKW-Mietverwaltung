package datenbank;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Supabaseverbindung {

    // Datenbank-Verbindungsdaten
    private static final String URL = "jdbc:postgresql://aws-0-eu-central-1.pooler.supabase.com:6543/postgres"; // <-- aus Supabase kopieren
    private static final String USER = "postgres.hhnfusbskegmqcbfwrxd"; // Standard-Supabase-User
    private static final String PASSWORD = "sololeveling123.456"; // Passwort aus dem Dashboard

    // Verbindungsmethode
    public static Connection connect() {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println("Verbindung erfolgreich!");
        } catch (SQLException e) {
            System.err.println("Fehler bei der Verbindung: " + e.getMessage());
        }
        return conn;
    }
}
