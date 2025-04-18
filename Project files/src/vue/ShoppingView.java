package vue;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;
import java.util.List;
import controleur.*;
import java.util.ArrayList;
import java.util.LinkedHashMap;


public class ShoppingView {
    private JFrame frame;
    private JPanel mainPanel;
    private CardLayout cardLayout;

    private JButton homeButton, accountButton, panierButton, loginButton, registerButton, searchButton, submitLoginButton, submitRegisterButton, logoutButton;
    private JTextField searchField, emailField, registerEmailField, registerPrenomField, registerNomField;
    private JPasswordField passwordField, registerPasswordField, registerConfirmPasswordField;

    private JPanel homePagePanel, accountPagePanel, panierPagePanel, loginPagePanel, registerPagePanel, commandePagePanel, updateAccountPagePanel;
    private JPanel listPanel; // Class-level field for listPanel
    private Map<String, JLabel> quantiteLabels;
    private JLabel errorMessageLabel;

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
        updateAccountPagePanel = createUpdateAccountPagePanel("","");

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

        JLabel label = new JLabel("Mon Compte", SwingConstants.CENTER);
        label.setFont(new Font("Arial", Font.BOLD, 24));
        label.setBorder(BorderFactory.createEmptyBorder(40, 0, 20, 0));
        panel.add(label, BorderLayout.NORTH);

        JPanel buttonsPanel = new JPanel();
        buttonsPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(15, 20, 15, 20);

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

        listPanel = new JPanel(); // Initialize listPanel
        listPanel.setLayout(new BoxLayout(listPanel, BoxLayout.Y_AXIS));

        JScrollPane scrollPane = new JScrollPane(listPanel);
        panel.add(scrollPane, BorderLayout.CENTER);

        JButton commanderButton = new JButton("Commander");
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


