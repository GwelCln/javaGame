src=$(wildcard */*.java)
cls=$(wildcard */*.class)

all: exec

exec: 
	javac $(src)

run:
	java monde2.Main
doc:
	./doc.sh monde2
	./doc.sh map
	./doc.sh player

doc_clean:
	rm -rf doc

clean:
	rm -f $(cls)
	
