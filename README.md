# Vector drawable to PNG, abbreviate: vdtopng.

Tool to convert Android drawable to usable png for iOS.

## Dependencies

To use this program you need to have [Kotlin](https://kotlinlang.org/) & [ImageMagick](https://imagemagick.org/index.php).

```
# Install Kotlin on Linux & Mac OSX.
curl -s https://get.sdkman.io | bash
sdk install kotlin

# Install ImageMagick for Mac OSX, it should already be installed on Linux.
brew install imagemagick
```

## How to use 

For now, the program is still in development, but you can already generate a PNG from the vector drawable, change 
its color, its size and make the export for iOS. If you encounter any problem feel free to submit an issue.
 
To compile the project, you can use the script **build.sh** or open the project with [IntelliJ IDEA](https://www.jetbrains.com/idea/). 

Here is a little example of how to use the script : 

```
./build.sh "data/ic_android.xml -c #F55600 -s 1200 1200"
```

Il will generate an SVG & a png file inside the same folder than the vector drawable.