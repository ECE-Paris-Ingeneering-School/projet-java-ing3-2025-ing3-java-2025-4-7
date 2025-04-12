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
    private JPanel homePagePanel; // Panel de la page d'accueil
    private JTextField nomField, prenomField, mailField;
    private JPasswordField passwordField, confirmPasswordField;
    private JLabel registerMessageLabel;
    private JButton submitRegisterButton;
    private JButton submitLoginButton;

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
        homePagePanel = createHomePagePanel(); // Créer la page d'accueil

        // Ajouter les pages au CardLayout
        mainPanel.add(homePanel, "Home");
        mainPanel.add(loginPanel, "Login");
        mainPanel.add(registerPanel, "Register");
        mainPanel.add(homePagePanel, "HomePage"); // Ajouter la page d'accueil

        frame.add(mainPanel);
        frame.setLocationRelativeTo(null); // Centrer la fenêtre
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

    // Page d'accueil
    private JPanel createHomePagePanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());

        // HEADER : Contenant le bouton "Mon compte"
        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(Color.CYAN); // Couleur différente pour le header
        headerPanel.setLayout(new FlowLayout(FlowLayout.RIGHT)); // Alignement à droite
        JButton accountButton = new JButton("Mon compte");
        headerPanel.add(accountButton);

        // BODY : Contenant le texte "Bienvenue"
        JPanel bodyPanel = new JPanel();
        bodyPanel.setBackground(Color.LIGHT_GRAY); // Couleur différente pour le body
        JLabel welcomeLabel = new JLabel("Bienvenue", SwingConstants.CENTER);
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 24));
        bodyPanel.add(welcomeLabel);

        // FOOTER : Contenant un espace vide
        JPanel footerPanel = new JPanel();
        footerPanel.setBackground(Color.GREEN); // Couleur différente pour le footer
        footerPanel.setPreferredSize(new Dimension(800, 50)); // Hauteur du footer

        // Ajout des panels au panneau principal
        panel.add(headerPanel, BorderLayout.NORTH);
        panel.add(bodyPanel, BorderLayout.CENTER);
        panel.add(footerPanel, BorderLayout.SOUTH);

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

        // Label pour afficher les messages d'erreur
        registerMessageLabel = new JLabel("");
        registerMessageLabel.setForeground(Color.RED);
        panel.add(registerMessageLabel, gbc);

        // Bouton retour
        JButton returnButton = createStyledButton("Retour");
        returnButton.addActionListener(e -> showPage("Home"));
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

        submitLoginButton = createStyledButton("Se connecter");
        panel.add(submitLoginButton, gbc);

        JButton returnButton = createStyledButton("Retour");
        returnButton.addActionListener(e -> showPage("Home"));
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

    public JButton getSubmitLoginButton() {
        return submitLoginButton;
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

    public void showHomePage() {
        showPage("HomePage"); // Affiche la page d'accueil
    }

    public void setRegisterMessage(String message) {
        registerMessageLabel.setText(message);
    }

    public void clearRegisterMessage() {
        registerMessageLabel.setText(""); // Effacer le message d'erreur
    }
}
