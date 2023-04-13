package cz.tul.alg2.semestral.patternmatcher;

public class HirschbergMatching {

    /**
 * Calculates the percentage similarity between two strings using the Hirschberg algorithm.
 * This implementation uses dynamic programming to optimize performance.
 *
 * @param s1 the first string
 * @param s2 the second string
 * @return the percentage similarity between s1 and s2 as a double from 0.0 to 1.0
 */
    public static double similarity(String s1, String s2) {
        // Initialize variables to store string lengths
        int n = s1.length();
        int m = s2.length();

        // If either string is empty, the similarity is 0%
        if (n == 0 || m == 0) {
            return 0.0;
        }

        // Initialize dynamic programming arrays
        int[] prev = new int[m + 1];
        int[] curr = new int[m + 1];

        // Calculate the LCS of the two strings
        for (int i = 1; i <= n; i++) {
            // Set the first element of the current row to 0
            curr[0] = 0;

            for (int j = 1; j <= m; j++) {
                if (s1.charAt(i - 1) == s2.charAt(j - 1)) {
                    // If the characters match, add 1 to the LCS
                    curr[j] = prev[j - 1] + 1;
                } else {
                    // Otherwise, take the maximum LCS from the previous row
                    curr[j] = Math.max(prev[j], curr[j - 1]);
                }
            }

            // Swap the previous and current rows for the next iteration
            int[] temp = prev;
            prev = curr;
            curr = temp;
        }

        // The LCS length is stored in the last element of the previous row
        int lcs = prev[m];

        // Calculate the percentage similarity
        return (double) lcs / Math.max(n, m);
    }
}
