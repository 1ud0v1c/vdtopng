#!/bin/bash
cd src/ || {
  echo "An error occurred while compiling the project, please check if the src/ folder exists. "
  echo "If not, you should re-clone the project and retry."
  exit 1
}
kotlinc ./*/*.kt -include-runtime -d ../vdtopng.jar
cd ..