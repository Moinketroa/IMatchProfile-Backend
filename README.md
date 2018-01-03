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

**Utiliser de préférence Glassfish 4 et non pas Glassfish 4.1.1, pour éviter des erreurs JBoss**  
Si toutefois vous utilisez Glassfish 4.1.1 et qu'une erreur JBoss survient il vous faudra télécharger le .jar JBoss-Logging 3.3.0.Final (https://mvnrepository.com/artifact/org.jboss.logging/jboss-logging/3.3.0.Final) et le placer dans votre répertoire Glassfish 4.1.1 à l'endroit [chemin vers Glassfish 4.1.1]/glassfish/modules
