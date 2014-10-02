Notre programme est livr� avec un script bash permettant le lancement simplifi� du simulateur avec des param�tres.

Le script va compiler le programme, generer la javadoc dans un dossier � part (javadocSysTrans) et lancer le simulateur.
Il est possible qu'il faille attribuer aux script les droits d'executions (chmod 753 sysTrans.sh)

Un lancement du script sans param�tres lancera une simulation sur un signal de 100 bits g�n�r� al�atoirement :
User :~/Test/SIT213 $ sysTrans.sh

Sinon le premier param�tre pourra prendre deux valeurs, une chaine de 0 et de 1 d�une taille >= 7 correspondant � l�entr�e g�n�r� par la source :
User :~/Test/SIT213 $ sysTrans.sh 10111011

Ou un nombre inf�rieur � 7 caract�re correspondant au nombre de bit � g�n�rer par la source al�atoire (ici 20 bits).
User :~/Test/SIT213 $ sysTrans.sh 20

Si le premier param�tre est donn� il est possible de sp�cifier un deuxi�me param�tre correspondant � l�affichage des sondes.
Si l�affichage est d�sir� il faudra indiquer 1. Indiquer 0 aura un comportement identique � ne rien mettre en deuxi�me param�tre.
User :~/Test/SIT213 $ sysTrans.sh 20 1
