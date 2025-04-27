package vue;

//modif temp
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;
import java.util.List;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import java.net.URL;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;


public class ShoppingView extends JFrame{
    //Frame principal
    private JFrame frame;
    private JPanel mainPanel;
    private CardLayout cardLayout;

    // Boutons
    private JButton homeButton;
    private JButton accountButton;
    private JButton panierButton;
    private JButton loginButton;
    private JButton registerButton;
    private JButton searchButton;
    private JButton submitLoginButton;
    private JButton submitRegisterButton;
    private JButton logoutButton;
    private JButton commanderButton;
    private JButton ajouterButton;
    private JButton adminButton;
    private JButton saveButton;
    private JButton plusBtn;
    private JButton minusBtn;
    private JButton supprimerButton;

    // Champs texte et password
    private JTextField searchField;
    private JTextField emailField;
    private JTextField registerEmailField;
    private JTextField registerPrenomField;
    private JTextField registerNomField;
    private JPasswordField passwordField;
    private JPasswordField registerPasswordField;
    private JPasswordField registerConfirmPasswordField;

    // Panneaux
    private JPanel homePagePanel;
    private JPanel accountPagePanel;
    private JPanel panierPagePanel;
    private JPanel loginPagePanel;
    private JPanel registerPagePanel;
    private JPanel commandePagePanel;
    private JPanel updateAccountPagePanel;
    private JPanel adminPagePanel;
    private JPanel listPanel; // Panneau pour la liste du panier

    // Labels
    private JLabel registerErrorMessageLabel;
    private JLabel loginErrorMessageLabel;
    private Map<String, JLabel> quantiteLabels = new HashMap<>();

    // Table admin
    private JTable adminTable;

    // Listes de boutons pour actions
    private List<JButton> rateButtons = new ArrayList<>();
    private List<JButton> viewDetailsButtons = new ArrayList<>();


    // Constructeur
    public ShoppingView(){
        frame = new JFrame("PokeShop App");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);

