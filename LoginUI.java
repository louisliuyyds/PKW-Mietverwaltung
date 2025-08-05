package ui;

import service.LoginService;

import javax.swing.*;
import java.awt.*;

public class LoginUI extends JFrame {

    private JTextField emailField;
    private JPasswordField passwordField;

    private final Color normalColor = new Color(66, 66, 66);
    private final Color hoverColor = new Color(44, 44, 44);
    private final Color foregroundColor = new Color(204, 204, 204);

    private final LoginService loginService = new LoginService();

    public LoginUI() {
        setTitle("Anmeldung");
        setSize(550, 450);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        add(createLoginPanel(), BorderLayout.CENTER);
        setVisible(true);
    }

    private JPanel createLoginPanel() {
        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new BoxLayout(inputPanel, BoxLayout.Y_AXIS));
        inputPanel.setBackground(normalColor);
        inputPanel.setBorder(BorderFactory.createEmptyBorder(40, 60, 40, 60));

        JLabel titleLabel = new JLabel("Anmeldung");
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        titleLabel.setForeground(foregroundColor);
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 22));

        emailField = new JTextField();
        passwordField = new JPasswordField();
        formatField(emailField);
        formatField(passwordField);

        JPanel emailPanel = new JPanel();
        emailPanel.setLayout(new BoxLayout(emailPanel, BoxLayout.Y_AXIS));
        emailPanel.setBackground(normalColor);
        emailPanel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel emailLabel = new JLabel("E-Mail:");
        emailLabel.setForeground(foregroundColor);
        emailLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        emailPanel.add(emailLabel);
        emailPanel.add(Box.createVerticalStrut(5));
        emailPanel.add(emailField);

        JPanel pwPanel = new JPanel();
        pwPanel.setLayout(new BoxLayout(pwPanel, BoxLayout.Y_AXIS));
        pwPanel.setBackground(normalColor);
        pwPanel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel pwLabel = new JLabel("Passwort:");
        pwLabel.setForeground(foregroundColor);
        pwLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        pwPanel.add(pwLabel);
        pwPanel.add(Box.createVerticalStrut(5));
        pwPanel.add(passwordField);

        JButton loginBtn = new JButton("Anmelden");
        styleSidebarButton(loginBtn);
        loginBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        loginBtn.addActionListener(e -> handleLogin());

        inputPanel.add(titleLabel);
        inputPanel.add(Box.createVerticalStrut(30));
        inputPanel.add(emailPanel);
        inputPanel.add(Box.createVerticalStrut(15));
        inputPanel.add(pwPanel);
        inputPanel.add(Box.createVerticalStrut(25));
        inputPanel.add(loginBtn);

        JPanel centerPanel = new JPanel(new GridBagLayout());
        centerPanel.setBackground(normalColor);
        centerPanel.add(inputPanel);

        return centerPanel;
    }

    private void formatField(JTextField field) {
        field.setMaximumSize(new Dimension(300, 35));
        field.setPreferredSize(new Dimension(300, 35));
        field.setBackground(Color.WHITE);
        field.setForeground(Color.BLACK);
        field.setCaretColor(Color.BLACK);
        field.setFont(new Font("SansSerif", Font.PLAIN, 14));
        field.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.GRAY),
                BorderFactory.createEmptyBorder(5, 8, 5, 8)
        ));
    }

    private void styleSidebarButton(JButton button) {
        button.setBackground(normalColor);
        button.setForeground(foregroundColor);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setMaximumSize(new Dimension(300, 40));
        button.setFont(new Font("SansSerif", Font.BOLD, 14));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setBorder(BorderFactory.createEmptyBorder(8, 15, 8, 15));
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
    }

    private void handleLogin() {
        String email = emailField.getText().trim();
        String passwort = new String(passwordField.getPassword());

        if (loginService.authenticate(email, passwort)) {
            loginService.proceedAfterLogin(this, email);
        } else {
            JOptionPane.showMessageDialog(this, "Login fehlgeschlagen!", "Fehler", JOptionPane.ERROR_MESSAGE);
        }
    }
}
