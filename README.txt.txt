Notre programme est livré avec un script bash permettant le lancement simplifié du simulateur avec des paramètres.

Le script va compiler le programme, generer la javadoc dans un dossier à part (javadocSysTrans) et lancer le simulateur.
Il est possible qu'il faille attribuer aux script les droits d'executions (chmod 753 sysTrans.sh)

Un lancement du script sans paramètres lancera une simulation sur un signal de 100 bits généré aléatoirement :
User :~/Test/SIT213 $ sysTrans.sh

Sinon le premier paramètre pourra prendre deux valeurs, une chaine de 0 et de 1 d’une taille >= 7 correspondant à l’entrée généré par la source :
User :~/Test/SIT213 $ sysTrans.sh 10111011

Ou un nombre inférieur à 7 caractère correspondant au nombre de bit à générer par la source aléatoire (ici 20 bits).
User :~/Test/SIT213 $ sysTrans.sh 20

Si le premier paramètre est donné il est possible de spécifier un deuxième paramètre correspondant à l’affichage des sondes.
Si l’affichage est désiré il faudra indiquer 1. Indiquer 0 aura un comportement identique à ne rien mettre en deuxième paramètre.
User :~/Test/SIT213 $ sysTrans.sh 20 1
