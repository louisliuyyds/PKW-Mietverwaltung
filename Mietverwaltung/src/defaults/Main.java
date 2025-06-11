package defaults;

import java.sql.Date;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import javax.swing.JOptionPane;

import datenbank.Supabaseverbindung;
import ui.EingabeTypen;
import ui.LoginService;

public class Main {

	public static void main(String[] args) throws SQLException {
		// TODO Auto-generated method stub

		Supabaseverbindung.connect();
		LoginService ln = new LoginService();
		ln.login("root", "root");
	
		
	}

}
