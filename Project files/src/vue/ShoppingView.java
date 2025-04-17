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

    private JPanel homePagePanel, accountPagePanel, panierPagePanel, loginPagePanel, registerPagePanel, commandePagePanel, updateAccountPagePanel;

    private JTextField emailField, registerEmailField;
    private JPasswordField passwordField, registerPasswordField;
    private JButton submitLoginButton, submitRegisterButton;
    private JButton logoutButton; // Déclarer le bouton
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
        updateAccountPagePanel = updateAccountPagePanel();

        mainPanel.add(homePagePanel, "HomePage");
        mainPanel.add(accountPagePanel, "Account");
        mainPanel.add(panierPagePanel, "Panier");
        mainPanel.add(loginPagePanel, "Login");
        mainPanel.add(registerPagePanel, "Register");
        mainPanel.add(commandePagePanel, "Commande");
        mainPanel.add(updateAccountPagePanel, "UpdateAccount");

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
        JPanel panel = new JPanel(new BorderLayout());

        // Titre
        JLabel label = new JLabel("Mon Compte", SwingConstants.CENTER);
        label.setFont(new Font("Arial", Font.BOLD, 24));
        label.setBorder(BorderFactory.createEmptyBorder(40, 0, 20, 0));
        panel.add(label, BorderLayout.NORTH);

        // Panel des boutons
        JPanel buttonsPanel = new JPanel();
        buttonsPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(15, 20, 15, 20); // espace entre les boutons

        loginButton = new JButton("Se connecter");
        loginButton.setPreferredSize(new Dimension(160, 40));
        loginButton.setFont(new Font("Arial", Font.PLAIN, 16));

        registerButton = new JButton("S'inscrire");
        registerButton.setPreferredSize(new Dimension(160, 40));
        registerButton.setFont(new Font("Arial", Font.PLAIN, 16));

        gbc.gridx = 0;
        buttonsPanel.add(loginButton, gbc);

        gbc.gridx = 1;
        buttonsPanel.add(registerButton, gbc);

        panel.add(buttonsPanel, BorderLayout.CENTER);

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

        JLabel label = new JLabel("Finalisez votre commande", SwingConstants.CENTER);
        label.setFont(new Font("Arial", Font.BOLD, 24));
        panel.add(label, BorderLayout.NORTH);

        JPanel formPanel = new JPanel();
        formPanel.setLayout(new GridLayout(12, 2, 10, 10));
        formPanel.setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40));

        // Section adresse
        formPanel.add(new JLabel("Adresse (ligne 1) :"));
        JTextField adresseField = new JTextField();
        formPanel.add(adresseField);

        formPanel.add(new JLabel("Code postal :"));
        JTextField codePostalField = new JTextField();
        formPanel.add(codePostalField);

        formPanel.add(new JLabel("Ville :"));
        JTextField villeField = new JTextField();
        formPanel.add(villeField);

        formPanel.add(new JLabel("Pays :"));
        JTextField paysField = new JTextField();
        formPanel.add(paysField);

        // Espacement visuel
        formPanel.add(new JLabel("")); formPanel.add(new JLabel(""));

        // Section paiement
        formPanel.add(new JLabel("Nom et prénom sur la carte :"));
        JTextField nomPrenomCarteField = new JTextField();
        formPanel.add(nomPrenomCarteField);

        formPanel.add(new JLabel("Numéro de carte :"));
        JTextField numeroCarteField = new JTextField();
        formPanel.add(numeroCarteField);

        formPanel.add(new JLabel("Date d'expiration (MM/AA) :"));
        JTextField expirationField = new JTextField();
        formPanel.add(expirationField);

        formPanel.add(new JLabel("Code de sécurité (CVC) :"));
        JTextField cvcField = new JTextField();
        formPanel.add(cvcField);

        // Espacement visuel
        formPanel.add(new JLabel("")); formPanel.add(new JLabel(""));

        // Bouton de validation
        JButton validerButton = new JButton("Valider la commande");
        validerButton.setFont(new Font("Arial", Font.BOLD, 16));
        validerButton.setBackground(new Color(34, 139, 34));
        validerButton.setForeground(Color.WHITE);
        validerButton.setFocusPainted(false);
        validerButton.setPreferredSize(new Dimension(200, 40));

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(validerButton);

        panel.add(formPanel, BorderLayout.CENTER);
        panel.add(buttonPanel, BorderLayout.SOUTH);

        return panel;
    }


    private JPanel createLoginPagePanel() {
        JPanel panel = new JPanel(new BorderLayout());
        // On ne définit pas de couleur ici, donc il garde le fond par défaut

        // Titre
        JLabel label = new JLabel("Connexion", SwingConstants.CENTER);
        label.setFont(new Font("Arial", Font.BOLD, 24));
        label.setBorder(BorderFactory.createEmptyBorder(30, 0, 20, 0));
        panel.add(label, BorderLayout.NORTH);

        // Panel du formulaire
        JPanel formPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Email
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.EAST;
        formPanel.add(new JLabel("Email :"), gbc);

        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        emailField = new JTextField(15);
        formPanel.add(emailField, gbc);

        // Mot de passe
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.EAST;
        formPanel.add(new JLabel("Mot de passe :"), gbc);

        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        passwordField = new JPasswordField(15);
        formPanel.add(passwordField, gbc);

        panel.add(formPanel, BorderLayout.CENTER);

        // Bouton
        submitLoginButton = new JButton("Se connecter");
        submitLoginButton.setFont(new Font("Arial", Font.BOLD, 16));
        submitLoginButton.setBackground(new Color(70, 130, 180));
        submitLoginButton.setForeground(Color.WHITE);
        submitLoginButton.setFocusPainted(false);
        submitLoginButton.setPreferredSize(new Dimension(160, 40));

        JPanel buttonPanel = new JPanel();
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(20, 0, 30, 0));
        buttonPanel.add(submitLoginButton);

        panel.add(buttonPanel, BorderLayout.SOUTH);

        return panel;
    }




    private JPanel createRegisterPagePanel() {
        JPanel panel = new JPanel(new GridBagLayout()); // Centrage global
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.fill = GridBagConstraints.NONE;
        gbc.insets = new Insets(20, 20, 20, 20);

        JPanel formContainer = new JPanel(new GridBagLayout()); // Deux colonnes
        formContainer.setBorder(BorderFactory.createEmptyBorder(30, 60, 30, 60));
        GridBagConstraints innerGbc = new GridBagConstraints();
        innerGbc.insets = new Insets(12, 12, 12, 12); // Espacement vertical & horizontal
        innerGbc.anchor = GridBagConstraints.LINE_END; // Aligner texte à droite

        int row = 0;

        // Titre centré
        JLabel label = new JLabel("Inscription", SwingConstants.CENTER);
        label.setFont(new Font("Arial", Font.BOLD, 26));
        innerGbc.gridx = 0;
        innerGbc.gridy = row++;
        innerGbc.gridwidth = 2;
        innerGbc.anchor = GridBagConstraints.CENTER;
        formContainer.add(label, innerGbc);

        innerGbc.gridwidth = 1;

        // Prénom
        innerGbc.gridy = row++;
        innerGbc.gridx = 0;
        innerGbc.anchor = GridBagConstraints.LINE_END;
        formContainer.add(new JLabel("Prénom :"), innerGbc);
        innerGbc.gridx = 1;
        innerGbc.anchor = GridBagConstraints.LINE_START;
        registerPrenomField = new JTextField(16);
        formContainer.add(registerPrenomField, innerGbc);

        // Nom
        innerGbc.gridy = row++;
        innerGbc.gridx = 0;
        innerGbc.anchor = GridBagConstraints.LINE_END;
        formContainer.add(new JLabel("Nom :"), innerGbc);
        innerGbc.gridx = 1;
        innerGbc.anchor = GridBagConstraints.LINE_START;
        registerNomField = new JTextField(16);
        formContainer.add(registerNomField, innerGbc);

        // Email
        innerGbc.gridy = row++;
        innerGbc.gridx = 0;
        innerGbc.anchor = GridBagConstraints.LINE_END;
        formContainer.add(new JLabel("Email :"), innerGbc);
        innerGbc.gridx = 1;
        innerGbc.anchor = GridBagConstraints.LINE_START;
        registerEmailField = new JTextField(16);
        formContainer.add(registerEmailField, innerGbc);

        // Mot de passe
        innerGbc.gridy = row++;
        innerGbc.gridx = 0;
        innerGbc.anchor = GridBagConstraints.LINE_END;
        formContainer.add(new JLabel("Mot de passe :"), innerGbc);
        innerGbc.gridx = 1;
        innerGbc.anchor = GridBagConstraints.LINE_START;
        registerPasswordField = new JPasswordField(16);
        formContainer.add(registerPasswordField, innerGbc);

        // Confirmation mot de passe
        innerGbc.gridy = row++;
        innerGbc.gridx = 0;
        innerGbc.anchor = GridBagConstraints.LINE_END;
        formContainer.add(new JLabel("Confirmer le mot de passe :"), innerGbc);
        innerGbc.gridx = 1;
        innerGbc.anchor = GridBagConstraints.LINE_START;
        registerConfirmPasswordField = new JPasswordField(16);
        formContainer.add(registerConfirmPasswordField, innerGbc);

        // Bouton centré
        innerGbc.gridy = row++;
        innerGbc.gridx = 0;
        innerGbc.gridwidth = 2;
        innerGbc.anchor = GridBagConstraints.CENTER;
        submitRegisterButton = new JButton("S'inscrire");
        submitRegisterButton.setFont(new Font("Arial", Font.BOLD, 16));
        submitRegisterButton.setBackground(new Color(70, 130, 180));
        submitRegisterButton.setForeground(Color.WHITE);
        formContainer.add(submitRegisterButton, innerGbc);

        panel.add(formContainer, gbc);
        return panel;
    }





    private JPanel updateAccountPagePanel() {
        JPanel panel = new JPanel(new BorderLayout());
        JLabel label = new JLabel("Mon Compte", SwingConstants.CENTER);
        label.setFont(new Font("Arial", Font.BOLD, 24));

        // Création du panneau d'informations de l'utilisateur
        JPanel userInfoPanel = new JPanel(new GridLayout(3, 1));
        JLabel userNameLabel = new JLabel("Nom: Jean Dupont"); // Exemple statique
        JLabel userEmailLabel = new JLabel("Email: jean.dupont@email.com"); // Exemple statique
        userInfoPanel.add(userNameLabel);
        userInfoPanel.add(userEmailLabel);

        // Bouton de logout
        logoutButton = new JButton("Logout");

        // Ajout des éléments au panneau principal de la page de compte
        panel.add(label, BorderLayout.NORTH);
        panel.add(userInfoPanel, BorderLayout.CENTER);  // Ajout du panneau d'infos utilisateur
        panel.add(logoutButton, BorderLayout.SOUTH); // Ajout du bouton logout en bas

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
    public JButton getSearchButton() { return searchButton; }
    public JButton getLogoutButton() { return logoutButton; }


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
