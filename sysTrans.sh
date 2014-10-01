#!/bin/bash




echo "Votre saisie :" 
echo "Argument info generee : $1"
echo "Argument sonde: $2"

javac src/*.java
cd src
java Test $*
