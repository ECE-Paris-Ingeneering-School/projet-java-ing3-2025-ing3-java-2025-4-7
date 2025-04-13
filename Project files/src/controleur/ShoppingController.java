package controleur;

import vue.ShoppingView;
import modele.Utilisateur;

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
                String email = view.getRegisterEmail();
                String password = view.getRegisterPassword();
                if (email.isEmpty() || password.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Tous les champs sont obligatoires.");
                } else {
                    Utilisateur utilisateur = new Utilisateur(email, password);
                    JOptionPane.showMessageDialog(null, "Inscription réussie pour : " + utilisateur.getEmail());
                    view.showPage("HomePage");
                }
            }
        });
    }
}
