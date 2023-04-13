package cz.tul.alg2.semestral.utilities;

import org.junit.jupiter.api.Test;

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
        b = "nechtjizhrisnesaxofonyablurozezvucisinudesnymitonywaltzutangaaquickstepu";
        assertEquals(TextNormalization.stringNormalize(a), b);
    }
}