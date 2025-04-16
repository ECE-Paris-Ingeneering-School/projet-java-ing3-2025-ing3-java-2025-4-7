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
        ArrayList<Article> listeArticle = new  ArrayList<Article>();

        /*
            Récupérer la liste des produits de la base de données dans listeProduits
        */
        try {
            // connexion
            Connection connexion = daoFactory.getConnection();;
            Statement statement = connexion.createStatement();

            // récupération des produits de la base de données avec la requete SELECT
            ResultSet resultats = statement.executeQuery("select * from articles");

            // 	Se déplacer sur le prochain enregistrement : retourne false si la fin est atteinte
            while (resultats.next()) {
                // récupérer les 7 champs de la table produits dans la base de données
                int articleID = resultats.getInt(1);
                String articleNom = resultats.getString(2);
                String articleMarque = resultats.getString(3);
                double articlePrixUnitaire = resultats.getDouble(4);
                double articlePrixVrac = resultats.getDouble(5);
                int articleSeuil = resultats.getInt(6);
                int articleStock = resultats.getInt(7);

                // instancier un objet de Produit avec ces 3 champs en paramètres
                Article article = new Article(articleID, articleNom, articleMarque, articlePrixUnitaire, articlePrixVrac, articleSeuil, articleStock);

                // ajouter ce produit à listeProduits
                listeArticle.add(article);
            }
        }
        catch (SQLException e) {
            //traitement de l'exception
            e.printStackTrace();
            System.out.println("Création de la liste d'article impossible");
        }

        return listeArticle;
    }

    @Override
    /**
     Ajouter un nouveau produit en paramètre dans la base de données
     @params : product = objet du Produit en paramètre à insérer dans la base de données
     */
    public void ajouter(Article article) {
        try {
            // Connexion à la base de données
            Connection connexion = daoFactory.getConnection();
            Statement statement = connexion.createStatement();

            int vArticleId = article.getId();
            String vArticleNom = article.getNom();
            String vArticleMarque = article.getMarque();
            double vArticlePrixUnique = article.getPrixUnitaire();
            double vArticlePrixVrac = article.getPrixVrac();
            int vArticleSeuilVrac = article.getSeuilVrac();
            int vArticleStock = article.getStock();

            // Construction manuelle de la requête SQL
            String sql = "INSERT INTO articles (articleID, articleNom, articleMarque, articlePrix_unitaire, articlePrix_vrac, articleSeuil_vrac, articleStock) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?)";

            try (PreparedStatement pStatement = connexion.prepareStatement(sql)) {
                pStatement.setInt(1, vArticleId);
                pStatement.setString(2, vArticleNom);
                pStatement.setString(3, vArticleMarque);
                pStatement.setDouble(4, vArticlePrixUnique);
                pStatement.setDouble(5, vArticlePrixVrac);
                pStatement.setInt(6, vArticleSeuilVrac);
                pStatement.setInt(7, vArticleStock);
                pStatement.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            // Exécution de la requête d'insertion
            statement.executeUpdate(sql);

            // Fermeture des ressources
            statement.close();
            connexion.close();
        } catch (SQLException e) {
            // Traitement de l'exception
            e.printStackTrace();
            System.out.println("Erreur lors de l'ajout de l'article dans la base de données");
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
            ResultSet resultats = statement.executeQuery("select * from articles where articleID="+id);

            // 	Se déplacer sur le prochain enregistrement : retourne false si la fin est atteinte
            while (resultats.next()) {
                // récupérer les 3 champs de la table produits dans la base de données
                // récupération des 3 champs du produit de la base de données
                int vArticleId = resultats.getInt(1);
                String vArticleNom = resultats.getString(2);
                String vArticleMarque = resultats.getString(3);
                double vArticlePrixUnique = resultats.getDouble(4);
                double vArticlePrixVrac = resultats.getDouble(5);
                int vArticleSeuilVrac = resultats.getInt(6);
                int vArticleStock = resultats.getInt(7);

                // Si l'id du produit est trouvé, l'instancier et sortir de la boucle
                if (id == vArticleId) {
                    // instanciation de l'objet de Produit avec ces 3 champs
                    product = new Article(vArticleId, vArticleNom, vArticleMarque, vArticlePrixUnique, vArticlePrixVrac, vArticleSeuilVrac, vArticleStock);
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
    public Article modifier(Article article, String nom, String marque, double prixUnitaire, double prixVrac, int seuilVrac, int stock) {
        Article newArticle = new Article(article.getId(), nom, marque, prixUnitaire, prixVrac, seuilVrac, stock);

        try{
            // connexion
            Connection connexion = daoFactory.getConnection();

            // récupération du nom et prix de l'objet product en paramètre
            int vArticleId = newArticle.getId();
            String vArticleNom = newArticle.getNom();
            String vArticleMarque = newArticle.getMarque();
            double vArticlePrixUnique = newArticle.getPrixUnitaire();
            double vArticlePrixVrac = newArticle.getPrixVrac();
            int vArticleSeuilVrac = newArticle.getSeuilVrac();
            int vArticleStock = newArticle.getStock();

            // Construction manuelle de la requête SQL
            String sql = "UPDATE articles SET articleNom = ?, " +
                    "articleMarque = ?, " +
                    "articlePrix_unitaire = ?, " +
                    "articlePrix_vrac = ?, " +
                    "articleSeuil_vrac = ?, " +
                    "articleStock = ?, " +
                    "WHERE utilisateurID = ?";

            try (PreparedStatement statement = connexion.prepareStatement(sql)) {
                statement.setString(1, vArticleNom);
                statement.setString(2, vArticleMarque);
                statement.setDouble(3, vArticlePrixUnique);
                statement.setDouble(4, vArticlePrixVrac);
                statement.setInt(5, vArticleSeuilVrac);
                statement.setInt(6, vArticleStock);
                statement.setInt(7, vArticleId);
                int rowsAffected = statement.executeUpdate();

                // Vérification que la mise à jour a bien eu lieu
                if (rowsAffected > 0) {
                    System.out.println("Utilisateur mis à jour avec succès.");
                } else {
                    System.out.println("Aucun utilisateur trouvé avec l'ID spécifié.");
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }

            // Fermeture des ressources
            connexion.close();
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Modification du produit impossible");
        }
        return newArticle;
    }

    @Override
    /**
     * Supprimer un objet de la classe Produit en paramètre dans la base de données en respectant la contrainte
     * d'intégrité référentielle : en supprimant un produit, supprimer aussi en cascade toutes les commandes de la
     * table commander qui ont l'id du produit supprimé.
     * @params : product = objet de Produit en paramètre à supprimer de la base de données
     */
    public Article supprimer(Article article) {
        Article articleSupp = new Article(article.getId(), article.getNom(), article.getMarque(), article.getPrixUnitaire(), article.getPrixVrac(), article.getSeuilVrac(), article.getStock());
        articleSupp.setAvailable(false);

        try {
            // connexion
            Connection connexion = daoFactory.getConnection();

            // récupération de l'id de l'objet product en paramètre
            int id = articleSupp.getId();
            boolean isAvailable = articleSupp.isAvailable();

            // Construction manuelle de la requête SQL
            String sql = "UPDATE articles SET isAvailable = ?, WHERE articleID = ?";

            try (PreparedStatement statement = connexion.prepareStatement(sql)) {
                statement.setBoolean(1, isAvailable);
                statement.setInt(2, id);
                int rowsAffected = statement.executeUpdate();

                // Vérification que la mise à jour a bien eu lieu
                if (rowsAffected > 0) {
                    System.out.println("Utilisateur mis à jour avec succès.");
                } else {
                    System.out.println("Aucun utilisateur trouvé avec l'ID spécifié.");
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Suppression du produit impossible");
        }
        return articleSupp;
    }
}
