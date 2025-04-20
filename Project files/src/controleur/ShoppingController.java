package controleur;

import vue.*;
import modele.*;
import DAO.*;
import javax.swing.*;
import java.util.List;
import java.util.Map;
import java.util.ArrayList;
import java.util.HashMap;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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
        view.getHomeButton().addActionListener(e -> view.showPage("HomePage"));

        view.getAccountButton().addActionListener(e -> {
            if (utilisateurConnecte != null) {

                view.showPage("UpdateAccount");
            } else {
                view.showPage("Account");
            }
        });

        view.getPanierButton().addActionListener(e -> view.showPage("Panier"));

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
//        view.getCommanderButton().addActionListener(e -> handleCommander());
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
                view.showPage("HomePage");
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
                view.showPage("HomePage");
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
        view.updateHomePageView(articlesFiltrés);
        view.showPage("HomePage");
    }


    private void afficherAccueil() {
        // Crée une instance de ArticleDAO pour récupérer tous les articles
        ArticleDAO articleDAO = new ArticleDAOImpl(daoFactory);
        List<Article> articles = articleDAO.getAll();  // Liste de tous les articles depuis la base de données

        // Création d'une liste pour stocker les articles disponibles formatés
        List<Map<String, String>> articlesFormates = new ArrayList<>();

        // Parcours de chaque article pour vérifier sa disponibilité et son stock
        for (Article article : articles) {
            if (article.getIsAvailable() && article.getStock() > 0) {  // Si l'article est disponible et a du stock
                Map<String, String> data = new HashMap<>();
                data.put("nom", article.getNom());  // Nom de l'article
                data.put("marque", article.getMarque());  // Marque de l'article
                data.put("prix", String.format("%.2f €", article.getPrixUnitaire()));  // Prix unitaire
                data.put("stock", String.valueOf(article.getStock()));  // Stock restant

                // Ajout de l'article formaté à la liste
                articlesFormates.add(data);
            }
        }

        // Mise à jour de la vue avec les articles filtrés
        view.updateHomePageView(articlesFormates);
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
        view.showPage("HomePage");
    }

    private void handleCommander() {
        if (utilisateurConnecte == null) {
            JOptionPane.showMessageDialog(null, "Veuillez vous connecter pour passer une commande.");
            view.showPage("Login");
            return;
        }

        try {
            // Récupérer les articles du panier
            List<Commande> commandeUtilisateur = new ArrayList<>();
            commandeUtilisateur = commandeDAO.getCommandesParUtilisateur(utilisateurConnecte.getId());
            for (Commande commande : commandeUtilisateur) {
                // Convertir les listes d'IDs et de quantités en chaînes formatées
                String listeID_Article = commande.getListeID_Article();
                String listeQuantite_Article = commande.getListeQuantite_Article();
                String[] listeID = listeID_Article.split("-");
                String[] listeQuantite = listeQuantite_Article.split("-");
                int [] quantites = new int[listeQuantite.length];
                for (int i = 0; i < listeQuantite.length; i++) {
                    quantites[i] = Integer.parseInt(listeQuantite[i]);
                }
                double prixTotal = 0.0;
                for (int i = 0; i < listeID.length; i++) {
                    int idArticle = Integer.parseInt(listeID[i]);
                    Article article = articleDAO.chercher(idArticle);
                    if (article != null) {
                        prixTotal += article.getPrixUnitaire() * quantites[i];
                    }
                }
            }


        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Erreur technique : " + ex.getMessage());
        }
    }
}