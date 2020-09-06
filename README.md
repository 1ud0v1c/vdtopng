# vdtopng

Tool to convert Android drawable to usable png for iOS.

For now, it's a work in progress. You can only convert a vector drawable to a corresponding SVG. 
Compile the project using the script build.sh, then execute: 

```
./build.sh data/ic_android.xml 
```

Il will generate a SVG file inside the same folder than the vector drawable.
