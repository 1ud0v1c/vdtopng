package export

import ProgramArgs
import common.Size
import file.FileExtension
import file.FileHandler
import os.RuntimeExecutor

class IOSExport: Export {
    override fun export(programArgs: ProgramArgs,
                        fileHandler: FileHandler) {
        if (programArgs.canMakeIOSExport()) {
            val size = Size(programArgs.size!!.width.div(3), programArgs.size!!.height.div(3))
            for (i in 1 until 4) {
                val currentCommand = "${RuntimeExecutor.IMAGE_MAGICK_NAME} ${fileHandler.getFilenameWithPNGExt()} " +
                        "${RuntimeExecutor.IMAGE_MAGICK_RESIZE_OPTION} ${size.width * i}x${size.height * i} " +
                        "${fileHandler.getFilenameWithoutExt()}@${i}x${FileExtension.PNG}"
                RuntimeExecutor.execute(currentCommand)
            }
        }
    }
}