    private JPanel createLoginPagePanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40));

        JLabel titleLabel = new JLabel("Connexion", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        panel.add(titleLabel, BorderLayout.NORTH);

        JPanel formPanel = new JPanel(new GridLayout(3, 2, 10, 10));
        formPanel.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));

        formPanel.add(new JLabel("Email :"));
        emailField = new JTextField();
        formPanel.add(emailField);

        formPanel.add(new JLabel("Mot de passe :"));
        passwordField = new JPasswordField();
        formPanel.add(passwordField);

        // Label pour afficher des messages d'erreur ou d'information sous les champs
        errorMessageLabel = new JLabel();
        errorMessageLabel.setForeground(Color.RED); // Message d'erreur en rouge
        formPanel.add(errorMessageLabel);  // Ajout du label d'erreur à la fin du formulaire

        submitLoginButton = new JButton("Se connecter");
        submitLoginButton.setFont(new Font("Arial", Font.BOLD, 16));
        submitLoginButton.setBackground(new Color(70, 130, 180));
        submitLoginButton.setForeground(Color.WHITE);
        submitLoginButton.setFocusPainted(false);

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(submitLoginButton);

        panel.add(formPanel, BorderLayout.CENTER);
        panel.add(buttonPanel, BorderLayout.SOUTH);

        return panel;
    }

    private JPanel createRegisterPagePanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40));

        JLabel titleLabel = new JLabel("Inscription", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        panel.add(titleLabel, BorderLayout.NORTH);

        JPanel formPanel = new JPanel(new GridLayout(6, 2, 10, 10));
        formPanel.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));

        formPanel.add(new JLabel("Prénom :"));
        registerPrenomField = new JTextField();
        formPanel.add(registerPrenomField);

        formPanel.add(new JLabel("Nom :"));
        registerNomField = new JTextField();
        formPanel.add(registerNomField);

        formPanel.add(new JLabel("Email :"));
        registerEmailField = new JTextField();
        formPanel.add(registerEmailField);

        formPanel.add(new JLabel("Mot de passe :"));
        registerPasswordField = new JPasswordField();
        formPanel.add(registerPasswordField);

        formPanel.add(new JLabel("Confirmer le mot de passe :"));
        registerConfirmPasswordField = new JPasswordField();
        formPanel.add(registerConfirmPasswordField);

        submitRegisterButton = new JButton("S'inscrire");
        submitRegisterButton.setFont(new Font("Arial", Font.BOLD, 16));
        submitRegisterButton.setBackground(new Color(34, 139, 34));
        submitRegisterButton.setForeground(Color.WHITE);
        submitRegisterButton.setFocusPainted(false);

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(submitRegisterButton);

        panel.add(formPanel, BorderLayout.CENTER);
        panel.add(buttonPanel, BorderLayout.SOUTH);

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

    public JPanel createUpdateAccountPagePanel(String userName, String userEmail) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(new Color(245, 245, 245));

        JLabel titleLabel = new JLabel("Mon Compte", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 26));
        titleLabel.setBorder(BorderFactory.createEmptyBorder(20, 0, 10, 0));
        panel.add(titleLabel, BorderLayout.NORTH);

        JPanel userInfoPanel = new JPanel(new GridLayout(2, 2, 10, 10));
        userInfoPanel.setBorder(BorderFactory.createTitledBorder("Informations utilisateur"));
        userInfoPanel.setBackground(Color.WHITE);

        JLabel userNameLabel = new JLabel("Nom :");
        JLabel userNameValue = new JLabel(userName); // Display the user's name
        JLabel userEmailLabel = new JLabel("Email :");
        JLabel userEmailValue = new JLabel(userEmail); // Display the user's email

        userInfoPanel.add(userNameLabel);
        userInfoPanel.add(userNameValue);
        userInfoPanel.add(userEmailLabel);
        userInfoPanel.add(userEmailValue);

        // Use the existing logoutButton instance
        if (logoutButton == null) {
            logoutButton = new JButton("Se déconnecter");
            logoutButton.setFont(new Font("Arial", Font.BOLD, 14));
            logoutButton.setBackground(new Color(220, 53, 69));
            logoutButton.setForeground(Color.WHITE);
            logoutButton.setFocusPainted(false);
        }

        JPanel logoutPanel = new JPanel();
        logoutPanel.setBackground(new Color(245, 245, 245));
        logoutPanel.add(logoutButton);

        panel.add(userInfoPanel, BorderLayout.CENTER);
        panel.add(logoutPanel, BorderLayout.SOUTH);

        return panel;
    }

    public void updateHomePageView(List<Map<String, String>> articles) {
        homePagePanel.removeAll(); // Clear existing content

        JPanel mainContainer = new JPanel();
        mainContainer.setLayout(new BoxLayout(mainContainer, BoxLayout.Y_AXIS));
        mainContainer.setBackground(Color.WHITE);
        mainContainer.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30)); // Padding autour

        Map<String, List<Map<String, String>>> articlesParMarque = new LinkedHashMap<>();
        for (Map<String, String> article : articles) {
            String marque = article.get("marque");
            if (marque == null) marque = "Autres";
            articlesParMarque.putIfAbsent(marque, new ArrayList<>());
            articlesParMarque.get(marque).add(article);
        }

        for (String marque : articlesParMarque.keySet()) {
            JPanel sectionPanel = new JPanel();
            sectionPanel.setLayout(new BoxLayout(sectionPanel, BoxLayout.Y_AXIS));
            sectionPanel.setBackground(Color.WHITE);

            // Titre de la marque, aligné à gauche
            JLabel marqueLabel = new JLabel(marque.toUpperCase());
            marqueLabel.setFont(new Font("SansSerif", Font.BOLD, 18));
            marqueLabel.setAlignmentX(Component.LEFT_ALIGNMENT); // Aligné à gauche
            marqueLabel.setBorder(BorderFactory.createEmptyBorder(10, 0, 5, 0)); // Collé à la liste
            sectionPanel.add(marqueLabel);

            // Contenu ligne d'articles
            JPanel ligneArticles = new JPanel();
            ligneArticles.setLayout(new BoxLayout(ligneArticles, BoxLayout.X_AXIS));
            ligneArticles.setBackground(Color.WHITE);

            for (Map<String, String> article : articlesParMarque.get(marque)) {
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

                String nom = article.getOrDefault("nom", "Nom inconnu");
                String prix = article.getOrDefault("prix", "N/A");

                JLabel nomLabel = new JLabel(nom);
                nomLabel.setFont(new Font("SansSerif", Font.PLAIN, 14));

                JLabel prixLabel = new JLabel("Prix : " + prix + " €");
                prixLabel.setFont(new Font("SansSerif", Font.BOLD, 13));
                prixLabel.setForeground(new Color(34, 139, 34));

                JButton ajouterBtn = new JButton("Ajouter au panier");
                ajouterBtn.setFocusPainted(false);
                ajouterBtn.setBackground(new Color(66, 133, 244));
                ajouterBtn.setForeground(Color.WHITE);
                ajouterBtn.setFont(new Font("SansSerif", Font.PLAIN, 12));
                ajouterBtn.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));

                card.add(nomLabel);
                card.add(Box.createVerticalStrut(5));
                card.add(prixLabel);
                card.add(Box.createVerticalGlue());
                card.add(ajouterBtn);

                ligneArticles.add(card);
                ligneArticles.add(Box.createHorizontalStrut(10));
            }

            // Scroll horizontal
            JScrollPane ligneScroll = new JScrollPane(ligneArticles);
            ligneScroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS); // Activation du scroll horizontal
            ligneScroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
            ligneScroll.getHorizontalScrollBar().setPreferredSize(new Dimension(0, 0));
            ligneScroll.getViewport().setScrollMode(JViewport.BLIT_SCROLL_MODE);
            ligneScroll.setBorder(BorderFactory.createEmptyBorder());
            ligneScroll.setPreferredSize(new Dimension(0, 180));

            // Propager scroll molette
            ligneScroll.addMouseWheelListener(e -> {
                JScrollPane scrollParent = (JScrollPane) SwingUtilities.getAncestorOfClass(JScrollPane.class, homePagePanel);
                if (scrollParent != null) {
                    scrollParent.dispatchEvent(SwingUtilities.convertMouseEvent(e.getComponent(), e, scrollParent));
                }
            });

            // Wrapper avec boutons gauche/droite
            JPanel scrollAvecBoutons = new JPanel(new BorderLayout());
            scrollAvecBoutons.setBackground(Color.WHITE);
            scrollAvecBoutons.setMaximumSize(new Dimension(Integer.MAX_VALUE, 190));

            // Boutons avec emoji flèche et taille réduite (carré)
            JButton btnGauche = new JButton("◁");  // Flèche gauche
            JButton btnDroite = new JButton("▷"); // Flèche droite
            Dimension btnSize = new Dimension(40, 40);  // Taille carrée des boutons
            btnGauche.setPreferredSize(btnSize);
            btnDroite.setPreferredSize(btnSize);

            // Style des boutons
            btnGauche.setFont(new Font("SansSerif", Font.PLAIN, 20));  // Emoji flèche
            btnDroite.setFont(new Font("SansSerif", Font.PLAIN, 20));

            btnGauche.setBackground(new Color(240, 240, 240));
            btnDroite.setBackground(new Color(240, 240, 240));

            btnGauche.setFocusable(false);
            btnDroite.setFocusable(false);

            // Déplacement gauche/droite
            btnGauche.addActionListener(e -> {
                JViewport view = ligneScroll.getViewport();
                Point p = view.getViewPosition();
                int newX = Math.max(0, p.x - 200);
                view.setViewPosition(new Point(newX, p.y));
            });

            btnDroite.addActionListener(e -> {
                JViewport view = ligneScroll.getViewport();
                Point p = view.getViewPosition();
                int maxX = ligneArticles.getWidth() - view.getWidth();
                int newX = Math.min(maxX, p.x + 200);
                view.setViewPosition(new Point(newX, p.y));
            });

            // Ajouter les boutons et le scroll horizontal
            scrollAvecBoutons.add(btnGauche, BorderLayout.WEST);
            scrollAvecBoutons.add(ligneScroll, BorderLayout.CENTER);
            scrollAvecBoutons.add(btnDroite, BorderLayout.EAST);

            sectionPanel.add(scrollAvecBoutons);
            sectionPanel.setAlignmentX(Component.LEFT_ALIGNMENT);

            mainContainer.add(sectionPanel);
        }

        JScrollPane scrollPane = new JScrollPane(mainContainer);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setBorder(null);

        homePagePanel.setLayout(new BorderLayout());
        homePagePanel.add(scrollPane, BorderLayout.CENTER);

        homePagePanel.revalidate();
        homePagePanel.repaint();
    }






    public JTextField getEmailField() {
        return emailField;
    }

    public JPasswordField getPasswordField() {
        return passwordField;
    }

    public JLabel getErrorMessageLabel() {
        return errorMessageLabel;
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
}