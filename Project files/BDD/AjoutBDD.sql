--
-- Déchargement des données de la table `clients`
--

INSERT INTO `clients` (`clientID`, `clientNom`, `clientMail`) VALUES
                                                                  (1, 'SEGADO', 'jean-pierre.seg'),
                                                                  (2, 'PALASI', 'julienne.palasi');

--
-- Déchargement des données de la table `commander`
--

INSERT INTO `commander` (`clientID`, `produitID`, `quantité`) VALUES
                                                                  (1, 1, 1),
                                                                  (1, 2, 2),
                                                                  (2, 1, 2),
                                                                  (2, 2, 4),
                                                                  (2, 3, 6);

--
-- Déchargement des données de la table `produits`
--

INSERT INTO `produits` (`produitID`, `produitNom`, `produitPrix`) VALUES
                                                                      (1, 'pantalons', 40),
                                                                      (2, 'chemise', 30),
                                                                      (3, 'chaussures', 50);