src=$(wildcard */*.java)
cls=$(wildcard */*.class)
all: exec

exec: 
	javac $(src)
	jar cfe javaGame.jar monde2/Main monde2/*.class map/*.class player/*.class
run:
	./run.sh 
doc:
	./doc.sh monde2
	./doc.sh map
	./doc.sh player

doc_clean:
	rm -rf doc

clean:
	rm -f $(cls)
	
