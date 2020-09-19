package file

import java.io.File

class FileHandler(private val filename: String) {
    companion object {

    }
    private var file = File(filename)

    fun readFile(): List<String> {
        return file.readLines()
    }

    fun isFileValidXML(): Boolean {
        if (!filename.contains(FileExtension.XML, ignoreCase = true)) {
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

    fun getFilenameWithoutExt(): String {
        return "${file.parentFile.absolutePath}/${file.nameWithoutExtension}"
    }

    fun getFilenameWithSVGExt(): String {
        return "${file.parentFile.absolutePath}/${file.nameWithoutExtension}${FileExtension.SVG}"
    }

    fun getFilenameWithPNGExt(): String {
        return "${file.parentFile.absolutePath}/${file.nameWithoutExtension}${FileExtension.PNG}"
    }
}