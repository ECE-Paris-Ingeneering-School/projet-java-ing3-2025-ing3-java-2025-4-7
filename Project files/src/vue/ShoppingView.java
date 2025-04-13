package vue;

import javax.swing.*;
import java.awt.*;

public class ShoppingView {
    private JFrame frame;
    private JPanel mainPanel;
    private CardLayout cardLayout;

    private JButton homeButton, accountButton, panierButton, loginButton, registerButton, searchButton;
    private JTextField searchField;

    private JPanel homePagePanel, accountPagePanel, panierPagePanel, loginPagePanel, registerPagePanel;

    private JTextField emailField, registerEmailField;
    private JPasswordField passwordField, registerPasswordField;
    private JButton submitLoginButton, submitRegisterButton;

    public ShoppingView() {
        frame = new JFrame("Shopping App");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);

        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);

        homePagePanel = createHomePagePanel();
        accountPagePanel = createAccountPagePanel();
        panierPagePanel = createPanierPagePanel();
        loginPagePanel = createLoginPagePanel();
        registerPagePanel = createRegisterPagePanel();

        mainPanel.add(homePagePanel, "HomePage");
        mainPanel.add(accountPagePanel, "Account");
        mainPanel.add(panierPagePanel, "Panier");
        mainPanel.add(loginPagePanel, "Login");
        mainPanel.add(registerPagePanel, "Register");

        JPanel globalPanel = new JPanel(new BorderLayout());
        globalPanel.add(createHeaderPanel(), BorderLayout.NORTH);
        globalPanel.add(mainPanel, BorderLayout.CENTER);

        frame.add(globalPanel);
        frame.setLocationRelativeTo(null);
    }

    private JPanel createHeaderPanel() {
        JPanel header = new JPanel(new FlowLayout(FlowLayout.LEFT));
        header.setBackground(Color.CYAN);

        homeButton = new JButton("Home");
        searchField = new JTextField(15);
        searchButton = new JButton("Rechercher");
        accountButton = new JButton("Mon compte");
        panierButton = new JButton("Panier");

        header.add(homeButton);
        header.add(searchField);
        header.add(searchButton);
        header.add(accountButton);
        header.add(panierButton);

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
        JLabel label = new JLabel("Votre Panier", SwingConstants.CENTER);
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
        JPanel panel = new JPanel(new GridLayout(4, 1));
        JLabel label = new JLabel("Inscription", SwingConstants.CENTER);
        label.setFont(new Font("Arial", Font.BOLD, 24));

        registerEmailField = new JTextField();
        registerPasswordField = new JPasswordField();
        submitRegisterButton = new JButton("S'inscrire");

        panel.add(label);
        panel.add(registerEmailField);
        panel.add(registerPasswordField);
        panel.add(submitRegisterButton);
        return panel;
    }

    public void showPage(String name) {
        cardLayout.show(mainPanel, name);
    }

    public JFrame getFrame() { return frame; }
    public void setVisible(boolean visible) { frame.setVisible(visible); }

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
}
