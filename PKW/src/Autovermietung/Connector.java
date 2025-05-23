package Autovermietung;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Connector {
    // Verbindungsdaten zur MySQL-Datenbank
    private static final String URL = "jdbc:mysql://localhost:3306/unisoft?useSSL=false&serverTimezone=UTC";
    private static final String USER = "root";
    private static final String PASSWORD = "passwort";

    // Methode zur Herstellung der Verbindung
    public static Connection connect() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}
