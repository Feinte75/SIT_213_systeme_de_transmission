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

java ihm.Simulateur -mess 10000 -etape 3 -ampl -5 5 -nbech 15 -s -form NRZ -snr 10 &
java ihm.Simulateur -mess 100 -etape 3 -ampl -5 5 -nbech 15 -s -form RZ -snr 10 &
java ihm.Simulateur -mess 30 -etape 3 -ampl -5 5 -nbech 15 -s -form NRZT -snr 10 &
