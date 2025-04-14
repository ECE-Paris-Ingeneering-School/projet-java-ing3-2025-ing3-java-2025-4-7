package DAO;

// import des packages

import modele.Utilisateur;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 * implémentation MySQL du stockage dans la base de données des méthodes définies dans l'interface
 * ClientDao.
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
     * Récupérer de la base de données tous les objets des clients dans une liste
     * @return : liste retournée des objets des clients récupérés
     */
    public ArrayList<Utilisateur> getAll() {
        ArrayList<Utilisateur> listeClients = new ArrayList<Utilisateur>();

        /*
            Récupérer la liste des clients de la base de données dans listeClients
        */
        try {
            // connexion
            Connection connexion = daoFactory.getConnection();
            ;
            Statement statement = connexion.createStatement();

            // récupération des produits de la base de données avec la requete SELECT
            ResultSet resultats = statement.executeQuery("select * from clients");

            // 	Se déplacer sur le prochain enregistrement : retourne false si la fin est atteinte
            while (resultats.next()) {
                // récupérer les 3 champs de la table produits dans la base de données
                int id = resultats.getInt(1);
                String nom = resultats.getString(2);
                String mail = resultats.getString(3);

                // instancier un objet de Produit avec ces 3 champs en paramètres
                //Utilisateur client = new Utilisateur(id, nom, mail);

                // ajouter ce produit à listeProduits
                //listeClients.add(client);
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
    public void ajouter(Utilisateur client) {
        try {
            // Connexion à la base de données
            Connection connexion = daoFactory.getConnection();
            Statement statement = connexion.createStatement();

            //int vClientId = client.getClientId();
            //String vClientMail = client.getclientMail();
            //String vClientNom = client.getclientNom();

            // Construction manuelle de la requête SQL
            //String sql = "INSERT INTO clients (clientID, clientNom, clientMail) VALUES (" +
            //        vClientId + ", '" + vClientMail + "', '" + vClientNom + "')";

            // Exécution de la requête d'insertion
            //statement.executeUpdate(sql);


            // Fermeture des ressources
            statement.close();
            connexion.close();
        } catch (SQLException e) {
            // Traitement de l'exception
            e.printStackTrace();
            System.out.println("Erreur lors de l'ajout du client dans la base de données");
        }
    }

    /**
     * Permet de chercher et récupérer un objet de Client dans la base de données via son id en paramètre
     *
     * @param : id
     * @return : objet de classe Client cherché et retourné
     */
    public Utilisateur chercher(int id) {
        Utilisateur client = null;

        try {
            // connexion
            Connection connexion = daoFactory.getConnection();
            ;
            Statement statement = connexion.createStatement();

            // Exécution de la requête SELECT pour récupérer le client de l'id dans la base de données
            ResultSet resultats = statement.executeQuery("select * from clients where clientID=" + id);

            // 	Se déplacer sur le prochain enregistrement : retourne false si la fin est atteinte
            while (resultats.next()) {
                // récupérer les 3 champs de la table produits dans la base de données
                int clientId = resultats.getInt(1);
                String clientNom = resultats.getString(2);
                String clientMail = resultats.getString(3);

                // Si l'id du client est trouvé, l'instancier et sortir de la boucle
                if (id == clientId) {
                    // instancier un objet de Produit avec ces 3 champs en paramètres
                    //client = new Utilisateur(clientId, clientNom, clientMail);
                    break;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Client non trouvé dans la base de données");
        }

        return client;
    }

    /**
     * Permet de modifier les données du nom de l'objet de la classe Client en paramètre
     * dans la base de données à partir de l'id de cet objet en paramètre
     *
     * @param : client = objet en paramètre de la classe Client à mettre à jour à partir de son id
     * @return : objet client en paramètre mis à jour  dans la base de données à retourner
     */
    @Override
    public Utilisateur modifier(Utilisateur client) {
        try {
            // Connexion à la base de données
            Connection connexion = daoFactory.getConnection();
            Statement statement = connexion.createStatement();

            // Récupération des valeurs de l'objet Client
            //int vClientId = client.getClientId();
            //String vClientMail = client.getclientMail();
            //String vClientNom = client.getclientNom();

            // Construction manuelle de la requête SQL
            //String sql = "UPDATE clients SET clientNom = '" + vClientNom + "', clientMail = '" + vClientMail + "' WHERE clientID = " + vClientId;

            // Exécution de la requête
            //int rowsAffected = statement.executeUpdate(sql);

            // Vérification que la mise à jour a bien eu lieu
            /*if (rowsAffected > 0) {
                System.out.println("Client mis à jour avec succès.");
            } else {
                System.out.println("Aucun client trouvé avec l'ID spécifié.");
            }*/

            // Fermeture des ressources
            statement.close();
            connexion.close();
        } catch (SQLException e) {
            // Traitement de l'exception
            e.printStackTrace();
            System.out.println("Erreur lors de la mise à jour du client dans la base de données");
        }

        // Retourne le client mis à jour
        return client;
    }

    @Override
    /**
     * Supprimer un objet de la classe Client en paramètre dans la base de données en respectant la contrainte
     * d'intégrité référentielle : en supprimant un client, supprimer aussi en cascade toutes les commandes de la
     * table commander qui ont l'id du client supprimé.
     * @params : client = objet de Client en paramètre à supprimer de la base de données
     */
    public void supprimer(Utilisateur client) {
        try {
            // Connexion à la base de données
            Connection connexion = daoFactory.getConnection();
            Statement statement = connexion.createStatement();

            // Récupération des valeurs de l'objet Client
            //int vClientId = client.getClientId();

            // Construction manuelle de la requête SQL
            //String sql = "DELETE FROM commander WHERE clientID = " + vClientId;

            // Exécution de la requête
            /*int rowsAffected = statement.executeUpdate(sql);

            // Vérification que la mise à jour a bien eu lieu
            if (rowsAffected > 0) {
                System.out.println("Client supprimé avec succès.");
            } else {
                System.out.println("Aucun client trouvé avec l'ID spécifié.");
            }
            // Construction manuelle de la requête SQL
            sql = "DELETE FROM clients WHERE clientID = " + vClientId;

            // Exécution de la requête
            rowsAffected = statement.executeUpdate(sql);

            // Vérification que la mise à jour a bien eu lieu
            if (rowsAffected > 0) {
                System.out.println("Client supprimé avec succès.");
            } else {
                System.out.println("Aucun client trouvé avec l'ID spécifié.");
            }*/

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


