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
            map.put((int) 'ú', (int) 'u');
            map.put((int) 'ů', (int) 'u');
            map.put((int) 'ď', (int) 'd');
    }
    public static String stringNormalize(String toNormalize) {
        sb.setLength(0);

        int c, mapped;
        for (int i = 0; i < toNormalize.length(); i++) {
            c = toNormalize.charAt(i);
            if (c == 32 || !Character.isLetterOrDigit(c))
                continue;
            c = Character.toLowerCase(c);
            if ((c >= 0x61 && c <= 0x7A) || (c >= 0x30 && c <= 0x39) )
                sb.appendCodePoint(c);
            else {
                mapped = map.get(c);
                sb.appendCodePoint(mapped);
            }
        }
        return sb.toString();
    }
}
