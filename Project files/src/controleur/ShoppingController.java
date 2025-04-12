package controleur;

import modele.ShoppingModel;
import vue.ShoppingView;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ShoppingController {
    private final ShoppingModel model;
    private final ShoppingView view;

    public ShoppingController(ShoppingModel model, ShoppingView view) {
        this.model = model;
        this.view = view;

        // Bouton Connexion -> Page Login
        view.getLoginButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                view.showPage("Login");
            }
        });

        // Bouton Inscription -> Page Register
        view.getRegisterButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                view.showPage("Register");
            }
        });

        // Bouton Soumettre Inscription
        view.getSubmitRegisterButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String nom = view.getNom().trim();
                String prenom = view.getPrenom().trim();
                String email = view.getEmail().trim();
                String password = view.getPassword();
                String confirmPassword = view.getConfirmPassword();

                // Validation des champs
                if (!nom.matches("^[a-zA-ZÀ-ÿ\\-]+$")) {
                    view.setRegisterMessage("Nom invalide (lettres et tirets uniquement).", false);
                } else if (!prenom.matches("^[a-zA-ZÀ-ÿ\\-]+$")) {
                    view.setRegisterMessage("Prénom invalide (lettres et tirets uniquement).", false);
                } else if (!email.matches("^[\\w.-]+@[\\w.-]+\\.\\w+$")) {
                    view.setRegisterMessage("Adresse email invalide.", false);
                } else if (password.length() < 8) {
                    view.setRegisterMessage("Mot de passe trop court (8 caractères minimum).", false);
                } else if (!password.equals(confirmPassword)) {
                    view.setRegisterMessage("Les mots de passe ne correspondent pas.", false);
                } else {
                    view.setRegisterMessage("Inscription réussie !", true);
                    // Appel éventuel au modèle ici
                    // model.registerUser(nom, prenom, email, password);
                }
            }
        });

        // Aucun accès aux composants de login dans la vue actuelle (pas de getteurs)
        // Si tu veux gérer le login, il faut exposer les champs et le bouton du loginPanel aussi
    }
}
