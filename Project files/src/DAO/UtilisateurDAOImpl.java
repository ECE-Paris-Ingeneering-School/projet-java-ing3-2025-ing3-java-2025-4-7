package DAO;

// import des packages

import modele.Utilisateur;

import java.sql.*;
import java.util.ArrayList;

/**
 * implémentation MySQL du stockage dans la base de données des méthodes définies dans l'interface
 * UtilisateurDAO
 */


public class UtilisateurDAOImpl implements UtilisateurDAO {
    // attribut privé pour l'objet du DaoFactoru
    private DaoFactory daoFactory;

    // constructeur dépendant de la classe DaoFactory
    public UtilisateurDAOImpl(DaoFactory daoFactory) {
        this.daoFactory = daoFactory;
    }

    @Override
    /**
     * Récupérer de la base de données tous les objets des utilisateurs dans une liste
     * @return : liste retournée des objets des clients récupérés
     */
    public ArrayList<Utilisateur> getAll() {
        ArrayList<Utilisateur> listeClients = new ArrayList<Utilisateur>();

        // Récupérer la liste des utilisateurs de la base de données dans listeUtilisateurs
        try {
            // connexion
            Connection connexion = daoFactory.getConnection();
            Statement statement = connexion.createStatement();

            // récupération des produits de la base de données avec la requete SELECT
            ResultSet resultats = statement.executeQuery("select * from utilisateurs");

            // 	Se déplacer sur le prochain enregistrement : retourne false si la fin est atteinte
            while (resultats.next()) {
                // récupérer les 8 champs de la table produits dans la base de données
                int id = resultats.getInt(1);
                String email = resultats.getString(2);
                String mdp = resultats.getString(3);
                String nom = resultats.getString(4);
                String prenom = resultats.getString(5);
                String adresse = resultats.getString(6);
                int telephone = resultats.getInt(7);
                boolean isAdmin = resultats.getBoolean(8);

                // instancier un objet de Produit avec ces 3 champs en paramètres
                Utilisateur utilisateur = new Utilisateur(id, email, mdp, nom, prenom, adresse, telephone, isAdmin);

                // ajouter ce produit à listeProduits
                listeClients.add(utilisateur);
            }
        } catch (SQLException e) {
            //traitement de l'exception
            e.printStackTrace();
            System.out.println("Création de la liste de clients impossible");
        }

        return listeClients;
    }

    /**
     * Ajouter un nouveau client en paramètre dans la base de données
     *
     * @params : client = objet de Client à insérer dans la base de données
     */
    @Override
    public boolean ajouter(Utilisateur user) {
        Connection connexion = null;
        PreparedStatement pStatement = null;
        ResultSet generatedKeys = null;

        String sql = "INSERT INTO utilisateurs (utilisateurPrenom, utilisateurNom, utilisateurMail, utilisateurMDP, utilisateurAdresse, utilisateurTel, utilisateurIsAdmin) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?)";

        try {
            connexion = daoFactory.getConnection();
            pStatement = connexion.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            pStatement.setString(1, user.getPrenom());
            pStatement.setString(2, user.getNom());
            pStatement.setString(3, user.getEmail());
            pStatement.setString(4, user.getMotDePasse());
            pStatement.setString(5, user.getAdresse());
            pStatement.setInt(6, user.getTelephone());
            pStatement.setBoolean(7, user.getIsAdmin());

            // Exécution de la requête
            int affectedRows = pStatement.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("L'insertion a échoué, aucune ligne affectée.");
            }
            // Récupération de l'ID généré
            generatedKeys = pStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                int generatedId = generatedKeys.getInt(1);
                user.setId(generatedId); // Supposons que votre classe Article a une méthode setId()
            } else {
                throw new SQLException("L'insertion a échoué, aucun ID généré.");
            }

