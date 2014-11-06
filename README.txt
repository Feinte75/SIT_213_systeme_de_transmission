Notre programme est livré avec un script bash permettant le lancement simplifié du simulateur avec des paramètres.

Le script va compiler le programme, generer la javadoc dans un dossier à part (javadocSysTrans) et lancer le simulateur.
Il est possible qu'il faille attribuer aux script les droits d'executions (chmod 753 sysTrans.sh)

Pour executer l'applications avec les paramètres souhaités il faut ensuite taper la commande :

java ihm.Simulateur

Differents paramètres peuvent être donnés en argument. La liste est donnée à suivre :

-etape e
Précise l’étape, donc la chaîne de transmission à utiliser.
Le paramètre e peut prendre les valeurs 1, 2, 3, 4a, 4b, 5a, 5b.
Par défaut le simulateur utilise la chaîne de transmission de l’étape 1.

-mess m
Précise le message ou la longueur du message à émettre :
Si m est une suite de 0 et de 1 de longueur au moins égale à 7, m est le message à émettre.
Si m comporte au plus 6 chiffres décimaux et correspond à la représentation en base 10 d'un entier
,cet entier est la longueur du message que le simulateur génére et transmets.
Par défaut le simulateur génére et transmettre un message de longueur 100.

-s
Utilisation de sondes. Par défaut le simulateur n’utilise pas de sondes

-form f
Précise la forme d’onde du signal analogique.
Le paramètre f peut prendre les valeurs suivantes :
NRZ forme d'onde rectangulaire
NRZT forme d'onde trapézoïdale (temps de montée ou de descente à 1/3 du temps bit)
RZ forme d'onde impulsionnelle (amplitude max sur la moitié du temps bit et deuxième moitié à min)
Par défaut le simulateur utilise la forme d’onde RZ pour le signal analogique.

-nbEch ne
Précise le nombre d’échantillons par bit.
Le paramètre doit être une valeur entière positive.
Par défaut le simulateur utilise 10 échantillons par bit.

-ampl min max
Précise l’amplitude min et max du signal analogique.
Les paramètres min et max doivent être des valeurs flottantes (avec min < max).
Par défaut le simulateur utilise 0.0f comme min et 1.0f comme max.

-snr s
précise la valeur du rapport signal sur bruit (SNR en dB). 