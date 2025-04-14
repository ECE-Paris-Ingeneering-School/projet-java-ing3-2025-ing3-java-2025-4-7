package vue;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class ShoppingView {
    private JFrame frame;
    private JPanel mainPanel;
    private CardLayout cardLayout;

    private JButton homeButton, accountButton, panierButton, loginButton, registerButton, searchButton;
    private JTextField searchField;

    private JPanel homePagePanel, accountPagePanel, panierPagePanel, loginPagePanel, registerPagePanel, commandePagePanel;

    private JTextField emailField, registerEmailField;
    private JPasswordField passwordField, registerPasswordField;
    private JButton submitLoginButton, submitRegisterButton;
    private JTextField registerPrenomField, registerNomField;
    private JPasswordField registerConfirmPasswordField;

    private JPanel listPanel;
    private Map<String, JLabel> quantiteLabels;

    public ShoppingView() {
        frame = new JFrame("Shopping App");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);

        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);

        quantiteLabels = new HashMap<>();

        homePagePanel = createHomePagePanel();
        accountPagePanel = createAccountPagePanel();
        panierPagePanel = createPanierPagePanel();
        loginPagePanel = createLoginPagePanel();
        registerPagePanel = createRegisterPagePanel();
        commandePagePanel = createCommandePagePanel();

        mainPanel.add(homePagePanel, "HomePage");
        mainPanel.add(accountPagePanel, "Account");
        mainPanel.add(panierPagePanel, "Panier");
        mainPanel.add(loginPagePanel, "Login");
        mainPanel.add(registerPagePanel, "Register");
        mainPanel.add(commandePagePanel, "Commande");

        JPanel globalPanel = new JPanel(new BorderLayout());
        globalPanel.add(createHeaderPanel(), BorderLayout.NORTH);
        globalPanel.add(mainPanel, BorderLayout.CENTER);

        frame.add(globalPanel);
        frame.setLocationRelativeTo(null);
    }

    private JPanel createHeaderPanel() {
        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(Color.CYAN);

        JPanel leftPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        leftPanel.setOpaque(false);
        homeButton = new JButton("Home");
        leftPanel.add(homeButton);

        JPanel centerPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        centerPanel.setOpaque(false);
        searchField = new JTextField(15);
        searchButton = new JButton("Rechercher");
        centerPanel.add(searchField);
        centerPanel.add(searchButton);

        JPanel rightPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        rightPanel.setOpaque(false);
        accountButton = new JButton("Mon compte");
        panierButton = new JButton("Panier");
        rightPanel.add(accountButton);
        rightPanel.add(panierButton);

        header.add(leftPanel, BorderLayout.WEST);
        header.add(centerPanel, BorderLayout.CENTER);
        header.add(rightPanel, BorderLayout.EAST);

        return header;
    }

    private JPanel createHomePagePanel() {
        JPanel panel = new JPanel(new BorderLayout());
        JLabel label = new JLabel("Bienvenue sur notre site de Shopping", SwingConstants.CENTER);
        label.setFont(new Font("Arial", Font.BOLD, 24));
        panel.add(label, BorderLayout.CENTER);
        return panel;
    }

    private JPanel createAccountPagePanel() {
        JPanel panel = new JPanel(new GridLayout(2, 1));
        JLabel label = new JLabel("Mon Compte", SwingConstants.CENTER);
        label.setFont(new Font("Arial", Font.BOLD, 24));

        JPanel buttonsPanel = new JPanel();
        loginButton = new JButton("Login");
        registerButton = new JButton("Register");
        buttonsPanel.add(loginButton);
        buttonsPanel.add(registerButton);

        panel.add(label);
        panel.add(buttonsPanel);
        return panel;
    }

    private JPanel createPanierPagePanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        listPanel = new JPanel();
        listPanel.setLayout(new BoxLayout(listPanel, BoxLayout.Y_AXIS));

        JScrollPane scrollPane = new JScrollPane(listPanel);
        panel.add(scrollPane, BorderLayout.CENTER);

        JButton commanderButton = new JButton("Commander");
        commanderButton.setFont(new Font("Arial", Font.BOLD, 16));
        commanderButton.setBackground(new Color(70, 130, 180));
        commanderButton.setForeground(Color.WHITE);
        commanderButton.setFocusPainted(false);
        commanderButton.setPreferredSize(new Dimension(150, 40));
        commanderButton.addActionListener(e -> showPage("Commande"));

        JPanel bottomPanel = new JPanel();
        bottomPanel.setBorder(BorderFactory.createEmptyBorder(20, 0, 0, 0));
        bottomPanel.add(commanderButton);

        panel.add(bottomPanel, BorderLayout.SOUTH);
        return panel;
    }

    private JPanel createCommandePagePanel() {
        JPanel panel = new JPanel(new BorderLayout());
        JLabel label = new JLabel("Merci pour votre commande !", SwingConstants.CENTER);
        label.setFont(new Font("Arial", Font.BOLD, 24));
        panel.add(label, BorderLayout.CENTER);
        return panel;
    }

    private JPanel createLoginPagePanel() {
        JPanel panel = new JPanel(new GridLayout(4, 1));
        JLabel label = new JLabel("Connexion", SwingConstants.CENTER);
        label.setFont(new Font("Arial", Font.BOLD, 24));

        emailField = new JTextField();
        passwordField = new JPasswordField();
        submitLoginButton = new JButton("Se connecter");

        panel.add(label);
        panel.add(emailField);
        panel.add(passwordField);
        panel.add(submitLoginButton);
        return panel;
    }

    private JPanel createRegisterPagePanel() {
        JPanel panel = new JPanel(new GridLayout(12, 1));
        JLabel label = new JLabel("Inscription", SwingConstants.CENTER);
        label.setFont(new Font("Arial", Font.BOLD, 24));

        registerPrenomField = new JTextField();
        registerNomField = new JTextField();
        registerEmailField = new JTextField();
        registerPasswordField = new JPasswordField();
        registerConfirmPasswordField = new JPasswordField();
        submitRegisterButton = new JButton("S'inscrire");

        panel.add(label);
        panel.add(new JLabel("Prénom :"));
        panel.add(registerPrenomField);
        panel.add(new JLabel("Nom :"));
        panel.add(registerNomField);
        panel.add(new JLabel("Email :"));
        panel.add(registerEmailField);
        panel.add(new JLabel("Mot de passe :"));
        panel.add(registerPasswordField);
        panel.add(new JLabel("Confirmer le mot de passe :"));
        panel.add(registerConfirmPasswordField);
        panel.add(submitRegisterButton);
        return panel;
    }

    public void showPage(String name) {
        cardLayout.show(mainPanel, name);
    }

    public void setVisible(boolean visible) {
        frame.setVisible(visible);
    }

    public JFrame getFrame() { return frame; }
    public JButton getHomeButton() { return homeButton; }
    public JButton getAccountButton() { return accountButton; }
    public JButton getPanierButton() { return panierButton; }
    public JButton getLoginButton() { return loginButton; }
    public JButton getRegisterButton() { return registerButton; }
    public JButton getSubmitLoginButton() { return submitLoginButton; }
    public JButton getSubmitRegisterButton() { return submitRegisterButton; }

    public String getLoginEmail() { return emailField.getText(); }
    public String getLoginPassword() { return new String(passwordField.getPassword()); }
    public String getRegisterEmail() { return registerEmailField.getText(); }
    public String getRegisterPassword() { return new String(registerPasswordField.getPassword()); }
    public String getRegisterPrenom() { return registerPrenomField.getText(); }
    public String getRegisterNom() { return registerNomField.getText(); }
    public String getRegisterConfirmPassword() { return new String(registerConfirmPasswordField.getPassword()); }

    public JPanel getPanierPagePanel() { return panierPagePanel; }
    public JPanel getListPanel() { return listPanel; }
    public Map<String, JLabel> getQuantiteLabels() { return quantiteLabels; }
}
