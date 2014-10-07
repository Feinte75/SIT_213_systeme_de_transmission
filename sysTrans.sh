#!/bin/bash

echo "Compilation des sources" 
cd ./src

javac ./application/*.java
javac ./exception/*.java
javac ./ihm/*.java
javac ./sonde/*.java
#javac ./test/*.java

echo "Generation de la javadoc" 
mkdir javadocSysTrans
javadoc -d javadocSysTrans ./*/*.java

java ihm.Simulateur -mess 10111101101 -etape 2 -ampl 0 5 -nbech 60 -s -form NRZT
