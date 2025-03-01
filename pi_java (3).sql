-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Hôte : 127.0.0.1
-- Généré le : sam. 01 mars 2025 à 02:05
-- Version du serveur : 10.4.28-MariaDB
-- Version de PHP : 8.2.4

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de données : `pi_java`
--

-- --------------------------------------------------------

--
-- Structure de la table `admin`
--

CREATE TABLE `admin` (
  `id` int(11) NOT NULL,
  `departement` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Déchargement des données de la table `admin`
--

INSERT INTO `admin` (`id`, `departement`) VALUES
(292, 'marketing');

-- --------------------------------------------------------

--
-- Structure de la table `avis`
--

CREATE TABLE `avis` (
  `id` int(11) NOT NULL,
  `name` varchar(255) NOT NULL,
  `comment` varchar(255) NOT NULL,
  `date` date NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Déchargement des données de la table `avis`
--

INSERT INTO `avis` (`id`, `name`, `comment`, `date`) VALUES
(1, 'bessa', 'bessa', '2012-12-12');

-- --------------------------------------------------------

--
-- Structure de la table `blog`
--

CREATE TABLE `blog` (
  `id` int(11) NOT NULL,
  `title` varchar(255) NOT NULL,
  `content` varchar(255) NOT NULL,
  `date` date NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Déchargement des données de la table `blog`
--

INSERT INTO `blog` (`id`, `title`, `content`, `date`) VALUES
(2, '2', 'ahz', '2025-12-12'),
(3, 'bessa', 'bessa', '2012-12-12'),
(4, 'A', 'ZZ', '2003-10-18');

-- --------------------------------------------------------

--
-- Structure de la table `client`
--

CREATE TABLE `client` (
  `id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Structure de la table `conducteur`
--

CREATE TABLE `conducteur` (
  `id` int(11) NOT NULL,
  `numero_permis` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Déchargement des données de la table `conducteur`
--

INSERT INTO `conducteur` (`id`, `numero_permis`) VALUES
(295, 12345678),
(296, 12345678);

-- --------------------------------------------------------

--
-- Structure de la table `evenment`
--

CREATE TABLE `evenment` (
  `id_event` int(11) NOT NULL,
  `nom` varchar(50) NOT NULL,
  `date` date NOT NULL,
  `lieu` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Déchargement des données de la table `evenment`
--

INSERT INTO `evenment` (`id_event`, `nom`, `date`, `lieu`) VALUES
(147, 'Aaa', '2025-03-01', 'aezaa'),
(148, 'aaaaaaa', '2025-03-01', 'DZd'),
(150, 'oussema23', '2025-03-01', 'tunis');

-- --------------------------------------------------------

--
-- Structure de la table `fedback`
--

CREATE TABLE `fedback` (
  `id` int(11) NOT NULL,
  `commentaire` varchar(250) NOT NULL,
  `note` int(11) NOT NULL,
  `id_event` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Structure de la table `organisateur`
--

CREATE TABLE `organisateur` (
  `id` int(11) NOT NULL,
  `num_badge` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Structure de la table `produit`
--

CREATE TABLE `produit` (
  `idproduit` int(11) NOT NULL,
  `nom` varchar(100) NOT NULL,
  `type` varchar(100) NOT NULL,
  `prix` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Déchargement des données de la table `produit`
--

INSERT INTO `produit` (`idproduit`, `nom`, `type`, `prix`) VALUES
(11, 'FREOKGRKOE', 'jhj', 100),
(12, 'gggh', 'bhg', 100),
(15, '1010', 'ttt', 100),
(16, 'dfdf', 'dfdf', 4404),
(22, 'fares', 'fbbc', 150),
(23, 'zerz', 'rez', 2222);

-- --------------------------------------------------------

--
-- Structure de la table `service`
--

CREATE TABLE `service` (
  `id_service` int(11) NOT NULL,
  `nom` varchar(100) DEFAULT NULL,
  `description` text DEFAULT NULL,
  `tarif` double DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci;

--
-- Déchargement des données de la table `service`
--

INSERT INTO `service` (`id_service`, `nom`, `description`, `tarif`) VALUES
(5, 'sqs', 'bbn', 100),
(6, 'ser', 'rrt', 100),
(9, 'OKEJORKZE', 'ghg', 125),
(11, 'ser', 'rrt', 100),
(13, 'ezrze', 'erzerz', 222);

-- --------------------------------------------------------

--
-- Structure de la table `trajet`
--

CREATE TABLE `trajet` (
  `id` int(50) NOT NULL,
  `pointD` varchar(100) NOT NULL,
  `pointA` varchar(100) NOT NULL,
  `dateD` datetime NOT NULL,
  `dateA` datetime NOT NULL,
  `distance` double NOT NULL,
  `prix` double NOT NULL,
  `id_veh` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Déchargement des données de la table `trajet`
--

INSERT INTO `trajet` (`id`, `pointD`, `pointA`, `dateD`, `dateA`, `distance`, `prix`, `id_veh`) VALUES
(121, 'zzz', 'aaa', '2025-01-27 00:00:00', '2025-02-04 00:00:00', 2, 2, 6),
(122, 'bbbbbbbb', 'bbb', '2025-02-03 00:00:00', '2025-02-04 00:00:00', 7, 7, 4),
(123, 'ccc', 'ccc', '2025-01-27 00:00:00', '2025-02-11 00:00:00', 1, 1, 11),
(124, 'ssssssssss', 'kkkkkkkkkkk', '2025-01-28 00:00:00', '2025-03-01 00:00:00', 7, 5, 2),
(126, 'aouina', 'lac 2', '2025-02-11 00:00:00', '2025-02-25 00:00:00', 3, 5, 8),
(127, 'azsza', 'azsaze', '2025-01-28 00:00:00', '2025-03-05 00:00:00', 222, 222, 2),
(128, 'AZIZ', 'AEZA', '2025-01-28 00:00:00', '2025-02-26 00:00:00', 2131, 213123, 2);

-- --------------------------------------------------------

--
-- Structure de la table `utilisateur`
--

CREATE TABLE `utilisateur` (
  `id` int(11) NOT NULL,
  `nom` varchar(200) NOT NULL,
  `prenom` varchar(200) NOT NULL,
  `nom_utilisateur` varchar(200) NOT NULL,
  `email` varchar(200) NOT NULL,
  `mot_de_passe` varchar(200) NOT NULL,
  `role` enum('ADMIN','CONDUCTEUR','ORGANISATEUR','CLIENT') NOT NULL,
  `reset_code` varchar(6) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Déchargement des données de la table `utilisateur`
--

INSERT INTO `utilisateur` (`id`, `nom`, `prenom`, `nom_utilisateur`, `email`, `mot_de_passe`, `role`, `reset_code`) VALUES
(292, 'Ahmed', 'Ahmed', 'Ahmed12', 'aa@gmail.com', '$2a$10$z12xp6MGcZx3rDNIPk6V8.6pWt7Tq/67L3ndmf8s6F5LrwI/w6yI2', 'ADMIN', NULL),
(295, 'Aziz', 'Aziz', 'Aziz123', 'aziz@gmail.com', '$2a$10$Tljv72PBTXWY6kShhE8ut.eicsssXmzCItwNqTyLwGoYfob5apfmi', 'CONDUCTEUR', NULL),
(296, 'Aziza', 'Aziza', 'Aziza123', 'aziza@gmail.com', '$2a$10$Jmlit5IOFe0.Mc4t/QjXpuvQdWMpP.zS1YtpQXW06l9FjI.t.zJ3u', 'CONDUCTEUR', NULL);

-- --------------------------------------------------------

--
-- Structure de la table `vehicule`
--

CREATE TABLE `vehicule` (
  `id` int(11) NOT NULL,
  `type` varchar(50) NOT NULL,
  `capacite` int(11) NOT NULL,
  `statut` varchar(50) NOT NULL,
  `dispo` tinyint(1) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Déchargement des données de la table `vehicule`
--

INSERT INTO `vehicule` (`id`, `type`, `capacite`, `statut`, `dispo`) VALUES
(2, 'metro', 150, 'Available', 1),
(4, 'bus', 100, 'en service', 1),
(5, 'voiture', 5, 'en service', 0),
(6, 'vélo', 1, 'en service', 1),
(8, 'moto', 23, 'hors service', 1),
(16, 'Bus', 20, 'en services', 0),
(121, 'Métro', 20, 'ssssssssssssss', 0),
(122, 'Car', 20, 'maintenance', 0),
(123, 'Trottinette', 1, 'hors service', 0);

--
-- Index pour les tables déchargées
--

--
-- Index pour la table `admin`
--
ALTER TABLE `admin`
  ADD PRIMARY KEY (`id`);

--
-- Index pour la table `avis`
--
ALTER TABLE `avis`
  ADD PRIMARY KEY (`id`);

--
-- Index pour la table `blog`
--
ALTER TABLE `blog`
  ADD PRIMARY KEY (`id`);

--
-- Index pour la table `client`
--
ALTER TABLE `client`
  ADD PRIMARY KEY (`id`);

--
-- Index pour la table `conducteur`
--
ALTER TABLE `conducteur`
  ADD PRIMARY KEY (`id`);

--
-- Index pour la table `evenment`
--
ALTER TABLE `evenment`
  ADD PRIMARY KEY (`id_event`);

--
-- Index pour la table `fedback`
--
ALTER TABLE `fedback`
  ADD PRIMARY KEY (`id`),
  ADD KEY `id_event` (`id_event`);

--
-- Index pour la table `organisateur`
--
ALTER TABLE `organisateur`
  ADD PRIMARY KEY (`id`);

--
-- Index pour la table `produit`
--
ALTER TABLE `produit`
  ADD PRIMARY KEY (`idproduit`);

--
-- Index pour la table `service`
--
ALTER TABLE `service`
  ADD PRIMARY KEY (`id_service`);

--
-- Index pour la table `trajet`
--
ALTER TABLE `trajet`
  ADD PRIMARY KEY (`id`);

--
-- Index pour la table `utilisateur`
--
ALTER TABLE `utilisateur`
  ADD PRIMARY KEY (`id`);

--
-- Index pour la table `vehicule`
--
ALTER TABLE `vehicule`
  ADD PRIMARY KEY (`id`);

--
-- AUTO_INCREMENT pour les tables déchargées
--

--
-- AUTO_INCREMENT pour la table `avis`
--
ALTER TABLE `avis`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT pour la table `blog`
--
ALTER TABLE `blog`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- AUTO_INCREMENT pour la table `evenment`
--
ALTER TABLE `evenment`
  MODIFY `id_event` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=151;

--
-- AUTO_INCREMENT pour la table `fedback`
--
ALTER TABLE `fedback`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=13;

--
-- AUTO_INCREMENT pour la table `produit`
--
ALTER TABLE `produit`
  MODIFY `idproduit` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=24;

--
-- AUTO_INCREMENT pour la table `service`
--
ALTER TABLE `service`
  MODIFY `id_service` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=14;

--
-- AUTO_INCREMENT pour la table `trajet`
--
ALTER TABLE `trajet`
  MODIFY `id` int(50) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=129;

--
-- AUTO_INCREMENT pour la table `utilisateur`
--
ALTER TABLE `utilisateur`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=297;

--
-- AUTO_INCREMENT pour la table `vehicule`
--
ALTER TABLE `vehicule`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=125;

--
-- Contraintes pour les tables déchargées
--

--
-- Contraintes pour la table `admin`
--
ALTER TABLE `admin`
  ADD CONSTRAINT `admin_ibfk_1` FOREIGN KEY (`id`) REFERENCES `utilisateur` (`id`) ON DELETE CASCADE;

--
-- Contraintes pour la table `client`
--
ALTER TABLE `client`
  ADD CONSTRAINT `client_ibfk_1` FOREIGN KEY (`id`) REFERENCES `utilisateur` (`id`) ON DELETE CASCADE;

--
-- Contraintes pour la table `conducteur`
--
ALTER TABLE `conducteur`
  ADD CONSTRAINT `conducteur_ibfk_1` FOREIGN KEY (`id`) REFERENCES `utilisateur` (`id`) ON DELETE CASCADE;

--
-- Contraintes pour la table `fedback`
--
ALTER TABLE `fedback`
  ADD CONSTRAINT `fedback_ibfk_1` FOREIGN KEY (`id_event`) REFERENCES `evenment` (`id_event`) ON DELETE CASCADE;

--
-- Contraintes pour la table `organisateur`
--
ALTER TABLE `organisateur`
  ADD CONSTRAINT `organisateur_ibfk_1` FOREIGN KEY (`id`) REFERENCES `utilisateur` (`id`) ON DELETE CASCADE;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
