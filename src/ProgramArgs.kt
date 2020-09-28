import common.Size

class ProgramArgs(args: Array<String>) {
    companion object {
        const val COLOR_OPTION = "-c"
        const val SIZE_OPTION = "-s"
        const val EXPORT_OPTION = "-e"
    }

    private val supportedExports = arrayOf( "ios" )
    private var isIOSExportNeeded: Boolean = false

    var size: Size? = null
    var color: String? = null

    init {
        if (args.contains(COLOR_OPTION)) {
            val colorIndex = args.indexOf(COLOR_OPTION) + 1
            if (colorIndex < args.size) {
                val element = args[colorIndex]
                if (isColorValid(element)) {
                    color = element
                } else {
                    println("You need to seize a valid hexadecimal color, this one is invalid: $element.")
                }
            } else {
                println("You need to seize a color.")
            }
        }

        if (args.contains(SIZE_OPTION)) {
            val sizeIndex = args.indexOf(SIZE_OPTION) + 1
            if (sizeIndex + 1 < args.size) {
                val stringWidth = args[sizeIndex]
                val stringHeight = args[sizeIndex+1]
                if (isSizeValid(stringWidth, stringHeight)) {
                    val width = stringWidth.toInt()
                    val height = stringHeight.toInt()
                    size = Size(width, height)
                } else {
                    println("You need to seize a valid size, this one is invalid: [$stringWidth,$stringHeight].")
                }
            } else {
                println("You need to seize an height and a width.")
            }
        }

        if (args.contains(EXPORT_OPTION)) {
            val exportIndex = args.indexOf(EXPORT_OPTION) + 1
            if (exportIndex < args.size) {
                val wantedExport = args[exportIndex].toLowerCase()
                if (supportedExports.contains(wantedExport)) {
                    isIOSExportNeeded = true
                } else {
                    val supportedExportOption = "[ ${supportedExports.joinToString(", ")} ]"
                    println("You need to seize a valid export options, currently supported: $supportedExportOption.")
                }
            } else {
                println("You need to seize a wanted export.")
            }
        }
    }

    private fun isColorValid(element: String): Boolean {
        val matches = "#[0-9A-Fa-f]{6,8}".toRegex().find(element)
        matches?.groupValues?.let { results ->
            return results.isNotEmpty()
        }
        return false
    }

    private fun isSizeValid(width: String,
                            height: String): Boolean {
        return isInteger(width) && isInteger(height)
    }

    private fun isInteger(element: String): Boolean {
        return element.toIntOrNull()?.let { true } ?: false
    }

    fun canMakeIOSExport(): Boolean {
        return size != null && isIOSExportNeeded
    }
}