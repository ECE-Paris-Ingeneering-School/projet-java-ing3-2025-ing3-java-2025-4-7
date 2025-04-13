package modele;

import java.util.HashMap;
import java.util.Map;

public class ShoppingModel {

    // Simulons une base de données d'utilisateurs (email, mot de passe)
    private Map<String, String> users;

    public ShoppingModel() {
        users = new HashMap<>();
        users.put("test@example.com", "password123");
    }

    // Méthode pour vérifier la connexion avec un email et un mot de passe
    public boolean checkLogin(String email, String password) {
        return users.containsKey(email) && users.get(email).equals(password);
    }

    // Méthode d'inscription
    public boolean registerUser(String nom, String prenom, String email, String password) {
        if (!users.containsKey(email)) {
            users.put(email, password);
            return true;
        }
        return false;
    }
}