        //Icone de l'app
        try{
            ImageIcon icon = new ImageIcon("Project files/src/image/pokeball.png");
            frame.setIconImage(icon.getImage());
        } catch (Exception e){
            System.err.println("Vue - Erreur de chargement de l'icône : " + e.getMessage());
        }

        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);

        //panneaux de l'app
        homePagePanel = createHomePagePanel();
        accountPagePanel = createAccountPagePanel();
        panierPagePanel = createPanierPagePanel();
        loginPagePanel = createLoginPagePanel();
        registerPagePanel = createRegisterPagePanel();
        commandePagePanel = createCommandePagePanel();
        updateAccountPagePanel = null; // Sera créé dynamiquement plus tard
        adminPagePanel = createAdminPagePanel();

        //bouton logout (pb disparition compte)
        if (logoutButton == null){
            logoutButton = new JButton("Se déconnecter");
            logoutButton.setFont(new Font("Arial", Font.BOLD, 14));
            logoutButton.setBackground(new Color(220, 53, 69));
            logoutButton.setForeground(Color.WHITE);
            logoutButton.setFocusPainted(false);
            logoutButton.setPreferredSize(new Dimension(150, 40));
        }

        //ajout des pannels au cardLayout
        mainPanel.add(homePagePanel, "HomePage");
        mainPanel.add(accountPagePanel, "Account");
        mainPanel.add(panierPagePanel, "Panier");
        mainPanel.add(loginPagePanel, "Login");
        mainPanel.add(registerPagePanel, "Register");
        mainPanel.add(commandePagePanel, "Commande");
        mainPanel.add(adminPagePanel, "AdminPage");

        //création du main pannel
        JPanel globalPanel = new JPanel(new BorderLayout());
        globalPanel.add(createHeaderPanel(), BorderLayout.NORTH);
        globalPanel.add(mainPanel, BorderLayout.CENTER);

        frame.add(globalPanel);
        frame.setLocationRelativeTo(null);
    }

    private JPanel createHeaderPanel(){
        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(new Color(38, 74, 193));  // Change la couleur de fond en bleu pour un style plus moderne
        header.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));

        //logo a gauche
        JPanel leftPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        leftPanel.setOpaque(false);
        ImageIcon originalIcon = new ImageIcon("Project files/src/image/logoAPP.png");
        if (originalIcon.getImageLoadStatus() != MediaTracker.COMPLETE){
            System.out.println("Vue - logo site n'a pas pu être chargée !");
        }

        int newWidth = 120;
        int newHeight = 50;
        Image resizedImage = originalIcon.getImage().getScaledInstance(newWidth, newHeight, Image.SCALE_SMOOTH);
        ImageIcon resizedIcon = new ImageIcon(resizedImage);

        homeButton = new JButton(resizedIcon);
        homeButton.setToolTipText("Accueil");
        homeButton.setBorderPainted(false);
        homeButton.setContentAreaFilled(false);
        homeButton.setFocusPainted(false);
        homeButton.setOpaque(false);
        leftPanel.add(homeButton);

        //barre de recherche et bouton
        JPanel centerPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        centerPanel.setOpaque(false);

        searchField = new JTextField(15);
        searchField.setFont(new Font("Arial", Font.PLAIN, 14));
        searchField.setPreferredSize(new Dimension(250, 30));
        searchField.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));  // Ajouter une bordure
        searchField.setBackground(Color.WHITE);

        searchButton = new JButton("\uD83D\uDD0D");
        searchButton.setFont(new Font("Arial", Font.PLAIN, 16));
        searchButton.setPreferredSize(new Dimension(40, 30));
        searchButton.setFocusPainted(false);
        searchButton.setBackground(new Color(40, 167, 69));
        searchButton.setForeground(Color.WHITE);
        searchButton.setOpaque(true);
        searchButton.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));

        centerPanel.add(searchField);
        centerPanel.add(searchButton);

        //boutons compte, panier, admin
        JPanel rightPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        rightPanel.setOpaque(false);

        //compte
        ImageIcon compteIcon = new ImageIcon("Project files/src/image/utilisateur.png");
        Image resizedCompteImage = compteIcon.getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH);
        ImageIcon resizedCompteIcon = new ImageIcon(resizedCompteImage);
        accountButton = new JButton(resizedCompteIcon);
        accountButton.setToolTipText("Mon compte");
        accountButton.setBorderPainted(false);
        accountButton.setContentAreaFilled(false);
        accountButton.setFocusPainted(false);
        accountButton.setOpaque(false);

        //panier
        ImageIcon panierIcon = new ImageIcon("Project files/src/image/panier/panier.png");
        Image resizedPanierImage = panierIcon.getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH);
        ImageIcon resizedPanierIcon = new ImageIcon(resizedPanierImage);
        panierButton = new JButton(resizedPanierIcon);
        panierButton.setToolTipText("Panier");
        panierButton.setBorderPainted(false);
        panierButton.setContentAreaFilled(false);
        panierButton.setFocusPainted(false);
        panierButton.setOpaque(false);

        //admin
        ImageIcon adminIcon = new ImageIcon("Project files/src/image/boutonadmin1.png");
        Image resizedAdminImage = adminIcon.getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH);
        ImageIcon resizedAdminIcon = new ImageIcon(resizedAdminImage);
        adminButton = new JButton(resizedAdminIcon);
        adminButton.setToolTipText("Admin");
        adminButton.setBorderPainted(false);
        adminButton.setContentAreaFilled(false);
        adminButton.setFocusPainted(false);
        adminButton.setOpaque(false);

        rightPanel.add(accountButton);
        rightPanel.add(panierButton);
        rightPanel.add(adminButton);

        header.add(leftPanel, BorderLayout.WEST);
        header.add(centerPanel, BorderLayout.CENTER);
        header.add(rightPanel, BorderLayout.EAST);

        //Hover pour esthétique
        addHoverEffect(homeButton);
        addHoverEffect(accountButton);
        addHoverEffect(panierButton);
        addHoverEffect(adminButton);
        addHoverEffect(searchButton);

        return header;
    }


    private JPanel createHomePagePanel(){
        JPanel panel = new JPanel(new BorderLayout());
        JLabel label = new JLabel("Bienvenue sur Pokéshop !", SwingConstants.CENTER);
        label.setFont(new Font("Arial", Font.BOLD, 24));
        panel.add(label, BorderLayout.CENTER);
        return panel;
    }


    private JPanel createAccountPagePanel(){
        //pannel
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(40, 40, 40, 40));

        //conteneur
        JPanel container = new JPanel(new GridBagLayout());
        container.setBackground(Color.WHITE);
        container.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 200), 1),
                BorderFactory.createEmptyBorder(30, 30, 30, 30)
        ));

        //grille
        GridBagConstraints grille = new GridBagConstraints();
        grille.insets = new Insets(20, 20, 20, 20);
        grille.gridx = 0;
        grille.gridy = 0;
        grille.gridwidth = 2;

        //titre
        JLabel label = new JLabel("Mon Compte", SwingConstants.CENTER);
        label.setFont(new Font("Arial", Font.BOLD, 26));
        container.add(label, grille);

        // Bouton Se connecter
        grille.gridwidth = 1;
        grille.gridy++;
        grille.gridx = 0;
        loginButton = new JButton("Se connecter");
        loginButton.setPreferredSize(new Dimension(160, 45));
        loginButton.setFont(new Font("Arial", Font.BOLD, 16));
        loginButton.setBackground(new Color(70, 130, 180));
        loginButton.setForeground(Color.WHITE);
        loginButton.setFocusPainted(false);
        loginButton.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        container.add(loginButton, grille);

        // Bouton S'inscrire
        grille.gridx = 1;
        registerButton = new JButton("S'inscrire");
        registerButton.setPreferredSize(new Dimension(160, 45));
        registerButton.setFont(new Font("Arial", Font.BOLD, 16));
        registerButton.setBackground(new Color(70, 130, 180));
        registerButton.setForeground(Color.WHITE);
        registerButton.setFocusPainted(false);
        registerButton.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        container.add(registerButton, grille);

        panel.add(container);
        return panel;
    }


    private JPanel createPanierPagePanel(){
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        //liste articles
        listPanel = new JPanel(); // Initialize listPanel
        listPanel.setLayout(new BoxLayout(listPanel, BoxLayout.Y_AXIS));

        //scroll
        JScrollPane scrollPane = new JScrollPane(listPanel);
        panel.add(scrollPane, BorderLayout.CENTER);

        //bouton commander
        commanderButton = new JButton("Commander");
        commanderButton.setFont(new Font("Arial", Font.BOLD, 16));
        commanderButton.setBackground(new Color(38, 74, 193));
        commanderButton.setForeground(Color.WHITE);
        commanderButton.setFocusPainted(false);
        commanderButton.setPreferredSize(new Dimension(150, 40));

        JPanel bottomPanel = new JPanel();
        bottomPanel.setBorder(BorderFactory.createEmptyBorder(20, 0, 0, 0));
        bottomPanel.add(commanderButton);

        panel.add(bottomPanel, BorderLayout.SOUTH);
        return panel;
    }


    public JPanel createLoginPagePanel(){
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(40, 40, 40, 40));

        //conteneur
        JPanel formContainer = new JPanel();
        formContainer.setLayout(new GridBagLayout());
        formContainer.setBackground(Color.WHITE);
        formContainer.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 200), 1),
                BorderFactory.createEmptyBorder(30, 30, 30, 30)
        ));

        //grille
        GridBagConstraints grille = new GridBagConstraints();
        grille.insets = new Insets(10, 10, 10, 10);
        grille.fill = GridBagConstraints.HORIZONTAL;
        grille.gridx = 0;
        grille.gridy = 0;
        grille.gridwidth = 2;

        //titre
        JLabel titleLabel = new JLabel("Connexion", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 26));
        formContainer.add(titleLabel, grille);

        //champs du form
        grille.gridwidth = 1;
        grille.gridy++;
        formContainer.add(new JLabel("Email :"), grille);
        grille.gridx = 1;
        emailField = new JTextField(20);
        formContainer.add(emailField, grille);

        grille.gridx = 0;
        grille.gridy++;
        formContainer.add(new JLabel("Mot de passe :"), grille);
        grille.gridx = 1;
        passwordField = new JPasswordField(20);
        formContainer.add(passwordField, grille);

        //label si erreur
        grille.gridx = 0;
        grille.gridy++;
        grille.gridwidth = 2;
        loginErrorMessageLabel = new JLabel("", SwingConstants.CENTER);
        loginErrorMessageLabel.setForeground(Color.RED);
        loginErrorMessageLabel.setFont(new Font("Arial", Font.ITALIC, 12));
        formContainer.add(loginErrorMessageLabel, grille);

        grille.gridy++;
        submitLoginButton = new JButton("Se connecter");
        submitLoginButton.setFont(new Font("Arial", Font.BOLD, 16));
        submitLoginButton.setBackground(new Color(70, 130, 180)); // Bleu doux
        submitLoginButton.setForeground(Color.WHITE);
        submitLoginButton.setFocusPainted(false);
        submitLoginButton.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        formContainer.add(submitLoginButton, grille);

        // Ajouter le formulaire au panel principal
        panel.add(formContainer);

        return panel;
    }


    public JPanel createRegisterPagePanel(){
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(40, 40, 40, 40));

        //container
        JPanel formContainer = new JPanel();
        formContainer.setLayout(new GridBagLayout());
        formContainer.setBackground(Color.WHITE);
        formContainer.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 200), 1),
                BorderFactory.createEmptyBorder(30, 30, 30, 30)
        ));

        //grille
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;

        //titre
        JLabel titleLabel = new JLabel("Inscription", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 26));
        formContainer.add(titleLabel, gbc);

        //form
        gbc.gridwidth = 1;
        gbc.gridy++;

        formContainer.add(new JLabel("Prénom :"), gbc);
        gbc.gridx = 1;
        registerPrenomField = new JTextField(20);
        formContainer.add(registerPrenomField, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        formContainer.add(new JLabel("Nom :"), gbc);
        gbc.gridx = 1;
        registerNomField = new JTextField(20);
        formContainer.add(registerNomField, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        formContainer.add(new JLabel("Email :"), gbc);
        gbc.gridx = 1;
        registerEmailField = new JTextField(20);
        formContainer.add(registerEmailField, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        formContainer.add(new JLabel("Mot de passe :"), gbc);
        gbc.gridx = 1;
        registerPasswordField = new JPasswordField(20);
        formContainer.add(registerPasswordField, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        formContainer.add(new JLabel("Confirmer le mot de passe :"), gbc);
        gbc.gridx = 1;
        registerConfirmPasswordField = new JPasswordField(20);
        formContainer.add(registerConfirmPasswordField, gbc);

        //label si erreur
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

    //methode fantome
    private JPanel createCommandePagePanel(){
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


    public JPanel createUpdateAccountPagePanel(String userName, String userEmail, String userPhone, String userAddress, List<String[]> commandes){
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(new Color(245, 245, 245));

        //titre
        JLabel titleLabel = new JLabel("Mon Compte", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 26));
        titleLabel.setBorder(BorderFactory.createEmptyBorder(20, 0, 10, 0));
        panel.add(titleLabel, BorderLayout.NORTH);

        //pannel infos
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

        //pannel historique
        JPanel commandesPanel = new JPanel();
        commandesPanel.setLayout(new BoxLayout(commandesPanel, BoxLayout.Y_AXIS));
        commandesPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.GRAY), "Historique des commandes"));
        commandesPanel.setBackground(Color.WHITE);

        //clear des boutons pour cliquer plusieurs fois
        rateButtons.clear();
        viewDetailsButtons.clear();

        //création de cards commandes
        for (String[] infos : commandes){
            String id = infos[0];
            String date = infos[1];
            String adresse = infos[2];
            String prix = infos[3];
            String statut = infos[4];

            JPanel cardPanel = new JPanel(new BorderLayout(10, 10));
            cardPanel.setBackground(Color.WHITE);
            cardPanel.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
            cardPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 170));

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

            JPanel bottomButtonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 5));
            bottomButtonPanel.setBackground(Color.WHITE);

            //bouton details
            JButton viewDetailsButton = new JButton("Voir détails");
            viewDetailsButton.setFont(new Font("Arial", Font.PLAIN, 12));
            viewDetailsButton.setBackground(new Color(0, 123, 255));
            viewDetailsButton.setForeground(Color.WHITE);
            viewDetailsButton.setFocusPainted(false);
            viewDetailsButton.setPreferredSize(new Dimension(180, 30));

            //bouton noter
            JButton rateButton = new JButton("Noter");
            rateButton.setFont(new Font("Arial", Font.PLAIN, 12));
            rateButton.setBackground(new Color(40, 167, 69));
            rateButton.setForeground(Color.WHITE);
            rateButton.setFocusPainted(false);
            rateButton.setPreferredSize(new Dimension(100, 30));

            //gestion des boutons pour controleur
            viewDetailsButton.setName(id);
            rateButton.setName(id);
            viewDetailsButtons.add(viewDetailsButton);
            rateButtons.add(rateButton);
            bottomButtonPanel.add(viewDetailsButton);
            bottomButtonPanel.add(rateButton);
            cardPanel.add(bottomButtonPanel, BorderLayout.SOUTH);
            commandesPanel.add(cardPanel);
        }

        JScrollPane commandesScrollPane = new JScrollPane(commandesPanel);
        commandesScrollPane.setBorder(null);
        commandesScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        commandesScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

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


    public JPanel createAdminPagePanel(){
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel titleLabel = new JLabel("Gestion des Articles", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        panel.add(titleLabel, BorderLayout.NORTH);

        //table articles
        String[] columnNames = {"ID", "Nom", "Marque", "Prix Unitaire", "Prix Vrac", "Seuil Vrac", "Stock", "Disponible","lien image", "Modifier"};
        adminTable = new JTable(new DefaultTableModel(columnNames, 0));
        JScrollPane scrollPane = new JScrollPane(adminTable);
        panel.add(scrollPane, BorderLayout.CENTER);

        //bouton modifier
        saveButton = new JButton("Enregistrer les modifications");
        saveButton.setFont(new Font("Arial", Font.BOLD, 16));
        saveButton.setBackground(new Color(34, 139, 34));
        saveButton.setForeground(Color.WHITE);
        saveButton.setFocusPainted(false);
        saveButton.setPreferredSize(new Dimension(250, 40));

        //bouton ajouter
        ajouterButton = new JButton("Ajouter un article");
        ajouterButton.setFont(new Font("Arial", Font.BOLD, 16));
        ajouterButton.setBackground(new Color(70, 130, 180));
        ajouterButton.setForeground(Color.WHITE);
        ajouterButton.setFocusPainted(false);
        ajouterButton.setPreferredSize(new Dimension(150, 40));

        //bouton supprimer
        supprimerButton = new JButton("Supprimer un article");
        supprimerButton.setFont(new Font("Arial", Font.BOLD, 16));
        supprimerButton.setBackground(new Color(220, 53, 69));
        supprimerButton.setForeground(Color.WHITE);
        supprimerButton.setFocusPainted(false);
        supprimerButton.setPreferredSize(new Dimension(200, 40));

        JPanel bottomPanel = new JPanel();
        bottomPanel.add(saveButton);
        bottomPanel.add(ajouterButton);
        bottomPanel.add(supprimerButton);
        panel.add(bottomPanel, BorderLayout.SOUTH);

        return panel;
    }


    public void updateHomePageView(List<Map<String, String>> articles, ActionListener ajouterPanierListener){
        homePagePanel.removeAll();

        //bandeau bienvenue
        JPanel bandeau = new JPanel(new FlowLayout(FlowLayout.LEFT));
        bandeau.setBackground(new Color(66, 133, 244));
        JLabel navbarLabel = new JLabel("Bienvenue sur Pokéshop ! Code PROMO spécial d'ouverture : WELCOME10");
        navbarLabel.setFont(new Font("SansSerif", Font.BOLD, 18));
        navbarLabel.setForeground(Color.WHITE);
        bandeau.add(navbarLabel);

        //container articles
        JPanel mainContainer = new JPanel();
        mainContainer.setLayout(new BoxLayout(mainContainer, BoxLayout.Y_AXIS));
        mainContainer.setBackground(Color.WHITE);
        mainContainer.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));

        //regrouper par 'marque'
        Map<String, List<Map<String, String>>> articlesParMarque = new LinkedHashMap<>();
        for (Map<String, String> article : articles) {
            String marque = article.getOrDefault("marque", "Autres");
            articlesParMarque.putIfAbsent(marque, new ArrayList<>());
            articlesParMarque.get(marque).add(article);
        }

        //remplissage
        for (String marque : articlesParMarque.keySet()) {
            JPanel sectionPanel = new JPanel();
            sectionPanel.setLayout(new BoxLayout(sectionPanel, BoxLayout.Y_AXIS));
            sectionPanel.setBackground(Color.WHITE);

            //creation ligne
            JLabel marqueLabel = new JLabel(marque.toUpperCase());
            marqueLabel.setFont(new Font("SansSerif", Font.BOLD, 18));
            marqueLabel.setBorder(BorderFactory.createEmptyBorder(10, 0, 5, 0));
            sectionPanel.add(marqueLabel);
            JPanel ligneArticles = new JPanel();
            ligneArticles.setLayout(new BoxLayout(ligneArticles, BoxLayout.X_AXIS));
            ligneArticles.setBackground(Color.WHITE);

            //remplissage ligne
            for (Map<String, String> article : articlesParMarque.get(marque)) {
                JPanel card = new JPanel();
                card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
                card.setPreferredSize(new Dimension(220, 380));
                card.setMaximumSize(new Dimension(220, 380));
                card.setBorder(BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(new Color(220, 220, 220)),
                        BorderFactory.createEmptyBorder(10, 10, 10, 10)
                ));
                card.setBackground(new Color(245, 245, 250));
                card.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

                //nom
                JLabel nomLabel = new JLabel(article.get("nom"));
                nomLabel.setFont(new Font("SansSerif", Font.BOLD, 15));
                nomLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
                nomLabel.setHorizontalAlignment(SwingConstants.CENTER);
                nomLabel.setMaximumSize(new Dimension(200, 40));
                card.add(nomLabel);
                card.add(Box.createVerticalStrut(10));

                //image
                String imageURL = article.get("articleImageURL");
                if (imageURL != null && !imageURL.isEmpty()) {
                    try {
                        URL url = new URL(imageURL);
                        ImageIcon icon = new ImageIcon(url);
                        if (icon.getImageLoadStatus() == MediaTracker.COMPLETE) {
                            Image scaled = icon.getImage().getScaledInstance(180, 240, Image.SCALE_SMOOTH);
                            JLabel imageLabel = new JLabel(new ImageIcon(scaled));
                            imageLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

                            JPanel imageContainer = new JPanel();
                            imageContainer.setPreferredSize(new Dimension(180, 240));
                            imageContainer.setMaximumSize(new Dimension(180, 240));
                            imageContainer.setBackground(Color.WHITE);
                            imageContainer.add(imageLabel);

                            card.add(imageContainer);
                        }
                    } catch (Exception e) {
                        System.out.println("Erreur chargement image: " + e.getMessage());
                        card.setBackground(Color.RED);
                    }
                }

                card.add(Box.createVerticalStrut(10));

                //prix normal
                JLabel prixLabel = new JLabel("Prix: " + article.get("prixUnitaire"));
                prixLabel.setFont(new Font("SansSerif", Font.BOLD, 14));
                prixLabel.setForeground(new Color(34, 139, 34));
                prixLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

                //prix vrac
                String seuil = article.get("seuilVrac");
                String prixVrac = article.get("prixVrac");
                JLabel prixVracLabel = new JLabel("Prix vrac ("+seuil+"+) : " + prixVrac);
                prixVracLabel.setFont(new Font("SansSerif", Font.PLAIN, 12));
                prixVracLabel.setForeground(Color.GRAY);
                prixVracLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

                card.add(prixLabel);
                card.add(prixVracLabel);
                card.add(Box.createVerticalStrut(10));

                //bouton panier
                JButton ajouterButton = new JButton("Ajouter au panier");
                ajouterButton.setFocusPainted(false);
                ajouterButton.setBackground(new Color(66, 133, 244));
                ajouterButton.setForeground(Color.WHITE);
                ajouterButton.setFont(new Font("SansSerif", Font.PLAIN, 12));
                ajouterButton.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
                ajouterButton.setAlignmentX(Component.CENTER_ALIGNMENT);
                ajouterButton.setActionCommand(article.get("id"));
                ajouterButton.addActionListener(ajouterPanierListener);

                card.add(ajouterButton);
                ligneArticles.add(card);
                ligneArticles.add(Box.createHorizontalStrut(10));
            }

            //scroll
            JScrollPane ligneScroll = new JScrollPane(ligneArticles);
            ligneScroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
            ligneScroll.getHorizontalScrollBar().setPreferredSize(new Dimension(0, 0));
            ligneScroll.setBorder(BorderFactory.createEmptyBorder());
            ligneScroll.setPreferredSize(new Dimension(0, 420)); // hauteur plus grande pour scroll correct

            sectionPanel.add(ligneScroll);
            sectionPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
            mainContainer.add(sectionPanel);
        }

        JScrollPane pageScroll = new JScrollPane(mainContainer);
        pageScroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        pageScroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        homePagePanel.setLayout(new BorderLayout());
        homePagePanel.add(bandeau, BorderLayout.NORTH);
        homePagePanel.add(pageScroll, BorderLayout.CENTER);

        homePagePanel.revalidate();
        homePagePanel.repaint();
    }


    public void afficherPageCompte(String nom, String email, String tel, String adresse, List<String[]> historique){
        if (updateAccountPagePanel != null) {
            mainPanel.remove(updateAccountPagePanel);
        }

        updateAccountPagePanel = createUpdateAccountPagePanel(nom, email, tel, adresse, historique);
        mainPanel.add(updateAccountPagePanel, "UpdateAccount");
        cardLayout.show(mainPanel, "UpdateAccount");
    }

    public void updatePanierPageView(List<Map<String, String>> articles, ActionListener plusListener, ActionListener minusListener){
        double totalPrix = 0.0;
        panierPagePanel.removeAll();

        //container
        JPanel mainContainer = new JPanel();
        mainContainer.setLayout(new GridLayout(0, 2, 20, 20));
        mainContainer.setBackground(Color.WHITE);
        mainContainer.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));

        //articles
        for (Map<String, String> articleData : articles){
            JPanel card = new JPanel(new BorderLayout());
            card.setPreferredSize(new Dimension(400, 300));
            card.setBorder(BorderFactory.createLineBorder(new Color(220, 220, 220), 1));
            card.setBackground(Color.WHITE);

            //panel image
            JPanel imageContainer = new JPanel(new BorderLayout());
            imageContainer.setBackground(Color.WHITE);

            //panel info
            JPanel infoContainer = new JPanel();
            infoContainer.setBackground(Color.WHITE);

            //TODO: mauvaise gestion prix total?
            try {
                String prixRaw = articleData.get("prixUnitaire");
                String quantiteRaw = articleData.get("quantite");
                if (prixRaw != null && quantiteRaw != null) {
                    String prixStr = prixRaw.replace("€", "").replace(",", ".").replaceAll("[^\\d.]", "").trim();
                    double prix = Double.parseDouble(prixStr);
                    int quantite = Integer.parseInt(quantiteRaw.trim());
                    totalPrix += prix * quantite;
                }
            } catch (Exception e) {
                System.out.println("Erreur calcul du total : " + e.getMessage());
            }

            //image
            String imageURL = articleData.get("imageUrl");
            if (imageURL != null && !imageURL.isEmpty()) {
                try {
                    URL url = new URL(imageURL);
                    ImageIcon icon = new ImageIcon(url);
                    if (icon.getImageLoadStatus() == MediaTracker.COMPLETE) {
                        Image scaled = icon.getImage().getScaledInstance(180, 240, Image.SCALE_SMOOTH);
                        JLabel imageLabel = new JLabel(new ImageIcon(scaled));

                        imageContainer.add(imageLabel, BorderLayout.CENTER);
                    }
                } catch (Exception e) {
                    System.out.println("Error loading image: " + e.getMessage());
                }
            }

            //informations
            infoContainer.setLayout(new BoxLayout(infoContainer, BoxLayout.Y_AXIS));
            infoContainer.setBackground(Color.WHITE);
            infoContainer.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));
            infoContainer.setAlignmentX(Component.RIGHT_ALIGNMENT);

            JLabel nomLabel = new JLabel(articleData.get("nom"));
            nomLabel.setFont(new Font("SansSerif", Font.BOLD, 22));
            nomLabel.setBorder(BorderFactory.createEmptyBorder(5, 0, 5, 0));
            JPanel nomPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 5));
            nomPanel.setBackground(Color.WHITE);
            nomPanel.add(nomLabel);

            JPanel boutonsPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 5, 5));
            boutonsPanel.setBackground(Color.WHITE);

            // Bouton pour ajouter des articles
            ImageIcon plusIcon = new ImageIcon("Project files/src/image/panier/panierAjouter.png");
            Image resizedPlusImage = plusIcon.getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH);
            ImageIcon resizedPlusIcon = new ImageIcon(resizedPlusImage);

            JButton plusBtn = new JButton(resizedPlusIcon);
            plusBtn.setToolTipText("Ajouter un élément du panier");
            plusBtn.setBorderPainted(false);
            plusBtn.setContentAreaFilled(false);
            plusBtn.setFocusPainted(false);

            // Bouton pou retirer des articles
            ImageIcon minIcon = new ImageIcon("Project files/src/image/panier/panierSupprimer.png");
            Image resizedMinImage = minIcon.getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH);
            ImageIcon resizedMinIcon = new ImageIcon(resizedMinImage);

            JButton minusBtn = new JButton(resizedMinIcon);
            minusBtn.setToolTipText("Supprimer un élément du panier");
            minusBtn.setBorderPainted(false);
            minusBtn.setContentAreaFilled(false);
            minusBtn.setFocusPainted(false);

            // Dimensions des boutons + et -
            plusBtn.setPreferredSize(new Dimension(60, 60));
            minusBtn.setPreferredSize(new Dimension(60, 60));

            // Logique des quantités et des prix
            final int initialQuantity = Integer.parseInt(articleData.get("quantite"));
            final double priceUnit = Double.parseDouble(articleData.get("prixUnitaire").replace(" €", "").replace(",", "."));
            final double priceVrac = Double.parseDouble(articleData.get("prixVrac").replace(" €", "").replace(",", "."));
            final int seuilVrac = Integer.parseInt(articleData.get("seuilVrac"));

            double prixAffiche = (initialQuantity >= seuilVrac) ? priceVrac : priceUnit;

            final JLabel quantiteLabel = new JLabel("    Quantité: " + initialQuantity);
            quantiteLabel.setFont(new Font("SansSerif", Font.PLAIN, 15));

            final JLabel prixUnitaireLabel = new JLabel("    Prix unitaire: " + String.format("%.2f €", priceUnit));
            prixUnitaireLabel.setFont(new Font("SansSerif", Font.PLAIN, 15));

            final JLabel prixVracLabel = new JLabel("    Prix vrac: " + String.format("%.2f €", priceVrac));
            prixVracLabel.setFont(new Font("SansSerif", Font.PLAIN, 15));

            final JLabel prixSeuilLabel = new JLabel("    Seuil vrac: " + seuilVrac);
            prixSeuilLabel.setFont(new Font("SansSerif", Font.PLAIN, 15));

            final JLabel prixTotalLabel = new JLabel("    Prix total: " + String.format("%.2f €", prixAffiche * initialQuantity));
            prixTotalLabel.setFont(new Font("SansSerif", Font.BOLD, 17));
            prixTotalLabel.setForeground(new Color(48, 80, 207));

            JPanel quantitePanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 5));
            quantitePanel.setBackground(Color.WHITE);
            quantitePanel.add(quantiteLabel);

            JPanel prixUnitairePanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 5));
            prixUnitairePanel.setBackground(Color.WHITE);
            prixUnitairePanel.add(prixUnitaireLabel);

            JPanel prixVracPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 5));
            prixVracPanel.setBackground(Color.WHITE);
            prixVracPanel.add(prixVracLabel);

            JPanel prixSeuilPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 5));
            prixSeuilPanel.setBackground(Color.WHITE);
            prixSeuilPanel.add(prixSeuilLabel);

            JPanel prixTotalPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 5));
            prixTotalPanel.setBackground(Color.WHITE);
            prixTotalPanel.add(prixTotalLabel);

            //definition listeners pour controleur
            String articleId = articleData.get("id");
            plusBtn.setActionCommand(articleId);
            minusBtn.setActionCommand(articleId);

            plusBtn.addActionListener(plusListener);
            minusBtn.addActionListener(minusListener);

            boutonsPanel.add(minusBtn);
            boutonsPanel.add(plusBtn);

            infoContainer.add(nomPanel);
            infoContainer.add(quantitePanel);
            infoContainer.add(prixUnitairePanel);
            infoContainer.add(prixVracPanel);
            infoContainer.add(prixSeuilPanel);
            infoContainer.add(prixTotalPanel);
            infoContainer.add(boutonsPanel);

            // Configuration du JSplitPane
            JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, imageContainer, infoContainer);
            splitPane.setResizeWeight(0.5);
            splitPane.setDividerSize(0);
            splitPane.setContinuousLayout(true);
            splitPane.setEnabled(false);
            splitPane.setBackground(Color.WHITE);

            card.add(splitPane, BorderLayout.CENTER);
            mainContainer.add(card);
        }

        JScrollPane scrollPane = new JScrollPane(mainContainer);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());

        panierPagePanel.add(scrollPane, BorderLayout.CENTER);

        // Panel du bas de page avec le boutton commander et le total de la commande
        JLabel totalLabel = new JLabel("Total de la commande : " + String.format("%.2f €", totalPrix));
        totalLabel.setFont(new Font("Arial", Font.BOLD, 16));
        totalLabel.setForeground(new Color(38, 74, 193));
        totalLabel.setBorder(BorderFactory.createEmptyBorder(5, 20, 0, 0));

        JPanel totalPanel = new JPanel();
        totalPanel.setBackground(Color.WHITE);
        totalPanel.add(totalLabel, BorderLayout.EAST);

        JSplitPane bottomPanel = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, totalPanel, commanderButton);
        bottomPanel.setResizeWeight(0.8);
        bottomPanel.setDividerSize(5);
        bottomPanel.setContinuousLayout(true);
        bottomPanel.setEnabled(false);
        bottomPanel.setBackground(Color.WHITE);

        panierPagePanel.add(bottomPanel, BorderLayout.SOUTH);

        // Ajoute le panel dans le container qui contient les articles
        mainContainer.add(Box.createVerticalStrut(20));

        panierPagePanel.revalidate();
        panierPagePanel.repaint();
    }

    //effet de hover sur les boutons
    private void addHoverEffect(JButton button){
        button.addMouseListener(new MouseAdapter(){
            @Override
            public void mouseEntered(MouseEvent e){
                button.setBackground(new Color(0, 123, 255)); // Change la couleur
                button.setCursor(new Cursor(Cursor.HAND_CURSOR)); // Curseur main
            }

            @Override
            public void mouseExited(MouseEvent e){
                button.setBackground(new Color(23, 162, 184)); // Remet la couleur originale
                button.setCursor(new Cursor(Cursor.DEFAULT_CURSOR)); // Curseur normal
            }
        });
    }


    public JTextField getEmailField() { return emailField; }

    public JPasswordField getPasswordField() { return passwordField; }

    public JLabel getRegisterErrorMessageLabel() { return registerErrorMessageLabel; }

    public void showPage(String name) { cardLayout.show(mainPanel, name); }

    public void setVisible(boolean visible) { frame.setVisible(visible); }

    public JButton getHomeButton() { return homeButton; }

    public JButton getAccountButton() { return accountButton; }

    public JButton getPanierButton() { return panierButton; }

    public JButton getLoginButton() { return loginButton; }

    public JTextField getRegisterPrenomField() { return registerPrenomField; }

    public JTextField getRegisterNomField() { return registerNomField; }

    public JTextField getRegisterEmailField() { return registerEmailField; }

    public JPasswordField getRegisterPasswordField() { return registerPasswordField; }

    public JPasswordField getRegisterConfirmPasswordField() { return registerConfirmPasswordField; }

    public JButton getRegisterButton() { return registerButton; }

    public JButton getSubmitLoginButton() { return submitLoginButton; }

    public JButton getSubmitRegisterButton() { return submitRegisterButton; }

    public JButton getLogoutButton() { return logoutButton; }

    public JButton getSearchButton() { return searchButton; }

    public String getSearchText() { return searchField.getText(); }

    public JLabel getLoginErrorMessageLabel() { return loginErrorMessageLabel; }

    public JButton getCommanderButton() {return commanderButton;}

    public JButton getAjouterButton() {return ajouterButton;}

    public JButton getAdminButton(){return adminButton;}

    public JTable getAdminTable() { return adminTable; }

    public JButton getSaveButton() { return saveButton; }

    public JButton getSupprimerButton() { return supprimerButton; }

    public List<JButton> getRateButtons() { return rateButtons; }

    public List<JButton> getViewDetailsButtons() { return viewDetailsButtons; }

}
