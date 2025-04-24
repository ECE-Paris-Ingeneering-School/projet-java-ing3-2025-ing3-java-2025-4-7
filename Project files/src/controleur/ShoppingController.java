package controleur;

import vue.*;
import modele.*;
import DAO.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;
import java.util.Map;
import java.util.ArrayList;
import java.util.HashMap;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;

public class ShoppingController {
    private ShoppingView view;
    private Utilisateur utilisateurConnecte;
    private UtilisateurDAOImpl utilisateurDAO;
    private CommandeDAOImpl commandeDAO;
    private ArticleDAOImpl articleDAO;
    private DaoFactory daoFactory;

    public ShoppingController(ShoppingView view) {
        this.view = view;
        this.utilisateurConnecte = null;
        this.daoFactory = DaoFactory.getInstance("projetshoppingjava", "root", "");
        this.utilisateurDAO = new UtilisateurDAOImpl(this.daoFactory);
        this.commandeDAO = new CommandeDAOImpl(this.daoFactory);
        this.articleDAO = new ArticleDAOImpl(this.daoFactory);

        //Test des fonctions de commandedao
        //print toutes les info:
        List<Commande> commandes = commandeDAO.getAll();
        for (Commande commande : commandes) {
            System.out.println("ID: " + commande.getId() + ", Utilisateur ID: " + commande.getUtilisateurID() +
                    ", Date: " + commande.getDate() + ", Statut: " + commande.getStatut() +
                    ", Liste ID Articles: " + commande.getListeID_Article() +
                    ", Liste Quantité Articles: " + commande.getListeQuantite_Article() +
                    ", Prix: " + commande.getPrix());
        }

        initializeListeners();
        afficherAccueil();
    }

