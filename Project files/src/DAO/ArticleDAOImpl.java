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
        ArrayList<Article> listeArticle = new ArrayList<Article>();

        /*
            Récupérer la liste des produits de la base de données dans listeProduits
        */
        try {
            // connexion
            Connection connexion = daoFactory.getConnection();
            Statement statement = connexion.createStatement();

            // récupération des produits de la base de données avec la requete SELECT
            ResultSet resultats = statement.executeQuery("select * from article");

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
                boolean articleIsAvailable = resultats.getBoolean(8);

                // instancier un objet de Produit avec ces 3 champs en paramètres
                Article article = new Article(articleID, articleNom, articleMarque, articlePrixUnitaire, articlePrixVrac, articleSeuil, articleStock, articleIsAvailable);

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
    public Article ajouter(Article article) {
        Connection connexion = null;
        PreparedStatement pStatement = null;
        ResultSet generatedKeys = null;

        try {
            // Connexion à la base de données
            connexion = daoFactory.getConnection();

            String vArticleNom = article.getNom();
            String vArticleMarque = article.getMarque();
            double vArticlePrixUnique = article.getPrixUnitaire();
            double vArticlePrixVrac = article.getPrixVrac();
            int vArticleSeuilVrac = article.getSeuilVrac();
            int vArticleStock = article.getStock();
            boolean vArticleIsAvailable = article.getIsAvailable();

            // Construction de la requête SQL avec RETURN_GENERATED_KEYS
            String sql = "INSERT INTO article (articleNom, articleMarque, articlePrix_unitaire, articlePrix_vrac, articleSeuil_vrac, articleStock, articleIsAvailable) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?)";

            // Préparation de la requête avec la possibilité de récupérer les clés générées
            pStatement = connexion.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            // Note: Les paramètres commencent à 1, pas à 2
            pStatement.setString(1, vArticleNom);
            pStatement.setString(2, vArticleMarque);
            pStatement.setDouble(3, vArticlePrixUnique);
            pStatement.setDouble(4, vArticlePrixVrac);
            pStatement.setInt(5, vArticleSeuilVrac);
            pStatement.setInt(6, vArticleStock);
            pStatement.setBoolean(7, vArticleIsAvailable);

            // Exécution de la requête
            int affectedRows = pStatement.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("L'insertion a échoué, aucune ligne affectée.");
            }

            // Récupération de l'ID généré
            generatedKeys = pStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                int generatedId = generatedKeys.getInt(1);
                article.setId(generatedId); // Supposons que votre classe Article a une méthode setId()
            } else {
                throw new SQLException("L'insertion a échoué, aucun ID généré.");
            }

            return article;

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Erreur lors de l'ajout de l'article dans la base de données");
            return null;
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
            ResultSet resultats = statement.executeQuery("select * from article where articleID="+id);

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
                boolean vArticleIsAvailable = resultats.getBoolean(8);

                // Si l'id du produit est trouvé, l'instancier et sortir de la boucle
                if (id == vArticleId) {
                    // instanciation de l'objet de Produit avec ces 3 champs
                    product = new Article(vArticleId, vArticleNom, vArticleMarque, vArticlePrixUnique, vArticlePrixVrac, vArticleSeuilVrac, vArticleStock, vArticleIsAvailable);
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
        Article newArticle = new Article(nom, marque, prixUnitaire, prixVrac, seuilVrac, stock, true);
        newArticle = ajouter(newArticle);
        supprimer(article);
        return newArticle;
    }

    @Override
    /**
     * Supprimer un objet de la classe Produit en paramètre dans la base de données en respectant la contrainte
     * d'intégrité référentielle : en supprimant un produit, supprimer aussi en cascade toutes les commandes de la
     * table commander qui ont l'id du produit supprimé.
     * @params : product = objet de Produit en paramètre à supprimer de la base de données
     */
    public void supprimer(Article article) {
        Connection connexion = null;
        try {
            // connexion
            connexion = daoFactory.getConnection();
            // Désactiver l'auto-commit pour gérer la transaction manuellement
            connexion.setAutoCommit(false);

            // récupération de l'id de l'objet product en paramètre
            int id = article.getId();

            // Correction de la requête SQL (virgule en trop enlevée)
            String sql = "UPDATE article SET articleIsAvailable = false WHERE articleID = ?";

            try (PreparedStatement statement = connexion.prepareStatement(sql)) {
                statement.setInt(1, id);
                // Exécution de la mise à jour
                int rowsAffected = statement.executeUpdate();

                if (rowsAffected > 0) {
                    connexion.commit(); // Valider la transaction
                    System.out.println("Produit marqué comme supprimé avec succès");
                } else {
                    System.out.println("Aucun produit trouvé avec cet ID");
                }
            } catch (SQLException e) {
                connexion.rollback(); // Annuler la transaction en cas d'erreur
                e.printStackTrace();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Suppression du produit impossible");
        } finally {
            // Fermer la connexion dans tous les cas
            if (connexion != null) {
                try {
                    connexion.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
