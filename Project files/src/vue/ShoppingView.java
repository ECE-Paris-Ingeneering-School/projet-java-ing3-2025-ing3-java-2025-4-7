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
    private JLabel welcomeLabel, accountLabel, panierLabel, loginLabel, registerLabel;
    private JPanel headerPanel;

    public ShoppingView() {
        frame = new JFrame("Shopping App");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);

        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);

        // Créer les pages
        homePagePanel = createHomePagePanel();
        accountPagePanel = createAccountPagePanel();
        panierPagePanel = createPanierPagePanel();
        loginPagePanel = createLoginPagePanel();
        registerPagePanel = createRegisterPagePanel();

        // Ajouter les pages au CardLayout
        mainPanel.add(homePagePanel, "HomePage");
        mainPanel.add(accountPagePanel, "Account");
        mainPanel.add(panierPagePanel, "Panier");
        mainPanel.add(loginPagePanel, "Login");
        mainPanel.add(registerPagePanel, "Register");

        // Créer un seul header réutilisable
        headerPanel = createHeaderPanel();

        // Panel global avec header en haut + mainPanel au centre
        JPanel globalPanel = new JPanel(new BorderLayout());
        globalPanel.add(headerPanel, BorderLayout.NORTH);
        globalPanel.add(mainPanel, BorderLayout.CENTER);

        frame.add(globalPanel);
        frame.setLocationRelativeTo(null); // Centrer la fenêtre
    }

    private JPanel createHeaderPanel() {
        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(Color.CYAN);
        headerPanel.setLayout(new FlowLayout(FlowLayout.LEFT));

        homeButton = new JButton("Home");
        searchField = new JTextField(15);
        searchButton = new JButton("Rechercher");
        accountButton = new JButton("Mon compte");
        panierButton = new JButton("Panier");

        headerPanel.add(homeButton);
        headerPanel.add(searchField);
        headerPanel.add(searchButton);
        headerPanel.add(accountButton);
        headerPanel.add(panierButton);

        return headerPanel;
    }

    private JPanel createHomePagePanel() {
        JPanel panel = new JPanel(new BorderLayout());

        JPanel bodyPanel = new JPanel();
        bodyPanel.setBackground(Color.LIGHT_GRAY);
        welcomeLabel = new JLabel("Bienvenue sur notre site de Shopping", SwingConstants.CENTER);
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 24));
        bodyPanel.add(welcomeLabel);

        JPanel footerPanel = new JPanel();
        footerPanel.setBackground(Color.GREEN);
        footerPanel.setPreferredSize(new Dimension(800, 50));

        panel.add(bodyPanel, BorderLayout.CENTER);
        panel.add(footerPanel, BorderLayout.SOUTH);

        return panel;
    }

    private JPanel createAccountPagePanel() {
        JPanel panel = new JPanel(new BorderLayout());

        JPanel bodyPanel = new JPanel();
        bodyPanel.setBackground(Color.LIGHT_GRAY);
        accountLabel = new JLabel("Mon Compte", SwingConstants.CENTER);
        accountLabel.setFont(new Font("Arial", Font.BOLD, 24));
        bodyPanel.add(accountLabel);

        loginButton = new JButton("Login");
        registerButton = new JButton("Register");

        JPanel buttonsPanel = new JPanel();
        buttonsPanel.add(loginButton);
        buttonsPanel.add(registerButton);

        bodyPanel.add(buttonsPanel);

        JPanel footerPanel = new JPanel();
        footerPanel.setBackground(Color.GREEN);
        footerPanel.setPreferredSize(new Dimension(800, 50));

        panel.add(bodyPanel, BorderLayout.CENTER);
        panel.add(footerPanel, BorderLayout.SOUTH);

        return panel;
    }

    private JPanel createPanierPagePanel() {
        JPanel panel = new JPanel(new BorderLayout());

        JPanel bodyPanel = new JPanel();
        bodyPanel.setBackground(Color.LIGHT_GRAY);
        panierLabel = new JLabel("Panier", SwingConstants.CENTER);
        panierLabel.setFont(new Font("Arial", Font.BOLD, 24));
        bodyPanel.add(panierLabel);

        JPanel footerPanel = new JPanel();
        footerPanel.setBackground(Color.GREEN);
        footerPanel.setPreferredSize(new Dimension(800, 50));

        panel.add(bodyPanel, BorderLayout.CENTER);
        panel.add(footerPanel, BorderLayout.SOUTH);

        return panel;
    }

    private JPanel createLoginPagePanel() {
        JPanel panel = new JPanel(new BorderLayout());

        JPanel bodyPanel = new JPanel();
        bodyPanel.setBackground(Color.LIGHT_GRAY);
        loginLabel = new JLabel("Page Login", SwingConstants.CENTER);
        loginLabel.setFont(new Font("Arial", Font.BOLD, 24));
        bodyPanel.add(loginLabel);

        JPanel footerPanel = new JPanel();
        footerPanel.setBackground(Color.GREEN);
        footerPanel.setPreferredSize(new Dimension(800, 50));

        panel.add(bodyPanel, BorderLayout.CENTER);
        panel.add(footerPanel, BorderLayout.SOUTH);

        return panel;
    }

    private JPanel createRegisterPagePanel() {
        JPanel panel = new JPanel(new BorderLayout());

        JPanel bodyPanel = new JPanel();
        bodyPanel.setBackground(Color.LIGHT_GRAY);
        registerLabel = new JLabel("Page Register", SwingConstants.CENTER);
        registerLabel.setFont(new Font("Arial", Font.BOLD, 24));
        bodyPanel.add(registerLabel);

        JPanel footerPanel = new JPanel();
        footerPanel.setBackground(Color.GREEN);
        footerPanel.setPreferredSize(new Dimension(800, 50));

        panel.add(bodyPanel, BorderLayout.CENTER);
        panel.add(footerPanel, BorderLayout.SOUTH);

        return panel;
    }

    public void showPage(String pageName) {
        System.out.println("Affichage de la page : " + pageName);
        cardLayout.show(mainPanel, pageName);
    }

    public JFrame getFrame() {
        return frame;
    }

    public void setVisible(boolean visible) {
        if (frame != null) {
            frame.setVisible(visible);
        }
    }

    public JButton getHomeButton() {
        return homeButton;
    }

    public JTextField getSearchField() {
        return searchField;
    }

    public JButton getSearchButton() {
        return searchButton;
    }

    public JButton getAccountButton() {
        return accountButton;
    }

    public JButton getPanierButton() {
        return panierButton;
    }

    public JButton getLoginButton() {
        return loginButton;
    }

    public JButton getRegisterButton() {
        return registerButton;
    }
}
