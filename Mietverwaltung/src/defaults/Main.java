package defaults;
import javax.swing.*;
import ui.LoginService;

public class Main {
    public static void main(String[] args) {
        LoginService login = new LoginService();
        login.login();
    }
}
