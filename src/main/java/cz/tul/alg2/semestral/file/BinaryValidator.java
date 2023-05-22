package cz.tul.alg2.semestral.file;

import java.io.File;

/**
 * The type Binary validator.
 */
public class BinaryValidator implements IValidator {
    /**
     * Validates file structure of records of CityTransport
     *
     * @param file file object
     * @return is file valid?
     */
    @Override
    public boolean validateFile(File file) {
        BinaryLoader bl = new BinaryLoader();
        return bl.loadFile(file.getAbsolutePath());
    }
}
