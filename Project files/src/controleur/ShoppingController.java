package controleur;

import vue.*;
import modele.*;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ShoppingController {
    private ShoppingView view;

    public ShoppingController(ShoppingView view) {
        this.view = view;

        view.getHomeButton().addActionListener(e -> view.showPage("HomePage"));
        view.getAccountButton().addActionListener(e -> view.showPage("Account"));
        view.getPanierButton().addActionListener(e -> view.showPage("Panier"));

        view.getLoginButton().addActionListener(e -> view.showPage("Login"));
        view.getRegisterButton().addActionListener(e -> view.showPage("Register"));

        view.getSubmitLoginButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String email = view.getLoginEmail();
                String password = view.getLoginPassword();
                if (email.isEmpty() || password.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Veuillez remplir tous les champs.");
                } else {
                    Utilisateur utilisateur = new Utilisateur(email, password);
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

                // Vérifications
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

                // Si tout est bon
                JOptionPane.showMessageDialog(null, "Inscription réussie ! Bienvenue " + prenom + " " + nom + " !");
                view.showPage("HomePage");
            }
        });

    }
}
