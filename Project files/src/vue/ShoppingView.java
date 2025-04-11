package vue;

import javax.swing.*;
import java.awt.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

public class ShoppingView {
    private JFrame frame;
    private JPanel mainPanel;
    private JPanel entryPanel;
    private JPanel loginPanel;
    private JPanel registerPanel;
    private CardLayout cardLayout;
    private JTextField nomField, prenomField, emailField;
    private JPasswordField passwordField, confirmPasswordField;
    private JLabel feedbackLabel;

    private JButton loginButtonOnEntryPanel;
    private JButton registerButtonOnEntryPanel;
    private JButton quitButtonOnEntryPanel;

    private JButton loginButtonOnLoginPanel;
    private JButton returnButtonOnLoginPanel;

    private JButton registerButtonOnRegisterPanel;
    private JButton returnButtonOnRegisterPanel;

    public ShoppingView() {
        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);

        entryPanel = createEntryPanel();
        loginPanel = createLoginPanel();
        registerPanel = createRegisterPanel();

        mainPanel.add(entryPanel, "Entry");
        mainPanel.add(loginPanel, "Login");
        mainPanel.add(registerPanel, "Register");

        createMainFrame();
    }

    private void createMainFrame() {
        frame = new JFrame("Shopping App");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(true);
        frame.setSize(800, 600);
        frame.add(mainPanel);
        frame.setVisible(true);
        showPage("Entry");
    }

    private JPanel createEntryPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;

        loginButtonOnEntryPanel = new JButton("Connexion");
        registerButtonOnEntryPanel = new JButton("Inscription");
        quitButtonOnEntryPanel = new JButton("Quitter");

        quitButtonOnEntryPanel.addActionListener(e -> System.exit(0));

        loginButtonOnEntryPanel.addActionListener(e -> showPage("Login"));
        registerButtonOnEntryPanel.addActionListener(e -> showPage("Register"));

        panel.add(loginButtonOnEntryPanel, gbc);
        panel.add(registerButtonOnEntryPanel, gbc);
        panel.add(quitButtonOnEntryPanel, gbc);

        return panel;
    }

    private JPanel createLoginPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.gridx = 0;
        gbc.gridy = GridBagConstraints.RELATIVE;

        emailField = new JTextField(20);
        passwordField = new JPasswordField(20);

        panel.add(new JLabel("Email:"), gbc);
        panel.add(emailField, gbc);

        panel.add(new JLabel("Mot de passe:"), gbc);
        panel.add(passwordField, gbc);

        loginButtonOnLoginPanel = new JButton("Connexion");
        returnButtonOnLoginPanel = new JButton("Retour");

        returnButtonOnLoginPanel.addActionListener(e -> showPage("Entry"));
        loginButtonOnLoginPanel.addActionListener(e -> {
            // Contrôleur doit valider ici
        });

        panel.add(loginButtonOnLoginPanel, gbc);
        panel.add(returnButtonOnLoginPanel, gbc);

        return panel;
    }

    private JPanel createRegisterPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.gridx = 0;
        gbc.gridy = GridBagConstraints.RELATIVE;

        nomField = new JTextField(20);
        prenomField = new JTextField(20);
        emailField = new JTextField(20);
        passwordField = new JPasswordField(20);
        confirmPasswordField = new JPasswordField(20);
        feedbackLabel = new JLabel(" ");

        panel.add(new JLabel("Nom:"), gbc);
        panel.add(nomField, gbc);
        panel.add(new JLabel("Prénom:"), gbc);
        panel.add(prenomField, gbc);
        panel.add(new JLabel("Email:"), gbc);
        panel.add(emailField, gbc);
        panel.add(new JLabel("Mot de passe:"), gbc);
        panel.add(passwordField, gbc);
        panel.add(new JLabel("Confirmer mot de passe:"), gbc);
        panel.add(confirmPasswordField, gbc);

        panel.add(feedbackLabel, gbc);

        registerButtonOnRegisterPanel = new JButton("S'inscrire");
        registerButtonOnRegisterPanel.addActionListener(e -> {
            // Contrôleur doit valider ici
        });

        returnButtonOnRegisterPanel = new JButton("Retour à l'accueil");
        returnButtonOnRegisterPanel.addActionListener(e -> showPage("Entry"));

        panel.add(registerButtonOnRegisterPanel, gbc);
        panel.add(returnButtonOnRegisterPanel, gbc);

        return panel;
    }

    public void showPage(String pageName) {
        cardLayout.show(mainPanel, pageName);
    }

    public JTextField getNomField() {
        return nomField;
    }

    public JTextField getPrenomField() {
        return prenomField;
    }

    public JTextField getEmailField() {
        return emailField;
    }

    public JPasswordField getPasswordField() {
        return passwordField;
    }

    public JPasswordField getConfirmPasswordField() {
        return confirmPasswordField;
    }

    public JLabel getFeedbackLabel() {
        return feedbackLabel;
    }

    public JButton getLoginButtonOnEntryPanel() {
        return loginButtonOnEntryPanel;
    }

    public JButton getRegisterButtonOnEntryPanel() {
        return registerButtonOnEntryPanel;
    }

    public JButton getQuitButtonOnEntryPanel() {
        return quitButtonOnEntryPanel;
    }

    public JButton getLoginButtonOnLoginPanel() {
        return loginButtonOnLoginPanel;
    }

    public JButton getReturnButtonOnLoginPanel() {
        return returnButtonOnLoginPanel;
    }

    public JButton getRegisterButtonOnRegisterPanel() {
        return registerButtonOnRegisterPanel;
    }

    public JButton getReturnButtonOnRegisterPanel() {
        return returnButtonOnRegisterPanel;
    }

    public JFrame getFrame() {
        return frame;
    }
}
