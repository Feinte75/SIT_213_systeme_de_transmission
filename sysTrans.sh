#!/bin/bash

javac src/*.java
cd src

mkdir javadocSysTrans
javadoc -d javadocSysTrans *.java

echo "Votre saisie :" 
echo "Argument info generee : $1"
echo "Argument sonde: $2"
java Test $1 $2