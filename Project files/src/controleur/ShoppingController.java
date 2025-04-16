package controleur;

import vue.*;
import modele.*;
import DAO.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.UnknownServiceException;
import java.util.ArrayList;

public class ShoppingController {
    private ShoppingView view;
    UtilisateurDAOImpl utilisateurDAO;
    ArticleDAOImpl articleDAO;
    CommandeDAOImpl commandeDAO;
    DaoFactory daofactory;


    public ShoppingController(ShoppingView view) {
        this.view = view;
        this.daofactory = DaoFactory.getInstance("projetshoppingjava","root","");

        /// TEST - DAO Utilisateur
        this.utilisateurDAO = new UtilisateurDAOImpl(daofactory);
        //Utilisateur user = new Utilisateur(4,"test", "test", "test", "test", "test", 1234567890, false);
        //utilisateurDAO.ajouter(user);
        //utilisateurDAO.supprimer(3);

        /// TEST - DAO Article
        this.articleDAO = new ArticleDAOImpl(daofactory);
        /*Article newArticle = new Article("Choubidou", "yolo", 12.00, 20.00, 5, 50, true);
        newArticle = articleDAO.ajouter(newArticle);

        System.out.println("Liste des articles");
        ArrayList<Article> liste = articleDAO.getAll();
        for (Article a : liste) {
            System.out.println("ID :" + a.getId() + ", nom :" + a.getNom() + ", marque :" + a.getMarque() + ", disponible :" + a.getIsAvailable());
        }

        Article aChercher = articleDAO.chercher(5);

        System.out.println(" ");
        System.out.println("Liste des articles");
        Article aModifier = articleDAO.modifier(aChercher, "Testxxxxxxxxxxxx...", aChercher.getMarque(), aChercher.getPrixUnitaire(), aChercher.getPrixVrac(), aChercher.getSeuilVrac(), aChercher.getStock());
        liste = articleDAO.getAll();
        for (Article a : liste) {
            System.out.println("ID :" + a.getId() + ", nom :" + a.getNom() + ", marque :" + a.getMarque() + ", disponible :" + a.getIsAvailable());
        }*/

        view.getHomeButton().addActionListener(e -> view.showPage("HomePage"));
        view.getAccountButton().addActionListener(e -> view.showPage("Account"));
        view.getPanierButton().addActionListener(e -> {
            view.showPage("Panier");
            setupPanierListeners();
        });

        view.getLoginButton().addActionListener(e -> view.showPage("Login"));
        view.getRegisterButton().addActionListener(e -> view.showPage("Register"));

        view.getSubmitLoginButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int Id = 1; // Dummy ID for demonstration
                String nom = "Doe"; // Dummy name for demonstration
                String prenom = "John"; // Dummy first name for demonstration
                String adresse = "123 Main St"; // Dummy address for demonstration
                int telephone = 1234567890; // Dummy phone number for demonstration
                boolean isAdmin = false; // Dummy admin status for demonstration
                String email = view.getLoginEmail();
                String password = view.getLoginPassword();
                if (email.isEmpty() || password.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Veuillez remplir tous les champs.");
                } else {
                    Utilisateur utilisateur = new Utilisateur(Id,email, password, nom, prenom, adresse, telephone, isAdmin);
                    JOptionPane.showMessageDialog(null, "Bienvenue " + utilisateur.getEmail());
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

                JOptionPane.showMessageDialog(null, "Inscription réussie ! Bienvenue " + prenom + " " + nom + " !");
                view.showPage("HomePage");
            }

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