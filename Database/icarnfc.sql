-- phpMyAdmin SQL Dump
-- version 4.1.4
-- http://www.phpmyadmin.net
--
-- Client :  127.0.0.1
-- Généré le :  Mer 03 Mai 2017 à 00:16
-- Version du serveur :  5.6.15-log
-- Version de PHP :  5.5.8

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Base de données :  `icarnfc`
--

-- --------------------------------------------------------

--
-- Structure de la table `detail_entretien`
--

CREATE TABLE IF NOT EXISTS `detail_entretien` (
  `id` int(11) NOT NULL,
  `type_entretien` varchar(11) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Structure de la table `entretien`
--

CREATE TABLE IF NOT EXISTS `entretien` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `date_creation` date NOT NULL,
  `id_voiture` int(11) NOT NULL,
  `id_etablissement` int(11) NOT NULL,
  `id_utlisateur` int(11) NOT NULL,
  `IdDetailEntretien` int(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Structure de la table `etablissement`
--

CREATE TABLE IF NOT EXISTS `etablissement` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `Nom` varchar(20) NOT NULL,
  `Siren` varchar(20) NOT NULL,
  `Adresse` varchar(20) NOT NULL,
  `Telephone` varchar(20) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Structure de la table `memo`
--

CREATE TABLE IF NOT EXISTS `memo` (
  `Id` int(11) NOT NULL,
  `IdVoiture` int(11) NOT NULL,
  `DatePrevu` varchar(20) NOT NULL,
  `TypeEntretien` varchar(20) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Structure de la table `proprietaire`
--

CREATE TABLE IF NOT EXISTS `proprietaire` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `Id_voiture` int(11) NOT NULL,
  `Id_User` int(11) NOT NULL,
  `DateAchat` date NOT NULL,
  `DateVente` date NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Structure de la table `users`
--

CREATE TABLE IF NOT EXISTS `users` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `nom` varchar(11) NOT NULL,
  `prenom` varchar(30) NOT NULL,
  `password` varchar(50) NOT NULL,
  `status` varchar(30) NOT NULL,
  `mail` varchar(30) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM  DEFAULT CHARSET=latin1 AUTO_INCREMENT=25 ;

--
-- Contenu de la table `users`
--

INSERT INTO `users` (`id`, `nom`, `prenom`, `password`, `status`, `mail`) VALUES
(20, 'test', 'test', '202cb962ac59075b964b07152d234b70', 'Particulier', 'test'),
(22, 'test', 'test', '202cb962ac5975b964b7152d234b70', 'Particulier', 'mars836@hot.fr'),
(11, 'meyer', 'alex', '202cb962ac59075b964b07152d234b70', 'Particulier', 'mars8.6@hotmail.fr'),
(23, 'mars', 'mars', '202cb962ac59075b964b07152d234b70', 'Particulier', 'mars86@hotmail.fr'),
(24, 'bonjour', 'bonjour', '202cb962ac59075b964b07152d234b70', 'Professionnel', 'bonjour@free.fr');

-- --------------------------------------------------------

--
-- Structure de la table `voiture`
--

CREATE TABLE IF NOT EXISTS `voiture` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `guid` varchar(30) NOT NULL,
  `id_proprietaire` int(11) NOT NULL,
  `Immatriculation` varchar(11) NOT NULL,
  `nom` varchar(30) NOT NULL,
  `marque` varchar(30) NOT NULL,
  `modele` varchar(50) NOT NULL,
  `DateImmat` date NOT NULL,
  `CV` int(11) NOT NULL,
  `urlimage` varchar(300) CHARACTER SET latin1 COLLATE latin1_general_ci DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM  DEFAULT CHARSET=latin1 AUTO_INCREMENT=5 ;

--
-- Contenu de la table `voiture`
--

INSERT INTO `voiture` (`id`, `guid`, `id_proprietaire`, `Immatriculation`, `nom`, `marque`, `modele`, `DateImmat`, `CV`, `urlimage`) VALUES
(1, '1234', 11, '4545BF', 'Lundi', 'Porsche', 'Panamera Turbo S', '2017-04-10', 53, 'https://pbs.twimg.com/profile_images/643385477131825152/ABq1D_PA.jpg'),
(2, '4569', 11, '12ZZ83', 'Mardi', 'Porsche', 'Gt3 991.2', '2017-04-12', 28, 'https://is4-ssl.mzstatic.com/image/thumb/Purple22/v4/18/d1/f2/18d1f206-8c2c-819a-c470-0c7e479bf879/source/256x256bb.jpg'),
(3, 'g', 0, 'hgf', 'hgf', 'hgf', 'hfg', '2017-05-23', 2, 'hfg'),
(4, '1', 11, 'DB-458-83', 'Titine', 'Porsche', 'panamera4s', '2017-04-10', 300, NULL);

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
