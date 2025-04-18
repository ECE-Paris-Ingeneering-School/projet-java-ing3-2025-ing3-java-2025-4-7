package controleur;

import vue.*;
import modele.*;
import DAO.*;
import javax.swing.*;
import java.util.List;
import java.util.Map;
import java.util.ArrayList;
import java.util.HashMap;

public class ShoppingController {
    private ShoppingView view;
    private Utilisateur utilisateurConnecte;
    private UtilisateurDAOImpl utilisateurDAO;
    private CommandeDAOImpl commandeDAO;
    private DaoFactory daoFactory;

    public ShoppingController(ShoppingView view) {
        this.view = view;
        this.utilisateurConnecte = null;
        this.daoFactory = DaoFactory.getInstance("projetshoppingjava", "root", "");
        this.utilisateurDAO = new UtilisateurDAOImpl(this.daoFactory);
        this.commandeDAO = new CommandeDAOImpl(this.daoFactory);

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

        view.getLogoutButton().addActionListener(e -> handleLogout());

        view.getSubmitLoginButton().addActionListener(e -> handleLogin());
        view.getSubmitRegisterButton().addActionListener(e -> handleRegister());
        view.getAccountButton().addActionListener(e -> afficherCompte());
    }

    private void handleLogin() {
        String email = view.getEmailField().getText();
        String password = new String(view.getPasswordField().getPassword());

        if (email.isEmpty() || password.isEmpty()) {
            view.getErrorMessageLabel().setText("Veuillez remplir tous les champs.");
            return;
        }

        try {
            // Chercher l'utilisateur dans la base de données
            utilisateurConnecte = utilisateurDAO.chercherLogin(email, password);

            if (utilisateurConnecte != null) {
                // Une fois l'utilisateur authentifié, afficher ses informations
                view.getErrorMessageLabel().setText("");

                // Appel pour afficher la page du compte avec les infos de l'utilisateur
                // Passer les informations de l'utilisateur à la vue pour créer la page de compte
                String userName = utilisateurConnecte.getPrenom() + " " + utilisateurConnecte.getNom(); // Nom complet
                String userEmail = utilisateurConnecte.getEmail(); // Récupérer l'email de l'utilisateur

                // Redirection vers la page "Mon Compte"
                view.showPage("HomePage");
            } else {
                view.getErrorMessageLabel().setText("Email ou mot de passe incorrect.");
            }
        } catch (Exception ex) {
            view.getErrorMessageLabel().setText("Erreur lors de la connexion. Veuillez réessayer.");
        }
    }



    private void handleRegister() {
        String prenom = view.getRegisterPrenomField().getText().trim();
        String nom = view.getRegisterNomField().getText().trim();
        String email = view.getRegisterEmailField().getText().trim();
        String mdp = new String(view.getRegisterPasswordField().getPassword());
        String confirmMdp = new String(view.getRegisterConfirmPasswordField().getPassword());

        if (prenom.isEmpty() || nom.isEmpty() || email.isEmpty() || mdp.isEmpty() || confirmMdp.isEmpty()) {
            view.getErrorMessageLabel().setText("Tous les champs sont obligatoires.");
            return;
        }

        if (!email.matches("^[\\w-.]+@([\\w-]+\\.)+[\\w-]{2,4}$")) {
            view.getErrorMessageLabel().setText("Adresse email invalide.");
            return;
        }

        if (!mdp.equals(confirmMdp)) {
            view.getErrorMessageLabel().setText("Les mots de passe ne correspondent pas.");
            return;
        }

        if (mdp.length() < 8) {
            view.getErrorMessageLabel().setText("Le mot de passe doit contenir au moins 8 caractères.");
            return;
        }

        try {
            // ✅ Vérification si email déjà utilisé
            Utilisateur existingUser = utilisateurDAO.chercherParEmail(email);
            if (existingUser != null) {
                view.getErrorMessageLabel().setText("Cet email est déjà utilisé.");
                return;
            }

            utilisateurConnecte = new Utilisateur(email, mdp, nom, prenom, "adresse", 1234567890, false);
            boolean success = utilisateurDAO.ajouter(utilisateurConnecte);
            if (success) {
                view.getErrorMessageLabel().setText(""); // Nettoyer les erreurs
                view.showPage("HomePage");
            } else {
                view.getErrorMessageLabel().setText("Erreur lors de l'inscription. Veuillez réessayer.");
            }
        } catch (Exception ex) {
            view.getErrorMessageLabel().setText("Erreur technique. Veuillez réessayer.");
        }
    }




    public void afficherAccueil() {
        ArticleDAO articleDAO = new ArticleDAOImpl(daoFactory);
        List<Article> articles = articleDAO.getAll();

        List<Map<String, String>> articlesFormates = new ArrayList<>();
        for (Article article : articles) {
            if (article.getIsAvailable()) {
                Map<String, String> data = new HashMap<>();
                data.put("nom", article.getNom());
                data.put("marque", article.getMarque());
                data.put("prix", String.format("%.2f €", article.getPrixUnitaire()));
                articlesFormates.add(data);
            }
        }

        view.updateHomePageView(articlesFormates);
        view.showPage("HomePage");
    }

    private void afficherCompte() {
        if (utilisateurConnecte != null) {
            String userName = utilisateurConnecte.getNom();
            String userEmail = utilisateurConnecte.getEmail();

            // Check if the panel already exists
            if (view.getMainPanel().getComponentCount() == 0 ||
                    !view.getMainPanel().isAncestorOf(view.createUpdateAccountPagePanel(userName, userEmail))) {
                JPanel accountPanel = view.createUpdateAccountPagePanel(userName, userEmail);
                view.getMainPanel().add(accountPanel, "UpdateAccount");
            }

            view.showPage("UpdateAccount");
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
}