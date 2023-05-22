package cz.tul.alg2.semestral.validator;

import cz.tul.alg2.semestral.file.BinaryValidator;
import cz.tul.alg2.semestral.utilities.PathBuilder;
import org.junit.jupiter.api.Test;

import java.io.File;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Test class for {@link BinaryValidator}.
 */
class BinaryValidatorTest {

    /**
     * Test case for validating a correct binary file.
     */
    @Test
    void validateCorrectFile() {
        File file = new File(PathBuilder.joinPath("data", "pid.ser"));
        BinaryValidator bv = new BinaryValidator();
        assertTrue(bv.validateFile(file));
    }

    /**
     * Test case for validating a broken binary file.
     */
    @Test
    void validateBrokenFile() {
        File file = new File(PathBuilder.joinPath("data", "pid-broken.ser"));
        BinaryValidator bv = new BinaryValidator();
        assertFalse(bv.validateFile(file));
    }

    /**
     * Test case for validating a file that is not found.
     */
    @Test
    void validateNotFoundFile() {
        File file = new File(PathBuilder.joinPath("data", "data.ser"));
        BinaryValidator bv = new BinaryValidator();
        assertFalse(bv.validateFile(file));
    }

    /**
     * Test case for validating a file that is not a binary file.
     */
    @Test
    void doNotLoadText() {
        File file = new File(PathBuilder.joinPath("data", "pid.txt"));
        BinaryValidator bv = new BinaryValidator();
        assertFalse(bv.validateFile(file));
    }
}
