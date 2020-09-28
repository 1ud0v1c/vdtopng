import export.Export
import export.IOSExport
import export.PNGExport
import export.SVGExport
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
    val exporters = buildExportersList()
    if (RuntimeExecutor.isImageMagickPresent()) {
        for (exporter in exporters) {
            exporter.export(programArgs, fileHandler)
        }
    }
}

private fun getHelp(): String {
    var text = "\t\t...:: Vector drawable to png ::...\n\n"
    text += "\tExample\n"
    text += "./vdtopng file.xml\n\n"
    text += "\tOptions\n"
    text += "-c: permit to change the color of the drawable, use a hexadecimal value, for example: #FF0000.\n"
    text += "-s: permit to change the size of the vector drawable, precise the width and height: 1200 1200.\n"
    text += "-e: allow to export the xml file to support iOS multiple format (1x, 2x, 3x): ios.\n"
    return text
}

private fun buildExportersList(): ArrayList<Export> {
    val exporters = ArrayList<Export>()
    exporters.add(SVGExport())
    exporters.add(PNGExport())
    exporters.add(IOSExport())
    return exporters
}