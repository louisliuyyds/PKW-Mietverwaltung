package main;

import javax.swing.SwingUtilities;

import datenbank.ZugriffUser;
import service.LoginService;
import ui.StartUI;

public class Main {
    public static void main(String[] args) {
      
    	SwingUtilities.invokeLater(() -> new StartUI().setVisible(true));
    }
}

