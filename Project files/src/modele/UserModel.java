package modele;

public class UserModel {
    public boolean authenticate(String email, String password) {
        // Logique pour vérifier les identifiants de l'utilisateur
        return true;  // Pour cet exemple, on considère toujours un authentification réussie
    }

    public boolean registerUser(String nom, String prenom, String email, String password) {
        // Logique pour enregistrer un utilisateur
        return true;  // On suppose ici que l'inscription a réussi
    }
}