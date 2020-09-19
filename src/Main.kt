import converter.VectorDrawableConverter
import export.IOSExport
import file.FileHandler
import os.RuntimeExecutor

/**
 * @see https://stackoverflow.com/questions/44948396/convert-vectordrawable-to-svg
 */
fun main(args: Array<String>) {
    if (args.isEmpty()) {
        println(getHelp())
        return
    }

    val filename = args[0]
    val fileHandler = FileHandler(filename)
    if (!fileHandler.isFileValidXML()) {
        println("You need to seize a valid vector drawable file.")
        return
    }

    val programArgs = ProgramArgs(args)
    val fileContents = fileHandler.readFile()
    val svgConverter = VectorDrawableConverter(fileContents.toMutableList(), programArgs)
    val svgContents = svgConverter.convertToSVG()
    fileHandler.createSVGFile(svgContents)

    if (RuntimeExecutor.isImageMagickPresent()) {
        val iOSExport = IOSExport(fileHandler, programArgs)
        iOSExport.export()
    }
}

private fun getHelp(): String {
    var text = "\t\t...:: Vector drawable to png ::...\n\n"
    text += "\tExample\n"
    text += "./vdtopng file.xml\n\n"
    text += "\tOptions\n"
    text += "-c: permit to change the color of the drawable, use a hexadecimal value, for example: #FF0000.\n"
    text += "-s: permit to change the size of the vector drawable\n"
    text += "-e: export three different png to support iOS multiple sizes (1x, 2x, 3x).\n"
    return text
}