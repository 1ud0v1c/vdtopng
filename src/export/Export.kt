package export

import ProgramArgs
import file.FileHandler

interface Export {
    /**
     * Method use to generate files from the SVG obtained with the vector drawable.
     * @param programArgs the options given by the user for the export (by default, we only generate a PNG)
     * @param fileHandler the file which contains SVG content
     */
    fun export(programArgs: ProgramArgs, fileHandler: FileHandler)
}