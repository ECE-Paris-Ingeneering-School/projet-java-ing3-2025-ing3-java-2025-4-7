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

    //TODO : enlever ces notifs de merde
    private void handleLogin() {
        String email = JOptionPane.showInputDialog(null, "Entrez votre email :", "Connexion", JOptionPane.PLAIN_MESSAGE);
        String password = JOptionPane.showInputDialog(null, "Entrez votre mot de passe :", "Connexion", JOptionPane.PLAIN_MESSAGE);

        if (email == null || email.isEmpty() || password == null || password.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Veuillez remplir tous les champs.");
            return;
        }

        try {
            utilisateurConnecte = utilisateurDAO.chercherLogin(email, password);
            if (utilisateurConnecte != null) {
                JOptionPane.showMessageDialog(null, "Bienvenue " + utilisateurConnecte.getEmail());
                view.showPage("HomePage");
            } else {
                JOptionPane.showMessageDialog(null, "Email ou mot de passe incorrect.");
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Erreur lors de la connexion. Veuillez réessayer.");
        }
    }

    //TODO : enlever ces notifs de merde
    private void handleRegister() {
        String prenom = JOptionPane.showInputDialog(null, "Entrez votre prénom :", "Inscription", JOptionPane.PLAIN_MESSAGE);
        String nom = JOptionPane.showInputDialog(null, "Entrez votre nom :", "Inscription", JOptionPane.PLAIN_MESSAGE);
        String email = JOptionPane.showInputDialog(null, "Entrez votre email :", "Inscription", JOptionPane.PLAIN_MESSAGE);
        String mdp = JOptionPane.showInputDialog(null, "Entrez votre mot de passe :", "Inscription", JOptionPane.PLAIN_MESSAGE);
        String confirmMdp = JOptionPane.showInputDialog(null, "Confirmez votre mot de passe :", "Inscription", JOptionPane.PLAIN_MESSAGE);

        if (prenom == null || prenom.isEmpty() || nom == null || nom.isEmpty() || email == null || email.isEmpty() ||
                mdp == null || mdp.isEmpty() || confirmMdp == null || confirmMdp.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Tous les champs sont obligatoires.");
            return;
        }

        if (!mdp.equals(confirmMdp)) {
            JOptionPane.showMessageDialog(null, "Les mots de passe ne correspondent pas.");
            return;
        }

        utilisateurConnecte = new Utilisateur(email, mdp, nom, prenom, "adresse", 1234567890, false);
        try {
            boolean success = utilisateurDAO.ajouter(utilisateurConnecte);
            if (success) {
                JOptionPane.showMessageDialog(null, "Inscription réussie !");
                view.showPage("HomePage");
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Erreur lors de l'inscription. Veuillez réessayer.");
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