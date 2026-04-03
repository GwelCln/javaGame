src=$(wildcard src/*/*.java)

all: comp

comp: # le / . indique qu'il faut tout prendre dans le repertoire classroot
	javac -d ./classroot/ -cp ./classroot/ $(src)
	jar cfe javaGame.jar monde3.Main -C classroot/ .  



# exec: # pour le monde 3 et moins. n'est plus la commande executer de base.
# 	javac $(src)
# 	jar cfe javaGame.jar monde3/Main monde3/*.class map/*.class player/*.class


run:
	./run.sh 
doc:
	./doc.sh monde3
	./doc.sh map
	./doc.sh player

doc_clean:
	rm -rf doc

clean:
	rm -f ./classroot/*/*
	
