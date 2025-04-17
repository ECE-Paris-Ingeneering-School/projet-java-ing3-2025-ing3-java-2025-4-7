package controleur;

import vue.*;
import modele.*;
import DAO.*;


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ShoppingController {
    private ShoppingView view;
    private Utilisateur utilisateurConnecte; // Stocke l'utilisateur connecté
    private UtilisateurDAOImpl utilisateurDAO; // DAO pour les utilisateurs
    private DaoFactory daoFactory;

    public ShoppingController(ShoppingView view) {
        System.out.println("Controller initialized");
        this.view = view;
        this.utilisateurConnecte = null; // Au début, personne n'est connecté
        this.daoFactory = DaoFactory.getInstance("projetshoppingjava","root","");
        this.utilisateurDAO = new UtilisateurDAOImpl(this.daoFactory);
//        Utilisateur user = new Utilisateur("email", "password", "nom", "prenom", "adresse", 1234567890, false);
//        this.utilisateurDAO.ajouter(user);
//        this.utilisateurDAO.modifier(user, "bite", "password", "nom", "prenom", "adresse", 1234567890, false);


        view.getHomeButton().addActionListener(e -> view.showPage("HomePage"));
        view.getAccountButton().addActionListener(e -> {
            if (utilisateurConnecte != null) {
                // Si l'utilisateur est connecté, affiche la page "UpdateAccount"
                view.showPage("UpdateAccount");
            } else {
                // Si l'utilisateur n'est pas connecté, affiche la page "Login" (connexion)
                view.showPage("Account");
            }
        });


        view.getPanierButton().addActionListener(e -> {
            view.showPage("Panier");
            setupPanierListeners();
        });

        view.getLoginButton().addActionListener(e -> view.showPage("Login"));
        view.getRegisterButton().addActionListener(e -> view.showPage("Register"));

        // Gestion du bouton Logout
        view.getLogoutButton().addActionListener(e -> {
            // Déconnexion de l'utilisateur
            utilisateurConnecte = null;
            JOptionPane.showMessageDialog(null, "Vous êtes maintenant déconnecté.");
            view.showPage("HomePage"); // Redirection vers la page d'accueil après déconnexion
        });

        view.getSubmitLoginButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String email = view.getLoginEmail();
                String password = view.getLoginPassword();
                if (email.isEmpty() || password.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Veuillez remplir tous les champs.");
                } else {
                    // Simulation de la connexion (ici, utiliser un vrai modèle d'authentification)
                    // Exemple d'un utilisateur connecté avec des valeurs fictives
                    utilisateurConnecte = new Utilisateur(1, email, password, "Doe", "John", "123 Main St", 1234567890, false);
                    JOptionPane.showMessageDialog(null, "Bienvenue " + utilisateurConnecte.getEmail());
                    view.showPage("HomePage");
                }
            }
        });

        view.getSubmitRegisterButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String prenom = view.getRegisterPrenom().trim();
                String nom = view.getRegisterNom().trim();
                String email = view.getRegisterEmail().trim();
                String mdp = view.getRegisterPassword();
                String confirmMdp = view.getRegisterConfirmPassword();

                if (prenom.isEmpty() || nom.isEmpty() || email.isEmpty() || mdp.isEmpty() || confirmMdp.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Tous les champs sont obligatoires.");
                    return;
                }

                if (!email.matches("^[\\w.-]+@[\\w.-]+\\.\\w+$")) {
                    JOptionPane.showMessageDialog(null, "Adresse email invalide.");
                    return;
                }

                if (!mdp.equals(confirmMdp)) {
                    JOptionPane.showMessageDialog(null, "Les mots de passe ne correspondent pas.");
                    return;
                }

                if (mdp.length() < 6) {
                    JOptionPane.showMessageDialog(null, "Le mot de passe doit contenir au moins 6 caractères.");
                    return;
                }

                // On peut simuler l'inscription ici
                // Exemple d'un nouvel utilisateur
                utilisateurConnecte = new Utilisateur(email, mdp, nom, prenom, "adresse", 1234567890, false);
                try{
                    boolean success = utilisateurDAO.ajouter(utilisateurConnecte);
                    if (success) {
                        JOptionPane.showMessageDialog(null, "Inscription réussie !");
                        view.showPage("HomePage");
                    }
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "Erreur lors de l'inscription. Veuillez réessayer.");
                }
                JOptionPane.showMessageDialog(null, "Inscription réussie ! Bienvenue " + prenom + " " + nom + " !");
                view.showPage("HomePage");
            }
        });

        view.getLogoutButton().addActionListener(e -> {
            // Déconnexion de l'utilisateur
            utilisateurConnecte = null;
            JOptionPane.showMessageDialog(null, "Vous êtes maintenant déconnecté.");
            view.showPage("HomePage"); // Redirige vers la page d'accueil après déconnexion
        });
    }

    private void setupPanierListeners() {
        JPanel panierPanel = view.getPanierPagePanel();
        JScrollPane scrollPane = (JScrollPane) ((BorderLayout) panierPanel.getLayout()).getLayoutComponent(BorderLayout.CENTER);
        JViewport viewport = scrollPane.getViewport();
        JPanel listPanel = (JPanel) viewport.getView();

        for (Component comp : listPanel.getComponents()) {
            if (comp instanceof JPanel articlePanel) {
                Component east = ((BorderLayout) articlePanel.getLayout()).getLayoutComponent(BorderLayout.EAST);
                Component west = ((BorderLayout) articlePanel.getLayout()).getLayoutComponent(BorderLayout.WEST);

                if (!(east instanceof JPanel buttonPanel) || !(west instanceof JLabel nomLabel)) continue;

                String labelText = nomLabel.getText();
                String articleName = labelText.split(" ")[0];

                JButton minusButton = (JButton) buttonPanel.getComponent(0);
                JButton plusButton = (JButton) buttonPanel.getComponent(1);
                JButton deleteButton = (JButton) buttonPanel.getComponent(2);

                for (ActionListener al : plusButton.getActionListeners()) plusButton.removeActionListener(al);
                for (ActionListener al : minusButton.getActionListeners()) minusButton.removeActionListener(al);
                for (ActionListener al : deleteButton.getActionListeners()) deleteButton.removeActionListener(al);

                plusButton.addActionListener(e -> {
                    int quantity = extractQuantity(nomLabel.getText());
                    quantity++;
                    nomLabel.setText(articleName + " (x" + quantity + ")");
                });

                minusButton.addActionListener(e -> {
                    int quantity = extractQuantity(nomLabel.getText());
                    if (quantity > 1) {
                        quantity--;
                        nomLabel.setText(articleName + " (x" + quantity + ")");
                    }
                });

                deleteButton.addActionListener(e -> {
                    listPanel.remove(articlePanel);
                    listPanel.revalidate();
                    listPanel.repaint();
                });
            }
        }

        JPanel bottomPanel = (JPanel) panierPanel.getComponent(1);
        JButton commanderButton = (JButton) bottomPanel.getComponent(0);

        for (ActionListener al : commanderButton.getActionListeners()) commanderButton.removeActionListener(al);
        commanderButton.addActionListener(e -> view.showPage("Commande"));
    }

    private int extractQuantity(String labelText) {
        try {
            int start = labelText.indexOf("(x") + 2;
            int end = labelText.indexOf(")", start);
            return Integer.parseInt(labelText.substring(start, end));
        } catch (Exception e) {
            return 1;
        }
    }
}
