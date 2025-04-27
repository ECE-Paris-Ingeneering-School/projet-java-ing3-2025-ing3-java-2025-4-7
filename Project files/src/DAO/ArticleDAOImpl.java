package DAO;

import modele.Article;
import java.sql.*;
import java.util.ArrayList;

/**
 * Implémentation MySQL du stockage dans la base de données des méthodes définies dans l'interface ProduitDao.
 */
public class ArticleDAOImpl implements ArticleDAO {
    private DaoFactory daoFactory;

    public ArticleDAOImpl(DaoFactory daoFactory) {
        this.daoFactory = daoFactory;
    }

    @Override
    public ArrayList<Article> getAll() {
        ArrayList<Article> listeArticle = new ArrayList<Article>();

        try {
            // Connexion à la base de données
            Connection connexion = daoFactory.getConnection();
            Statement statement = connexion.createStatement();

            // Modification de la requête SQL pour utiliser 'articleImageURL' au lieu de 'imageURL'
            ResultSet resultats = statement.executeQuery("SELECT articleID, articleNom, articleMarque, articlePrix_unitaire, articlePrix_vrac, articleSeuil_vrac, articleStock, articleIsAvailable, articleImageURL FROM articles");

            while (resultats.next()) {
                int articleID = resultats.getInt(1);
                String articleNom = resultats.getString(2);
                String articleMarque = resultats.getString(3);
                double articlePrixUnitaire = resultats.getDouble(4);
                double articlePrixVrac = resultats.getDouble(5);
                int articleSeuil = resultats.getInt(6);
                int articleStock = resultats.getInt(7);
                boolean articleIsAvailable = resultats.getBoolean(8);
                String articleImageURL = resultats.getString(9);  // Utilisation de 'articleImageURL'

                // Création de l'objet Article avec l'URL de l'image
                Article article = new Article(articleID, articleNom, articleMarque, articlePrixUnitaire, articlePrixVrac, articleSeuil, articleStock, articleIsAvailable, articleImageURL);

                // Ajout de l'article à la liste
                listeArticle.add(article);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Création de la liste d'articles impossible");
        }

        return listeArticle;
    }

    @Override
    public Article ajouter(Article article) {
        Connection connexion = null;
        PreparedStatement pStatement = null;
        ResultSet generatedKeys = null;

        String vArticleNom = article.getNom();
        String vArticleMarque = article.getMarque();
        double vArticlePrixUnique = article.getPrixUnitaire();
        double vArticlePrixVrac = article.getPrixVrac();
        int vArticleSeuilVrac = article.getSeuilVrac();
        int vArticleStock = article.getStock();
        boolean vArticleIsAvailable = article.getIsAvailable();
        String vArticleImageURL = article.getImageUrl();  // Utilisation de 'articleImageURL'

        // Modification de la requête SQL pour utiliser 'articleImageURL' au lieu de 'imageURL'
        String sql = "INSERT INTO articles (articleNom, articleMarque, articlePrix_unitaire, articlePrix_vrac, articleSeuil_vrac, articleStock, articleIsAvailable, articleImageURL) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try {
            connexion = daoFactory.getConnection();
            pStatement = connexion.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            pStatement.setString(1, vArticleNom);
            pStatement.setString(2, vArticleMarque);
            pStatement.setDouble(3, vArticlePrixUnique);
            pStatement.setDouble(4, vArticlePrixVrac);
            pStatement.setInt(5, vArticleSeuilVrac);
            pStatement.setInt(6, vArticleStock);
            pStatement.setBoolean(7, vArticleIsAvailable);
            pStatement.setString(8, vArticleImageURL);  // Utilisation de 'articleImageURL'

            int affectedRows = pStatement.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("L'insertion a échoué, aucune ligne affectée.");
            }

            generatedKeys = pStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                int generatedId = generatedKeys.getInt(1);
                article.setId(generatedId);
            } else {
                throw new SQLException("L'insertion a échoué, aucun ID généré.");
            }

