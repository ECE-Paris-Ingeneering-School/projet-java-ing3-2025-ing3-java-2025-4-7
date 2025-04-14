package DAO;

// import des packages

import modele.Commande;

import java.sql.*;
import java.util.ArrayList;

/**
 * implémentation MySQL du stockage dans la base de données des méthodes définies dans l'interface
 * CommanderDao.
 */
public class CommandeDAOImpl implements CommandeDAO {
    // attribut privé pour l'objet du DaoFactoru
    private DaoFactory daoFactory;

    // constructeur dépendant de la classe DaoFactory
    public CommandeDAOImpl(DaoFactory daoFactory) {
        this.daoFactory = daoFactory;
    }

    @Override
    /**
     * Récupérer de la base de données tous les objets des commandes des produits par les clients dans une liste
     * @return : liste retournée des objets des produits récupérés
     */
    public ArrayList<Commande> getAll() {
        ArrayList<Commande> listeCommandes = new ArrayList<Commande>();

        /*
            Récupérer la liste des clients de la base de données dans listeProduits
        */
        try {
            // connexion
            Connection connexion = daoFactory.getConnection();
            ;
            Statement statement = connexion.createStatement();

            // récupération de l'ordre de la requete
            ResultSet resultats = statement.executeQuery("select * from commander");

            // récupération du résultat de l'ordre
            ResultSetMetaData rsmd = resultats.getMetaData();

            // 	Se déplacer sur le prochain enregistrement : retourne false si la fin est atteinte
            while (resultats.next()) {
                // récupérer les 3 champs de la table commander
                int clientId = resultats.getInt(1);
                int produitId = resultats.getInt(2);
                int quantite = resultats.getInt(3);

                // instancier un objet de Produit
                //Commande achat = new Commande(clientId, produitId, quantite);

                // ajouter ce produit à listeProduits
                //listeCommandes.add(achat);
            }
        } catch (SQLException e) {
            //traitement de l'exception
            e.printStackTrace();
            System.out.println("Création de la liste des commandes impossible");
        }

        return listeCommandes;
    }

    @Override
    /**
     Ajouter une nouvelle commande d'un produit par un client en paramètre dans la base de données
     @params : achat = objet de la commande en paramètre à insérer dans la base de données
     */
    public void ajouter(Commande achat) {
        try {
            // connexion
            Connection connexion = daoFactory.getConnection();

            // récupération du nom et prix de l'objet product en paramètre
            //int vClientID = achat.getClientId();
            //int vProduitID = achat.getProduitId();
            //int vQuantite = achat.getQuantite();

            // Exécution de la requête INSERT INTO de l'objet product en paramètre
            //PreparedStatement preparedStatement = connexion.prepareStatement("INSERT INTO commander(clientID, produitID,quantité) VALUES ('" + vClientID + "','" + vProduitID + "','" + vQuantite + "')");
            //preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Ajout du produit impossible");
        }
    }

    @Override
    /**
     * Permet de chercher et récupérer un objet de Commander dans la base de données via ses clientID et produitID
     * en paramètres
     * @param : clientID et produitID
     * @return : objet de commande cherché et retourné
     */
    public Commande chercher(int clientID, int produitID) {
        Commande achat = null;

        try {
            // connexion
            Connection connexion = daoFactory.getConnection();
            ;
            Statement statement = connexion.createStatement();

            // Exécution de la requête SELECT pour récupérer le produit de l'id dans la base de données
            ResultSet resultats = statement.executeQuery("select * from commander where clientID=" + clientID);

            // 	Se déplacer sur le prochain enregistrement : retourne false si la fin est atteinte
            while (resultats.next()) {
                // récupérer les 3 champs de la table produits dans la base de données
                // récupération des 3 champs du produit de la base de données
                int vClientId = resultats.getInt(1);
                int vProduitID = resultats.getInt(2);
                int vQuantité = resultats.getInt(3);

                // Si l'id du produit est trouvé, l'instancier et sortir de la boucle
                if (clientID == vClientId) {
                    System.out.println("Produit  trouvé dans la base de données");
                    // instanciation de l'objet de Produit avec ces 3 champs
                    //achat = new Commande(vClientId, vProduitID, vQuantité);
                    break;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Produit non trouvé dans la base de données");
        }
        return achat;
    }

    @Override
    /**
     * Permet de modifier les données du nom de l'objet de la classe Commander en paramètre
     * dans la base de données à partir de clientID et produitID de cet objet en paramètre
     * @param : achat = objet en paramètre de la classe Commander à mettre à jour
     * @return : objet achat en paramètre mis à jour  dans la base de données à retourner
     */
    public Commande modifier(Commande achat) {
        /*
            A COMPLETER
         */

        return achat;
    }

    @Override
    /**
     Supprimer un objet de la classe Commander en paramètre dans la base de données
     @params : product = objet de Produit en paramètre à supprimer de la base de données
     */
    public void supprimer(Commande achat) {
        /*
            A COMPLETER
         */

    }
}
