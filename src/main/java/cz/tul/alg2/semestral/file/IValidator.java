package cz.tul.alg2.semestral.file;

import java.io.File;

public interface IValidator {

    /**
     * Validates file structure
     * @param file file object
     * @return is file valid?
     */
    boolean validateFile(File file);
}
