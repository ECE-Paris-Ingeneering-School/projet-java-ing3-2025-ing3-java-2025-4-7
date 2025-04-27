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
import java.io.File;
import java.io.PrintWriter;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class ShoppingController{
    private ShoppingView view;
    private Utilisateur utilisateurConnecte;
    private UtilisateurDAOImpl utilisateurDAO;
    private CommandeDAOImpl commandeDAO;
    private ArticleDAOImpl articleDAO;
    private DaoFactory daoFactory;
    private PromoDAOImpl promoDAO;

    public ShoppingController(ShoppingView view){
        this.view = view;
        this.utilisateurConnecte = null;
        this.daoFactory = DaoFactory.getInstance("projetshoppingjava", "root", "");
        this.utilisateurDAO = new UtilisateurDAOImpl(this.daoFactory);
        this.commandeDAO = new CommandeDAOImpl(this.daoFactory);
        this.articleDAO = new ArticleDAOImpl(this.daoFactory);
        this.promoDAO = new PromoDAOImpl(this.daoFactory);

        initializeListeners();
        afficherAccueil();
    }

    //listeners des boutons etc..
    private void initializeListeners(){
        //retour accueil
        view.getHomeButton().addActionListener(e -> {afficherAccueil();});

        //page compte
        view.getAccountButton().addActionListener(e -> {
            if (utilisateurConnecte != null) {
                view.showPage("UpdateAccount");
            } else {
                view.showPage("Account");
            }
        });

        //page panier
        view.getPanierButton().addActionListener(e -> afficherPanier());

        //gestion compte
        view.getLoginButton().addActionListener(e -> view.showPage("Login"));
        view.getRegisterButton().addActionListener(e -> view.showPage("Register"));
        view.getSubmitLoginButton().addActionListener(e -> handleLogin());
        view.getSubmitRegisterButton().addActionListener(e -> handleRegister());
        view.getAccountButton().addActionListener(e -> afficherCompte());
        view.getLogoutButton().addActionListener(e -> handleLogout());

        //barre de recherche
        view.getSearchButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String searchText = view.getSearchText();  // Récupère le texte de la zone de recherche
                filterArticles(searchText);  // Filtrer les articles en fonction du texte de recherche
            }
        });

        //commande
        view.getCommanderButton().addActionListener(e -> handleCommanderButton());

        //admin
        view.getAdminButton().addActionListener(e -> afficherPageAdminGestionArticles());
        view.getSaveButton().addActionListener(e -> {
            JTable table = view.getAdminArticleTable();
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

        view.getSupprimerButton().addActionListener(e -> handleSupprimerArticle());

        view.getSaveUserButton().addActionListener(e -> handleSaveUsers());

        view.getDeleteUserButton().addActionListener(e -> handleDeleteUsers());
    }

    private void handleLogin(){
        String email = view.getEmailField().getText().trim();
        String password = new String(view.getPasswordField().getPassword()).trim();

        //label erreur
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

                afficherCompte();
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

        //blindage
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
            //vérification si email déjà utilisé
            Utilisateur existingUser = utilisateurDAO.chercherParEmail(email);
            if (existingUser != null) {
                view.getRegisterErrorMessageLabel().setText("Cet email est déjà utilisé.");
                return;
            }

            utilisateurConnecte = new Utilisateur(email, mdp, nom, prenom, "non renseignée", 0000000000, false);
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


    private void filterArticles(String searchText){
        List<Article> articles = articleDAO.rechercherArticles(searchText);

        List<Map<String, String>> articlesFormates = new ArrayList<>();
        for (Article article : articles) {
            Map<String, String> data = new HashMap<>();
            data.put("id", String.valueOf(article.getId()));
            data.put("nom", article.getNom());
            data.put("marque", article.getMarque());
            data.put("prix", String.format("%.2f €", article.getPrixUnitaire()));
            data.put("prixUnitaire", String.format("%.2f €", article.getPrixUnitaire()));
            data.put("prixVrac", String.format("%.2f €", article.getPrixVrac()));
            data.put("seuilVrac", String.valueOf(article.getSeuilVrac()));
            data.put("stock", String.valueOf(article.getStock()));
            data.put("articleImageURL", article.getImageUrl());
            articlesFormates.add(data);
        }

        if (articlesFormates.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Aucun article trouvé pour : " + searchText);
        }

        view.updateHomePageView(articlesFormates, e -> handleCommander(e.getActionCommand()));
    }


    private void afficherAccueil() {
        //si admin bouton admin
        if(utilisateurConnecte != null && utilisateurConnecte.getIsAdmin()) {
            view.getAdminButton().setVisible(true); // Show the admin button
            System.out.println("Admin Button Visible: " + view.getAdminButton().isVisible());
        } else {
            view.getAdminButton().setVisible(false); // Hide the admin button
            System.out.println("Utilisateur non-admin");
        }
        //appel dao
        ArticleDAO articleDAO = new ArticleDAOImpl(daoFactory);
        List<Article> articles = articleDAO.getAll();  // Liste de tous les articles depuis la base de données
        List<Map<String, String>> articlesFormates = new ArrayList<>();

        // Parcours de chaque article pour vérifier sa disponibilité et son stock
        for (Article article : articles) {
            if (article.getIsAvailable() && article.getStock() > 0) {  // Si l'article est disponible et a du stock
                Map<String, String> data = new HashMap<>();
                data.put("id",String.valueOf(article.getId()));  // ID de l'article
                data.put("nom", article.getNom());  // Nom de l'article
                data.put("marque", article.getMarque());  // Marque de l'article
                data.put("prix", String.format("%.2f €", article.getPrixUnitaire()));// Prix unitaire
                data.put("prixUnitaire", String.format("%.2f €", article.getPrixUnitaire()));
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
        view.showPage("HomePage");
    }


    private void afficherCompte(){
        if (utilisateurConnecte != null){
            //Récupérer les informations personnelles de l'utilisateur
            String userPrenom = utilisateurConnecte.getPrenom();
            String userNom = utilisateurConnecte.getNom();
            String userEmail = utilisateurConnecte.getEmail();
            String userTel = String.valueOf(utilisateurConnecte.getTelephone());
            String userAddresse = utilisateurConnecte.getAdresse();

            //Récupérer l'historique des commandes
            List<Commande> commandes = commandeDAO.getCommandesParUtilisateur(utilisateurConnecte.getId());

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

            view.afficherPageCompte(userPrenom, userNom, userEmail, userTel, userAddresse, historiqueCommandes);

            //listeners
            for (JButton rateButton : view.getRateButtons()) {
                rateButton.addActionListener(e -> {
                    JButton clickedButton = (JButton) e.getSource();
                    String commandeId = clickedButton.getName();
                    System.out.println("note " + commandeId);
                    afficherRatingForm();
                });
            }

            for (JButton viewDetailsButton : view.getViewDetailsButtons()) {
                viewDetailsButton.addActionListener(e -> {
                    JButton clickedButton = (JButton) e.getSource();
                    String commandeId = clickedButton.getName();
                    System.out.println("details " + commandeId);

                    // Lire le contenu du fichier de facture
                    String facturePath = "Project files/src/factures/facture_" + commandeId + ".txt";
                    File factureFile = new File(facturePath);

                    if (factureFile.exists()) {
                        try {
                            StringBuilder factureContent = new StringBuilder();
                            BufferedReader reader = new BufferedReader(new FileReader(factureFile));
                            String line;
                            while ((line = reader.readLine()) != null) {
                                factureContent.append(line).append("\n");
                            }
                            reader.close();

                            // Afficher le contenu dans une nouvelle petite fenêtre
                            JTextArea textArea = new JTextArea(factureContent.toString());
                            textArea.setEditable(false);
                            textArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
                            JScrollPane scrollPane = new JScrollPane(textArea);

                            JFrame detailsFrame = new JFrame("Détails de la Commande n°" + commandeId);
                            detailsFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                            detailsFrame.setSize(500, 600);
                            detailsFrame.add(scrollPane);
                            detailsFrame.setLocationRelativeTo(null); // Centre la fenêtre
                            detailsFrame.setVisible(true);

                        } catch (IOException ex) {
                            ex.printStackTrace();
                            JOptionPane.showMessageDialog(null, "Erreur lors de la lecture de la facture.", "Erreur", JOptionPane.ERROR_MESSAGE);
                        }
                    } else {
                        JOptionPane.showMessageDialog(null, "Facture non trouvée pour cette commande.", "Erreur", JOptionPane.ERROR_MESSAGE);
                    }
                });
            }

        } else {
            view.showPage("Login");
        }
    }


    private void handleLogout(){
        utilisateurConnecte = null;

        JOptionPane.showMessageDialog(null, "Vous êtes maintenant déconnecté.");

        afficherAccueil();
    }

    private void handleCommander(String articleId){
        if (utilisateurConnecte == null) {
            JOptionPane.showMessageDialog(null, "Veuillez vous connecter pour passer une commande.");
            view.showPage("Login");
            return;
        }

        try {
            int quantity = 1; //+1 à chaque clic

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
                view.showPage("HomePage");
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

                Map<String, String> data = new HashMap<>();
                data.put("id", String.valueOf(idArticle));
                data.put("nom", article.getNom());
                data.put("marque", article.getMarque());
                data.put("prixUnitaire", String.format("%.2f €", article.getPrixUnitaire()));
                data.put("prixVrac", String.format("%.2f €", article.getPrixVrac()));
                data.put("seuilVrac", String.valueOf(article.getSeuilVrac()));
                data.put("quantite", String.valueOf(quantite));
                data.put("imageUrl", article.getImageUrl());
                data.put("liste_id_article", commandeEnCours.getListeID_Article());
                articlesPanier.add(data);
            }

            // Update the view with the articles
            view.updatePanierPageView(articlesPanier, e -> {
                String articleId = e.getActionCommand();
                modifierQuantiteArticleDansCommande(articleId, 1);
            }, e -> {
                String articleId = e.getActionCommand();
                modifierQuantiteArticleDansCommande(articleId, -1);
            });

            view.showPage("Panier");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Erreur technique : " + ex.getMessage());
        }
    }

    private void modifierQuantiteArticleDansCommande(String articleIdStr, int delta) {
        if (utilisateurConnecte == null) return;

        int articleId = Integer.parseInt(articleIdStr);
        List<Commande> commandes = commandeDAO.getCommandesParUtilisateur(utilisateurConnecte.getId());
        Commande commandeEnCours = commandes.stream()
                .filter(c -> "en cours".equals(c.getStatut()))
                .findFirst()
                .orElse(null);

        if (commandeEnCours == null) return;

        String[] ids = commandeEnCours.getListeID_Article().split("-");
        String[] quantites = commandeEnCours.getListeQuantite_Article().split("-");

        boolean updated = false;
        for (int i = 0; i < ids.length; i++) {
            if (ids[i].equals(String.valueOf(articleId))) {
                int qte = Integer.parseInt(quantites[i]) + delta;
                if (qte <= 0) {
                    // Supprimer l'article de la commande
                    ids = removeIndex(ids, i);
                    quantites = removeIndex(quantites, i);
                } else {
                    quantites[i] = String.valueOf(qte);
                }
                updated = true;
                break;
            }
        }

        if (!updated && delta > 0) {
            ids = append(ids, String.valueOf(articleId));
            quantites = append(quantites, "1");
        }

        String newIds = String.join("-", ids);
        String newQtes = String.join("-", quantites);

        // Recalcul du prix
        double nouveauPrix = 0;
        for (int i = 0; i < ids.length; i++) {
            Article article = articleDAO.chercher(Integer.parseInt(ids[i]));
            int qte = Integer.parseInt(quantites[i]);
            double prix = (qte >= article.getSeuilVrac()) ? article.getPrixVrac() : article.getPrixUnitaire();
            nouveauPrix += prix * qte;
        }

        commandeEnCours.setListeID_Article(newIds);
        commandeEnCours.setListeQuantite_Article(newQtes);
        commandeEnCours.setPrix(nouveauPrix);

        commandeDAO.modifier(commandeEnCours);

        // Mise à jour interface
        afficherPanier();
    }

    private String[] removeIndex(String[] array, int index) {
        List<String> list = new ArrayList<>(List.of(array));
        list.remove(index);
        return list.toArray(new String[0]);
    }

    private String[] append(String[] array, String value) {
        List<String> list = new ArrayList<>(List.of(array));
        list.add(value);
        return list.toArray(new String[0]);
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
                view.showPage("HomePage");
                return;
            }

            // Show payment + address input form
            JPanel creditCardPanel = new JPanel(new GridLayout(9, 2, 10, 10));
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

            creditCardPanel.add(new JLabel("Code promo (optionnel):"));
            JTextField promoCodeField = new JTextField();
            creditCardPanel.add(promoCodeField);

            // Adresse
            creditCardPanel.add(new JLabel("Adresse:"));
            JTextField adresseField = new JTextField();
            creditCardPanel.add(adresseField);

            creditCardPanel.add(new JLabel("Ville:"));
            JTextField villeField = new JTextField();
            creditCardPanel.add(villeField);

            creditCardPanel.add(new JLabel("Code postal:"));
            JTextField codePostalField = new JTextField();
            creditCardPanel.add(codePostalField);

            creditCardPanel.add(new JLabel("Pays:"));
            JTextField paysField = new JTextField();
            creditCardPanel.add(paysField);

            int result = JOptionPane.showConfirmDialog(null, creditCardPanel, "Informations de paiement", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

            if (result != JOptionPane.OK_OPTION) {
                JOptionPane.showMessageDialog(null, "Commande annulée.");
                return;
            }

            // Validate credit card inputs
            if (cardNameField.getText().trim().isEmpty() || cardNumberField.getText().trim().isEmpty() ||
                    expiryDateField.getText().trim().isEmpty() || cvvField.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Veuillez remplir toutes les informations de paiement.");
                return;
            }

            // Validate address fields
            if (adresseField.getText().trim().isEmpty() || villeField.getText().trim().isEmpty() ||
                    codePostalField.getText().trim().isEmpty() || paysField.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Veuillez remplir toutes les informations d'adresse.");
                return;
            }

            // Récupérer et concaténer l’adresse complète
            String adresseComplete = adresseField.getText().trim() + ", " +
                    villeField.getText().trim() + ", " +
                    codePostalField.getText().trim() + ", " +
                    paysField.getText().trim();

            // Appliquer la réduction si code promo valide
            String codePromo = promoCodeField.getText().trim().toUpperCase();
            double reduction = 0.0;
            if (!codePromo.isEmpty()) {
                Promo promo = promoDAO.chercherParCode(codePromo);
                if (promo != null && promo.isActive()) {
                    reduction = promo.getReduction(); // valeur entre 0.0 et 1.0
                } else {
                    JOptionPane.showMessageDialog(null, "Code promo invalide ou inactif.");
                }
            }

            double prixOriginal = commandeEnCours.getPrix(); // doit être correctement calculé avant
            double prixFinal = prixOriginal;

            if (reduction > 0.0) {
                prixFinal = prixOriginal * (1 - reduction);
                JOptionPane.showMessageDialog(null, "Code promo appliqué ! Nouveau total : " + String.format("%.2f €", prixFinal));
            }

            System.out.println("Prix original : " + prixOriginal + " €");
            System.out.println("Code promo saisi : " + codePromo);
            System.out.println("Réduction : " + (reduction * 100) + "%");
            System.out.println("Prix final après réduction : " + prixFinal + " €");

            // Mettre à jour la commande actuelle
            commandeEnCours.setPrix(prixFinal);
            commandeEnCours.setStatut("commande passée");
            commandeEnCours.setAdresse(adresseComplete);
            commandeDAO.modifier(commandeEnCours);

            // Créer une nouvelle commande vide "en cours"
            LocalDate today = LocalDate.now();
            Commande nouvelleCommande = new Commande(
                    0, // ID auto-généré
                    utilisateurConnecte.getId(),
                    today.toString(),
                    "en cours",
                    0.0,
                    "",
                    "",
                    utilisateurConnecte.getAdresse() // adresse par défaut pour la nouvelle commande vide
            );
            commandeDAO.ajouter(nouvelleCommande);
            generateFacture(commandeEnCours);

            JOptionPane.showMessageDialog(null, "Votre commande a été passée avec succès !");
            afficherRatingForm();
            afficherAccueil();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Erreur technique : " + ex.getMessage());
        }
    }

    private void generateFacture(Commande commande) {
        try {
            // Crée le répertoire des factures s'il n'existe pas
            String factureDirectory = "Project files/src/factures/";
            File dir = new File(factureDirectory);
            if (!dir.exists()) {
                dir.mkdirs();
            }

            String factureFilePath = factureDirectory + "facture_" + commande.getId() + ".txt";
            File factureFile = new File(factureFilePath);

            try (PrintWriter writer = new PrintWriter(factureFile)) {
                //infos de base
                writer.println("----- FACTURE -----");
                writer.println("Numéro de commande : " + commande.getId());
                writer.println("Date : " + commande.getDate());
                writer.println("Client : " + utilisateurConnecte.getNom() + " " + utilisateurConnecte.getPrenom()); // Ajout prénom
                writer.println("Adresse de livraison : " + commande.getAdresseLivraison());
                writer.println("Statut : " + commande.getStatut());
                writer.println();

                //infos articles
                writer.println("-- ARTICLES --");

                List<Article> articles = commandeDAO.getArticlesParCommande(commande, articleDAO);
                String[] idsStr = commande.getListeID_Article().split("-");
                String[] quantitesStr = commande.getListeQuantite_Article().split("-");

                if (idsStr.length != quantitesStr.length) {
                    System.out.println("Erreur : Le nombre d'IDs d'articles ne correspond pas au nombre de quantités.");
                    return;
                }

                double totalAvantReduction = 0.0;

                for (int i = 0; i < idsStr.length; i++) {
                    try {
                        int articleId = Integer.parseInt(idsStr[i].trim());
                        int quantite = Integer.parseInt(quantitesStr[i].trim());

                        Article article = articles.stream()
                                .filter(a -> a.getId() == articleId)
                                .findFirst()
                                .orElse(null);

                        if (article != null) {
                            double prixUnitaire;
                            if (quantite >= article.getSeuilVrac()) {
                                prixUnitaire = article.getPrixVrac();
                                writer.println("x" + quantite + " " + article.getNom() + " (Prix vrac) : " + String.format("%.2f €", prixUnitaire));
                            } else {
                                prixUnitaire = article.getPrixUnitaire();
                                writer.println("x" + quantite + " " + article.getNom() + " : " + String.format("%.2f €", prixUnitaire));
                            }

                            double sousTotal = prixUnitaire * quantite;
                            totalAvantReduction += sousTotal;
                        } else {
                            System.out.println("Article non trouvé pour l'ID: " + articleId);
                        }
                    } catch (NumberFormatException e) {
                        System.out.println("Erreur de format de nombre pour l'article ID: " + idsStr[i] + " ou quantité: " + quantitesStr[i]);
                    }
                }

                writer.println();

                // Vérifier s'il y a une réduction
                double reduction = totalAvantReduction - commande.getPrix(); // Le total stocké dans commande est après réduction

                writer.println("Total avant réduction : " + String.format("%.2f €", totalAvantReduction));
                if (reduction > 0.01) { // S'il y a une réduction significative
                    writer.println("Réduction appliquée : -" + String.format("%.2f €", reduction));
                }
                writer.println("Total à payer : " + String.format("%.2f €", commande.getPrix()));

                writer.println("--------------------");
                writer.println("Merci pour votre achat !");
            }

            System.out.println("Facture générée : " + factureFilePath);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Erreur lors de la génération de la facture : " + e.getMessage());
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
            System.out.println("Note: " + rating);
            System.out.println("Commentaire: " + comment);
            ///TODO: BDD ?

            JOptionPane.showMessageDialog(null, "Merci pour votre retour !");
        }
    }


    private void handleAjouterArticle() {
        //formulaire
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

        int result = JOptionPane.showConfirmDialog(null, formPanel, "Ajouter un Article", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (result == JOptionPane.OK_OPTION) {
            try {
                //recuperer les inputs
                String nom = nomField.getText().trim();
                String marque = marqueField.getText().trim();
                double prixUnitaire = Double.parseDouble(prixUnitaireField.getText().trim());
                double prixVrac = Double.parseDouble(prixVracField.getText().trim());
                int seuilVrac = Integer.parseInt(seuilVracField.getText().trim());
                int stock = Integer.parseInt(stockField.getText().trim());
                boolean disponible = Boolean.parseBoolean(disponibleField.getText().trim());
                String imageUrl = imageUrlField.getText().trim();

                //nouvel objet article
                Article newArticle = new Article(0, nom, marque, prixUnitaire, prixVrac, seuilVrac, stock, disponible, imageUrl);

                //ajout bdd
                articleDAO.ajouter(newArticle);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(null, "Veuillez entrer des valeurs valides.");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "Erreur technique : " + ex.getMessage());
            }
        }
    }


    private void afficherPageAdminGestionArticles() {
        if (utilisateurConnecte != null && utilisateurConnecte.getIsAdmin()) {
            // Load articles into the admin table
            List<Article> articles = articleDAO.getAll();
            DefaultTableModel articleModel = (DefaultTableModel) view.getAdminArticleTable().getModel();
            articleModel.setRowCount(0); // Clear existing rows

            for (Article article : articles) {
                articleModel.addRow(new Object[]{
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

            // Load users into the admin user table
            List<Utilisateur> utilisateurs = utilisateurDAO.getAll();
            DefaultTableModel userModel = (DefaultTableModel) view.getAdminUserTable().getModel();
            userModel.setRowCount(0); // Clear existing rows

            for (Utilisateur utilisateur : utilisateurs) {
                userModel.addRow(new Object[]{
                        utilisateur.getId(),
                        utilisateur.getPrenom(),
                        utilisateur.getNom(),
                        utilisateur.getEmail(),
                        utilisateur.getMotDePasse(),
                        utilisateur.getAdresse(),
                        utilisateur.getTelephone(),
                        utilisateur.getIsAdmin(),
                        "Modifier"
                });
            }

            view.showPage("AdminPage");
        } else {
            JOptionPane.showMessageDialog(null, "Accès refusé. Vous n'êtes pas administrateur.");
        }
    }

    private void handleSaveUsers() {
        JTable userTable = view.getAdminUserTable();
        DefaultTableModel userModel = (DefaultTableModel) userTable.getModel();

        for (int i = 0; i < userModel.getRowCount(); i++) {
            try {
                int id = parseInteger(userModel.getValueAt(i, 0));
                String mdp = userModel.getValueAt(i, 1).toString();
                String email = userModel.getValueAt(i, 2).toString();
                String prenom = userModel.getValueAt(i, 3).toString();
                String nom = userModel.getValueAt(i, 4).toString();
                String adresse = userModel.getValueAt(i, 5).toString();
                int telephone = parseInteger(userModel.getValueAt(i, 6));
                boolean isAdmin = Boolean.parseBoolean(userModel.getValueAt(i, 7).toString());

                // Update user in the database
                utilisateurDAO.modifier(
                        new Utilisateur(id, email, mdp, nom, prenom, adresse, telephone, isAdmin),
                        email, mdp, nom, prenom, adresse, telephone, isAdmin
                );
            } catch (Exception e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(view, "Erreur lors de l'enregistrement de l'utilisateur à la ligne " + (i + 1));
            }
        }

        JOptionPane.showMessageDialog(view, "Modifications enregistrées avec succès !");
    }

    private void handleDeleteUsers()
    {
        JTable userTable = view.getAdminUserTable();
        int selectedRow = userTable.getSelectedRow();

        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(null, "Veuillez sélectionner un utilisateur à supprimer.");
            return;
        }

        int userId = parseInteger(userTable.getValueAt(selectedRow, 0));
        int confirmation = JOptionPane.showConfirmDialog(null, "Êtes-vous sûr de vouloir supprimer cet utilisateur ?", "Confirmation", JOptionPane.YES_NO_OPTION);

        if (confirmation == JOptionPane.YES_OPTION) {
            try {
                utilisateurDAO.supprimer(userId);
                ((DefaultTableModel) userTable.getModel()).removeRow(selectedRow);
                JOptionPane.showMessageDialog(null, "Utilisateur supprimé avec succès !");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "Erreur lors de la suppression : " + ex.getMessage());
            }
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


    private void handleSupprimerArticle() {
        JTable table = view.getAdminArticleTable();
        int selectedRow = table.getSelectedRow();

        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(null, "Veuillez sélectionner un article à supprimer.");
            return;
        }

        int articleId = parseInteger(table.getValueAt(selectedRow, 0));
        int confirmation = JOptionPane.showConfirmDialog(null, "Êtes-vous sûr de vouloir supprimer cet article ?", "Confirmation", JOptionPane.YES_NO_OPTION);

        if (confirmation == JOptionPane.YES_OPTION) {
            try {
                Article articleASupprimier = articleDAO.chercher(articleId);
                articleDAO.supprimer(articleASupprimier);
                ((DefaultTableModel) table.getModel()).removeRow(selectedRow);
                JOptionPane.showMessageDialog(null, "Article supprimé avec succès !");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "Erreur lors de la suppression : " + ex.getMessage());
            }
        }
    }
}

