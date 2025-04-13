package controleur;

import vue.ShoppingView;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ShoppingController {

    private ShoppingView view;

    public ShoppingController(ShoppingView view) {
        this.view = view;

        // Actions pour les boutons dans le header
        view.getHomeButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                view.showPage("HomePage");
            }
        });

        view.getAccountButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                view.showPage("Account");
            }
        });

        view.getPanierButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                view.showPage("Panier");
            }
        });

        // Actions pour les boutons de la page "Mon compte"
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

        // Actions pour la recherche dans le header
        view.getSearchButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String searchQuery = view.getSearchField().getText();
                System.out.println("Recherche effectuée : " + searchQuery);
            }
        });
    }
}