            return article;

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Erreur lors de l'ajout de l'article dans la base de données");
            return null;
        } finally {
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
    public Article chercher(int id) {
        Article product = null;

        try {
            Connection connexion = daoFactory.getConnection();
            Statement statement = connexion.createStatement();

            // Modification de la requête SQL pour utiliser 'articleImageURL' au lieu de 'imageURL'
            ResultSet resultats = statement.executeQuery("SELECT articleID, articleNom, articleMarque, articlePrix_unitaire, articlePrix_vrac, articleSeuil_vrac, articleStock, articleIsAvailable, articleImageURL FROM articles WHERE articleID=" + id);

            while (resultats.next()) {
                int vArticleId = resultats.getInt(1);
                String vArticleNom = resultats.getString(2);
                String vArticleMarque = resultats.getString(3);
                double vArticlePrixUnique = resultats.getDouble(4);
                double vArticlePrixVrac = resultats.getDouble(5);
                int vArticleSeuilVrac = resultats.getInt(6);
                int vArticleStock = resultats.getInt(7);
                boolean vArticleIsAvailable = resultats.getBoolean(8);
                String vArticleImageURL = resultats.getString(9);  // Utilisation de 'articleImageURL'

                if (id == vArticleId) {
                    product = new Article(vArticleId, vArticleNom, vArticleMarque, vArticlePrixUnique, vArticlePrixVrac, vArticleSeuilVrac, vArticleStock, vArticleIsAvailable, vArticleImageURL);
                    break;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Produit non trouvé dans la base de données");
        }

        return product;
    }



    /**
     * Permet de modifier les données du nom de l'objet de la classe Produit en paramètre
     * dans la base de données à partir de l'id de cet objet en paramètre
     * @param : product = objet en paramètre de la classe Produit à mettre à jour
     * @return : objet product en paramètre mis à jour  dans la base de données à retourner
      */
    @Override
    public Article modifier(Article article) {
        try {
            System.out.println("Debug: Début de la méthode modifier");
            System.out.println("Debug: Article ID = " + article.getId());

            // Check if the article exists using the DAO's chercher method
            Article existingArticle = chercher(article.getId());
            if (existingArticle != null) {
                System.out.println("Debug: L'article existe déjà. Mise à jour en cours...");
                // Update the article using the DAO's update logic
                String updateQuery = "UPDATE articles SET articleNom = ?, articleMarque = ?, articlePrix_unitaire = ?, articlePrix_vrac = ?, articleSeuil_vrac = ?, articleStock = ?, articleIsAvailable = ? WHERE articleID = ?";
                try (Connection connection = daoFactory.getConnection();
                     PreparedStatement updateStmt = connection.prepareStatement(updateQuery)) {
                    updateStmt.setString(1, article.getNom());
                    updateStmt.setString(2, article.getMarque());
                    updateStmt.setDouble(3, article.getPrixUnitaire());
                    updateStmt.setDouble(4, article.getPrixVrac());
                    updateStmt.setInt(5, article.getSeuilVrac());
                    updateStmt.setInt(6, article.getStock());
                    updateStmt.setBoolean(7, article.getIsAvailable());
                    updateStmt.setInt(8, article.getId());
                    updateStmt.executeUpdate();
                }
            } else {
                System.out.println("Debug: L'article n'existe pas. Insertion en cours...");
                ajouter(article); // Use the DAO's ajouter method to insert the article
            }
        } catch (SQLException e) {
            System.out.println("Debug: Erreur SQL - " + e.getMessage());
            e.printStackTrace();
        }

        System.out.println("Debug: Fin de la méthode modifier");
        return article;
    }
    public ArrayList<Article> rechercherArticles(String motCle) {
        ArrayList<Article> articles = new ArrayList<>();
        String sql = "SELECT * FROM articles WHERE articleNom LIKE ? OR articleMarque LIKE ?";

        try (Connection connexion = daoFactory.getConnection();
             PreparedStatement ps = connexion.prepareStatement(sql)) {

            ps.setString(1, "%" + motCle + "%");
            ps.setString(2, "%" + motCle + "%");

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int articleID = rs.getInt("articleID");
                String articleNom = rs.getString("articleNom");
                String articleMarque = rs.getString("articleMarque");
                double prixUnitaire = rs.getDouble("articlePrix_unitaire");
                double prixVrac = rs.getDouble("articlePrix_vrac");
                int seuilVrac = rs.getInt("articleSeuil_vrac");
                int stock = rs.getInt("articleStock");
                boolean isAvailable = rs.getBoolean("articleIsAvailable");
                String imageURL = rs.getString("articleImageURL");

                if (isAvailable && stock > 0) {
                    articles.add(new Article(articleID, articleNom, articleMarque, prixUnitaire, prixVrac, seuilVrac, stock, isAvailable, imageURL));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Erreur lors de la recherche d'articles.");
        }

        return articles;
    }


    /**
     * Supprimer un objet de la classe Produit en paramètre dans la base de données en respectant la contrainte
     * d'intégrité référentielle : en supprimant un produit, supprimer aussi en cascade toutes les commandes de la
     * table commander qui ont l'id du produit supprimé.
     * @params : product = objet de Produit en paramètre à supprimer de la base de données
     */
    //Supprimer un article
    @Override
    public void supprimer(Article article) {
        try (Connection connexion = daoFactory.getConnection()) {
            // Step 1: Delete the article from the "articles" table
            String deleteArticleSQL = "DELETE FROM articles WHERE articleID = ?";
            try (PreparedStatement deleteArticleStmt = connexion.prepareStatement(deleteArticleSQL)) {
                deleteArticleStmt.setInt(1, article.getId());
                deleteArticleStmt.executeUpdate();
            }

            // Step 2: Update all commandes to remove the article from Liste_Id_articles and Liste_Quantite_articles
            String selectCommandesSQL = "SELECT commandeID, Liste_Id_articles, Liste_Quantite_articles FROM commandes";
            try (PreparedStatement selectCommandesStmt = connexion.prepareStatement(selectCommandesSQL);
                 ResultSet resultSet = selectCommandesStmt.executeQuery()) {

                while (resultSet.next()) {
                    int commandeId = resultSet.getInt("commandeID");
                    String listeIdArticles = resultSet.getString("Liste_Id_articles");
                    String listeQuantiteArticles = resultSet.getString("Liste_Quantite_articles");

                    // Parse and update the lists
                    String[] idArray = listeIdArticles.split("-");
                    String[] quantityArray = listeQuantiteArticles.split("-");

                    StringBuilder updatedIdList = new StringBuilder();
                    StringBuilder updatedQuantityList = new StringBuilder();

                    for (int i = 0; i < idArray.length; i++) {
                        if (!idArray[i].equals(String.valueOf(article.getId()))) {
                            if (updatedIdList.length() > 0) {
                                updatedIdList.append("-");
                                updatedQuantityList.append("-");
                            }
                            updatedIdList.append(idArray[i]);
                            updatedQuantityList.append(quantityArray[i]);
                        }
                    }

                    // Update the commande if changes were made
                    if (!updatedIdList.toString().equals(listeIdArticles)) {
                        String updateCommandeSQL = "UPDATE commandes SET Liste_Id_articles = ?, Liste_Quantite_articles = ? WHERE commandeID = ?";
                        try (PreparedStatement updateCommandeStmt = connexion.prepareStatement(updateCommandeSQL)) {
                            updateCommandeStmt.setString(1, updatedIdList.toString());
                            updateCommandeStmt.setString(2, updatedQuantityList.toString());
                            updateCommandeStmt.setInt(3, commandeId);
                            updateCommandeStmt.executeUpdate();
                        }
                    }
                }
            }

            System.out.println("Article and its references in commandes removed successfully.");
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error while deleting the article and updating commandes.");
        }
    }
}
