package DAO;

import modele.Promo;
import java.util.ArrayList;
import java.sql.*;

public class PromoDAOImpl implements PromoDAO {
    private final DaoFactory daoFactory;

    public PromoDAOImpl(DaoFactory daoFactory) {
        this.daoFactory = daoFactory;
    }

    @Override
    public ArrayList<Promo> getAll() {
        ArrayList<Promo> promos = new ArrayList<>();
        String query = "SELECT * FROM promo";

        try (Connection connection = daoFactory.getConnection();
             PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                Promo promo = new Promo();
                promo.setPromoID(resultSet.getInt("id"));
                promo.setCode(resultSet.getString("code"));
                promo.setReduction(resultSet.getFloat("reduction"));
                promo.setActive(resultSet.getBoolean("active"));
                promos.add(promo);
            }
        } catch (SQLException e) {
            System.err.println("Error fetching all promos: " + e.getMessage());
        }
        return promos;
    }

    @Override
    public Promo ajouter(Promo promo) {
        String query = "INSERT INTO promo (code, reduction, active) VALUES (?, ?, ?)";

        try (Connection connection = daoFactory.getConnection();
             PreparedStatement statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {

            statement.setString(1, promo.getCode());
            statement.setFloat(2, promo.getReduction());
            statement.setBoolean(3, promo.isActive());
            statement.executeUpdate();

            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    promo.setPromoID(generatedKeys.getInt(1));
                }
            }
        } catch (SQLException e) {
            System.err.println("Error adding promo: " + e.getMessage());
        }
        return promo;
    }

    @Override
    public Promo chercher(int id) {
        Promo promo = null;
        String query = "SELECT * FROM promo WHERE id = ?";

        try (Connection connection = daoFactory.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setInt(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    promo = new Promo();
                    promo.setPromoID(resultSet.getInt("id"));
                    promo.setCode(resultSet.getString("code"));
                    promo.setReduction(resultSet.getFloat("reduction"));
                    promo.setActive(resultSet.getBoolean("active"));
                }
            }
        } catch (SQLException e) {
            System.err.println("Error fetching promo by ID: " + e.getMessage());
        }
        return promo;
    }

    @Override
    public Promo modifier(Promo promo) {
        String query = "UPDATE promo SET code = ?, reduction = ?, active = ? WHERE id = ?";

        try (Connection connection = daoFactory.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setString(1, promo.getCode());
            statement.setFloat(2, promo.getReduction());
            statement.setBoolean(3, promo.isActive());
            statement.setInt(4, promo.getPromoID());
            statement.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error updating promo: " + e.getMessage());
        }
        return promo;
    }

    @Override
    public void supprimer(Promo promo) {
        String query = "DELETE FROM promo WHERE id = ?";

        try (Connection connection = daoFactory.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setInt(1, promo.getPromoID());
            statement.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error deleting promo: " + e.getMessage());
        }
    }

    @Override
    public Promo chercherParCode(String code) {
        Promo promo = null;
        String query = "SELECT * FROM promo WHERE code = ?";

        try (Connection connection = daoFactory.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setString(1, code);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    promo = new Promo();
                    promo.setPromoID(resultSet.getInt("promoID"));
                    promo.setCode(resultSet.getString("code"));
                    promo.setReduction(resultSet.getFloat("reduction"));
                    promo.setActive(resultSet.getBoolean("actif"));
                }
            }
        } catch (SQLException e) {
            System.err.println("Error fetching promo by code: " + e.getMessage());
        }
        return promo;
    }
}
