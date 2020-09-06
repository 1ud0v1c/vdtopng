class ProgramArgs(args: Array<String>) {
    var color: String? = null

    init {
        if (args.contains("-c")) {
            val colorIndex = args.indexOf("-c") + 1
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
    }

    private fun isColorValid(element: String): Boolean {
        val matches = "#[0-9A-Fa-f]{6,8}".toRegex().find(element)
        matches?.groupValues?.let { results ->
            return results.isNotEmpty()
        }
        return false
    }
}