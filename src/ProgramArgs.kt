class ProgramArgs(args: Array<String>) {
    companion object {
        const val COLOR_OPTION = "-c"
        const val SIZE_OPTION = "-s"
    }

    var color: String? = null
    var size: Size? = null

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
}