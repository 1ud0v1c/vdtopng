object RuntimeExecutor {
    const val IMAGE_MAGICK_NAME = "convert"
    const val IMAGE_MAGICK_BACKGROUND_OPTION = "-background none"
    const val IMAGE_MAGICK_RESIZE_OPTION = "-resize"

    fun isImageMagickPresent(): Boolean {
        val runtime = Runtime.getRuntime()
        val process = runtime.exec("which $IMAGE_MAGICK_NAME")
        process.waitFor()
        return process.exitValue() == 0
    }

    fun execute(command: String): Boolean {
        if (command.isEmpty()) {
            println("You need to seize a command.")
            return false
        }
        val runtime = Runtime.getRuntime()
        val process = runtime.exec(command)
        process.waitFor()
        return process.exitValue() == 0
    }
}