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

DROP TABLE IF EXISTS `articles`;
CREATE TABLE IF NOT EXISTS `articles` (
                                         `articleID` int NOT NULL AUTO_INCREMENT,
                                         `articleNom` varchar(50) NOT NULL,
                                         `articleMarque` varchar(50) NOT NULL,
                                         `articlePrix_unitaire` FLOAT NOT NULL,
                                         `articlePrix_vrac` FLOAT NOT NULL,
                                         `articleSeuil_vrac` FLOAT NOT NULL,
                                         `articleStock` int NOT NULL,
                                         `articleIsAvailable` boolean NOT NULL,
                                         `articleImageURL` varchar(255) NOT NULL,
                                         PRIMARY KEY (`articleID`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


--
-- Structure de la table `commande_totale`
--

DROP TABLE IF EXISTS `commandes`;
CREATE TABLE IF NOT EXISTS `commandes` (
                                                 `commandeID` int NOT NULL AUTO_INCREMENT,
                                                 `utilisateurID` int NOT NULL,
                                                 `commandeDate` DATE NOT NULL,
                                                 `commandeStatut`varchar(20) NOT NULL,
                                                 `commandeAdresse` varchar(50) NOT NULL,
                                                 `Liste_Id_articles` VARCHAR(255) NOT NULL,
                                                 `Liste_Quantite_articles` VARCHAR(255) NOT NULL,
                                                 `commandePrix` FLOAT NOT NULL,
                                                 FOREIGN KEY (utilisateurID) REFERENCES utilisateurs(utilisateurID) ON DELETE SET NULL,
                                                 PRIMARY KEY (`commandeID`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Structure de la table `promo`

DROP TABLE IF EXISTS `promo`;
CREATE TABLE IF NOT EXISTS `promo` (
                                         `promoID` int NOT NULL AUTO_INCREMENT,
                                         `code` varchar(50) NOT NULL,
                                         `reduction` FLOAT NOT NULL,
                                         `actif` BOOLEAN NOT NULL,
                                         PRIMARY KEY (`promoID`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
--


--
-- Déchargement des données de la table `promo`
--
INSERT INTO `promo` (`promoID`, `code`, `reduction`, `actif`) VALUES
                                                                                                                (1, 'WELCOME10', 0.10, true),
                                                                                                                (2, 'neuil', 0.5, true);

--
-- Déchargement des données de la table `Utilisateur`
--


INSERT INTO `utilisateurs` (`utilisateurID`, `utilisateurPrenom`, `utilisateurNom`,`utilisateurMail`,`utilisateurMDP`,`utilisateurAdresse`,`utilisateurTel`,`utilisateurIsAdmin` ) VALUES
                                                                                                                                                                                       (1, 'Jade', 'Parrilla', 'jade.parrilla', 'Jade1234', '1 rue de la paix, 75000 Paris', '0606060606', 0),
                                                                                                                                                                                       (2, 'Theo', 'Melly', 'theo.melly', 'Theo1234', '2 rue de la paix, 75000 Paris', '0707070707', 0),
                                                                                                                                                                                       (3, 'Nicolas', 'Duzan', 'nicolas.duzan', 'Nicolas1234', '3 rue de la paix, 75000 Paris', '0808080808', 1),
                                                                                                                                                                                       (4, 'Matheo', 'Leon', 'matheo.leon', 'Matheo1234', '4 rue de la paix, 75000 Paris', '0909090909', 1);


--
-- Déchargement des données de la table `commande_totale`
--

INSERT INTO `commandes` (`commandeID`, `utilisateurID`, `commandeDate`,`Liste_Id_articles`,`Liste_Quantite_articles`,`commandeStatut`,`commandePrix`, commandeAdresse) VALUES
                                                                                                                                                    (1, 1, '2023-11-11', '0-1-2','0-4-2','commande passée', 0, '3 rue du Loup'),
                                                                                                                                                    (2, 2, '2023-11-12', '0-1','0-4','commande passée', 0, '35 boulevard Raspail'),
                                                                                                                                                    (3, 3, '2023-11-13', '0-1-2','0-4-2','commande passée', 0, '3 avenue du General Leclerc'),
                                                                                                                                                    (4, 1, '2023-11-14', '0-1','0-4','commande passée', 0, '3 rue du Loup'),
                                                                                                                                                    (5, 2, '2023-11-15', '0-1','0-4','commande passée', 0, '35 boulevard Raspail'),
                                                                                                                                                    (6, 3, '2023-11-16', '0-1','0-4','commande passée', 0, '3 avenue du General Leclerc');

--
-- Déchargement des données de la table `articles`
--

INSERT INTO `articles` (`articleID`, `articleNom`, `articleMarque`, `articlePrix_unitaire`, `articlePrix_vrac`, `articleSeuil_vrac`, `articleStock`, `articleIsAvailable`, `articleImageURL`) VALUES
                                                                                                                                                                                                  (1, 'Léviator VMAX', 'Évolution Céleste', 5, 4, 5, 12, true, 'https://pokecardex.b-cdn.net/assets/images/sets/EVS/29.jpg?'),
                                                                                                                                                                                                  (2, 'Nymphali VMAX', 'Évolution Céleste', 5, 4, 5, 8, true, 'https://pokecardex.b-cdn.net/assets/images/sets/EVS/75.jpg?'),
                                                                                                                                                                                                  (3, 'Dracolosse V', 'Évolution Céleste', 10, 9, 5, 15, true, 'https://pokecardex.b-cdn.net/assets/images/sets/EVS/191.jpg?'),
                                                                                                                                                                                                  (4, 'Noctali V', 'Évolution Céleste', 80, 75, 3, 5, true, 'https://pokecardex.b-cdn.net/assets/images/sets/EVS/189.jpg?'),
                                                                                                                                                                                                  (5, 'Résolution d Amaryllis', 'Évolution Céleste', 45, 40, 3, 10, true, 'https://pokecardex.b-cdn.net/assets/images/sets/EVS/203.jpg?'),

                                                                                                                                                                                                  (6, 'Zacian VSTAR', 'Zénith Suprême', 6, 5, 5, 6, true, 'https://pokecardex.b-cdn.net/assets/images/sets/CRZ/96.jpg?'),
                                                                                                                                                                                                  (7, 'Evoli V', 'Zénith Suprême', 6, 5, 5, 12, true, 'https://pokecardex.b-cdn.net/assets/images/sets/CRZ/108.jpg?'),
                                                                                                                                                                                                  (8, 'Keldeo', 'Zénith Suprême', 20, 17, 3, 8, true, 'https://pokecardex.b-cdn.net/assets/images/sets/CRZ/167.jpg?'),
                                                                                                                                                                                                  (9, 'Latias', 'Zénith Suprême', 30, 25, 3, 7, true, 'https://pokecardex.b-cdn.net/assets/images/sets/CRZ/180.jpg?'),
                                                                                                                                                                                                  (10, 'Deoxys VSTAR', 'Zénith Suprême', 60, 56, 2, 20, true, 'https://pokecardex.b-cdn.net/assets/images/sets/CRZ/206.jpg?'),

                                                                                                                                                                                                  (11, 'Pyrobut V', 'Poing de Fusion', 4, 3, 5, 4, true, 'https://pokecardex.b-cdn.net/assets/images/sets/FST/44.jpg?'),
                                                                                                                                                                                                  (12, 'Lucario V', 'Poing de Fusion', 5, 4, 5, 8, true, 'https://pokecardex.b-cdn.net/assets/images/sets/FST/146.jpg?'),
                                                                                                                                                                                                  (13, 'Genesect V', 'Poing de Fusion', 5, 4, 5, 12, true, 'https://pokecardex.b-cdn.net/assets/images/sets/FST/185.jpg?'),
                                                                                                                                                                                                  (14, 'Célébi V', 'Poing de Fusion', 70, 68, 2, 10, true, 'https://pokecardex.b-cdn.net/assets/images/sets/FST/245.jpg?'),
                                                                                                                                                                                                  (15, 'Eclat d Inezia', 'Poing de Fusion', 40, 36, 3, 4, true, 'https://pokecardex.b-cdn.net/assets/images/sets/FST/260.jpg?'),

                                                                                                                                                                                                  (16, 'Pikachu EX', 'Évolution Prismatique', 3, 2, 5, 7, true, 'https://pokecardex.b-cdn.net/assets/images/sets/PRE/28.jpg?'),
                                                                                                                                                                                                  (17, 'Lugia EX', 'Évolution Prismatique', 4, 3, 5, 15, true, 'https://pokecardex.b-cdn.net/assets/images/sets/PRE/82.jpg?'),
                                                                                                                                                                                                  (18, 'Ire-Foudre EX', 'Évolution Prismatique', 60, 55, 3, 14, true, 'https://pokecardex.b-cdn.net/assets/images/sets/PRE/166.jpg?'),
                                                                                                                                                                                                  (19, 'Noctali EX', 'Évolution Prismatique', 260, 240, 2, 9, true, 'https://pokecardex.b-cdn.net/assets/images/sets/PRE/161.jpg?'),
                                                                                                                                                                                                  (20, 'Gromago EX', 'Évolution Prismatique', 40, 36, 3, 18, true, 'https://pokecardex.b-cdn.net/assets/images/sets/PRE/164.jpg?'),

                                                                                                                                                                                                  (21, 'Entei', 'Destinées de Paldea', 10, 9, 5, 17, true, 'https://pokecardex.b-cdn.net/assets/images/sets/PAF/112.jpg?'),
                                                                                                                                                                                                  (22, 'Mew EX', 'Destinées de Paldea', 15, 13, 5, 16, true, 'https://pokecardex.b-cdn.net/assets/images/sets/PAF/216.jpg?'),
                                                                                                                                                                                                  (23, 'Menzi', 'Destinées de Paldea', 20, 18, 3, 20, true, 'https://pokecardex.b-cdn.net/assets/images/sets/PAF/229.jpg?'),
                                                                                                                                                                                                  (24, 'Mew EX', 'Destinées de Paldea', 140, 130, 2, 10, true, 'https://pokecardex.b-cdn.net/assets/images/sets/PAF/232.jpg?'),
                                                                                                                                                                                                  (25, 'Gardevoir EX', 'Destinées de Paldea', 120, 115, 4, 14, true, 'https://pokecardex.b-cdn.net/assets/images/sets/PAF/233.jpg?'),

                                                                                                                                                                                                  (26, 'Majaspic V', 'Tempête Argentée', 6, 5, 5, 8, true, 'https://pokecardex.b-cdn.net/assets/images/sets/SIT/7.jpg?'),
                                                                                                                                                                                                  (27, 'Ho-Oh V', 'Tempête Argentée', 4, 3, 6, 12, true, 'https://pokecardex.b-cdn.net/assets/images/sets/SIT/140.jpg?'),
                                                                                                                                                                                                  (28, 'Regidraco V', 'Tempête Argentée', 40, 37, 3, 10, true, 'https://pokecardex.b-cdn.net/assets/images/sets/SIT/184.jpg?'),
                                                                                                                                                                                                  (29, 'Altaria', 'Tempête Argentée', 20, 18, 5, 7, true, 'https://pokecardex.b-cdn.net/assets/images/sets/SIT/226.jpg?'),
                                                                                                                                                                                                  (30, 'Rayquaza VMAX', 'Tempête Argentée', 30, 28, 3, 13, true, 'https://pokecardex.b-cdn.net/assets/images/sets/SIT/235.jpg?');

COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
