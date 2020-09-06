import java.io.File

class FileHandler(private val filename: String) {
    companion object {
        const val XML_EXTENSION = ".xml"
        const val SVG_EXTENSION = ".svg"
        const val PNG_EXTENSION = ".png"
    }
    private var file = File(filename)

    fun readFile(): List<String> {
        return file.readLines()
    }

    fun isFileValidXML(): Boolean {
        if (!filename.contains(XML_EXTENSION, ignoreCase = true)) {
            return false
        }
        val file = File(filename)
        if (!file.exists()) {
            return false
        }
        return true
    }

    fun createSVGFile(contents: List<String>) {
        val file = File(getFilenameWithSVGExt())
        if (file.exists()) {
            file.delete()
        }
        for (line in contents) {
            if (contents.indexOf(line) != contents.size -1) {
                file.appendText("$line\n")
            } else {
                file.appendText(line)
            }
        }
    }

    fun getFilenameWithSVGExt(): String {
        return "${file.parentFile.absolutePath}/${file.nameWithoutExtension}$SVG_EXTENSION"
    }

    fun getFilenameWithPNGExt(): String {
        return "${file.parentFile.absolutePath}/${file.nameWithoutExtension}$PNG_EXTENSION"
    }
}