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
-- Structure de la table `clients`
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
-- Structure de la table `articles`
--

DROP TABLE IF EXISTS `article`;
CREATE TABLE IF NOT EXISTS `article` (
                                         `articleID` int NOT NULL,
                                         `articleNom` varchar(50) NOT NULL,
                                         `articleMarque` varchar(50) NOT NULL,
                                         `articlePrix_unitaire` FLOAT NOT NULL,
                                         `articlePrix_vrac` FLOAT NOT NULL,
                                         `articleSeuil_vrac` FLOAT NOT NULL,
                                         `articleStock` int NOT NULL,
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
                                                 `statut_commande`varchar(20) NOT NULL,
                                                 `Liste_Id_articles` VARCHAR(255) NOT NULL,
                                                 `Liste_Quantite_articles` VARCHAR(255) NOT NULL,
                                                 `prix` FLOAT,
                                                 FOREIGN KEY (utilisateurID) REFERENCES utilisateurs(utilisateurID) ON DELETE SET NULL,
                                                 PRIMARY KEY (`commandeID`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


--
-- Déchargement des données de la table `Utilisateur`
--


INSERT INTO `utilisateurs` (`utilisateurID`, `utilisateurPrenom`, `utilisateurNom`,`utilisateurMail`,`utilisateurMDP`,`utilisateurAdresse`,`utilisateurTel`,`utilisateurIsAdmin` ) VALUES
                                                                                                                                                                                       (1, 'Jade', 'Parilla', 'jade.parilla', 'Jade1234', '1 rue de la paix, 75000 Paris', '0606060606', 0),
                                                                                                                                                                                       (2, 'Julienne', 'Palasi', 'julienne.palasi', 'Julienne1234', '2 rue de la paix, 75000 Paris', '0707070707', 0),
                                                                                                                                                                                       (3, 'Admin', 'Admin', 'admin.admin', 'Admin1234', '3 rue de la paix, 75000 Paris', '0808080808', 1);


--
-- Déchargement des données de la table `commande_totale`
--

INSERT INTO `commande_totale` (`commandeID`, `utilisateurID`, `commandeDate`,`Liste_Id_articles`,`Liste_Quantite_articles`,`statut_commande`,`prix`) VALUES
                                                                                                                                                    (1, 1, '2023-11-11', '0-1-2','0-4-2','en cours', 0),
                                                                                                                                                    (2, 2, '2023-11-12', '0-1','0-4','en cours', 0),
                                                                                                                                                    (3, 3, '2023-11-13', '0-1-2','0-4-2','en cours', 0),
                                                                                                                                                    (4, 1, '2023-11-14', '0-1','0-4','en cours', 0),
                                                                                                                                                    (5, 2, '2023-11-15', '0-1','0-4','en cours', 0),
                                                                                                                                                    (6, 3, '2023-11-16', '0-1','0-4','en cours', 0);

--
-- Déchargement des données de la table `articles`
--

INSERT INTO `article` (`articleID`, `articleNom`, `articleMarque`,`articlePrix_unitaire`,`articlePrix_vrac`,`articleSeuil_vrac`,`articleStock`) VALUES
                                                                                                                                                    (1, 'pantalons', 'adidas', 20, 15, 10, 100),
                                                                                                                                                    (2, 'chemise', 'adidas', 30, 25, 5, 50),
                                                                                                                                                    (3, 'chaussures', 'adidas', 50, 40, 2, 20);

COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
