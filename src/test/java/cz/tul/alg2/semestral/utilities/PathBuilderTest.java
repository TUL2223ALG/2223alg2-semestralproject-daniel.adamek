package cz.tul.alg2.semestral.utilities;

import org.junit.jupiter.api.Test;

import java.io.File;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * A set of JUnit5 tests for the PathBuilder class.
 */
class PathBuilderTest {

    /**
     * Tests the joinPath method with an empty array of path parts.
     */
    @Test
    void testEmptyArray() {
        String[] parts = {};
        assertEquals("", PathBuilder.joinPath(parts));
    }

    /**
     * Tests the joinPath method with a single path part.
     */
    @Test
    void testSinglePart() {
        String[] parts = {"dir1"};
        assertEquals("dir1", PathBuilder.joinPath(parts));
    }

    /**
     * Tests the joinPath method with multiple path parts.
     */
    @Test
    void testMultipleParts() {
        String[] parts = {"dir1", "dir2", "file.txt"};
        String expected = "dir1" + File.separator +
                "dir2" + File.separator + "file.txt";
        assertEquals(expected, PathBuilder.joinPath(parts));
    }
}