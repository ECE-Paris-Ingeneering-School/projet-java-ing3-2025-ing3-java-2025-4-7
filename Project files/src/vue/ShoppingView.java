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
    private JPanel loginPanel;
    private JPanel registerPanel;
    private JTextField nomField, prenomField, mailField;
    private JPasswordField passwordField, confirmPasswordField;
    private JLabel registerMessageLabel;
    private JButton submitRegisterButton;

    public ShoppingView() {
        frame = new JFrame("Shopping App");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);

        try {
            Image icon = ImageIO.read(Objects.requireNonNull(getClass().getResource("/image/logo.jpg")));
            frame.setIconImage(icon);
        } catch (IOException e) {
            e.printStackTrace();
        }

        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);

        JPanel homePanel = createHomePanel();
        loginPanel = createLoginPanel();
        registerPanel = createRegisterPanel();

        // Ajouter les panneaux
        mainPanel.add(registerPanel, "Register");
        mainPanel.add(homePanel, "Accueil");
        mainPanel.add(loginPanel, "Login");

        frame.add(mainPanel);
        frame.setLocationRelativeTo(null);

        // Affiche la page d'accueil au démarrage
        showPage("Accueil");
    }

    private JPanel createHomePanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        loginButton = createStyledButton("Connexion");
        registerButton = createStyledButton("Inscription");
        quitButton = createStyledButton("Quitter");

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(loginButton);
        buttonPanel.add(registerButton);

        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(buttonPanel, gbc);

        gbc.gridy = 1;
        panel.add(quitButton, gbc);

        quitButton.addActionListener(e -> System.exit(0));

        return panel;
    }

    private JPanel createRegisterPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.gridx = 0;
        gbc.gridy = GridBagConstraints.RELATIVE;
        gbc.anchor = GridBagConstraints.CENTER;

        panel.add(new JLabel("Nom:"), gbc);
        nomField = new JTextField(20);
        panel.add(nomField, gbc);

        panel.add(new JLabel("Prénom:"), gbc);
        prenomField = new JTextField(20);
        panel.add(prenomField, gbc);

        panel.add(new JLabel("Email:"), gbc);
        mailField = new JTextField(20);
        panel.add(mailField, gbc);

        panel.add(new JLabel("Mot de passe:"), gbc);
        passwordField = new JPasswordField(20);
        panel.add(passwordField, gbc);

        panel.add(new JLabel("Confirmer mot de passe:"), gbc);
        confirmPasswordField = new JPasswordField(20);
        panel.add(confirmPasswordField, gbc);

        submitRegisterButton = createStyledButton("S'inscrire");
        panel.add(submitRegisterButton, gbc);

        // Message d’erreur ou de succès
        registerMessageLabel = new JLabel("");
        registerMessageLabel.setForeground(Color.RED);
        panel.add(registerMessageLabel, gbc);

        // Bouton retour
        JButton returnButton = createStyledButton("Retour");
        returnButton.addActionListener(e -> showPage("Accueil"));
        panel.add(returnButton, gbc);

        return panel;
    }

    private JPanel createLoginPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.gridx = 0;
        gbc.gridy = GridBagConstraints.RELATIVE;
        gbc.anchor = GridBagConstraints.CENTER;

        JLabel emailLabel = new JLabel("Email :");
        JTextField emailField = new JTextField(20);
        panel.add(emailLabel, gbc);
        panel.add(emailField, gbc);

        JLabel passwordLabel = new JLabel("Mot de passe :");
        JPasswordField passwordField = new JPasswordField(20);
        panel.add(passwordLabel, gbc);
        panel.add(passwordField, gbc);

        JButton submitButton = createStyledButton("Se connecter");
        panel.add(submitButton, gbc);

        JButton returnButton = createStyledButton("Retour");
        returnButton.addActionListener(e -> showPage("Accueil"));
        panel.add(returnButton, gbc);

        return panel;
    }

    private JButton createStyledButton(String text) {
        JButton button = new JButton(text);
        button.setBackground(new Color(245, 245, 220));
        button.setForeground(Color.BLACK);
        button.setFocusPainted(false);
        button.setFont(new Font("Arial", Font.BOLD, 14));
        button.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
        return button;
    }

    public void setVisible(boolean visible) {
        if (frame != null) {
            frame.setVisible(visible);
        }
    }

    public void showPage(String pageName) {
        cardLayout.show(mainPanel, pageName);
    }

    public JFrame getFrame() {
        return frame;
    }

    public JButton getLoginButton() {
        return loginButton;
    }

    public JButton getRegisterButton() {
        return registerButton;
    }

    public JButton getQuitButton() {
        return quitButton;
    }

    public JButton getSubmitRegisterButton() {
        return submitRegisterButton;
    }

    public String getNom() {
        return nomField.getText();
    }

    public String getPrenom() {
        return prenomField.getText();
    }

    public String getEmail() {
        return mailField.getText();
    }

    public String getPassword() {
        return new String(passwordField.getPassword());
    }

    public String getConfirmPassword() {
        return new String(confirmPasswordField.getPassword());
    }

    public void setRegisterMessage(String message, boolean isSuccess) {
        registerMessageLabel.setText(message);
        registerMessageLabel.setForeground(isSuccess ? Color.GREEN : Color.RED);
    }
}
