-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Hôte : 127.0.0.1:3306
-- Généré le : mar. 25 fév. 2025 à 18:51
-- Version du serveur : 9.1.0
-- Version de PHP : 8.3.14

DROP DATABASE IF EXISTS projetShoppingJava;
CREATE DATABASE projetShoppingJava;
USE projetShoppingJava;

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de données : `projetShoppingJava`
--

-- --------------------------------------------------------

--
-- Structure de la table `utilisateurs`
--

DROP TABLE IF EXISTS `utilisateurs`;
CREATE TABLE IF NOT EXISTS `utilisateurs` (
                                              `utilisateurID` int NOT NULL AUTO_INCREMENT,
                                              `utilisateurPrenom` varchar(50) NOT NULL,
                                              `utilisateurNom` varchar(50) NOT NULL,
                                              `utilisateurMail` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL UNIQUE,
                                              `utilisateurMDP` varchar(100) NOT NULL,
                                              `utilisateurAdresse` varchar(50) NOT NULL,
                                              `utilisateurTel` varchar(50) NOT NULL,
                                              `utilisateurIsAdmin` boolean NOT NULL,
                                              PRIMARY KEY (`utilisateurID`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Structure de la table `article`
--

DROP TABLE IF EXISTS `article`;
CREATE TABLE IF NOT EXISTS `article` (
                                         `articleID` int NOT NULL AUTO_INCREMENT,
                                         `articleNom` varchar(50) NOT NULL,
                                         `articleMarque` varchar(50) NOT NULL,
                                         `articlePrix_unitaire` FLOAT NOT NULL,
                                         `articlePrix_vrac` FLOAT NOT NULL,
                                         `articleSeuil_vrac` FLOAT NOT NULL,
                                         `articleStock` int NOT NULL,
                                         `articleIsAvailable` boolean NOT NULL,
                                         PRIMARY KEY (`articleID`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Structure de la table `commande_totale`
--

DROP TABLE IF EXISTS `commande_totale`;
CREATE TABLE IF NOT EXISTS `commande_totale` (
                                                 `commandeID` int NOT NULL AUTO_INCREMENT,
                                                 `utilisateurID` int NOT NULL,
                                                 `commandeDate` DATE NOT NULL,
                                                 `statut_commande` varchar(20) NOT NULL,
                                                 `Liste_Id_articles` VARCHAR(255) NOT NULL,
                                                 `Liste_Quantite_articles` VARCHAR(255) NOT NULL,
                                                 `prix` FLOAT,
                                                 `adresseLivraison` VARCHAR(255) NOT NULL,
                                                 FOREIGN KEY (utilisateurID) REFERENCES utilisateurs(utilisateurID) ON DELETE SET NULL,
                                                 PRIMARY KEY (`commandeID`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Déchargement des données de la table `utilisateurs`
--

INSERT INTO `utilisateurs` (`utilisateurID`, `utilisateurPrenom`, `utilisateurNom`, `utilisateurMail`, `utilisateurMDP`, `utilisateurAdresse`, `utilisateurTel`, `utilisateurIsAdmin`)
VALUES
    (1, 'Jade', 'Parilla', 'jade.parilla', 'Jade1234', '1 rue de la paix, 75000 Paris', '0606060606', 0),
    (2, 'Julienne', 'Palasi', 'julienne.palasi', 'Julienne1234', '2 rue de la paix, 75000 Paris', '0707070707', 0),
    (3, 'Admin', 'Admin', 'admin.admin', 'Admin1234', '3 rue de la paix, 75000 Paris', '0808080808', 1);

--
-- Déchargement des données de la table `commande_totale`
--

INSERT INTO `commande_totale` (`commandeID`, `utilisateurID`, `commandeDate`, `Liste_Id_articles`, `Liste_Quantite_articles`, `statut_commande`, `prix`, `adresseLivraison`)
VALUES
    (1, 4, '2025-02-25', '1,2,3', '2,1,1', 'en cours', 100.0, '1 rue de la paix, 75000 Paris'),
    (2, 4, '2025-02-28', '4,5', '1,2', 'livrée', 200.0, '2 rue de la paix, 75000 Paris'),
    (3, 3, '2025-02-25', '6,7', '3,4', 'annulée', 300.0, '3 rue de la paix, 75000 Paris');

--
-- Déchargement des données de la table `article`
--

INSERT INTO `article` (`articleID`, `articleNom`, `articleMarque`, `articlePrix_unitaire`, `articlePrix_vrac`, `articleSeuil_vrac`, `articleStock`, `articleIsAvailable`)
VALUES
    (1, 'pantalons', 'adidas', 20, 15, 10, 100, true),
    (2, 'chemise', 'adidas', 30, 25, 5, 50, true),
    (3, 'chaussures', 'adidas', 50, 40, 2, 20, true),
    (4, 'sac', 'adidas', 10, 8, 20, 200, true),
    (5, 'veste', 'adidas', 40, 35, 15, 150, true),
    (6, 'ceinture', 'adidas', 15, 12, 25, 250, true),
    (7, 'montre', 'nike', 100, 80, 1, 10, true),
    (8, 'lunettes', 'nike', 60, 50, 3, 30, true),
    (9, 'chapeau', 'nike', 25, 20, 8, 80, true),
    (10, 'gants', 'nike', 12.5, 10, 12, 120, true),
    (11, 'sac à dos', 'nike', 35, 30, 5, 50, true),
    (12, 'écharpe', 'nike', 20, 15, 10, 100, true),
    (13, 'bonnet', 'adidas', 15, 12, 20, 200, true),
    (14, 'moufles', 'nike', 10, 8, 25, 250, true),
    (15, 'parapluie', 'adidas', 5, 4, 30, 300, true),
    (16, 'sous-vêtements', 'adidas', 8, 6, 50, 500, true),
    (17, 'chaussettes', 'nike', 3, 2.5, 100, 1000, true),
    (18, 'cravate', 'adidas', 12.5, 10, 15, 150, true),
    (19, 'ceinture', 'nike', 20, 15, 20, 200, true),
    (20, 'montre connectée', 'adidas', 150, 120, 1, 10, true),
    (21, 'bracelet', 'nike', 25, 20, 5, 50, true),
    (23, 'collier', 'nike', 40, 35, 2, 20, true),
    (24, 'bague', 'adidas', 50, 40, 3, 30, true),
    (25, 'broche', 'nike', 15, 12, 8, 80, true),
    (26, 'CACA', 'adidas', 100, 80, 1, -10, true),
    (27, 'CACA', 'nike', 60, 50, 5, -5, true),
    (28, 'CACA', 'adidas', 20, 15, 10, 0, true),
    (29, 'CACA', 'nike', 200, 150, 1, 10, true);

COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
