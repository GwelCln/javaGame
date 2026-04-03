#!/bin/bash
# echo -n "Entrer le nom du fichier de sauvegarde : "
# read varname

# if [ -z "$varname" ]; then # -z test if the string is empty
#     java -jar javaGame.jar
# else
#     java -jar javaGame.jar "save/$varname.txt"
# fi


java -jar javaGame.jar "save/map2.txt" "save/map1.txt" "save/map3.txt"