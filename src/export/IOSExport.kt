package export

import ProgramArgs
import common.Size
import file.FileExtension
import file.FileHandler
import os.RuntimeExecutor

class IOSExport(private val fileHandler: FileHandler,
                private val programArgs: ProgramArgs) {
    fun export() {
        val imageMagickCommand = "${RuntimeExecutor.IMAGE_MAGICK_NAME} ${RuntimeExecutor.IMAGE_MAGICK_BACKGROUND_OPTION}"
        val imageMagickParameters = "${fileHandler.getFilenameWithSVGExt()} ${fileHandler.getFilenameWithPNGExt()}"
        if (RuntimeExecutor.execute("$imageMagickCommand $imageMagickParameters")) {
            if (programArgs.canExport()) {
                generateIOSExport(programArgs, fileHandler)
            }
        } else {
            println("An error occurred while executing the ImageMagick command")
        }
    }

    private fun generateIOSExport(programArgs: ProgramArgs, fileHandler: FileHandler) {
        val size = Size(programArgs.size!!.width.div(3), programArgs.size!!.height.div(3))
        for (i in 1 until 4) {
            val currentCommand = "${RuntimeExecutor.IMAGE_MAGICK_NAME} ${fileHandler.getFilenameWithPNGExt()} " +
                    "${RuntimeExecutor.IMAGE_MAGICK_RESIZE_OPTION} ${size.width * i}x${size.height * i} " +
                    "${fileHandler.getFilenameWithoutExt()}@${i}x${FileExtension.PNG}"
            RuntimeExecutor.execute(currentCommand)
        }
    }
}