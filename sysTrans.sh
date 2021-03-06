#!/bin/bash

echo "Compilation des sources" 
cd ./src

javac -encoding ISO-8859-1 ./application/*.java
javac -encoding ISO-8859-1 ./element_transmission/*.java
javac -encoding ISO-8859-1 ./exception/*.java
javac -encoding ISO-8859-1 ./ihm/*.java
javac -encoding ISO-8859-1 ./sonde/*.java
#javac ./test/*.java

echo "Generation de la javadoc" 
mkdir javadocSysTrans
javadoc -d javadocSysTrans ./*/*.java

java ihm.Simulateur -mess 10111010 -etape 4b -ampl 0 5 -nbEch 24 -s -form NRZ -snr 10 -ti 2 10 12 0.5 0.2
