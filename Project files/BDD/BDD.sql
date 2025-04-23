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
                                                                                                                                                    (1, 1, '2023-11-11', '0-1-2','0-4-2','en cours', 0, '3 rue du Loup'),
                                                                                                                                                    (2, 2, '2023-11-12', '0-1','0-4','en cours', 0, '35 boulevard Raspail'),
                                                                                                                                                    (3, 3, '2023-11-13', '0-1-2','0-4-2','en cours', 0, '3 avenue du General Leclerc'),
                                                                                                                                                    (4, 1, '2023-11-14', '0-1','0-4','en cours', 0, '3 rue du Loup'),
                                                                                                                                                    (5, 2, '2023-11-15', '0-1','0-4','en cours', 0, '35 boulevard Raspail'),
                                                                                                                                                    (6, 3, '2023-11-16', '0-1','0-4','en cours', 0, '3 avenue du General Leclerc');

--
-- Déchargement des données de la table `articles`
--

INSERT INTO `articles` (`articleID`, `articleNom`, `articleMarque`, `articlePrix_unitaire`, `articlePrix_vrac`, `articleSeuil_vrac`, `articleStock`, `articleIsAvailable`, `articleImageURL`) VALUES
                                                                                                                                                                                                  (1, 'Pikachu-V', 'Voltage Éclatant', 6, 5, 10, 40, true, 'https://images.pokemontcg.io/swsh4/43_hires.png'),
                                                                                                                                                                                                  (2, 'Dracaufeu-VMAX', 'Ténèbres Embrasées', 55, 50, 1, 4, true, 'https://images.pokemontcg.io/swsh3/20_hires.png'),
                                                                                                                                                                                                  (3, 'Pyrobut-V', 'Clash des Rebelles', 5, 4.5, 8, 30, true, 'https://images.pokemontcg.io/swsh2/36_hires.png'),
                                                                                                                                                                                                  (4, 'Éthernatos-V', 'Ténèbres Embrasées', 5, 4, 10, 45, true, 'https://images.pokemontcg.io/swsh3/116_hires.png'),
                                                                                                                                                                                                  (5, 'Zacian-V', 'Épée et Bouclier', 8, 7, 6, 25, true, 'https://images.pokemontcg.io/swsh1/138_hires.png'),
                                                                                                                                                                                                  (6, 'Ronflex-VMAX', 'Épée et Bouclier', 12, 10, 3, 15, true, 'https://images.pokemontcg.io/swsh1/142_hires.png'),
                                                                                                                                                                                                  (7, 'Liv Marnie (Full Art)', 'Épée et Bouclier', 25, 22, 2, 8, true, 'https://images.pokemontcg.io/swsh1/200_hires.png'),
                                                                                                                                                                                                  (8, 'Nigosier-V', 'Destinées Radieuses', 4, 3.5, 10, 40, true, 'https://images.pokemontcg.io/swsh45/44_hires.png'),
                                                                                                                                                                                                  (9, 'Évoli-VMAX', 'Destinées Radieuses', 6, 5, 10, 35, true, 'https://images.pokemontcg.io/swsh45/50_hires.png'),
                                                                                                                                                                                                  (10, 'Dracaufeu-VSTAR', 'Astres Radieux', 28, 25, 2, 10, true, 'https://images.pokemontcg.io/swsh9/18_hires.png'),
                                                                                                                                                                                                  (11, 'Arceus-V', 'Astres Radieux', 7, 6, 10, 30, true, 'https://images.pokemontcg.io/swsh9/123_hires.png'),
                                                                                                                                                                                                  (12, 'Luminéon-V', 'Astres Radieux', 4, 3.5, 12, 45, true, 'https://images.pokemontcg.io/swsh9/40_hires.png'),
                                                                                                                                                                                                  (13, 'Dardargnan-V', 'Origine Perdue', 5, 4.2, 10, 40, true, 'https://images.pokemontcg.io/swsh10/3_hires.png'),
                                                                                                                                                                                                  (14, 'Palkia Originel-VSTAR', 'Origine Perdue', 15, 13, 4, 18, true, 'https://images.pokemontcg.io/swsh10/40_hires.png'),
                                                                                                                                                                                                  (15, 'Carchacrok-V', 'Origine Perdue', 6, 5, 10, 30, true, 'https://images.pokemontcg.io/swsh10/109_hires.png'),
                                                                                                                                                                                                  (16, 'Darkrai-VSTAR', 'Origine Perdue', 10, 9, 6, 20, true, 'https://images.pokemontcg.io/swsh10/99_hires.png'),
                                                                                                                                                                                                  (17, 'Amphinobi Radieux', 'Origine Perdue', 8, 7, 5, 25, true, 'https://images.pokemontcg.io/swsh10/46_hires.png'),
                                                                                                                                                                                                  (18, 'Pikachu-VMAX (Galerie)', 'Origine Perdue', 35, 30, 2, 6, true, 'https://images.pokemontcg.io/swsh10/TG29_hires.png'),
                                                                                                                                                                                                  (19, 'Giratina-VSTAR', 'Origine Perdue', 28, 25, 3, 10, true, 'https://images.pokemontcg.io/swsh10/131_hires.png'),
                                                                                                                                                                                                  (20, 'Ptéra-V', 'Origine Perdue', 7, 6, 10, 25, true, 'https://images.pokemontcg.io/swsh10/111_hires.png'),
                                                                                                                                                                                                  (21, 'Motisma-V', 'Origine Perdue', 4, 3.5, 10, 35, true, 'https://images.pokemontcg.io/swsh10/65_hires.png'),
                                                                                                                                                                                                  (22, 'Dracaufeu (Galerie)', 'Origine Perdue', 20, 18, 5, 15, true, 'https://images.pokemontcg.io/swsh10/TG03_hires.png'),
                                                                                                                                                                                                  (23, 'Lugia-VSTAR', 'Tempête Argentée', 30, 27, 2, 10, true, 'https://images.pokemontcg.io/swsh12/139_hires.png'),
                                                                                                                                                                                                  (24, 'Regidrago-V', 'Tempête Argentée', 5, 4.2, 10, 30, true, 'https://images.pokemontcg.io/swsh12/136_hires.png'),
                                                                                                                                                                                                  (25, 'Goupix d’Alola-VSTAR', 'Tempête Argentée', 9, 8, 6, 22, true, 'https://images.pokemontcg.io/swsh12/34_hires.png'),
                                                                                                                                                                                                  (26, 'Zarbi-V', 'Tempête Argentée', 4, 3.5, 12, 40, true, 'https://images.pokemontcg.io/swsh12/76_hires.png'),
                                                                                                                                                                                                  (27, 'Serena (Full Art)', 'Tempête Argentée', 40, 36, 2, 8, true, 'https://images.pokemontcg.io/swsh12/193_hires.png'),
                                                                                                                                                                                                  (28, 'Miraidon-ex', 'Écarlate & Violet', 15, 13, 5, 18, true, 'https://images.pokemontcg.io/sv1/81_hires.png'),
                                                                                                                                                                                                  (29, 'Gardevoir-ex', 'Écarlate & Violet', 12, 10, 6, 20, true, 'https://images.pokemontcg.io/sv1/85_hires.png'),
                                                                                                                                                                                                  (30, 'Menzi (Full Art)', 'Écarlate & Violet', 18, 16, 4, 14, true, 'https://images.pokemontcg.io/sv1/195_hires.png'),
                                                                                                                                                                                                  (31, 'Mew-ex', 'Pokémon 151', 22, 20, 3, 12, true, 'https://images.pokemontcg.io/sv151/151_hires.png'),
                                                                                                                                                                                                  (32, 'Dracaufeu-ex', 'Pokémon 151', 35, 32, 2, 8, true, 'https://images.pokemontcg.io/sv151/150_hires.png'),
                                                                                                                                                                                                  (33, 'Électhor-ex', 'Pokémon 151', 14, 12, 5, 16, true, 'https://images.pokemontcg.io/sv151/145_hires.png'),
                                                                                                                                                                                                  (34, 'Attrape-Pokémon - Objet', 'Écarlate & Violet', 2, 1.5, 20, 100, true, 'https://images.pokemontcg.io/sv1/198_hires.png'),
                                                                                                                                                                                                  (35, 'Hyper Ball - Objet', 'Épée et Bouclier', 3, 2.5, 10, 80, true, 'https://images.pokemontcg.io/swsh1/179_hires.png'),
                                                                                                                                                                                                  (36, 'Ordres du Boss - Supporter', 'Clash des Rebelles', 5, 4, 12, 40, true, 'https://images.pokemontcg.io/swsh4/151_hires.png'),
                                                                                                                                                                                                  (37, 'Juge - Supporter', 'Coup Fusion', 3, 2.5, 10, 60, true, 'https://images.pokemontcg.io/swsh6/163_hires.png'),
                                                                                                                                                                                                  (38, 'Carnet de Soin - Objet', 'Astres Radieux', 2, 1.5, 15, 90, true, 'https://images.pokemontcg.io/swsh9/107_hires.png'),
                                                                                                                                                                                                  (39, 'Rare Candy - Bonbon Rare', 'Écarlate & Violet', 4, 3.5, 10, 70, true, 'https://images.pokemontcg.io/sv1/183_hires.png'),
                                                                                                                                                                                                  (40, 'Chaussures de Randonnée - Objet', 'Astres Radieux', 2.5, 2, 15, 90, true, 'https://images.pokemontcg.io/swsh9/106_hires.png'),
                                                                                                                                                                                                  (41, 'Pass VIP Combat - Objet', 'Coup Fusion', 6, 5, 10, 30, true, 'https://images.pokemontcg.io/swsh6/163_hires.png'),
                                                                                                                                                                                                  (42, 'Chariot de Secours - Objet', 'Astres Radieux', 2, 1.5, 20, 100, true, 'https://images.pokemontcg.io/swsh9/110_hires.png'),
                                                                                                                                                                                                  (43, 'Manaphy - Holographique', 'Astres Radieux', 3, 2.5, 15, 50, true, 'https://images.pokemontcg.io/swsh9/17_hires.png'),
                                                                                                                                                                                                  (44, 'Dracaufeu Radieux', 'Pokémon GO', 28, 25, 2, 10, true, 'https://images.pokemontcg.io/pgo/19_hires.png'),
                                                                                                                                                                                                  (45, 'Melmetal-VMAX', 'Pokémon GO', 10, 9, 6, 20, true, 'https://images.pokemontcg.io/pgo/55_hires.png'),
                                                                                                                                                                                                  (46, 'Dracolosse-VSTAR', 'Pokémon GO', 18, 16, 4, 15, true, 'https://images.pokemontcg.io/pgo/40_hires.png'),
                                                                                                                                                                                                  (47, 'Poké Ball - Objet', 'Épée et Bouclier', 2, 1.5, 15, 100, true, 'https://images.pokemontcg.io/swsh1/135_hires.png'),
                                                                                                                                                                                                  (48, 'PokéNav - Objet', 'Épée et Bouclier', 3, 2.5, 12, 70, true, 'https://images.pokemontcg.io/swsh1/191_hires.png'),
                                                                                                                                                                                                  (49, 'Téléporteur d’Urgence - Objet', 'Écarlate & Violet', 4, 3.2, 10, 60, true, 'https://images.pokemontcg.io/sv1/162_hires.png'),
                                                                                                                                                                                                  (50, 'Cramorant Radieux', 'Destinées Radieuses', 6, 5, 10, 25, true, 'https://images.pokemontcg.io/swsh45/32_hires.png');
;

COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
