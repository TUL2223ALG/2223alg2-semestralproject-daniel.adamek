package cz.tul.alg2.semestral.file;

import cz.tul.alg2.semestral.errorhandle.ErrorLogger;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * The type Description saver.
 */
public class DescriptionSaver {
    /**
     * Save text to file.
     *
     * @param file the file
     * @param text the text
     */
    public static void saveTextToFile(File file, String text) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            writer.write(text);
        } catch (IOException e) {
            new ErrorLogger("error.log").logError("Chyba při ukládání výsledku.", e);
        }
    }
}
