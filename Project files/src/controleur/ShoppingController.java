// ShoppingController.java
package controleur;

import modele.ShoppingModel;
import vue.ShoppingView;

public class ShoppingController {
    private ShoppingModel model;
    private ShoppingView view;

    public ShoppingController(ShoppingModel model, ShoppingView view) {
        this.model = model;
        this.view = view;

        view.getLoginButton().addActionListener(e -> view.setRegisterError("Connexion non implémentée.", false));
        view.getRegisterButton().addActionListener(e -> view.showPage("Register"));
        view.getQuitButton().addActionListener(e -> System.exit(0));
    }

    public void handleRegister() {
        String nom = view.getNom();
        String prenom = view.getPrenom();
        String email = view.getEmail();
        String pass = view.getPassword();
        String confirm = view.getConfirmPassword();

        String message = validateInput(nom, prenom, email, pass, confirm);
        if (message != null) {
            view.setRegisterError(message, false);
        } else {
            // model.saveUser(nom, prenom, email, pass); // à implémenter dans ShoppingModel
            view.setRegisterError("Inscription réussie !", true);
        }
    }

    private String validateInput(String nom, String prenom, String email, String pass, String confirm) {
        if (!nom.matches("[a-zA-Z\\-]+")) return "Nom invalide.";
        if (!prenom.matches("[a-zA-Z\\-]+")) return "Prénom invalide.";
        if (!email.matches("^[\\w.-]+@[\\w.-]+\\.[a-zA-Z]{2,}$")) return "Email invalide.";
        if (pass.length() < 8) return "Mot de passe trop court.";
        if (!pass.equals(confirm)) return "Les mots de passe ne correspondent pas.";
        return null;
    }
} // Fin
