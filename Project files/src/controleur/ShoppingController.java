package controleur;

import javax.swing.*;
import modele.ShoppingModel;
import vue.ShoppingView;

import java.awt.*;

public class ShoppingController {
    private ShoppingModel model;
    private ShoppingView view;

    public ShoppingController(ShoppingModel model, ShoppingView view) {
        this.model = model;
        this.view = view;

        // Bouton "Connexion" depuis la page d'entrée
        view.getLoginButtonOnEntryPanel().addActionListener(e -> view.showPage("Login"));

        // Bouton "Inscription" depuis la page d'entrée
        view.getRegisterButtonOnEntryPanel().addActionListener(e -> view.showPage("Register"));

        // Bouton "Quitter" depuis la page d'entrée
        view.getQuitButtonOnEntryPanel().addActionListener(e -> {
            int confirm = JOptionPane.showConfirmDialog(null,
                    "Voulez-vous vraiment quitter l'application ?",
                    "Confirmation",
                    JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                System.exit(0);
            }
        });

        // Gestion du bouton de connexion sur la page Login
        view.getLoginButtonOnLoginPanel().addActionListener(e -> {
            String email = view.getEmailField().getText().trim();
            String password = new String(view.getPasswordField().getPassword()).trim();

            if (model.loginUtilisateur(email, password)) {
                JOptionPane.showMessageDialog(view.getFrame(), "Connexion réussie !");
                view.showPage("ShopMain");
            } else {
                JOptionPane.showMessageDialog(view.getFrame(), "Erreur de connexion. Vérifiez vos identifiants.");
            }
        });

        // Gestion du bouton d'inscription sur la page Register
        view.getRegisterButtonOnRegisterPanel().addActionListener(e -> {
            String nom = view.getNomField().getText().trim();
            String prenom = view.getPrenomField().getText().trim();
            String email = view.getEmailField().getText().trim();
            String password = new String(view.getPasswordField().getPassword()).trim();
            String confirmPassword = new String(view.getConfirmPasswordField().getPassword()).trim();

            // Validation des champs
            if (!nom.matches("[a-zA-Z\\-]+")) {
                view.getFeedbackLabel().setText("Le nom ne doit contenir que des lettres ou des tirets.");
            } else if (!prenom.matches("[a-zA-Z\\-]+")) {
                view.getFeedbackLabel().setText("Le prénom ne doit contenir que des lettres ou des tirets.");
            } else if (!email.matches("^[\\w.-]+@[\\w.-]+\\.[a-zA-Z]{2,}$")) {
                view.getFeedbackLabel().setText("Adresse email invalide.");
            } else if (password.length() < 8) {
                view.getFeedbackLabel().setText("Le mot de passe doit contenir au moins 8 caractères.");
            } else if (!password.equals(confirmPassword)) {
                view.getFeedbackLabel().setText("Les mots de passe ne correspondent pas.");
            } else {
                // Inscription réussie
                view.getFeedbackLabel().setForeground(Color.white);
                view.getFeedbackLabel().setText("Inscription réussie ! Redirection...");

                // TODO: Enregistrer dans la base de données ici
                Timer redirectTimer = new Timer(1500, evt -> view.showPage("ShopMain"));
                redirectTimer.setRepeats(false);
                redirectTimer.start();
            }
        });

        // Gestion du bouton retour depuis la page d'inscription
        view.getReturnButtonOnRegisterPanel().addActionListener(e -> view.showPage("Entry"));
    }
}
