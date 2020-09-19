package converter

import ProgramArgs

class VectorDrawableConverter(private val vectorDrawable: MutableList<String>,
                              private val options: ProgramArgs) {
    fun convertToSVG(): List<String> {
        var viewBoxWith: String? = null
        var viewBoxHeight: String? = null
        var viewPortIndexToDelete: Int? = null
        val vectorDrawableToSVGAttributes = buildMapAttributes(options)
        val indexesToDelete = ArrayList<Int>()

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
                extractRegex(line, "android:fillColor=\"[#@:/0-9A-Fa-fa-zA-Z]+\"(.*)".toRegex())?.let {
                    vectorDrawable[index] = "\t\tfill=\"$color\"$it"
                }
            }

            if (line.contains("android:tint")) {
                indexesToDelete.add(index)
            }

            // Handle width & height attributes
            extractRegex(line, "android:width=\"([0-9]+)dp\"".toRegex())?.let {
                vectorDrawable[index] = "\t\twidth=\"$it\""
                options.size?.let { size ->
                    vectorDrawable[index] = "\t\twidth=\"${size.width}\""
                }
            }
            extractRegex(line, "android:height=\"([0-9]+)dp\"".toRegex())?.let {
                vectorDrawable[index] = "\t\theight=\"$it\""
                options.size?.let { size ->
                    vectorDrawable[index] = "\t\theight=\"${size.height}\""
                }
            }

            // Handle viewBox attribute
            extractRegex(line, "android:viewportWidth=\"([0-9]+)\"".toRegex())?.let { viewportWidth ->
                viewBoxWith = viewportWidth
                if (viewBoxHeight != null) {
                    vectorDrawable[index] = "\t\tviewBox=\"0 0 $viewBoxWith $viewBoxHeight\">"
                } else {
                    viewPortIndexToDelete = index
                }
            }
            extractRegex(line, "android:viewportHeight=\"([0-9]+)\"".toRegex())?.let { viewportHeight ->
                viewBoxHeight = viewportHeight
                if (viewBoxWith != null) {
                    vectorDrawable[index] = "\t\tviewBox=\"0 0 $viewBoxWith $viewBoxHeight\">"
                } else {
                    viewPortIndexToDelete = index
                }
            }
        }
        viewPortIndexToDelete?.let {
            vectorDrawable.removeAt(it)
        }
        for (index in indexesToDelete) {
            vectorDrawable.removeAt(index)
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

    private fun extractRegex(content: String,
                             regex: Regex): String? {
        val matchHeight = regex.find(content)
        matchHeight?.groupValues?.let { results ->
            if (results.isNotEmpty() && results.size > 1) {
                return results[1]
            }
        }
        return null
    }
}