#!/bin/bash


echo -n "Entrer le nom du fichier de sauvegarde : "
read varname

java -jar javaGame.jar "save/$varname.txt"