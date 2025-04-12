package controleur;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import modele.ShoppingModel;
import vue.ShoppingView;

public class ShoppingController {
    private ShoppingModel model;
    private ShoppingView view;

    public ShoppingController(ShoppingModel model, ShoppingView view) {
        this.model = model;
        this.view = view;

        // ActionListener pour le bouton d'inscription
        view.getSubmitRegisterButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String nom = view.getNom();
                String prenom = view.getPrenom();
                String email = view.getEmail();
                String password = view.getPassword();
                String confirmPassword = view.getConfirmPassword();

                // Vérification des champs
                if (nom.isEmpty() || prenom.isEmpty() || email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
                    view.setRegisterMessage("Tous les champs doivent être remplis !");
                } else if (!password.equals(confirmPassword)) {
                    view.setRegisterMessage("Les mots de passe ne correspondent pas !");
                } else {
                    boolean success = model.registerUser(nom, prenom, email, password);
                    if (success) {
                        view.clearRegisterMessage(); // Effacer le message
                        view.showHomePage(); // Redirection vers la HomePage
                    } else {
                        view.setRegisterMessage("Cet email est déjà utilisé.");
                    }
                }
            }
        });

        // ActionListener pour le bouton de connexion
        view.getSubmitLoginButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String email = view.getEmail();
                String password = view.getPassword();

                // Vérification des champs
                if (email.isEmpty() || password.isEmpty()) {
                    view.setRegisterMessage("Tous les champs doivent être remplis !");
                } else {
                    if (model.checkLogin(email, password)) {
                        view.showHomePage(); // Redirection vers la HomePage
                    } else {
                        view.setRegisterMessage("Email ou mot de passe incorrect.");
                    }
                }
            }
        });

        // Actions pour les boutons de navigation
        view.getLoginButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                view.showPage("Login");
            }
        });

        view.getRegisterButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                view.showPage("Register");
            }
        });
    }
}
