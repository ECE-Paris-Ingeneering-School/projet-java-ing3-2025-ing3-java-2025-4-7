package modele;

public class ShoppingModel {
    // Liste simulée d'utilisateurs
    private static final String EMAIL_VALID = "utilisateur@example.com";
    private static final String PASSWORD_VALID = "password123";

    // Méthode de connexion
    public boolean loginUtilisateur(String email, String password) {
        // Cette méthode simule une vérification avec des valeurs fixes
        // Il faudrait ici interroger la base de données ou autre source de données
        return email.equals(EMAIL_VALID) && password.equals(PASSWORD_VALID);
    }
}
