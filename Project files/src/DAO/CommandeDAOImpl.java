package DAO;

import modele.Commande;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CommandeDAOImpl implements CommandeDAO {
    private final DaoFactory daoFactory;

    public CommandeDAOImpl(DaoFactory daoFactory) {
        this.daoFactory = daoFactory;
    }

    @Override
    public List<Commande> getAll() {
        List<Commande> commandes = new ArrayList<>();
        String sql = "SELECT * FROM commandes";

        try (Connection connexion = daoFactory.getConnection();
             Statement statement = connexion.createStatement();
             ResultSet resultats = statement.executeQuery(sql)) {

            while (resultats.next()) {
                Commande commande = map(resultats);
                commandes.add(commande);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Erreur lors de la récupération des commandes.");
        }

        return commandes;
    }

    @Override
    public void ajouter(Commande commande) {
        String sql = "INSERT INTO commandes (utilisateurID, commandeDate, commandeStatut, Liste_Id_articles, Liste_Quantite_articles, commandePrix) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection connexion = daoFactory.getConnection();
             PreparedStatement preparedStatement = connexion.prepareStatement(sql)) {

            preparedStatement.setInt(1, commande.getUtilisateurID());
            preparedStatement.setString(2, commande.getDate());
            preparedStatement.setString(3, commande.getStatut());
            preparedStatement.setString(4, commande.getListeID_Article());
            preparedStatement.setString(5, commande.getListeQuantite_Article());
            preparedStatement.setDouble(6, commande.getPrix());

            preparedStatement.executeUpdate();
            System.out.println("Commande ajoutée avec succès.");
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Erreur lors de l'ajout de la commande.");
        }
    }

    @Override
    public Commande chercher(int id) {
        Commande commande = null;
        String sql = "SELECT * FROM commandes WHERE commandeID = ?";

        try (Connection connexion = daoFactory.getConnection();
             PreparedStatement preparedStatement = connexion.prepareStatement(sql)) {

            preparedStatement.setInt(1, id);

            try (ResultSet resultats = preparedStatement.executeQuery()) {
                if (resultats.next()) {
                    commande = map(resultats);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Erreur lors de la recherche de la commande.");
        }

        return commande;
    }

    @Override
    public Commande modifier(Commande commande) {
        String sql = "UPDATE commandes SET commandeStatut = ?, Liste_Id_articles = ?, Liste_Quantite_articles = ?, prix = ? WHERE commandeID = ?";

        try (Connection connexion = daoFactory.getConnection();
             PreparedStatement preparedStatement = connexion.prepareStatement(sql)) {

            preparedStatement.setString(1, commande.getStatut());
            preparedStatement.setString(2, commande.getListeID_Article());
            preparedStatement.setString(3, commande.getListeQuantite_Article());
            preparedStatement.setDouble(4, commande.getPrix());
            preparedStatement.setInt(5, commande.getId());

            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Commande mise à jour avec succès.");
            } else {
                System.out.println("Aucune commande trouvée avec l'ID sp��cifié.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Erreur lors de la mise à jour de la commande.");
        }

        return commande;
    }

    @Override
    public void supprimer(int id) {
        String sql = "DELETE FROM commande_totale WHERE commandeID = ?";

        try (Connection connexion = daoFactory.getConnection();
             PreparedStatement preparedStatement = connexion.prepareStatement(sql)) {

            preparedStatement.setInt(1, id);

            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Commande supprimée avec succès.");
            } else {
                System.out.println("Aucune commande trouvée avec l'ID spécifié.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Erreur lors de la suppression de la commande.");
        }
    }

    public List<Commande> getCommandesParUtilisateur(int utilisateurID) {
        List<Commande> commandes = new ArrayList<>();
        String sql = "SELECT * FROM commande_totale WHERE utilisateurID = ? ORDER BY commandeDate DESC";  // Tri par date décroissante

        try (Connection connexion = daoFactory.getConnection();
             PreparedStatement preparedStatement = connexion.prepareStatement(sql)) {

            preparedStatement.setInt(1, utilisateurID);

            try (ResultSet resultats = preparedStatement.executeQuery()) {
                while (resultats.next()) {
                    Commande commande = map(resultats);
                    commandes.add(commande);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Erreur lors de la récupération des commandes de l'utilisateur.");
        }

        return commandes;
    }



    private Commande map(ResultSet resultats) throws SQLException {
        int id = resultats.getInt("commandeID");
        int utilisateurID = resultats.getInt("utilisateurID");
        String date = resultats.getString("commandeDate");
        String statut = resultats.getString("statut_commande");
        String listeID_Article = resultats.getString("Liste_Id_articles");
        String listeQuantite_Article = resultats.getString("Liste_Quantite_articles");
        double prix = resultats.getDouble("prix");
        String adresseLivraison = resultats.getString("adresseLivraison");

        return new Commande(id, utilisateurID, date, statut, prix, listeID_Article, listeQuantite_Article, adresseLivraison);
    }

}