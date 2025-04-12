package modele;

import java.util.HashMap;
import java.util.Map;

public class ShoppingModel {

    // Simulons une base de données d'utilisateurs (email, mot de passe)
    private Map<String, String> users;

    public ShoppingModel() {
        // Exemple d'utilisateurs avec des emails et des mots de passe
        users = new HashMap<>();
        users.put("test@example.com", "password123");  // Utilisateur de test
    }

    // Méthode pour vérifier la connexion avec un email et un mot de passe
    public boolean checkLogin(String email, String password) {
        // Vérifie si l'email existe et si le mot de passe correspond
        return users.containsKey(email) && users.get(email).equals(password);
    }

    // Méthode pour inscrire un utilisateur (simulée ici, mais à adapter pour une vraie base de données)
    public boolean registerUser(String nom, String prenom, String email, String password) {
        // Vérifie si l'email est déjà utilisé
        if (users.containsKey(email)) {
            return false;  // L'email existe déjà
        }
        // Sinon, on ajoute l'utilisateur
        users.put(email, password);
        return true;
    }
}
