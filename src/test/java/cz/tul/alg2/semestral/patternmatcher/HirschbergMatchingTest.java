package cz.tul.alg2.semestral.patternmatcher;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class HirschbergMatchingTest {

    /**
     * Tests the similarity method with empty strings.
     */
    @Test
    void testEmptyStrings() {
        assertEquals(0.0, HirschbergMatching.similarity("", ""));
        assertEquals(0.0, HirschbergMatching.similarity("abc", ""));
        assertEquals(0.0, HirschbergMatching.similarity("", "xyz"));
    }

    /**
     * Tests the similarity method with identical strings.
     */
    @Test
    void testIdenticalStrings() {
        assertEquals(1.0, HirschbergMatching.similarity("abc", "abc"));
        assertEquals(1.0, HirschbergMatching.similarity("12345", "12345"));
    }

    /**
     * Tests the similarity method with different strings.
     */
    @Test
    void testDifferentStrings() {
        assertEquals(0.0, HirschbergMatching.similarity("abc", "xyz"));
        assertEquals(0.0, HirschbergMatching.similarity("12345", "67890"));
    }

    /**
     * Tests the similarity method with similar strings.
     */
    @Test
    void testSimilarStrings() {
        assertEquals(7./8., HirschbergMatching.similarity("abcdefg", "abcdfeg"), 0.03);
        assertEquals(5./8., HirschbergMatching.similarity("12345", "12abc345"), 0.01);
    }

    /**
     * Tests the similarity method with long strings.
     */
    @Test
    void testLongStrings() {
        String s1 = "abcdefghijklmnopqrstuvwxyz";
        String s2 = "bcdefghijklmnopqrstuvwxyza";
        assertEquals(25./26., HirschbergMatching.similarity(s1, s2), 0.01);
    }

}