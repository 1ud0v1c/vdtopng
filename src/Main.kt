import java.io.File

/**
 * @see https://stackoverflow.com/questions/44948396/convert-vectordrawable-to-svg
 */
fun main(args: Array<String>) {
    if (args.isEmpty()) {
        println(getHelp())
        return
    }

    val filename = args[0]
    if (!isFileValid(filename)) {
        println("You need to seize a valid vector drawable file.")
        return
    }

    val vectorDrawableFile = File(filename)
    val fileContents = vectorDrawableFile.readLines()
    val svgContents = vectorDrawableToSVG(fileContents.toMutableList(), ProgramArgs(args))
    createSVGFile("${vectorDrawableFile.parentFile.absolutePath}/${vectorDrawableFile.nameWithoutExtension}.svg", svgContents)
}

private fun getHelp(): String {
    var text = "\t\t...:: Vector drawable to png ::...\n\n"
    text += "\tExample\n"
    text += "./vdtopng file.xml\n\n"
    text += "\tOptions\n"
    text += "-c: permit to change the color of the drawable, use a hexadecimal value, for example: #FF0000.\n"
    text += "-s: permit to change the size of the vector drawable\n"
    text += "-e: export three different png to support iOS multiple sizes (1x, 1.5x, 2x).\n"
    return text
}

private fun isFileValid(filename: String): Boolean {
    if (!filename.contains(".xml", ignoreCase = true)) {
        return false
    }
    val file = File(filename)
    if (!file.exists()) {
        return false
    }
    return true
}

/**
 * Format an Android vector drawable to a SVG
 */
private fun vectorDrawableToSVG(vectorDrawable: MutableList<String>,
                                options: ProgramArgs): List<String> {
    var viewBoxWith: String? = null
    var viewBoxHeight: String? = null
    var indexNeedDeletion: Int? = null
    val vectorDrawableToSVGAttributes = buildMapAttributes(options)

    vectorDrawable.forEachIndexed { index, line ->
        // Update vector drawables properties
        for ((androidName, svgName) in vectorDrawableToSVGAttributes) {
            if (line.contains(androidName)) {
                val newLine = line.replace(androidName, svgName)
                vectorDrawable[index] = newLine
            }
        }

        // Handle color attribute
        options.color?.let { color ->
            replaceWithRegex(line, "android:fillColor=\"#[0-9A-Fa-f]{6,8}\"(.*)".toRegex())?.let {
                vectorDrawable[index] = "\t\tfill=\"$color\"$it"
            }
        }

        // Handle width & height attributes
        replaceWithRegex(line, "android:width=\"([0-9]+)dp\"".toRegex())?.let {
            vectorDrawable[index] = "\t\twidth=\"$it\""
        }
        replaceWithRegex(line, "android:height=\"([0-9]+)dp\"".toRegex())?.let {
            vectorDrawable[index] = "\t\theight=\"$it\""
        }

        // Handle viewBox attribute
        replaceWithRegex(line, "android:viewportWidth=\"([0-9]+)\"".toRegex())?.let { viewportWidth ->
            viewBoxWith = viewportWidth
            if (viewBoxHeight != null) {
                vectorDrawable[index] = "\t\tviewBox=\"0 0 $viewBoxWith $viewBoxHeight\">"
            } else {
                indexNeedDeletion = index
            }
        }
        replaceWithRegex(line, "android:viewportHeight=\"([0-9]+)\"".toRegex())?.let { viewportHeight ->
            viewBoxHeight = viewportHeight
            if (viewBoxWith != null) {
                vectorDrawable[index] = "\t\tviewBox=\"0 0 $viewBoxWith $viewBoxHeight\">"
            } else {
                indexNeedDeletion = index
            }
        }
    }
    indexNeedDeletion?.let {
        vectorDrawable.removeAt(it)
    }

    return vectorDrawable
}

private fun buildMapAttributes(options: ProgramArgs): MutableMap<String, String> {
    val vectorDrawableToSVGAttributes = mutableMapOf(
            "<vector xmlns:android=\"http://schemas.android.com/apk/res/android\"" to "<svg xmlns=\"http://www.w3.org/2000/svg\"",
            "android:pathData" to "d",
            "android:fillType" to "fill-rule",
            "</vector>" to "</svg>"
    )
    if (options.color == null) {
        vectorDrawableToSVGAttributes["android:fillColor"] = "fill"
    }
    return vectorDrawableToSVGAttributes
}

private fun replaceWithRegex(content: String,
                             regex: Regex): String? {
    val matchHeight = regex.find(content)
    matchHeight?.groupValues?.let { results ->
        if (results.isNotEmpty() && results.size > 1) {
            return results[1]
        }
    }
    return null
}

private fun createSVGFile(filename: String, svgContents: List<String>) {
    val svgFile = File(filename)
    if (svgFile.exists()) {
        svgFile.delete()
    }
    for (line in svgContents) {
        if (svgContents.indexOf(line) != svgContents.size -1) {
            svgFile.appendText("$line\n")
        } else {
            svgFile.appendText(line)
        }
    }
}