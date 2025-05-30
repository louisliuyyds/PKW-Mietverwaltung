package defaults;

import javax.swing.*;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Image;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

public class VerwaltungsUI extends JFrame {
	
	JPanel mainPanel = new JPanel();
	
	public static void main(String[] args) {
        SwingUtilities.invokeLater(VerwaltungsUI::new);
    }
	
    public VerwaltungsUI() {
        // Grundzeugs
    	setTitle("Fahrzeugvermietung");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000, 600);
        setLayout(new BorderLayout());
        
        // Hauptbereich
        mainPanel.setBackground(new Color(204, 204, 204));
        mainPanel.setLayout(new BorderLayout());

        // Seitenleiste (links)
        JPanel sidebar = new JPanel();
        sidebar.setBackground(new Color(37, 37, 37));
        sidebar.setLayout(new BoxLayout(sidebar, BoxLayout.Y_AXIS));
        sidebar.setPreferredSize(new Dimension(180, getHeight()));
        
        // Hinzufügen des Logos
        sidebar.add(Box.createVerticalStrut(10));
        try {
        	URL url = new URL("https://www.borgmann-krefeld.de/fileadmin/allgemein/logos/teaser-logo-rent-a-car-borgmann-krefeld.png");
            Image image = ImageIO.read(url);
            Image scaled = image.getScaledInstance(150, 100, Image.SCALE_SMOOTH);
            JLabel imagelabel = new JLabel(new ImageIcon(scaled));
            sidebar.add(imagelabel);
        } catch (IOException e ) {
        	e.printStackTrace();
        }
        sidebar.add(Box.createVerticalStrut(20));
        
        // Nutzer
        sidebar.add(createSectionLabel("User"));
        sidebar.add(createSidebarButton("➤","Fahrzeuge ansehen", () -> openBrowser()));
        
        // Hinzufügen der Elemente von Seitenleiste
        sidebar.add(Box.createVerticalStrut(20));
        sidebar.add(createSectionLabel("Admin"));
        sidebar.add(createSidebarButton("𝓓","AdminDashboard", () -> openAdminDashboard()));
        sidebar.add(createSidebarButton("➤","Fahrzeuge verwalten", () -> openVerhicleManagment()));
        sidebar.add(createSidebarButton("➤","Aufträge verwalten", () -> openContractManagment()));
        sidebar.add(createSidebarButton("➤","Nutzer verwalten", () -> openUserManagment()));
        
        sidebar.add(Box.createVerticalStrut(20));
        sidebar.add(createSectionLabel("Mein Konto"));
        sidebar.add(createSidebarButton("𝓐","Meine Buchungen", () -> openBuchungen()));
        sidebar.add(createSidebarButton("⚙","Settings", () -> openSettings()));        

        // Alles zusammenfügen
        loginStuff();
        add(sidebar, BorderLayout.WEST);
        add(mainPanel, BorderLayout.CENTER);

        setVisible(true);
    }
    
    // Sidebar Ueberschriften
    private JLabel createSectionLabel(String text) {
    	JLabel label = new JLabel(text);
    	label.setForeground(new Color(121, 121, 121));
        label.setFont(new Font("Arial", Font.PLAIN, 12));
        label.setBorder(BorderFactory.createEmptyBorder(10, 15, 5, 0));
    	return label;
    }
    
    // Sidebar Knoepfe 
    private JButton createSidebarButton(String icon, String iconText, Runnable action) {
    	JButton button = new JButton(icon + "    " + iconText);
    	button.setHorizontalAlignment(SwingConstants.LEFT);
        button.setBackground(new Color(37, 37, 37));
        button.setForeground(new Color(204, 204, 204));
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        button.setBorder(BorderFactory.createEmptyBorder(5, 15, 5, 0));
    	
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        button.addActionListener(e -> action.run());
        
        Color normalColor = new Color(37, 37, 37);
        Color hoverColor = new Color(44, 44, 44);

        button.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(hoverColor);
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(normalColor);
            }
        });
        
    	return button;
    }
    
    private void loginStuff() {
    	// Leiste oben
        JPanel statusPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 20, 5));
        statusPanel.setBackground(new Color(204, 204, 204));
        
        //LoginButton erstellen und anbinden
        JButton loginButton = new JButton("Login");
        loginButton.setFocusPainted(false);
        loginButton.setBackground(new Color(37, 37, 37));
        loginButton.setForeground(new Color(204, 204, 204));
        
        loginButton.addActionListener(e -> {
            // Was passieren soll, wenn der Button geklickt wird
            System.out.println("Login geklickt!");
        });
        
        statusPanel.add(loginButton);
        
        JButton registerButton = new JButton("Register");
        registerButton.setFocusPainted(false);
        registerButton.setBackground(new Color(49, 112, 234));
        registerButton.setForeground(new Color(204, 204, 204));
        
        registerButton.addActionListener(e -> {
            // Was passieren soll, wenn der Button geklickt wird
            System.out.println("Register geklickt!");
        });
        
        statusPanel.add(registerButton);
        mainPanel.add(statusPanel,BorderLayout.NORTH);
    }
    
    // Einbauen zum laden einer neuen Seite
    private void openNewPage(String pageTitle) {
        mainPanel.removeAll();
        JLabel label = new JLabel("Willkommen auf der Seite: " + pageTitle);
        System.out.println("Willkommen auf der Seite: " + pageTitle);
        label.setFont(new Font("Arial", Font.BOLD, 20));
        label.setHorizontalAlignment(SwingConstants.CENTER);
        mainPanel.add(label, BorderLayout.CENTER);
        mainPanel.revalidate();
        mainPanel.repaint();
    }
    
    // Admin zugriff 
    private void openAdminDashboard() {
    	openNewPage("Admin Dashboard");
    	loginStuff();
    }
    
    private void openVerhicleManagment() {
    	openNewPage("Fahrzeug Managment");
    	loginStuff();
    }
    
    private void openContractManagment() {
    	openNewPage("Auftrag Managment");
    	loginStuff();
    }
    
    private void openUserManagment() {
    	openNewPage("Nutzer Managment");
    	loginStuff();
    }
    
    // Nutzer zugriff
    private void openBrowser() {
    	openNewPage("Fahrzeug Browser");
    	loginStuff();
    }
    
    private void openBuchungen() {
    	openNewPage("Meine Buchungen");
    	loginStuff();
    }
    
    private void openSettings() {
    	openNewPage("Settings");
    	loginStuff();
    }

}