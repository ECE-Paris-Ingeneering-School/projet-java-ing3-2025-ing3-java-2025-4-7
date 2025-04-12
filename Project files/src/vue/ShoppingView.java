// ShoppingView.java
package vue;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;
import javax.imageio.ImageIO;

public class ShoppingView {
    private JFrame frame;
    private JPanel mainPanel;
    private CardLayout cardLayout;

    private JButton loginButton;
    private JButton registerButton;
    private JButton quitButton;

    private JTextField nomField, prenomField, emailField;
    private JPasswordField passwordField, confirmPasswordField;
    private JLabel registerErrorLabel;

    public ShoppingView() {
        frame = new JFrame("Shopping App");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);

        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);

        mainPanel.add(createEntryPanel(), "Entry");
        mainPanel.add(createRegisterPanel(), "Register");

        frame.add(mainPanel);
        frame.setVisible(true);
    }

    private JPanel createEntryPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        loginButton = new JButton("Connexion");
        registerButton = new JButton("Inscription");
        quitButton = new JButton("Quitter");

        JPanel rowPanel = new JPanel(new FlowLayout());
        rowPanel.add(loginButton);
        rowPanel.add(registerButton);

        gbc.gridy = 0;
        panel.add(rowPanel, gbc);

        gbc.gridy = 1;
        panel.add(quitButton, gbc);

        return panel;
    }

    private JPanel createRegisterPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        gbc.gridx = 0; gbc.gridy = 0; panel.add(new JLabel("Nom:"), gbc);
        gbc.gridx = 1; nomField = new JTextField(20); panel.add(nomField, gbc);

        gbc.gridx = 0; gbc.gridy++; panel.add(new JLabel("Prénom:"), gbc);
        gbc.gridx = 1; prenomField = new JTextField(20); panel.add(prenomField, gbc);

        gbc.gridx = 0; gbc.gridy++; panel.add(new JLabel("Email:"), gbc);
        gbc.gridx = 1; emailField = new JTextField(20); panel.add(emailField, gbc);

        gbc.gridx = 0; gbc.gridy++; panel.add(new JLabel("Mot de passe:"), gbc);
        gbc.gridx = 1; passwordField = new JPasswordField(20); panel.add(passwordField, gbc);

        gbc.gridx = 0; gbc.gridy++; panel.add(new JLabel("Confirmer le mot de passe:"), gbc);
        gbc.gridx = 1; confirmPasswordField = new JPasswordField(20); panel.add(confirmPasswordField, gbc);

        gbc.gridx = 0; gbc.gridy++; gbc.gridwidth = 2;
        registerErrorLabel = new JLabel(" ");
        registerErrorLabel.setForeground(Color.RED);
        panel.add(registerErrorLabel, gbc);

        gbc.gridy++;
        JButton registerBtn = new JButton("S'inscrire");
        panel.add(registerBtn, gbc);
        registerBtn.addActionListener(e -> fireRegisterEvent());

        gbc.gridy++;
        JButton backBtn = new JButton("Retour à l'accueil");
        backBtn.addActionListener(e -> showPage("Entry"));
        panel.add(backBtn, gbc);

        return panel;
    }

    private void fireRegisterEvent() {
        // Placeholder, actual logic handled in controller
    }

    public void setRegisterError(String message, boolean success) {
        registerErrorLabel.setText(message);
        registerErrorLabel.setForeground(success ? new Color(0, 128, 0) : Color.RED);
    }

    public void showPage(String name) {
        cardLayout.show(mainPanel, name);
    }

    public void setVisible(boolean visible) {
        if (frame != null) {
            frame.setVisible(visible);
        }
    }

    public JFrame getFrame() { return frame; }
    public JButton getLoginButton() { return loginButton; }
    public JButton getRegisterButton() { return registerButton; }
    public JButton getQuitButton() { return quitButton; }

    public String getNom() { return nomField.getText().trim(); }
    public String getPrenom() { return prenomField.getText().trim(); }
    public String getEmail() { return emailField.getText().trim(); }
    public String getPassword() { return new String(passwordField.getPassword()); }
    public String getConfirmPassword() { return new String(confirmPasswordField.getPassword()); }
}