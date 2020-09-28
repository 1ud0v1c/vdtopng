package export

import ProgramArgs
import converter.VectorDrawableConverter
import file.FileHandler

class SVGExport: Export {
    override fun export(programArgs: ProgramArgs, fileHandler: FileHandler) {
        val fileContents = fileHandler.readFile()
        val svgConverter = VectorDrawableConverter(fileContents.toMutableList(), programArgs)
        val svgContents = svgConverter.convertToSVG()
        fileHandler.createSVGFile(svgContents)
    }
}