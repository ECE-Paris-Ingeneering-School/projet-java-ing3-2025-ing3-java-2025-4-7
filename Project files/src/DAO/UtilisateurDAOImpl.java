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
    public void ajouter(Utilisateur user) {
        String sql = "INSERT INTO utilisateurs (utilisateurID, utilisateurPrenom, utilisateurNom, utilisateurMail, utilisateurMDP, utilisateurAdresse, utilisateurTel, utilisateurIsAdmin) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection connexion = daoFactory.getConnection();
             PreparedStatement pStatement = connexion.prepareStatement(sql)) {

            pStatement.setInt(1, user.getId());
            pStatement.setString(2, user.getPrenom());
            pStatement.setString(3, user.getNom());
            pStatement.setString(4, user.getEmail());
            pStatement.setString(5, user.getMotDePasse());
            pStatement.setString(6, user.getAdresse());
            pStatement.setInt(7, user.getTelephone());
            pStatement.setBoolean(8, user.getIsAdmin());

            pStatement.executeUpdate();
            System.out.println("Utilisateur ajouté avec succès !");
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Erreur lors de l'ajout du client dans la base de données");
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

    /**
     * Permet de modifier les données de l'objet
     *
     * @params : client = objet en paramètre de la classe Client à mettre à jour à partir de son id
     * @return : objet client en paramètre mis à jour  dans la base de données à retourner
     */
    @Override
    public Utilisateur modifier(Utilisateur user, String mail, String mdp, String nom, String prenom, String adresse, int telephone, Boolean isAdmin) {
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

    @Override
    /**
     * Supprimer un objet de la classe Client en paramètre dans la base de données en respectant la contrainte
     * d'intégrité référentielle : en supprimant un client, supprimer aussi en cascade toutes les commandes de la
     * table commander qui ont l'id du client supprimé.
     * @params : client = objet de Client en paramètre à supprimer de la base de données
     */
    public void supprimer(Utilisateur user) {
        try {
            // Connexion à la base de données
            Connection connexion = daoFactory.getConnection();
            Statement statement = connexion.createStatement();

            // Récupération des valeurs de l'objet Client
            int vUserID = user.getId();

            // Construction manuelle de la requête SQL
            String sql = "DELETE FROM commander WHERE utilisateurID = " + vUserID;

            // Exécution de la requête
            int rowsAffected = statement.executeUpdate(sql);

            // Vérification que la mise à jour a bien eu lieu
            if (rowsAffected > 0) {
                System.out.println("Utilisateur supprimé avec succès.");
            } else {
                System.out.println("Aucun utilisateur trouvé avec l'ID spécifié.");
            }
            // Construction manuelle de la requête SQL
            sql = "DELETE FROM clients WHERE utilisateurID = " + vUserID;

            // Exécution de la requête
            rowsAffected = statement.executeUpdate(sql);

            // Vérification que la mise à jour a bien eu lieu
            if (rowsAffected > 0) {
                System.out.println("Utilisateur supprimé avec succès.");
            } else {
                System.out.println("Aucun Utilisateur trouvé avec l'ID spécifié.");
            }

            // Fermeture des ressources
            statement.close();
            connexion.close();
        } catch (SQLException e) {
            // Traitement de l'exception
            e.printStackTrace();
            System.out.println("Erreur lors de la mise à jour du client dans la base de données");
        }
    }

}


