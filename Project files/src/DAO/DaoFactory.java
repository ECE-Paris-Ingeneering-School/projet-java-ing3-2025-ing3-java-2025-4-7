package DAO;

// import des packages

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * La DAO Factory (DaoFactory.java) permet d'initialiser le DAO en chargeant notamment les drivers nécessaires
 * (ici un driver JDBC MySQL) et se connecte à la base de données. La Factory peut fournir plusieurs DAO (ici,
 * il n'y en a qu'un seul, UtilisateurDao, qui correspond à une table de la base).
 */
public class DaoFactory {
    /**
     * Attributs private pour la connexion JDBC
     */
    private static String url;
    private String username;
    private String password;

    // constructeur
    public DaoFactory(String url, String username, String password) {
        this.url = url;
        this.username = username;
        this.password = password;
    }

    /**
     * Méthode qui retourne 1 objet de DaoFactory
     * @param : url, username et password de la base de données
     * @return : objet de la classe DaoFactoru
     */
    public static DaoFactory getInstance(String database, String username, String password) {
        try {
            // chargement driver "com.mysql.cj.jdbc.Driver"
            Class.forName("com.mysql.cj.jdbc.Driver");
        }
        catch (ClassNotFoundException e) {
            System.out.println("Erreur de connexion à la base de données");
        }

        url = "jdbc:mysql://localhost:3306/" + database;

        // Instancier une instance l'objet de DaoFactory
        DaoFactory instance = new DaoFactory(url, username,password );

        // Retourner cette instance
        return instance;
    }

    public static DaoFactory getInstanceTest(String database, String username, String password) {
        try {
            // Chargement du driver JDBC
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            System.out.println("Erreur : le driver JDBC n'a pas été trouvé.");
        }

        if (database == null || database.isEmpty() || username == null || username.isEmpty()) {
            throw new IllegalArgumentException("Les paramètres de connexion ne peuvent pas être vides.");
        }

        String url = "jdbc:mysql://localhost:3306/" + database;

        return new DaoFactory(url, username, password);
    }

    /**
     * Méthode qui retourne le driver de base de données approprié
     * @return : le driver approprié
     * @throws SQLException
     */
    public Connection getConnection() throws SQLException {
        // Retourner la connection du driver de la base de données
        return DriverManager.getConnection(url, username, password);
    }

    /**
     * Récupération du Dao pour le produit
     * @return : objet de la classe ProduitDAOImpl
     */
    public ArticleDAO getArticle() {
        // Retourner un objet de ProduitDAOImpl qui implémente ProduitDAO
        return new ArticleDAOImpl(this);
    }

    /**
     * Récupération du Dao pour les clients
     * @return : objet de la classe ClientDAOImpl
     */
    public UtilisateurDAO getUtilisateur() {
        // Retourner un objet de ClientDAOImpl qui implémente ClientDAO
        return new UtilisateurDAOImpl(this);
    }

    /**
     * Récupération du Dao pour les commandes
     * @return : objet de la classe CommanderDAOImpl
     */
    public CommandeDAO getCommandeDAO() {
        // Retourner un objet de CommanderDAOImpl qui implémente CommanderDAO
        return new CommandeDAOImpl(this);
    }

    /**
     *     Fermer la connexion à la base de données
     */
    public void disconnect() {
        Connection connexion = null;

        try {
            // création d'un ordre SQL (statement)
            connexion = this.getConnection();
            connexion.close();
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Erreur de déconnexion à la base de données");
        }
    }
}