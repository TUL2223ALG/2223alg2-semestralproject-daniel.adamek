package cz.tul.alg2.semestral.utilities;

public class TextNormalization {
    public static String stringNormalize(String toNormalize) {
        return toNormalize
                .toLowerCase()
                .replaceAll("[^ěščřžýáíéťňóúůa-zA-Z0-9]", "")
                .replace('é', 'e')
                .replace('í', 'i')
                .replace('á', 'a')
                .replace('ý', 'y')
                .replace('ž', 'z')
                .replace('ř', 'r')
                .replace('č', 'c')
                .replace('š', 's')
                .replace('ě', 'e')
                .replace('ť', 't')
                .replace('ň', 'n')
                .replaceAll("[ůú]", "u")
                .replace('ó', 'o')
                ; }
}
