package ui;

import javax.swing.*;

public class KundeUI {
    public void start() {
        JFrame frame = new JFrame("Kundenbereich");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(900, 700);
        frame.setLocationRelativeTo(null);
        frame.setContentPane(new CustomerHomePanel());
        frame.setVisible(true);
    }
}