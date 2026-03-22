#!/bin/bash
echo -n "Entrer le nom du fichier de sauvegarde : "
read varname

if [ -z "$varname" ]; then
    java -jar javaGame.jar
else
    java -jar javaGame.jar "save/$varname.txt"
fi