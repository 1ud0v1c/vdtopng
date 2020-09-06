#!/bin/bash
cd src
kotlinc *.kt -include-runtime -d ../vdtopng.jar
cd ..
java -jar vdtopng.jar $1 $2 $3 $4 $5
