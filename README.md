# UBomb - Projet de POO (2021)

## Groupe

* Guéwen Cousseau (INF501 A2)
* Léo Daudier de Cassini (INF501 A2)

## Le jeu

Toutes les fonctionnalités décrites dans la feuille de route ont été codées.
Il en est de même pour les questions facultatives.
Tout éventuel bug trouvé sur le jeu n'est pas connu par notre groupe.

## Modifications apportées

Certains noms de fonctions / packages ont été modifiés pour apporter une cohérence dans notre projet.
Nous avons grandement remanié le fichier `GameEngine` pour éviter la duplication de code. \
Le fichier `Game` a, lui aussi, grandement été modifié. Nous avons fait le choix de créer une classe `GameConfig` qui donne accès à tout le projet (à condition de disposer d'un accès à la `game`) aux valeurs par défaut présentes dans `config.properties`. \
Tous les éléments qui ne sont pas des décors (`Stone`, `Tree` et `Bonus`) sont stockés dans une `liste` indépendante.\
Une composante `level` a été ajouté à la classe `Position` pour faciliter la gestion des `Sprites`.

Nous avons fait le choix d'afficher toute la `statusBar` sur les petits niveaux. Il serait bien de délimiter graphiquement le jeu.

## Possibilités d'améliorations

La classe `SmartPosition` présente dans le package `game` n'est pas optimale. Elle ne prend en compte que quelques paramètres :

* La distance entre la position du monstre et du joueur
* Une certaine probabilité de déplacement vers le joueur selon la distance

Si nous avions voulu rendre le monstre encore plus "autonome", on aurait pu :

* Prendre en compte la position de la princesse (pour que les monstres la protège)
* Établir un plus court chemin entre le joueur et le monstre (certaines fois, lorsque le monstre est bloqué par des caisses, aucun chemin ne lie le joueur et le monstre)
* Faire communiquer les monstres entre eux : un nombre `n` de montres peuvent s'occuper du joueur pendant que les autres bloquent les axes principaux ou tournent autour de la princesse.
