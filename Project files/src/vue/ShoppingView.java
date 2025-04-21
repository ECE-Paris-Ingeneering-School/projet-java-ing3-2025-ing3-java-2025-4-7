package vue;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;
import java.util.List;
import java.util.ArrayList;
import java.util.LinkedHashMap;


public class ShoppingView {
    private JFrame frame;
    private JPanel mainPanel;
    private CardLayout cardLayout;

    private JButton homeButton, accountButton, panierButton, loginButton, registerButton, searchButton, submitLoginButton, submitRegisterButton, logoutButton, commanderButton, ajouterButton;
    private JTextField searchField, emailField, registerEmailField, registerPrenomField, registerNomField;
    private JPasswordField passwordField, registerPasswordField, registerConfirmPasswordField;

    private JPanel homePagePanel, accountPagePanel, panierPagePanel, loginPagePanel, registerPagePanel, commandePagePanel, updateAccountPagePanel;
    private JPanel listPanel; // Class-level field for listPanel
    private Map<String, JLabel> quantiteLabels;
    private JLabel registerErrorMessageLabel;
    private JLabel loginErrorMessageLabel;

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
        updateAccountPagePanel = null;

        if (logoutButton == null) {
            logoutButton = new JButton("Se déconnecter");
            logoutButton.setFont(new Font("Arial", Font.BOLD, 14));
            logoutButton.setBackground(new Color(220, 53, 69));
            logoutButton.setForeground(Color.WHITE);
            logoutButton.setFocusPainted(false);
            logoutButton.setPreferredSize(new Dimension(150, 40));
        }

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
        searchButton = new JButton("\uD83D\uDD0D");
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
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(40, 40, 40, 40));

        JPanel container = new JPanel(new GridBagLayout());
        container.setBackground(Color.WHITE);
        container.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 200), 1),
                BorderFactory.createEmptyBorder(30, 30, 30, 30)
        ));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(20, 20, 20, 20);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;

        JLabel label = new JLabel("Mon Compte", SwingConstants.CENTER);
        label.setFont(new Font("Arial", Font.BOLD, 26));
        container.add(label, gbc);

        // Bouton Se connecter
        gbc.gridwidth = 1;
        gbc.gridy++;
        gbc.gridx = 0;
        loginButton = new JButton("Se connecter");
        loginButton.setPreferredSize(new Dimension(160, 45));
        loginButton.setFont(new Font("Arial", Font.BOLD, 16));
        loginButton.setBackground(new Color(70, 130, 180)); // Bleu doux
        loginButton.setForeground(Color.WHITE);
        loginButton.setFocusPainted(false);
        loginButton.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        container.add(loginButton, gbc);

        // Bouton S'inscrire
        gbc.gridx = 1;
        registerButton = new JButton("S'inscrire");
        registerButton.setPreferredSize(new Dimension(160, 45));
        registerButton.setFont(new Font("Arial", Font.BOLD, 16));
        registerButton.setBackground(new Color(34, 139, 34)); // Vert doux
        registerButton.setForeground(Color.WHITE);
        registerButton.setFocusPainted(false);
        registerButton.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        container.add(registerButton, gbc);

        panel.add(container);
        return panel;
    }


    private JPanel createPanierPagePanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        listPanel = new JPanel(); // Initialize listPanel
        listPanel.setLayout(new BoxLayout(listPanel, BoxLayout.Y_AXIS));

        JScrollPane scrollPane = new JScrollPane(listPanel);
        panel.add(scrollPane, BorderLayout.CENTER);

        commanderButton = new JButton("Commander");
        commanderButton.setFont(new Font("Arial", Font.BOLD, 16));
        commanderButton.setBackground(new Color(70, 130, 180));
        commanderButton.setForeground(Color.WHITE);
        commanderButton.setFocusPainted(false);
        commanderButton.setPreferredSize(new Dimension(150, 40));

        JPanel bottomPanel = new JPanel();
        bottomPanel.setBorder(BorderFactory.createEmptyBorder(20, 0, 0, 0));
        bottomPanel.add(commanderButton);

        panel.add(bottomPanel, BorderLayout.SOUTH);
        return panel;
    }


    public JPanel createLoginPagePanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(40, 40, 40, 40));

        JPanel formContainer = new JPanel();
        formContainer.setLayout(new GridBagLayout());
        formContainer.setBackground(Color.WHITE);
        formContainer.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 200), 1),
                BorderFactory.createEmptyBorder(30, 30, 30, 30)
        ));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;

        // Titre de la page
        JLabel titleLabel = new JLabel("Connexion", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 26));
        formContainer.add(titleLabel, gbc);

        // Champs Email
        gbc.gridwidth = 1;
        gbc.gridy++;
        formContainer.add(new JLabel("Email :"), gbc);
        gbc.gridx = 1;
        emailField = new JTextField(20);
        formContainer.add(emailField, gbc);

        // Champs Mot de passe
        gbc.gridx = 0;
        gbc.gridy++;
        formContainer.add(new JLabel("Mot de passe :"), gbc);
        gbc.gridx = 1;
        passwordField = new JPasswordField(20);
        formContainer.add(passwordField, gbc);

        // Message d'erreur
        gbc.gridx = 0;
        gbc.gridy++;
        gbc.gridwidth = 2;
        loginErrorMessageLabel = new JLabel("", SwingConstants.CENTER); // Initialisation du label d'erreur
        loginErrorMessageLabel.setForeground(Color.RED);
        loginErrorMessageLabel.setFont(new Font("Arial", Font.ITALIC, 12));
        formContainer.add(loginErrorMessageLabel, gbc);

        // Bouton de connexion
        gbc.gridy++;
        submitLoginButton = new JButton("Se connecter");
        submitLoginButton.setFont(new Font("Arial", Font.BOLD, 16));
        submitLoginButton.setBackground(new Color(70, 130, 180)); // Bleu doux
        submitLoginButton.setForeground(Color.WHITE);
        submitLoginButton.setFocusPainted(false);
        submitLoginButton.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        formContainer.add(submitLoginButton, gbc);

        // Ajouter le formulaire au panel principal
        panel.add(formContainer);

        return panel;
    }




    public JPanel createRegisterPagePanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(40, 40, 40, 40));

        JPanel formContainer = new JPanel();
        formContainer.setLayout(new GridBagLayout());
        formContainer.setBackground(Color.WHITE);
        formContainer.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 200), 1),
                BorderFactory.createEmptyBorder(30, 30, 30, 30)
        ));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;

        JLabel titleLabel = new JLabel("Inscription", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 26));
        formContainer.add(titleLabel, gbc);

        gbc.gridwidth = 1;
        gbc.gridy++;

        // Prénom
        formContainer.add(new JLabel("Prénom :"), gbc);
        gbc.gridx = 1;
        registerPrenomField = new JTextField(20);
        formContainer.add(registerPrenomField, gbc);

        // Nom
        gbc.gridx = 0;
        gbc.gridy++;
        formContainer.add(new JLabel("Nom :"), gbc);
        gbc.gridx = 1;
        registerNomField = new JTextField(20);
        formContainer.add(registerNomField, gbc);

        // Email
        gbc.gridx = 0;
        gbc.gridy++;
        formContainer.add(new JLabel("Email :"), gbc);
        gbc.gridx = 1;
        registerEmailField = new JTextField(20);
        formContainer.add(registerEmailField, gbc);

        // Mot de passe
        gbc.gridx = 0;
        gbc.gridy++;
        formContainer.add(new JLabel("Mot de passe :"), gbc);
        gbc.gridx = 1;
        registerPasswordField = new JPasswordField(20);
        formContainer.add(registerPasswordField, gbc);

        // Confirmation mot de passe
        gbc.gridx = 0;
        gbc.gridy++;
        formContainer.add(new JLabel("Confirmer le mot de passe :"), gbc);
        gbc.gridx = 1;
        registerConfirmPasswordField = new JPasswordField(20);
        formContainer.add(registerConfirmPasswordField, gbc);

        // Message d'erreur
        gbc.gridx = 0;
        gbc.gridy++;
        gbc.gridwidth = 2;
        registerErrorMessageLabel = new JLabel("", SwingConstants.CENTER);
        registerErrorMessageLabel.setForeground(Color.RED);
        registerErrorMessageLabel.setFont(new Font("Arial", Font.ITALIC, 12));
        formContainer.add(registerErrorMessageLabel, gbc);

        // Bouton
        gbc.gridy++;
        submitRegisterButton = new JButton("S'inscrire");
        submitRegisterButton.setFont(new Font("Arial", Font.BOLD, 16));
        submitRegisterButton.setBackground(new Color(34, 139, 34)); // Vert doux
        submitRegisterButton.setForeground(Color.WHITE);
        submitRegisterButton.setFocusPainted(false);
        submitRegisterButton.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        formContainer.add(submitRegisterButton, gbc);

        panel.add(formContainer);
        return panel;
    }



    private JPanel createCommandePagePanel() {
        JPanel panel = new JPanel(new BorderLayout());

        JLabel label = new JLabel("Finalisez votre commande", SwingConstants.CENTER);
        label.setFont(new Font("Arial", Font.BOLD, 24));
        panel.add(label, BorderLayout.NORTH);

        JPanel formPanel = new JPanel(new GridLayout(4, 2, 10, 10));
        formPanel.setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40));

        formPanel.add(new JLabel("Adresse :"));
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

        JButton validerButton = new JButton("Valider la commande");
        validerButton.setFont(new Font("Arial", Font.BOLD, 16));
        validerButton.setBackground(new Color(34, 139, 34));
        validerButton.setForeground(Color.WHITE);
        validerButton.setFocusPainted(false);

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(validerButton);

        panel.add(formPanel, BorderLayout.CENTER);
        panel.add(buttonPanel, BorderLayout.SOUTH);

        return panel;
    }

    public JPanel createUpdateAccountPagePanel(String userName, String userEmail, String userPhone, String userAddress, List<String[]> commandes) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(new Color(245, 245, 245));

        // Titre
        JLabel titleLabel = new JLabel("Mon Compte", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 26));
        titleLabel.setBorder(BorderFactory.createEmptyBorder(20, 0, 10, 0));
        panel.add(titleLabel, BorderLayout.NORTH);

        // Infos utilisateur
        JPanel userInfoPanel = new JPanel(new GridLayout(4, 2, 10, 10));
        userInfoPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.GRAY), "Informations utilisateur"));
        userInfoPanel.setBackground(Color.WHITE);
        userInfoPanel.setPreferredSize(new Dimension(500, 200));

        userInfoPanel.add(new JLabel("Nom :"));
        userInfoPanel.add(new JLabel(userName));
        userInfoPanel.add(new JLabel("Email :"));
        userInfoPanel.add(new JLabel(userEmail));
        userInfoPanel.add(new JLabel("Téléphone :"));
        userInfoPanel.add(new JLabel(userPhone));
        userInfoPanel.add(new JLabel("Adresse :"));
        userInfoPanel.add(new JLabel(userAddress));

        // Panel commandes
        JPanel commandesPanel = new JPanel();
        commandesPanel.setLayout(new BoxLayout(commandesPanel, BoxLayout.Y_AXIS));
        commandesPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.GRAY), "Historique des commandes"));
        commandesPanel.setBackground(Color.WHITE);

        for (String[] infos : commandes) {
            String id = infos[0];
            String date = infos[1];
            String adresse = infos[2];
            String prix = infos[3];
            String statut = infos[4];

            JPanel cardPanel = new JPanel(new BorderLayout(10, 10));
            cardPanel.setBackground(Color.WHITE);
            cardPanel.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
            cardPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 150));

            JPanel topLeftPanel = new JPanel(new GridLayout(5, 1));
            topLeftPanel.setBackground(Color.WHITE);

            JLabel statusLabel = new JLabel("Statut : " + statut);
            statusLabel.setFont(new Font("Arial", Font.BOLD, 12));
            statusLabel.setForeground(new Color(0, 123, 255));

            JLabel numeroLabel = new JLabel("Commande n°" + id);
            JLabel dateLabel = new JLabel("Date : " + date);
            JLabel adresseLabel = new JLabel("Adresse : " + adresse);
            JLabel prixLabel = new JLabel("Prix : " + prix + " €");

            topLeftPanel.add(statusLabel);
            topLeftPanel.add(numeroLabel);
            topLeftPanel.add(dateLabel);
            topLeftPanel.add(adresseLabel);
            topLeftPanel.add(prixLabel);

            cardPanel.add(topLeftPanel, BorderLayout.CENTER);

            JButton viewDetailsButton = new JButton("Voir détails");
            viewDetailsButton.setFont(new Font("Arial", Font.PLAIN, 12));
            viewDetailsButton.setBackground(new Color(0, 123, 255));
            viewDetailsButton.setForeground(Color.WHITE);
            viewDetailsButton.setFocusPainted(false);
            viewDetailsButton.setPreferredSize(new Dimension(120, 40));

            JPanel bottomRightPanel = new JPanel(new BorderLayout());
            bottomRightPanel.setBackground(Color.WHITE);
            bottomRightPanel.add(viewDetailsButton, BorderLayout.EAST);

            cardPanel.add(bottomRightPanel, BorderLayout.SOUTH);
            commandesPanel.add(cardPanel);
        }

        JScrollPane commandesScrollPane = new JScrollPane(commandesPanel);
        commandesScrollPane.setBorder(null);
        commandesPanel.setPreferredSize(new Dimension(600, 300));



        JPanel logoutPanel = new JPanel();
        logoutPanel.setBackground(new Color(245, 245, 245));
        logoutPanel.add(logoutButton);

        JPanel centerPanel = new JPanel(new BorderLayout(10, 10));
        centerPanel.setBackground(new Color(245, 245, 245));
        centerPanel.add(userInfoPanel, BorderLayout.NORTH);
        centerPanel.add(commandesScrollPane, BorderLayout.CENTER);

        panel.add(centerPanel, BorderLayout.CENTER);
        panel.add(logoutPanel, BorderLayout.SOUTH);

        return panel;
    }









    public void updateHomePageView(List<Map<String, String>> articles, ActionListener ajouterPanierListener) {
        homePagePanel.removeAll();  // On vide le panneau existant

        JPanel mainContainer = new JPanel();
        mainContainer.setLayout(new BoxLayout(mainContainer, BoxLayout.Y_AXIS));
        mainContainer.setBackground(Color.WHITE);
        mainContainer.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));

        // Grouper les articles par marque
        Map<String, List<Map<String, String>>> articlesParMarque = new LinkedHashMap<>();
        for (Map<String, String> articleData : articles) {
            String marque = articleData.get("marque");
            System.out.println(articleData.get("id"));
            if (marque == null) marque = "Autres";
            articlesParMarque.putIfAbsent(marque, new ArrayList<>());
            articlesParMarque.get(marque).add(articleData);
        }

        // Affichage des sections par marque
        for (String marque : articlesParMarque.keySet()) {
            JPanel sectionPanel = new JPanel();
            sectionPanel.setLayout(new BoxLayout(sectionPanel, BoxLayout.Y_AXIS));
            sectionPanel.setBackground(Color.WHITE);

            JLabel marqueLabel = new JLabel(marque.toUpperCase());
            marqueLabel.setFont(new Font("SansSerif", Font.BOLD, 18));
            marqueLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
            marqueLabel.setBorder(BorderFactory.createEmptyBorder(10, 0, 5, 0));
            sectionPanel.add(marqueLabel);

            JPanel ligneArticles = new JPanel();
            ligneArticles.setLayout(new BoxLayout(ligneArticles, BoxLayout.X_AXIS));
            ligneArticles.setBackground(Color.WHITE);

            // Affichage des articles sous chaque marque
            for (Map<String, String> articleData : articlesParMarque.get(marque)) {
                JPanel card = new JPanel();
                card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
                card.setPreferredSize(new Dimension(180, 150));
                card.setMaximumSize(new Dimension(180, 150));
                card.setBorder(BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(new Color(220, 220, 220)),
                        BorderFactory.createEmptyBorder(10, 10, 10, 10)
                ));
                card.setBackground(new Color(245, 245, 250));
                card.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

                JLabel nomLabel = new JLabel(articleData.get("nom"));
                nomLabel.setFont(new Font("SansSerif", Font.PLAIN, 14));

                JLabel prixLabel = new JLabel("Prix : " + articleData.get("prix"));
                prixLabel.setFont(new Font("SansSerif", Font.BOLD, 13));
                prixLabel.setForeground(new Color(34, 139, 34));

                JButton ajouterButton = new JButton("Ajouter au panier");
                ajouterButton.setFocusPainted(false);
                ajouterButton.setBackground(new Color(66, 133, 244));
                ajouterButton.setForeground(Color.WHITE);
                ajouterButton.setFont(new Font("SansSerif", Font.PLAIN, 12));
                ajouterButton.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));

                // Définir l'ID de l'article comme ActionCommand
                ajouterButton.setActionCommand(articleData.get("id"));
                // Attacher l'ActionListener passé depuis le contrôleur
                ajouterButton.addActionListener(ajouterPanierListener);

                card.add(nomLabel);
                card.add(Box.createVerticalStrut(5));
                card.add(prixLabel);
                card.add(Box.createVerticalGlue());
                card.add(ajouterButton);

                ligneArticles.add(card);
                ligneArticles.add(Box.createHorizontalStrut(10));
            }

            JScrollPane ligneScroll = new JScrollPane(ligneArticles);
            ligneScroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
            ligneScroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
            ligneScroll.getHorizontalScrollBar().setPreferredSize(new Dimension(0, 0));
            ligneScroll.setBorder(BorderFactory.createEmptyBorder());
            ligneScroll.setPreferredSize(new Dimension(0, 180));

            sectionPanel.add(ligneScroll);
            sectionPanel.setAlignmentX(Component.LEFT_ALIGNMENT);

            mainContainer.add(sectionPanel);
        }

        homePagePanel.add(mainContainer);
        homePagePanel.revalidate();
        homePagePanel.repaint();
    }


    public void afficherPageCompte(String nom, String email, String tel, String adresse, List<String[]> historique) {
        if (updateAccountPagePanel != null) {
            mainPanel.remove(updateAccountPagePanel);
        }

        updateAccountPagePanel = createUpdateAccountPagePanel(nom, email, tel, adresse, historique);
        mainPanel.add(updateAccountPagePanel, "UpdateAccount");
        cardLayout.show(mainPanel, "UpdateAccount");
    }

    public void updatePanierPageView(List<Map<String, String>> articles) {
        panierPagePanel.removeAll();  // Clear the existing panel

        JPanel mainContainer = new JPanel();
        mainContainer.setLayout(new BoxLayout(mainContainer, BoxLayout.Y_AXIS));
        mainContainer.setBackground(Color.WHITE);
        mainContainer.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));

        for (Map<String, String> articleData : articles) {
            JPanel articlePanel = new JPanel();
            articlePanel.setLayout(new BoxLayout(articlePanel, BoxLayout.X_AXIS));
            articlePanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
            articlePanel.setBackground(new Color(245, 245, 250));

            JLabel nomLabel = new JLabel(articleData.get("nom"));
            nomLabel.setFont(new Font("SansSerif", Font.BOLD, 14));

            JLabel quantiteLabel = new JLabel("Quantité: " + articleData.get("quantite"));
            quantiteLabel.setFont(new Font("SansSerif", Font.PLAIN, 12));

            JLabel prixLabel = new JLabel("Prix: " + articleData.get("prix"));
            prixLabel.setFont(new Font("SansSerif", Font.PLAIN, 12));
            prixLabel.setForeground(new Color(34, 139, 34));

            articlePanel.add(nomLabel);
            articlePanel.add(Box.createHorizontalStrut(20));
            articlePanel.add(quantiteLabel);
            articlePanel.add(Box.createHorizontalStrut(20));
            articlePanel.add(prixLabel);

            mainContainer.add(articlePanel);
            mainContainer.add(Box.createVerticalStrut(10));
        }

        JScrollPane scrollPane = new JScrollPane(mainContainer);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());

        panierPagePanel.add(scrollPane, BorderLayout.CENTER);

        // Ajouter le bouton "Commander" en bas
        JPanel bottomPanel = new JPanel();
        bottomPanel.setBorder(BorderFactory.createEmptyBorder(20, 0, 0, 0));
        bottomPanel.add(commanderButton);
        panierPagePanel.add(bottomPanel, BorderLayout.SOUTH);

        panierPagePanel.revalidate();
        panierPagePanel.repaint();
    }



    public JTextField getEmailField() {
        return emailField;
    }

    public JPasswordField getPasswordField() {
        return passwordField;
    }

    public JLabel getRegisterErrorMessageLabel() {
        return registerErrorMessageLabel;
    }

    public void showPage(String name) {
        cardLayout.show(mainPanel, name);
    }

    public void setVisible(boolean visible) {
        frame.setVisible(visible);
    }

    public JButton getHomeButton() {
        return homeButton;
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
    // Getter pour prenomField
    public JTextField getRegisterPrenomField() {
        return registerPrenomField;
    }

    // Getter pour nomField
    public JTextField getRegisterNomField() {
        return registerNomField;
    }

    // Getter pour emailField
    public JTextField getRegisterEmailField() {
        return registerEmailField;
    }

    // Getter pour passwordField
    public JPasswordField getRegisterPasswordField() {
        return registerPasswordField;
    }

    // Getter pour confirmPasswordField
    public JPasswordField getRegisterConfirmPasswordField() {
        return registerConfirmPasswordField;
    }

    public JButton getRegisterButton() {
        return registerButton;
    }

    public JButton getSubmitLoginButton() {
        return submitLoginButton;
    }

    public JButton getSubmitRegisterButton() {
        return submitRegisterButton;
    }

    public JButton getLogoutButton() {
        return logoutButton;
    }

    public JPanel getPanierPagePanel() {
        return panierPagePanel;
    }

    public JPanel getListPanel() {
        return listPanel;
    }

    public JPanel getMainPanel() {
        return mainPanel;
    }
    public JPanel getHomePagePanel() {
        return homePagePanel;
    }

    public JButton getSearchButton() {
        return searchButton;
    }

    public String getSearchText() {
        return searchField.getText();
    }

    public JLabel getLoginErrorMessageLabel() {
        return loginErrorMessageLabel;
    }

    public JButton getCommanderButton() {return commanderButton;}

    public JButton getAjouterButton() {return ajouterButton;}

}