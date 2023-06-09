package cz.tul.alg2.semestral.file;

import java.io.File;

/**
 * The type Text validator.
 */
public class TextValidator implements IValidator {
    /**
     * Validates file structure
     *
     * @param file file object
     * @return is file valid?
     */
    @Override
    public boolean validateFile(File file) {
        // Loader as self implemented validator -> loading and not saving
        // does validation
        TextLoader tl = new TextLoader();
        return tl.loadFile(file.getAbsolutePath());
    }
}
