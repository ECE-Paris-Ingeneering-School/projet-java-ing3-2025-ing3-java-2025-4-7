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

DROP TABLE IF EXISTS `utilisateur`;
CREATE TABLE IF NOT EXISTS `utilisateur` (
  `utilisateurID` int NOT NULL AUTO_INCREMENT,
  `utilisateurPrenom` varchar(50) NOT NULL,
  `utilisateurNom` varchar(50) NOT NULL,
  `utilisateurMail` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL UNIQUE,
  `utilisateurMDP` varchar(100) NOT NULL,
  PRIMARY KEY (`utilisateurID`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

DROP TABLE IF EXISTS `client`;
CREATE TABLE IF NOT EXISTS `client` (
    `clientID` int NOT NULL AUTO_INCREMENT,
    `clientAdresse` varchar(50) NOT NULL,
    `clientTel` varchar(50) NOT NULL,
    FOREIGN KEY (clientID) REFERENCES utilisateur(utilisateurID) ON DELETE CASCADE,
    PRIMARY KEY (`clientID`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Structure de la table `articles`
--

DROP TABLE IF EXISTS `articles`;
CREATE TABLE IF NOT EXISTS `articles` (
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
-- Structure de la table `admin`
--

DROP TABLE IF EXISTS `admin`;
CREATE TABLE IF NOT EXISTS `admin` (
   `adminID` int NOT NULL AUTO_INCREMENT,
   FOREIGN KEY (adminID) REFERENCES utilisateur(utilisateurID) ON DELETE CASCADE,
   PRIMARY KEY (`adminID`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


--
-- Structure de la table `commande_totale`
--

DROP TABLE IF EXISTS `commande_totale`;
CREATE TABLE IF NOT EXISTS `commande_totale` (
   `commandeID` int NOT NULL AUTO_INCREMENT,
   `clientID` int NOT NULL,
   `commandeDate` DATE NOT NULL,
   `statut_commande`varchar(20) NOT NULL,
   `commande_total` FLOAT,
   FOREIGN KEY (clientID) REFERENCES client(clientID) ON DELETE SET NULL,
   PRIMARY KEY (`commandeID`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
