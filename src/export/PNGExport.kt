package export

import ProgramArgs
import file.FileHandler
import os.RuntimeExecutor

class PNGExport: Export {
    override fun export(programArgs: ProgramArgs,
                        fileHandler: FileHandler) {
        val imageMagickCommand = "${RuntimeExecutor.IMAGE_MAGICK_NAME} ${RuntimeExecutor.IMAGE_MAGICK_BACKGROUND_OPTION}"
        val imageMagickParameters = "${fileHandler.getFilenameWithSVGExt()} ${fileHandler.getFilenameWithPNGExt()}"
        if (!RuntimeExecutor.execute("$imageMagickCommand $imageMagickParameters")) {
            println("An error occurred while executing the ImageMagick command.")
        }
    }
}