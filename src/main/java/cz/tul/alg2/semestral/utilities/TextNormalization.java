package cz.tul.alg2.semestral.utilities;

import java.util.HashMap;

public class TextNormalization {
    private final static StringBuilder sb = new StringBuilder();
    //private static int[] map = new int[383];
    private final static HashMap<Integer, Integer> map = new HashMap<>();
    static {
            map.put((int) 'é', (int) 'e');
            map.put((int) 'ě', (int) 'e');
            map.put((int) 'š', (int) 's');
            map.put((int) 'č', (int) 'c');
            map.put((int) 'ř', (int) 'r');
            map.put((int) 'ž', (int) 'z');
            map.put((int) 'ý', (int) 'y');
            map.put((int) 'á', (int) 'a');
            map.put((int) 'í', (int) 'i');
            map.put((int) 'ť', (int) 't');
            map.put((int) 'ň', (int) 'n');
            map.put((int) 'ó', (int) 'o');
            map.put((int) 'ö', (int) 'o');
            map.put((int) 'ú', (int) 'u');
            map.put((int) 'ů', (int) 'u');
            map.put((int) 'ü', (int) 'u');
            map.put((int) 'ď', (int) 'd');
    }
    /**
     * The stringNormalize function takes a string and returns a normalized version of it.
     * The normalization process is as follows:
     * 1) All characters are converted to lowercase.
     * 2) All non-alphanumeric characters are removed, except for spaces (which become underscores).
     * 3) Any character that does not have an ASCII code between 0x61 and 0x7A or between 0x30 and 0x39 is replaced by its Unicode decomposition mapping, if one exists; otherwise the character is removed. For example, the Euro symbol € becomes &quot;euro&quot; (U+20AC -&gt; U+0065 U
     *
     * @param toNormalize Pass in the string that needs to be normalized
     *
     * @return A string with all the characters that are not letters or digits removed, and all the remaining letters converted to lowercase
     */
    public static String stringNormalize(String toNormalize) {
        sb.setLength(0);

        int c;
        Integer mapped;
        for (int i = 0; i < toNormalize.length(); i++) {
            c = toNormalize.charAt(i);
            if (c == 32 || !Character.isLetterOrDigit(c))
                continue;
            c = Character.toLowerCase(c);
            if ((c >= 0x61 && c <= 0x7A) || (c >= 0x30 && c <= 0x39) )
                sb.appendCodePoint(c);
            else {
                mapped = map.get(c);
                if (mapped == null) continue;
                sb.appendCodePoint(mapped);
            }
        }
        return sb.toString();
    }
}
