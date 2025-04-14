package DAO;

// import des packages

import modele.Article;

import java.sql.*;
import java.util.ArrayList;

/**
 * implémentation MySQL du stockage dans la base de données des méthodes définies dans l'interface
 * ProduitDao.
 */
public class ArticleDAOImpl implements ArticleDAO {
    // attribut privé pour l'objet du DaoFactoru
    private DaoFactory daoFactory;

    // constructeur dépendant de la classe DaoFactory
    public ArticleDAOImpl(DaoFactory daoFactory) {
        this.daoFactory = daoFactory;
    }

    @Override
    /**
     * Récupérer de la base de données tous les objets des produits dans une liste
     * @return : liste retournée des objets des produits récupérés
     */
    public ArrayList<Article> getAll() {
        ArrayList<Article> listeProduits = new  ArrayList<Article>();

        /*
            Récupérer la liste des produits de la base de données dans listeProduits
        */
        try {
            // connexion
            Connection connexion = daoFactory.getConnection();;
            Statement statement = connexion.createStatement();

            // récupération des produits de la base de données avec la requete SELECT
            ResultSet resultats = statement.executeQuery("select * from produits");

            // 	Se déplacer sur le prochain enregistrement : retourne false si la fin est atteinte
            while (resultats.next()) {
                // récupérer les 3 champs de la table produits dans la base de données
                int produitId = resultats.getInt(1);
                String produitNom = resultats.getString(2);
                double produitPrix = resultats.getDouble(3);

                // instancier un objet de Produit avec ces 3 champs en paramètres
                //Article product = new Article(produitId,produitNom,produitPrix);

                // ajouter ce produit à listeProduits
                //listeProduits.add(Article);
            }
        }
        catch (SQLException e) {
            //traitement de l'exception
            e.printStackTrace();
            System.out.println("Création de la liste de produits impossible");
        }

        return listeProduits;
    }

    @Override
    /**
     Ajouter un nouveau produit en paramètre dans la base de données
     @params : product = objet du Produit en paramètre à insérer dans la base de données
     */
    public void ajouter(Article product) {
        try {
            // connexion
            Connection connexion = daoFactory.getConnection();

            // récupération du nom et prix de l'objet product en paramètre
            //String nom = product.getProduitNom();
            //double prix = product.getProduitPrix();

            // Exécution de la requête INSERT INTO de l'objet product en paramètre
            //PreparedStatement preparedStatement = connexion.prepareStatement("INSERT INTO produits(produitNom, produitPrix) VALUES ('"+nom+"',"+prix+")");
            //preparedStatement.executeUpdate();
        }
        catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Ajout du produit impossible");
        }
    }

    @Override
    /**
     * Permet de chercher et récupérer un objet de Produit dans la base de données via son id en paramètre
     * @param : id
     * @return : objet de Produit cherché et retourné
     */
    public Article chercher(int id){
        Article product = null;

        try {
            // connexion
            Connection connexion = daoFactory.getConnection();;
            Statement statement = connexion.createStatement();

            // Exécution de la requête SELECT pour récupérer le produit de l'id dans la base de données
            ResultSet resultats = statement.executeQuery("select * from produits where produitID="+id);

            // 	Se déplacer sur le prochain enregistrement : retourne false si la fin est atteinte
            while (resultats.next()) {
                // récupérer les 3 champs de la table produits dans la base de données
                // récupération des 3 champs du produit de la base de données
                int produitId = resultats.getInt(1);
                String produitNom = resultats.getString(2);
                double produitPrix = resultats.getDouble(3);

                // Si l'id du produit est trouvé, l'instancier et sortir de la boucle
                if (id == produitId) {
                    // instanciation de l'objet de Produit avec ces 3 champs
                    //product = new Produit(produitId,produitNom,produitPrix);
                    break;
                }
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Produit non trouvé dans la base de données");
        }

        return product;
    }

    @Override
    /**
     * Permet de modifier les données du nom de l'objet de la classe Produit en paramètre
     * dans la base de données à partir de l'id de cet objet en paramètre
     * @param : product = objet en paramètre de la classe Produit à mettre à jour
     * @return : objet product en paramètre mis à jour  dans la base de données à retourner
      */
    public Article modifier(Article product) {
        try{
            // connexion
            Connection connexion = daoFactory.getConnection();

            // récupération du nom et prix de l'objet product en paramètre
            //String nom = product.getProduitNom();
            //double prix = product.getProduitPrix();
            //int id = product.getProduitId();

            // Exécution de la requête UPDATE pour modifier le produit dans la base de données
            PreparedStatement preparedStatement = connexion.prepareStatement("UPDATE produits SET produitNom = ?, produitPrix = ? WHERE produitID = ?");
            /*preparedStatement.setString(1, nom);
            preparedStatement.setDouble(2, prix);
            preparedStatement.setInt(3, id);
            preparedStatement.executeUpdate();*/
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Modification du produit impossible");
        }
        return product;
    }

    @Override
    /**
     * Supprimer un objet de la classe Produit en paramètre dans la base de données en respectant la contrainte
     * d'intégrité référentielle : en supprimant un produit, supprimer aussi en cascade toutes les commandes de la
     * table commander qui ont l'id du produit supprimé.
     * @params : product = objet de Produit en paramètre à supprimer de la base de données
     */
    public void supprimer(Article product) {
        try {
            // connexion
            Connection connexion = daoFactory.getConnection();

            // récupération de l'id de l'objet product en paramètre
            //int id = product.getProduitId();

            // Exécution de la requête DELETE pour supprimer les commandes associées au produit
            PreparedStatement preparedStatement = connexion.prepareStatement("DELETE FROM commander WHERE produitID = ?");
            //preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();

            // Exécution de la requête DELETE pour supprimer le produit de la base de données
            preparedStatement = connexion.prepareStatement("DELETE FROM produits WHERE produitID = ?");
            //preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Suppression du produit impossible");
        }
    }
}