            return true;

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Erreur lors de l'ajout du client dans la base de données");
            return false;
        } finally {
            // Fermeture des ressources dans le bloc finally
            try {
                if (generatedKeys != null) generatedKeys.close();
                if (pStatement != null) pStatement.close();
                if (connexion != null) connexion.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Permet de chercher et récupérer un objet de Client dans la base de données via son id en paramètre
     *
     * @params : id
     * @return : objet de classe Client cherché et retourné
     */
    public Utilisateur chercher(int id) {
        Utilisateur user = null;

        try {
            // connexion
            Connection connexion = daoFactory.getConnection();
            Statement statement = connexion.createStatement();

            // Exécution de la requête SELECT pour récupérer le client de l'id dans la base de données
            ResultSet resultats = statement.executeQuery("select * from utilisateurs where utilisateurID=" + id);

            // 	Se déplacer sur le prochain enregistrement : retourne false si la fin est atteinte
            while (resultats.next()) {
                // récupérer les 8 champs de la table produits dans la base de données
                int utilisateurId = resultats.getInt(1);
                String utilisateurEmail = resultats.getString(2);
                String utilisateurMDP = resultats.getString(3);
                String utilisateurNom = resultats.getString(4);
                String utilisateurPrenom = resultats.getString(5);
                String utilisateurAdresse = resultats.getString(6);
                int utilisateurTelephone = resultats.getInt(7);
                Boolean utilisateurIsAdmin = resultats.getBoolean(8);

                // Si l'id du client est trouvé, l'instancier et sortir de la boucle
                if (id == utilisateurId) {
                    // instancier un objet de Produit avec ces 3 champs en paramètres
                    user = new Utilisateur(utilisateurId, utilisateurEmail, utilisateurMDP, utilisateurNom, utilisateurPrenom, utilisateurAdresse, utilisateurTelephone, utilisateurIsAdmin);
                    break;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Client non trouvé dans la base de données");
        }

        return user;
    }

    public Utilisateur chercherLogin(String email, String mdp) throws Exception {
        Utilisateur user = null;
        boolean emailExists = false;

        try (Connection connexion = daoFactory.getConnection();
             PreparedStatement statement = connexion.prepareStatement("SELECT * FROM utilisateurs WHERE utilisateurMail = ?")) {

            // Check if the email exists
            statement.setString(1, email);
            try (ResultSet resultats = statement.executeQuery()) {
                if (resultats.next()) {
                    emailExists = true;

                    // Check if the password matches
                    if (mdp.equals(resultats.getString("utilisateurMDP"))) {
                        // Create the user object if both email and password match
                        user = new Utilisateur(
                                resultats.getInt("utilisateurID"),
                                resultats.getString("utilisateurMail"),
                                resultats.getString("utilisateurMDP"),
                                resultats.getString("utilisateurNom"),
                                resultats.getString("utilisateurPrenom"),
                                resultats.getString("utilisateurAdresse"),
                                resultats.getInt("utilisateurTel"),
                                resultats.getBoolean("utilisateurIsAdmin")
                        );
                    } else {
                        throw new Exception("Mot de passe incorrect.");
                    }
                }
            }

            if (!emailExists) {
                throw new Exception("Adresse email introuvable.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
            throw new Exception("Erreur lors de la recherche de l'utilisateur.");
        }

        return user;
    }

    /**
     * Permet de modifier les données de l'objet
     *
     * @params : client = objet en paramètre de la classe Client à mettre à jour à partir de son id
     * @return : objet client en paramètre mis à jour  dans la base de données à retourner
     */
    @Override
    public Utilisateur modifier(Utilisateur user, String mail, String mdp, String nom, String prenom, String adresse, int telephone, Boolean isAdmin) {
        if (user == null) {
            throw new IllegalArgumentException("L'utilisateur à modifier ne peut pas être null.");
        }

        Utilisateur newUser = new Utilisateur(user.getId(), mail, mdp, nom, prenom, adresse, telephone, isAdmin);

        String sql = "UPDATE utilisateurs SET utilisateurPrenom = ?, utilisateurNom = ?, utilisateurMail = ?, utilisateurMDP = ?, utilisateurAdresse = ?, utilisateurTel = ?, utilisateurIsAdmin = ? WHERE utilisateurID = ?";

        try (Connection connexion = daoFactory.getConnection();
             PreparedStatement pStatement = connexion.prepareStatement(sql)) {

            // Définir les paramètres de la requête
            pStatement.setString(1, prenom);
            pStatement.setString(2, nom);
            pStatement.setString(3, mail);
            pStatement.setString(4, mdp);
            pStatement.setString(5, adresse);
            pStatement.setInt(6, telephone);
            pStatement.setBoolean(7, isAdmin);
            pStatement.setInt(8, user.getId());

            // Exécuter la requête
            int rowsAffected = pStatement.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Utilisateur mis à jour avec succès.");
            } else {
                System.out.println("Aucun utilisateur trouvé avec l'ID spécifié.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Erreur lors de la mise à jour du client dans la base de données");
        }

        return newUser;
    }

    /**
     * Supprimer un objet de la classe Client en paramètre dans la base de données en respectant la contrainte
     * d'intégrité référentielle : en supprimant un client, supprimer aussi en cascade toutes les commandes de la
     * table commander qui ont l'id du client supprimé.
     * @params : client = objet de Client en paramètre à supprimer de la base de données
     */
    @Override
    public void supprimer(int id) {
        String deleteCommandesSQL = "DELETE FROM commandes WHERE utilisateurID = ?";
        String deleteUtilisateurSQL = "DELETE FROM utilisateurs WHERE utilisateurID = ?";

        try (Connection connexion = daoFactory.getConnection();
             PreparedStatement deleteCommandesStmt = connexion.prepareStatement(deleteCommandesSQL);
             PreparedStatement deleteUtilisateurStmt = connexion.prepareStatement(deleteUtilisateurSQL)) {

            // Définir l'ID de l'utilisateur pour les deux requêtes
            deleteCommandesStmt.setInt(1, id);
            deleteUtilisateurStmt.setInt(1, id);

            // Supprimer les commandes associées
            int commandesAffected = deleteCommandesStmt.executeUpdate();
            if (commandesAffected > 0) {
                System.out.println("Commandes associées supprimées avec succès.");
            } else {
                System.out.println("Aucune commande trouvée pour cet utilisateur.");
            }

            // Supprimer l'utilisateur
            int utilisateurAffected = deleteUtilisateurStmt.executeUpdate();
            if (utilisateurAffected > 0) {
                System.out.println("Utilisateur supprimé avec succès.");
            } else {
                System.out.println("Aucun utilisateur trouvé avec l'ID spécifié.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Erreur lors de la suppression de l'utilisateur dans la base de données.");
        }
    }

    public Utilisateur chercherParEmail(String email) {
        Utilisateur user = null;

        String sql = "SELECT * FROM utilisateurs WHERE utilisateurMail = ?";

        try (Connection connexion = daoFactory.getConnection();
             PreparedStatement statement = connexion.prepareStatement(sql)) {

            statement.setString(1, email);
            try (ResultSet resultats = statement.executeQuery()) {
                if (resultats.next()) {
                    user = new Utilisateur(
                            resultats.getInt("utilisateurID"),
                            resultats.getString("utilisateurMail"),
                            resultats.getString("utilisateurMDP"),
                            resultats.getString("utilisateurNom"),
                            resultats.getString("utilisateurPrenom"),
                            resultats.getString("utilisateurAdresse"),
                            resultats.getInt("utilisateurTel"),
                            resultats.getBoolean("utilisateurIsAdmin")
                    );
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Erreur lors de la recherche par email.");
        }

        return user;
    }

}