    private void initializeListeners() {
        view.getHomeButton().addActionListener(e -> {afficherAccueil();});

        view.getAccountButton().addActionListener(e -> {
            if (utilisateurConnecte != null) {

                view.showPage("UpdateAccount");
            } else {
                view.showPage("Account");
            }
        });

        view.getPanierButton().addActionListener(e -> afficherPanier());

        view.getLoginButton().addActionListener(e -> view.showPage("Login"));
        view.getRegisterButton().addActionListener(e -> view.showPage("Register"));


        view.getSubmitLoginButton().addActionListener(e -> handleLogin());
        view.getSubmitRegisterButton().addActionListener(e -> handleRegister());
        view.getAccountButton().addActionListener(e -> afficherCompte());

        view.getSearchButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String searchText = view.getSearchText();  // Récupère le texte de la zone de recherche
                filterArticles(searchText);  // Filtrer les articles en fonction du texte de recherche
            }
        });
        view.getLogoutButton().addActionListener(e -> handleLogout());
        view.getCommanderButton().addActionListener(e -> handleCommanderButton());
        view.getAdminButton().addActionListener(e -> afficherPageAdmin());
        view.getSaveButton().addActionListener(e -> {
            JTable table = view.getAdminTable();
            DefaultTableModel model = (DefaultTableModel) table.getModel();

            for (int i = 0; i < model.getRowCount(); i++) {
                try {
                    int id = parseInteger(model.getValueAt(i, 0));
                    String nom = model.getValueAt(i, 1).toString();
                    String marque = model.getValueAt(i, 2).toString();
                    double prixUnitaire = parseDouble(model.getValueAt(i, 3));
                    double prixVrac = parseDouble(model.getValueAt(i, 4));
                    int seuilVrac = parseInteger(model.getValueAt(i, 5));
                    int stock = parseInteger(model.getValueAt(i, 6));
                    boolean isAvailable = Boolean.parseBoolean(model.getValueAt(i, 7).toString());
                    String imageURL = model.getValueAt(i, 8).toString();

                    // Create an Article object and update it in the database
                    Article article = new Article(id, nom, marque, prixUnitaire, prixVrac, seuilVrac, stock, isAvailable, imageURL);
                    System.out.println(article.getIsAvailable());
                    articleDAO.modifier(article);
                } catch (NumberFormatException ex) {
                    System.err.println("Invalid data in table row " + i + ": " + ex.getMessage());
                }
            }

            JOptionPane.showMessageDialog(null, "Modifications enregistrées avec succès !");
        });

        view.getAjouterButton().addActionListener(e -> handleAjouterArticle());

    }

    private void handleLogin() {
        String email = view.getEmailField().getText().trim();
        String password = new String(view.getPasswordField().getPassword()).trim();

        // Obtention du label d'erreur spécifique à la connexion
        JLabel loginErrorMessageLabel = (JLabel) view.getLoginErrorMessageLabel();

        if (email.isEmpty() || password.isEmpty()) {
            loginErrorMessageLabel.setText("Veuillez remplir tous les champs.");
            return;
        }

        try {
            // Chercher l'utilisateur dans la base de données
            utilisateurConnecte = utilisateurDAO.chercherLogin(email, password);

            if (utilisateurConnecte != null) {
                // Une fois l'utilisateur authentifié, afficher ses informations
                loginErrorMessageLabel.setText(""); // Nettoyer le message d'erreur

                // Redirection vers la page "Mon Compte"
                afficherAccueil();
            } else {
                loginErrorMessageLabel.setText("Email ou mot de passe incorrect.");
            }
        } catch (Exception ex) {
            loginErrorMessageLabel.setText("Erreur lors de la connexion. Veuillez réessayer.");
        }
    }



    private void handleRegister() {
        String prenom = view.getRegisterPrenomField().getText().trim();
        String nom = view.getRegisterNomField().getText().trim();
        String email = view.getRegisterEmailField().getText().trim();
        String mdp = new String(view.getRegisterPasswordField().getPassword());
        String confirmMdp = new String(view.getRegisterConfirmPasswordField().getPassword());

        if (prenom.isEmpty() || nom.isEmpty() || email.isEmpty() || mdp.isEmpty() || confirmMdp.isEmpty()) {
            view.getRegisterErrorMessageLabel().setText("Tous les champs sont obligatoires.");
            return;
        }

        if (!email.matches("^[\\w-.]+@([\\w-]+\\.)+[\\w-]{2,4}$")) {
            view.getRegisterErrorMessageLabel().setText("Adresse email invalide.");
            return;
        }

        if (!mdp.equals(confirmMdp)) {
            view.getRegisterErrorMessageLabel().setText("Les mots de passe ne correspondent pas.");
            return;
        }

        if (mdp.length() < 8) {
            view.getRegisterErrorMessageLabel().setText("Le mot de passe doit contenir au moins 8 caractères.");
            return;
        }

        try {
            // ✅ Vérification si email déjà utilisé
            Utilisateur existingUser = utilisateurDAO.chercherParEmail(email);
            if (existingUser != null) {
                view.getRegisterErrorMessageLabel().setText("Cet email est déjà utilisé.");
                return;
            }

            utilisateurConnecte = new Utilisateur(email, mdp, nom, prenom, "adresse", 1234567890, false);
            boolean success = utilisateurDAO.ajouter(utilisateurConnecte);
            if (success) {
                view.getRegisterErrorMessageLabel().setText(""); // Nettoyer les erreurs
                afficherAccueil();
            } else {
                view.getRegisterErrorMessageLabel().setText("Erreur lors de l'inscription. Veuillez réessayer.");
            }
        } catch (Exception ex) {
            view.getRegisterErrorMessageLabel().setText("Erreur technique. Veuillez réessayer.");
        }
    }


    private void filterArticles(String searchText) {
        // Récupérer tous les articles depuis la base de données
        ArticleDAO articleDAO = new ArticleDAOImpl(daoFactory);
        List<Article> articles = articleDAO.getAll();

        // Création d'une liste pour stocker les articles filtrés
        List<Map<String, String>> articlesFiltrés = new ArrayList<>();

        // Filtrage des articles en fonction du nom, de la marque, de la disponibilité et du stock
        for (Article article : articles) {
            // Vérifier si le texte de recherche correspond soit au nom ou à la marque de l'article
            boolean matchesName = article.getNom().toLowerCase().contains(searchText.toLowerCase());
            boolean matchesBrand = article.getMarque().toLowerCase().contains(searchText.toLowerCase());
            boolean isAvailable = article.getIsAvailable() && article.getStock() > 0;

            if ((matchesName || matchesBrand) && isAvailable) {
                Map<String, String> data = new HashMap<>();
                data.put("nom", article.getNom());
                data.put("marque", article.getMarque());
                data.put("prix", String.format("%.2f €", article.getPrixUnitaire()));
                data.put("stock", String.valueOf(article.getStock()));

                articlesFiltrés.add(data);
            }
        }

        // Mettre à jour la vue avec les articles filtrés
        view.updateHomePageView(articlesFiltrés, e -> handleCommander(e.getActionCommand()));
        afficherAccueil();
    }


    private void afficherAccueil() {
        // Check if the connected user is an admin
        // Hide the admin button
        if(utilisateurConnecte != null && utilisateurConnecte.getIsAdmin()) {
            view.getAdminButton().setVisible(true); // Show the admin button
            System.out.println("Admin Button Visible: " + view.getAdminButton().isVisible());
        } else {
            view.getAdminButton().setVisible(false); // Hide the admin button
            System.out.println("tedvhb");
        }
        // Crée une instance de ArticleDAO pour récupérer tous les articles
        ArticleDAO articleDAO = new ArticleDAOImpl(daoFactory);
        List<Article> articles = articleDAO.getAll();  // Liste de tous les articles depuis la base de données

        // Création d'une liste pour stocker les articles disponibles formatés
        List<Map<String, String>> articlesFormates = new ArrayList<>();

        // Parcours de chaque article pour vérifier sa disponibilité et son stock
        for (Article article : articles) {
            if (article.getIsAvailable() && article.getStock() > 0) {  // Si l'article est disponible et a du stock
                Map<String, String> data = new HashMap<>();
                data.put("id",String.valueOf(article.getId()));  // ID de l'article
                data.put("nom", article.getNom());  // Nom de l'article
                data.put("marque", article.getMarque());  // Marque de l'article
                data.put("prix", String.format("%.2f €", article.getPrixUnitaire()));  // Prix unitaire
                data.put("prixVrac", String.format("%.2f €", article.getPrixVrac()));  // Prix en vrac
                data.put("seuilVrac", String.valueOf(article.getSeuilVrac()));  // Seuil de vrac
                data.put("stock", String.valueOf(article.getStock()));  // Stock restant
                data.put("articleImageURL", article.getImageUrl());

                // Ajout de l'article formaté à la liste
                articlesFormates.add(data);
            }
        }

        // Mise à jour de la vue avec les articles filtrés
        view.updateHomePageView(articlesFormates, e -> handleCommander(e.getActionCommand()));
        view.showPage("HomePage");  // Affichage de la page d'accueil
    }



    private void afficherCompte() {
        if (utilisateurConnecte != null) {
            // Récupérer les informations personnelles de l'utilisateur
            String userName = utilisateurConnecte.getNom();
            String userEmail = utilisateurConnecte.getEmail();
            String userTel = String.valueOf(utilisateurConnecte.getTelephone());
            String userAddress = utilisateurConnecte.getAdresse();

            // Récupérer l'historique des commandes en utilisant l'utilisateurID
            List<Commande> commandes = commandeDAO.getCommandesParUtilisateur(utilisateurConnecte.getId());

            // Préparer les données à envoyer à la vue
            List<String[]> historiqueCommandes = new ArrayList<>();
            for (Commande commande : commandes) {
                String[] infos = new String[]{
                        String.valueOf(commande.getId()),
                        commande.getDate().toString(),
                        commande.getAdresseLivraison(),
                        String.format("%.2f", commande.getPrix()),
                        commande.getStatut()
                };
                historiqueCommandes.add(infos);
            }

            // Afficher côté console pour debug si besoin
            System.out.println("Nom : " + userName);
            System.out.println("Email : " + userEmail);
            System.out.println("Téléphone : " + userTel);
            System.out.println("Adresse : " + userAddress);
            System.out.println("Commandes : " + historiqueCommandes.size());

            view.afficherPageCompte(userName, userEmail, userTel, userAddress, historiqueCommandes);
        } else {
            view.showPage("Login");
        }
    }




    private void handleLogout() {
        // Clear the connected user
        utilisateurConnecte = null;

        // Show a confirmation message
        JOptionPane.showMessageDialog(null, "Vous êtes maintenant déconnecté.");

        // Redirect to the home page
        afficherAccueil();
    }

    private void handleCommander(String articleId) {
        if (utilisateurConnecte == null) {
            JOptionPane.showMessageDialog(null, "Veuillez vous connecter pour passer une commande.");
            view.showPage("Login");
            return;
        }

        try {
            int quantity = 1; // Toujours +1 à chaque clic

            // Récupérer les infos de l'article
            Article article = articleDAO.chercher(Integer.parseInt(articleId));

            // Récupérer les commandes de l'utilisateur
            List<Commande> commandes = commandeDAO.getCommandesParUtilisateur(utilisateurConnecte.getId());
            Commande commandeEnCours = null;

            for (Commande commande : commandes) {
                if ("en cours".equals(commande.getStatut())) {
                    commandeEnCours = commande;
                    break;
                }
            }

            if (commandeEnCours == null) {
                // Nouvelle commande
                double pricePerUnit = (quantity >= article.getSeuilVrac()) ? article.getPrixVrac() : article.getPrixUnitaire();
                double totalPrice = pricePerUnit * quantity;

                LocalDate today = LocalDate.now();
                commandeEnCours = new Commande(
                        0,
                        utilisateurConnecte.getId(),
                        today.toString(),
                        "en cours",
                        totalPrice,
                        String.valueOf(article.getId()),
                        String.valueOf(quantity),
                        utilisateurConnecte.getAdresse()
                );
                commandeDAO.ajouter(commandeEnCours);
            } else {
                // Modifier la commande existante
                String listeID_Article = commandeEnCours.getListeID_Article();
                String listeQuantite_Article = commandeEnCours.getListeQuantite_Article();

                String[] ids = listeID_Article.isEmpty() ? new String[0] : listeID_Article.split("-");
                String[] quantites = listeQuantite_Article.isEmpty() ? new String[0] : listeQuantite_Article.split("-");

                boolean found = false;
                for (int i = 0; i < ids.length; i++) {
                    if (ids[i].equals(articleId)) {
                        int currentQty = Integer.parseInt(quantites[i]);
                        quantites[i] = String.valueOf(currentQty + quantity);
                        found = true;
                        break;
                    }
                }

                if (!found) {
                    // Ajout de nouvel article
                    listeID_Article = listeID_Article.isEmpty() ? articleId : listeID_Article + "-" + articleId;
                    listeQuantite_Article = listeQuantite_Article.isEmpty() ? String.valueOf(quantity) : listeQuantite_Article + "-" + quantity;
                } else {
                    // Reconstruction des listes mises à jour
                    listeID_Article = String.join("-", ids);
                    listeQuantite_Article = String.join("-", quantites);
                }

                // Recalcul du prix total
                double prixTotal = 0;
                String[] listeID_ArticleSplit = listeID_Article.split("-");
                String[] listeQuantite_ArticleSplit = listeQuantite_Article.split("-");
                for (int i = 0; i < listeID_ArticleSplit.length; i++) {
                    int idArticle = Integer.parseInt(listeID_ArticleSplit[i]);
                    Article currentArticle = articleDAO.chercher(idArticle);
                    int quantite = Integer.parseInt(listeQuantite_ArticleSplit[i]);
                    double currentPricePerUnit = (quantite >= currentArticle.getSeuilVrac()) ? currentArticle.getPrixVrac() : currentArticle.getPrixUnitaire();
                    prixTotal += currentPricePerUnit * quantite;
                }

                commandeEnCours.setListeID_Article(listeID_Article);
                commandeEnCours.setListeQuantite_Article(listeQuantite_Article);
                commandeEnCours.setPrix(prixTotal);
                commandeDAO.modifier(commandeEnCours);
            }

            JOptionPane.showMessageDialog(null, "Article ajouté au panier avec succès !");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Erreur technique : " + ex.getMessage());
        }
    }




    private void afficherPanier() {
        if (utilisateurConnecte == null) {
            JOptionPane.showMessageDialog(null, "Veuillez vous connecter pour accéder à votre panier.");
            view.showPage("Login");
            return;
        }

        try {
            // Retrieve the user's current order
            List<Commande> commandeUtilisateur = commandeDAO.getCommandesParUtilisateur(utilisateurConnecte.getId());
            Commande commandeEnCours = null;

            for (Commande commande : commandeUtilisateur) {
                if ("en cours".equals(commande.getStatut())) {
                    commandeEnCours = commande;
                    break;
                }
            }

            if (commandeEnCours == null ||
                    commandeEnCours.getListeID_Article() == null ||
                    commandeEnCours.getListeID_Article().trim().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Votre panier est vide.");
                return;
            }

            // Extract article details
            String[] listeID_Article = commandeEnCours.getListeID_Article().split("-");
            String[] listeQuantite_Article = commandeEnCours.getListeQuantite_Article().split("-");
            List<Map<String, String>> articlesPanier = new ArrayList<>();

            for (int i = 0; i < listeID_Article.length; i++) {
                int idArticle = Integer.parseInt(listeID_Article[i]);
                Article article = articleDAO.chercher(idArticle);
                int quantite = Integer.parseInt(listeQuantite_Article[i]);

                // Determine the price to display based on the quantity
                double prixAffiche = (quantite >= article.getSeuilVrac()) ? article.getPrixVrac() : article.getPrixUnitaire();

                Map<String, String> data = new HashMap<>();
                data.put("nom", article.getNom());
                data.put("marque", article.getMarque());
                data.put("prix", String.format("%.2f €", prixAffiche));
                data.put("quantite", String.valueOf(quantite));
                data.put("imageUrl", article.getImageUrl());
                articlesPanier.add(data);
            }

            // Update the view with the articles
            view.updatePanierPageView(articlesPanier);
            view.showPage("Panier");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Erreur technique : " + ex.getMessage());
        }
    }


    private void handleCommanderButton() {
        if (utilisateurConnecte == null) {
            JOptionPane.showMessageDialog(null, "Veuillez vous connecter pour passer une commande.");
            view.showPage("Login");
            return;
        }

        try {
            // Retrieve the user's current order
            List<Commande> commandes = commandeDAO.getCommandesParUtilisateur(utilisateurConnecte.getId());
            Commande commandeEnCours = null;

            for (Commande commande : commandes) {
                if ("en cours".equals(commande.getStatut())) {
                    commandeEnCours = commande;
                    break;
                }
            }

            if (commandeEnCours == null) {
                JOptionPane.showMessageDialog(null, "Votre panier est vide.");
                return;
            }

            // Show credit card input form
            JPanel creditCardPanel = new JPanel(new GridLayout(4, 2, 10, 10));
            creditCardPanel.add(new JLabel("Nom sur la carte:"));
            JTextField cardNameField = new JTextField();
            creditCardPanel.add(cardNameField);

            creditCardPanel.add(new JLabel("Numéro de carte:"));
            JTextField cardNumberField = new JTextField();
            creditCardPanel.add(cardNumberField);

            creditCardPanel.add(new JLabel("Date d'expiration (MM/YY):"));
            JTextField expiryDateField = new JTextField();
            creditCardPanel.add(expiryDateField);

            creditCardPanel.add(new JLabel("CVV:"));
            JTextField cvvField = new JTextField();
            creditCardPanel.add(cvvField);

            int result = JOptionPane.showConfirmDialog(null, creditCardPanel, "Informations de paiement", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

            if (result != JOptionPane.OK_OPTION) {
                JOptionPane.showMessageDialog(null, "Commande annulée.");
                return;
            }

            // Validate credit card inputs (basic validation)
            if (cardNameField.getText().trim().isEmpty() || cardNumberField.getText().trim().isEmpty() ||
                    expiryDateField.getText().trim().isEmpty() || cvvField.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Veuillez remplir toutes les informations de paiement.");
                return;
            }

            // Update the status of the current order
            commandeEnCours.setStatut("commande passée");
            commandeDAO.modifier(commandeEnCours);

            // Create a new "en cours" order
            LocalDate today = LocalDate.now();
            Commande nouvelleCommande = new Commande(
                    0, // ID will be auto-generated
                    utilisateurConnecte.getId(),
                    today.toString(),
                    "en cours",
                    0.0, // Initial price is 0
                    "",  // No articles yet
                    "",  // No quantities yet
                    utilisateurConnecte.getAdresse()
            );
            commandeDAO.ajouter(nouvelleCommande);

            JOptionPane.showMessageDialog(null, "Votre commande a été passée avec succès !");
            afficherRatingForm(); // Show the rating form after the order is placed
            afficherAccueil();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Erreur technique : " + ex.getMessage());
        }
    }

    private void afficherRatingForm() {
        JPanel ratingPanel = new JPanel(new GridLayout(3, 1, 10, 10));
        ratingPanel.add(new JLabel("Notez votre expérience (1 à 5):"));
        JSlider ratingSlider = new JSlider(1, 5, 3);
        ratingSlider.setMajorTickSpacing(1);
        ratingSlider.setPaintTicks(true);
        ratingSlider.setPaintLabels(true);
        ratingPanel.add(ratingSlider);

        ratingPanel.add(new JLabel("Laissez un commentaire:"));
        JTextArea commentArea = new JTextArea(5, 20);
        JScrollPane scrollPane = new JScrollPane(commentArea);
        ratingPanel.add(scrollPane);

        int result = JOptionPane.showConfirmDialog(null, ratingPanel, "Évaluation de la commande", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (result == JOptionPane.OK_OPTION) {
            int rating = ratingSlider.getValue();
            String comment = commentArea.getText().trim();

            // Save the rating and comment (you can store it in the database)
            System.out.println("Note: " + rating);
            System.out.println("Commentaire: " + comment);

            JOptionPane.showMessageDialog(null, "Merci pour votre retour !");
        }
    }

    // Add this method in ShoppingController
    private void handleAjouterArticle() {
        // Create a panel for the form
        JPanel formPanel = new JPanel(new GridLayout(9, 2, 10, 10));
        formPanel.add(new JLabel("Nom:"));
        JTextField nomField = new JTextField();
        formPanel.add(nomField);

        formPanel.add(new JLabel("Marque:"));
        JTextField marqueField = new JTextField();
        formPanel.add(marqueField);

        formPanel.add(new JLabel("Prix Unitaire:"));
        JTextField prixUnitaireField = new JTextField();
        formPanel.add(prixUnitaireField);

        formPanel.add(new JLabel("Prix Vrac:"));
        JTextField prixVracField = new JTextField();
        formPanel.add(prixVracField);

        formPanel.add(new JLabel("Seuil Vrac:"));
        JTextField seuilVracField = new JTextField();
        formPanel.add(seuilVracField);

        formPanel.add(new JLabel("Stock:"));
        JTextField stockField = new JTextField();
        formPanel.add(stockField);

        formPanel.add(new JLabel("Disponible (true/false):"));
        JTextField disponibleField = new JTextField();
        formPanel.add(disponibleField);

        formPanel.add(new JLabel("Lien Image:"));
        JTextField imageUrlField = new JTextField();
        formPanel.add(imageUrlField);

        // Show the form in a dialog
        int result = JOptionPane.showConfirmDialog(null, formPanel, "Ajouter un Article", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (result == JOptionPane.OK_OPTION) {
            try {
                // Retrieve the input values
                String nom = nomField.getText().trim();
                String marque = marqueField.getText().trim();
                double prixUnitaire = Double.parseDouble(prixUnitaireField.getText().trim());
                double prixVrac = Double.parseDouble(prixVracField.getText().trim());
                int seuilVrac = Integer.parseInt(seuilVracField.getText().trim());
                int stock = Integer.parseInt(stockField.getText().trim());
                boolean disponible = Boolean.parseBoolean(disponibleField.getText().trim());
                String imageUrl = imageUrlField.getText().trim();

                // Create a new Article object
                Article newArticle = new Article(0, nom, marque, prixUnitaire, prixVrac, seuilVrac, stock, disponible, imageUrl);

                // Add the article to the database
                articleDAO.ajouter(newArticle);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(null, "Veuillez entrer des valeurs valides.");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "Erreur technique : " + ex.getMessage());
            }
        }
    }
    private void afficherPageAdmin() {
        if (utilisateurConnecte != null && utilisateurConnecte.getIsAdmin()) {
            // Load articles into the table
            List<Article> articles = articleDAO.getAll();
            DefaultTableModel model = (DefaultTableModel) view.getAdminTable().getModel();
            model.setRowCount(0); // Clear existing rows

            for (Article article : articles) {
                model.addRow(new Object[]{
                        article.getId(),
                        article.getNom(),
                        article.getMarque(),
                        article.getPrixUnitaire(),
                        article.getPrixVrac(),
                        article.getSeuilVrac(),
                        article.getStock(),
                        article.getIsAvailable(),
                        article.getImageUrl(),
                        "Modifier"
                });
            }

            view.showPage("AdminPage");
        } else {
            JOptionPane.showMessageDialog(null, "Accès refusé. Vous n'êtes pas administrateur.");
        }
    }
    private double parseDouble(Object value) {
        if (value == null || value.toString().trim().isEmpty()) {
            return 0.0; // Default value for doubles
        }
        return Double.parseDouble(value.toString());
    }

    private int parseInteger(Object value) {
        if (value == null || value.toString().trim().isEmpty()) {
            return 0; // Default value for integers
        }
        return Integer.parseInt(value.toString());
    }
}