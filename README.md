# IMatchProfile-Backend

Pour ne pas avoir à executer les tests faites cette commande avant
$ mvn install -Dmaven.test.skip=true

## Lancement rapide

### BDD

Connectez-vous sur la BDD distante, ou, connectez-vous sur une BDD MySQL locale en utilisant le script de création de schéma disponible s
sur ./sql/IMPDB.sql ou ./sql/imatchprofile_imatchprofile.sql (en fonction des branches)

Le fichier de config Hibernate se trouve sur ./src/main/resources/hibernate.cfg.xml

### API

Pour un lancement simple, importez le projet sur Netbeans et clic-droit sur le projet -> Run

