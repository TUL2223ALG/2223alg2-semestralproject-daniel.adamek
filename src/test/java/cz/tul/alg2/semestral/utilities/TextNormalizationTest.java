package cz.tul.alg2.semestral.utilities;

import org.junit.jupiter.api.Test;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TextNormalizationTest {

    @Test
    void stringNormalizeLettersAndSpaces() {
        // Big-Small letters
        String a = "AhOj";
        String b = "ahoj";
        assertEquals(TextNormalization.stringNormalize(a), b);

        // Spaces
        a = "mama mele maso";
        b = "mamamelemaso";
        assertEquals(TextNormalization.stringNormalize(a), b);

        // Both
        a = "LOREM IPSum doloR sit ameT cOnsectETUer adipisCIng ELit";
        b = "loremipsumdolorsitametconsectetueradipiscingelit";
        assertEquals(TextNormalization.stringNormalize(a), b);
    }

    @Test
    void stringNormalizeWithNumbers() {
        // numbers
        String a = "art1f1c1al 1ntell1gence";
        String b = "art1f1c1al1ntell1gence";
        assertEquals(TextNormalization.stringNormalize(a), b);
    }

    @Test
    void stringNormalizeWithSpecialCharacters() {
        // basic special characters
        String a = "*+C#kpp#F!++AuOdh=Zq";
        String b = "ckppfauodhzq";
        assertEquals(TextNormalization.stringNormalize(a), b);

        // specific czech characters
        a = "Nechť již hříšné saxofony ďáblů rozezvučí síň úděsnými tóny waltzu, tanga a quickstepu.";
        b = "nechtjizhrisnesaxofonydablurozezvucisinudesnymitonywaltzutangaaquickstepu";
        assertEquals(TextNormalization.stringNormalize(a), b);
    }


    @Test
    void testStringNormalizePerformance() {
        StringBuilder sb = new StringBuilder();
        Random random = new Random();
        String alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789ĚŠŘŽÝÁÍÉÚŤŇÓěščřžýáíéúůťňóěď!@#$%^&*{}=´\\¯[]$'<>\",.-?:_*|@()€¶←↓→~`°#^";
        for (int i = 0; i < 100_000_000; i++) {
            sb.append(alphabet.charAt(random.nextInt(alphabet.length())));
        }
        String input = sb.toString();
        String newString;
        long startTime = System.nanoTime();
        newString = TextNormalization.stringNormalize(input);
        long endTime = System.nanoTime();

        double elapsedTime = (endTime - startTime) / 1_000_000_000.0;
        System.out.printf("Elapsed time: %.3f seconds%n", elapsedTime);

        StringBuilder expected = new StringBuilder(input.length());

        startTime = System.nanoTime();
        for (char c : input.toLowerCase().toCharArray()) {
            switch (c) {
                case 'ě', 'é' -> expected.append('e');
                case 'š' -> expected.append('s');
                case 'č' -> expected.append('c');
                case 'ř' -> expected.append('r');
                case 'ž' -> expected.append('z');
                case 'ý' -> expected.append('y');
                case 'á' -> expected.append('a');
                case 'í' -> expected.append('i');
                case 'ť' -> expected.append('t');
                case 'ň' -> expected.append('n');
                case 'ó', 'ö' -> expected.append('o');
                case 'ú', 'ů', 'ü' -> expected.append('u');
                case 'ď' -> expected.append('d');
                default -> {
                    if (Character.isLetterOrDigit(c)) {
                        expected.append(c);
                    }
                }
            }
        }
        endTime = System.nanoTime();

        elapsedTime = (endTime - startTime) / 1_000_000_000.0;
        System.out.printf("Old method elapsed time: %.3f seconds%n", elapsedTime);

        assertEquals(expected.toString(), newString);
    }

}