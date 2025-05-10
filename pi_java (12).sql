-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Hôte : 127.0.0.1
-- Généré le : sam. 10 mai 2025 à 19:36
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
(305, 'Marketing'),
(316, 'HR'),
(322, 'RH');

-- --------------------------------------------------------

--
-- Structure de la table `avis`
--

CREATE TABLE `avis` (
  `avis_id` int(11) NOT NULL,
  `blog_id` int(11) NOT NULL,
  `name` varchar(255) NOT NULL,
  `comment` varchar(255) NOT NULL,
  `date` date NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Déchargement des données de la table `avis`
--

INSERT INTO `avis` (`avis_id`, `blog_id`, `name`, `comment`, `date`) VALUES
(2, 3, 'lahzimmmmmmmm', 'shdshjd shdj', '2025-04-25'),
(3, 3, 'dfffffffffffff', 'sdfhjdfhdjfhsjdfhsjdfhsdjf', '2025-04-25'),
(4, 8, 'hazem', 'test test', '2025-04-29'),
(5, 8, 'hazem', 'great', '2025-04-29'),
(6, 8, 'hazem', 'usless blog', '2025-04-29'),
(7, 6, 'hazem', 'hazemmmm', '2025-04-29'),
(8, 6, 'hazem', 'u so nice', '2025-04-29'),
(9, 8, 'hazem', 'this is a great blog', '2025-04-29'),
(11, 8, 'charef', 'great post', '2025-04-29');

-- --------------------------------------------------------

--
-- Structure de la table `blog`
--

CREATE TABLE `blog` (
  `blog_id` int(11) NOT NULL,
  `title` varchar(255) NOT NULL,
  `content` varchar(255) NOT NULL,
  `date` date NOT NULL,
  `image` varchar(255) DEFAULT NULL,
  `is_featured` tinyint(1) NOT NULL,
  `updated_at` datetime DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Déchargement des données de la table `blog`
--

INSERT INTO `blog` (`blog_id`, `title`, `content`, `date`, `image`, `is_featured`, `updated_at`) VALUES
(3, 'The Joy of Gardeningggg', 'Gardening is a relaxing hobby that connects you with nature. Planting flowers or vegetables can bring joy and a sense of accomplishment. Plus, homegrown tomatoes taste amazing!', '2025-04-24', '680ab11bd18c8_Security-Shield-PNG-Free-Download.png', 0, '2025-04-24 23:46:03'),
(6, 'Why Drink More Water?', 'Staying hydrated improves focus, energy, and skin health. Aim for 8 glasses a day—your body will thank you!', '2020-01-01', '680bfb4063296_61457.png', 0, '2025-04-25 23:14:40'),
(7, 'Best Travel Destinations for 2024', 'From Bali to Iceland, this year’s top spots offer adventure and relaxation. Where will you go next?', '2020-01-01', '680bfb463ce85_61457.png', 0, '2025-04-25 23:14:46'),
(8, 'Easy Pancake Recipe', 'Mix flour, eggs, milk, and a pinch of sugar. Cook on a hot pan, flip, and enjoy with syrup or fruit. Breakfast is served!', '2020-01-01', '680bfbbb3f85b_Security-Shield-PNG-Free-Download.png', 1, '2025-04-25 23:16:43'),
(9, 'How to Stay Productive', 'Focus on one task at a time, take breaks, and avoid distractions. Small steps lead to big progress.', '2020-01-01', '680c01209a46e_Capture d’écran 2023-08-18 114412.png', 0, '2025-04-25 23:39:44'),
(10, 'The Benefits of Reading', 'Books expand knowledge, reduce stress, and boost creativity. Even 10 pages a day makes a difference!', '2020-01-01', '680fbed5805ea_Sans titre.png', 1, '2025-04-28 19:45:57'),
(11, 'My Favorite Workout Routine', 'A 20-minute mix of squats, push-ups, and jumping jacks keeps me energized. No gym required!', '2020-01-01', '680fbeef1e9be_Capture d’écran 2024-11-30 161244.png', 0, '2025-04-28 19:46:23'),
(12, 'Why Pets Improve Your Life', 'Dogs, cats, or even fish bring companionship and reduce stress. Pets make every day brighter.', '2020-01-01', '680fbf02c514d_Capture d’écran 2023-04-19 031051.png', 0, '2025-04-28 19:46:42'),
(13, 'Learning a New Language', 'Start with common phrases, use apps, and practice daily. Bonjour! Hola! 你好! The world opens up with new languages.', '2020-01-01', '680fbf384254b_wallpaper-1920x1080-5.jpg', 0, '2025-04-28 19:47:36'),
(15, 'testttt', 'test test test test test test test test test test', '2025-04-29', '6810df864d2bf_logo.png', 0, '2025-04-29 16:17:42'),
(16, 'hazemm', 'hazemmm ahazeeù hazemm hazemmm', '2020-01-01', NULL, 0, NULL);

-- --------------------------------------------------------

--
-- Structure de la table `client`
--

CREATE TABLE `client` (
  `id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Déchargement des données de la table `client`
--

INSERT INTO `client` (`id`) VALUES
(329),
(330),
(332),
(333);

-- --------------------------------------------------------

--
-- Structure de la table `conducteur`
--

CREATE TABLE `conducteur` (
  `id` int(11) NOT NULL,
  `numero_permis` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Déchargement des données de la table `conducteur`
--

INSERT INTO `conducteur` (`id`, `numero_permis`) VALUES
(308, 10101010),
(317, 12121212),
(331, 12345678);

-- --------------------------------------------------------

--
-- Structure de la table `doctrine_migration_versions`
--

CREATE TABLE `doctrine_migration_versions` (
  `version` varchar(191) NOT NULL,
  `executed_at` datetime DEFAULT NULL,
  `execution_time` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Déchargement des données de la table `doctrine_migration_versions`
--

INSERT INTO `doctrine_migration_versions` (`version`, `executed_at`, `execution_time`) VALUES
('DoctrineMigrations\\Version20250409191915', '2025-04-09 21:19:33', 75);

-- --------------------------------------------------------

--
-- Structure de la table `evenment`
--

CREATE TABLE `evenment` (
  `id_event` int(11) NOT NULL,
  `nom` varchar(50) NOT NULL,
  `date` date NOT NULL,
  `lieu` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Déchargement des données de la table `evenment`
--

INSERT INTO `evenment` (`id_event`, `nom`, `date`, `lieu`) VALUES
(1, 'Aaaa', '2025-04-30', 'tunis'),
(2, 'bbb', '2025-05-01', 'tunis'),
(3, 'AHMED', '2025-04-15', 'bizert'),
(4, 'hedi', '2025-06-02', 'gabes'),
(5, 'oussema', '2025-06-02', 'tounes'),
(6, 'ouss', '2025-05-02', 'wedhref'),
(7, 'talel ben aziza', '2025-08-08', 'nabeul'),
(8, 'oussema', '2025-05-01', 'tunis');

-- --------------------------------------------------------

--
-- Structure de la table `fedback`
--

CREATE TABLE `fedback` (
  `id` int(11) NOT NULL,
  `id_event` int(11) DEFAULT NULL,
  `commentaire` varchar(250) NOT NULL,
  `note` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Déchargement des données de la table `fedback`
--

INSERT INTO `fedback` (`id`, `id_event`, `commentaire`, `note`) VALUES
(1, 2, 'rzrez', 2),
(2, 4, 'rzrez', 3);

-- --------------------------------------------------------

--
-- Structure de la table `messenger_messages`
--

CREATE TABLE `messenger_messages` (
  `id` bigint(20) NOT NULL,
  `body` longtext NOT NULL,
  `headers` longtext NOT NULL,
  `queue_name` varchar(190) NOT NULL,
  `created_at` datetime NOT NULL COMMENT '(DC2Type:datetime_immutable)',
  `available_at` datetime NOT NULL COMMENT '(DC2Type:datetime_immutable)',
  `delivered_at` datetime DEFAULT NULL COMMENT '(DC2Type:datetime_immutable)'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- --------------------------------------------------------

--
-- Structure de la table `organisateur`
--

CREATE TABLE `organisateur` (
  `id` int(11) NOT NULL,
  `num_badge` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Déchargement des données de la table `organisateur`
--

INSERT INTO `organisateur` (`id`, `num_badge`) VALUES
(328, 12345678),
(342, 1234);

-- --------------------------------------------------------

--
-- Structure de la table `produit`
--

CREATE TABLE `produit` (
  `idproduit` int(11) NOT NULL,
  `nom` varchar(100) NOT NULL,
  `type` varchar(100) NOT NULL,
  `prix` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Déchargement des données de la table `produit`
--

INSERT INTO `produit` (`idproduit`, `nom`, `type`, `prix`) VALUES
(9, 'fffffff', 'fffffffff', 154555),
(10, 'dsfghjkl', 'etudiant', 754),
(11, 'dfghjkl', 'ghjk', 32365),
(12, 'sdfghjkl', 'dfghjkl', 777),
(13, 'sdfghjklmù', 'fghjklm', 209);

-- --------------------------------------------------------

--
-- Structure de la table `produit_service`
--

CREATE TABLE `produit_service` (
  `produit_id` int(11) NOT NULL,
  `service_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Déchargement des données de la table `produit_service`
--

INSERT INTO `produit_service` (`produit_id`, `service_id`) VALUES
(0, 0),
(9, 4),
(10, 1),
(11, 4),
(12, 4),
(13, 4);

-- --------------------------------------------------------

--
-- Structure de la table `service`
--

CREATE TABLE `service` (
  `id_service` int(11) NOT NULL,
  `nom` varchar(100) NOT NULL,
  `description` longtext NOT NULL,
  `tarif` double NOT NULL,
  `categorie_id` int(11) DEFAULT NULL COMMENT 'Référence vers categorie'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Déchargement des données de la table `service`
--

INSERT INTO `service` (`id_service`, `nom`, `description`, `tarif`, `categorie_id`) VALUES
(1, 'dzada', 'dzafafafafafaf', 32, 1),
(4, 'sdfghbnj,', 'wxcvbnazer', 896, 4);

-- --------------------------------------------------------

--
-- Structure de la table `trajet`
--

CREATE TABLE `trajet` (
  `id` int(11) NOT NULL,
  `pointD` varchar(100) NOT NULL,
  `pointA` varchar(100) NOT NULL,
  `dateD` datetime NOT NULL,
  `dateA` datetime NOT NULL,
  `distance` double NOT NULL,
  `prix` double NOT NULL,
  `id_veh` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Déchargement des données de la table `trajet`
--

INSERT INTO `trajet` (`id`, `pointD`, `pointA`, `dateD`, `dateA`, `distance`, `prix`, `id_veh`) VALUES
(1, 'iIII', 'rrrr', '2025-04-30 22:23:00', '2025-05-01 22:23:00', 13, 111, 1),
(2, 'ariana', 'ariana', '2025-04-29 14:51:00', '2025-04-30 14:51:00', 10, 20, 3);

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
  `reset_code` varchar(6) DEFAULT NULL,
  `ban` tinyint(1) NOT NULL DEFAULT 0,
  `google_authenticator_secret` varchar(1000) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Déchargement des données de la table `utilisateur`
--

INSERT INTO `utilisateur` (`id`, `nom`, `prenom`, `nom_utilisateur`, `email`, `mot_de_passe`, `role`, `reset_code`, `ban`, `google_authenticator_secret`) VALUES
(305, 'Ahmed', 'AAA', 'Haythem123', 'haythem@gmail.com', '$2y$13$M/IPQoOvWsPJkZwDUg2V9Oeu0i9sfCDo7EyDyUboZ8/mIFuEAX2J6', 'ADMIN', NULL, 0, NULL),
(308, 'BB', 'ZZ', 'B1', 'b@gmail.com', '$2y$13$AfJOG43964l9C9P167fIHu71.sSZGcJ.P7xMrsbNRYCt6pcQr6kJO', 'CONDUCTEUR', NULL, 1, NULL),
(316, 'Admin', 'Admin', 'Admin1', 'admin@gmail.com', '$2y$13$nWkPHxX5djeoZ6pLgINCleL5dNMIFzl84h5EeGrfbN46UG/B5hthm', 'ADMIN', NULL, 1, NULL),
(317, 'BB', 'AEE', 'B14', 'BB@gmail.com', '$2y$13$Gnp9G5KPkSixtVHVJHLxi.mXMg3WK8.dUaq//5pO93uQ6xWlC8fbi', 'CONDUCTEUR', NULL, 1, NULL),
(322, 'Admin', 'Admin', 'Admin23', 'admin2@gmail.com', '$2y$13$zVokLj0/I0wwB/uv.DzRy.uxlDXquhx4QBjTs1OoT28hbMULAcAai', 'ADMIN', NULL, 0, 'IFMBGK5ML3AMWJNSC2OSGLYEO7AOFSNIBK5JZIBNBHGZES6GJ62Q'),
(328, 'AA', 'ZZ', 'A111111', 'adzedzdz@gmail.com', '$2y$13$ifrmJ6FRINJ9i4xGXRyJ/.tZWh/6PoRxa7d/LlFIHm2JiAXKfe7hS', 'ORGANISATEUR', NULL, 0, NULL),
(329, 'AA', 'ZZ', 'B122222', 'haythemabdellaoui007@gmail.com', '$2y$13$ZGfEaoOmTtqujOKmeHYGqebx3QZFhj0xYyuIdZ6VqsKlazDdcfEEO', 'CLIENT', '', 0, '2CREJ3JZJ37TG4EHUH4MMEFHSMM3AHDRQB5QUKF7APFVCCWJPZ2A'),
(330, 'AA', 'ZZ', 'B111111', 'bdzdzR3R3@gmail.com', '$2y$13$5pyUlF2BkmOGN9szSZ17LuL7FfzMtvLq2frTSdPHGZA2QdA0oG9ey', 'CLIENT', NULL, 0, NULL),
(331, 'Haythem', 'Haythem', 'Bogalight69', 'haythem2@gmail.com', '$2y$13$gfwuwITCBkwbXult0wPgdOVCZB42v1daAoxLq1s/QriISPR5Ll9NG', 'CONDUCTEUR', NULL, 0, NULL),
(332, 'Haythem', 'Haythem', 'Haythem123456', 'haythem2345@gmail.com', '$2y$13$ht6GXcOCZdpwpHlSIYs.C.rblaq8..IuQIrfFCQEY4IGcjAyKswuq', 'CLIENT', NULL, 0, NULL),
(333, 'Haythem', 'Haythem', 'Haythem1234567', 'haythem26666@gmail.com', '$2y$13$HLfHcFN1HIcQfB/w9rM.vu1y2eF0hGpJMPj1jE29Xl6HstQatok7m', 'CLIENT', NULL, 0, NULL),
(342, 'Ahlem', 'Ahlem', 'Ahlem12', 'fekih@gmail.com', '$2y$12$i0yqB0MexRUL7jizOmCvs.MPsxr5Q49uj6MmaeZhRebVswohgWovK', 'ORGANISATEUR', NULL, 0, 'SEE2GMJ53K5CTOEBV3MFGKM5SECHJVREA4RT4QXQ7XRGSBG5SHYA');

-- --------------------------------------------------------

--
-- Structure de la table `vehicule`
--

CREATE TABLE `vehicule` (
  `id` int(11) NOT NULL,
  `type` varchar(50) NOT NULL,
  `capacite` int(11) NOT NULL,
  `statut` varchar(50) NOT NULL,
  `dispo` tinyint(1) NOT NULL,
  `conducteur_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Déchargement des données de la table `vehicule`
--

INSERT INTO `vehicule` (`id`, `type`, `capacite`, `statut`, `dispo`, `conducteur_id`) VALUES
(1, 'aaa', 2, 'available', 1, 317),
(2, 'aaa', 2, 'available', 1, 331),
(3, 'car', 4, 'available', 0, 331);

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
  ADD PRIMARY KEY (`avis_id`),
  ADD KEY `IDX_8F91ABF0DAE07E97` (`blog_id`);

--
-- Index pour la table `blog`
--
ALTER TABLE `blog`
  ADD PRIMARY KEY (`blog_id`);

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
-- Index pour la table `doctrine_migration_versions`
--
ALTER TABLE `doctrine_migration_versions`
  ADD PRIMARY KEY (`version`);

--
-- Index pour la table `evenment`
--
ALTER TABLE `evenment`
  ADD PRIMARY KEY (`id_event`);

--
-- Index pour la table `fedback`
--
ALTER TABLE `fedback`
  ADD PRIMARY KEY (`id`);

--
-- Index pour la table `messenger_messages`
--
ALTER TABLE `messenger_messages`
  ADD PRIMARY KEY (`id`),
  ADD KEY `IDX_75EA56E0FB7336F0` (`queue_name`),
  ADD KEY `IDX_75EA56E0E3BD61CE` (`available_at`),
  ADD KEY `IDX_75EA56E016BA31DB` (`delivered_at`);

--
-- Index pour la table `organisateur`
--
ALTER TABLE `organisateur`
  ADD PRIMARY KEY (`id`);

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
  MODIFY `avis_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=12;

--
-- AUTO_INCREMENT pour la table `blog`
--
ALTER TABLE `blog`
  MODIFY `blog_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=17;

--
-- AUTO_INCREMENT pour la table `evenment`
--
ALTER TABLE `evenment`
  MODIFY `id_event` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=9;

--
-- AUTO_INCREMENT pour la table `fedback`
--
ALTER TABLE `fedback`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT pour la table `messenger_messages`
--
ALTER TABLE `messenger_messages`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT pour la table `trajet`
--
ALTER TABLE `trajet`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT pour la table `utilisateur`
--
ALTER TABLE `utilisateur`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=343;

--
-- AUTO_INCREMENT pour la table `vehicule`
--
ALTER TABLE `vehicule`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- Contraintes pour les tables déchargées
--

--
-- Contraintes pour la table `admin`
--
ALTER TABLE `admin`
  ADD CONSTRAINT `FK_admin_utilisateur` FOREIGN KEY (`id`) REFERENCES `utilisateur` (`id`);

--
-- Contraintes pour la table `client`
--
ALTER TABLE `client`
  ADD CONSTRAINT `FK_client_utilisateur` FOREIGN KEY (`id`) REFERENCES `utilisateur` (`id`);

--
-- Contraintes pour la table `conducteur`
--
ALTER TABLE `conducteur`
  ADD CONSTRAINT `FK_conducteur_utilisateur` FOREIGN KEY (`id`) REFERENCES `utilisateur` (`id`);

--
-- Contraintes pour la table `organisateur`
--
ALTER TABLE `organisateur`
  ADD CONSTRAINT `FK_organisateur_utilisateur` FOREIGN KEY (`id`) REFERENCES `utilisateur` (`id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
