#!/bin/bash

if(( ! doc -d ))
then
    mkdir doc
fi

javadoc -d doc */*.java 
