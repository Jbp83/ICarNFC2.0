# ICarNFC2.0

- Télécharger le dossier git à l'adresse suivante : https://github.com/Jbp83/ICarNFC2.0

*Coté serveur*

- Ouvrir intelliJ
- Import Project et aller chercher le pom.xml à la racine du dossier ICarNFC2.0/serveur
- Edit configuration -> + -> Maven et entrez dans la commande line : spring-boot:run
- Lancer votre serveur.


*Lancement de l'application avec Android Studio*
*Coté client (android)*

- Ouvrir android Studio
- File->Open->ICarNFC2.0/Android
- Lancer le projet

*Lancement de l'application sur le portable avec Android Studio*

- Ouvrir un shell cmd.
- Tapez ipconfig/all
- Récupérer l'ip de la machine et vérifier que toutes les requetes de okhttp pointe vers cette ip.
- Verifier que le portable physique qui fera tourner le serveur est connecté au même reseau internet que le téléphone portable android
