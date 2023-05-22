package cz.tul.alg2.semestral.validator;

import cz.tul.alg2.semestral.file.TextValidator;
import cz.tul.alg2.semestral.utilities.PathBuilder;
import org.junit.jupiter.api.Test;

import java.io.File;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Test class for {@link TextValidator}.
 */
class TextValidatorTest {

    /**
     * Test case for validating a correct text file.
     */
    @Test
    void validateCorrectFile() {
        File file = new File(PathBuilder.joinPath("data", "pid.txt"));
        TextValidator bv = new TextValidator();
        assertTrue(bv.validateFile(file));
    }

    /**
     * Test case for validating a broken text file.
     */
    @Test
    void validateBrokenFile() {
        File file = new File(PathBuilder.joinPath("data", "pid-broken.txt"));
        TextValidator bv = new TextValidator();
        assertFalse(bv.validateFile(file));
    }

    /**
     * Test case for validating a file that is not found.
     */
    @Test
    void validateNotFoundFile() {
        File file = new File(PathBuilder.joinPath("data", "data.txt"));
        TextValidator bv = new TextValidator();
        assertFalse(bv.validateFile(file));
    }

    /**
     * Test case for validating a file that is not a text file.
     */
    @Test
    void doNotLoadBinary() {
        File file = new File(PathBuilder.joinPath("data", "pid.ser"));
        TextValidator bv = new TextValidator();
        assertFalse(bv.validateFile(file));
    }
}
