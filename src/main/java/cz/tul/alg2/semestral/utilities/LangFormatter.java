package cz.tul.alg2.semestral.utilities;

public class LangFormatter {
    /**
     * The formatCzechMinutes function takes an integer and returns a string.
     * The returned string is the number of minutes in Czech, with the correct suffix.
     * For example, formatCzechMinutes(5) would return 5 minut.
     *
     * @param a Determine the correct suffix
     *
     * @return A string that is the number of minutes followed by a word
     */
    public static String formatCzechMinutes(int a) {
        return a + switch(a) {
            case 1 -> " minuta";
            case 2, 3, 4 -> " minuty";
            default -> " minut";
        };
    }
}
