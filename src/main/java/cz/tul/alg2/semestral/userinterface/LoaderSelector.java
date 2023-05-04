package cz.tul.alg2.semestral.userinterface;

import cz.tul.alg2.semestral.file.BinaryLoader;
import cz.tul.alg2.semestral.file.GTFSLoader;
import cz.tul.alg2.semestral.file.ILoader;
import cz.tul.alg2.semestral.file.TextLoader;

import java.io.File;
import java.util.Scanner;

public class LoaderSelector {
    public static ILoader getLoaderMethod() {
        Scanner sc = new Scanner(System.in);
        String str;

        System.out.println("Zadejte cestu k souboru, ze kterého chcete načíst MHD: \n - \"*.ser\" pro binární soubory\n - \"*.txt\" pro textové soubory\n - \"*.zip\" pro GTFS formát.");
        System.out.println("------------------------------------\n" +
                           "| 3 možné testovací varianty:      |\n" +
                           "|   \"data/pid.txt\"       (rychlé)  |\n" +
                           "|   \"data/pid.ser\"       (rychlé)  |\n" +
                           "|   \"data/PID_GTFS.zip\"  (pomalé)  |\n" +
                            "------------------------------------");
        while (true) {
            System.out.print("> ");
            str = sc.nextLine();
            // exists?
            if (str.matches("^(.+/)*.+\\.(ser|txt|zip)$")) {
                if (! new File(str).exists()) {
                    System.out.println("Soubor na takovéto cestě neexistuje.  Formát: cesta/k/souboru");
                    continue;
                }
            } else {
                System.out.println("Neplatný formát. Zadejte znovu. Formát: povolené koncovky: .ser, .txt a .zip");
                continue;
            }

            if (str.matches("^(.+/)*.+\\.ser$")) {
                BinaryLoader bl = new BinaryLoader();
                if (bl.loadFile(str)) return bl;
            }
            else if (str.matches("^(.+/)*.+\\.txt$")) {
                TextLoader tl = new TextLoader();
                if (tl.loadFile(str)) return tl;
            }
            else if (str.matches("^(.+/)*.+\\.zip$")) {
                GTFSLoader gtfsl = new GTFSLoader();
                if (gtfsl.loadFile(str)) return gtfsl;
            } else {
                System.out.println("Neplatný formát. Zadejte znovu. Formát: povolené koncovky: .ser, .txt a .zip");
                continue;
            }
            System.out.println("Soubor se nepodačilo načíst. Zadejte správnou cestu.");
        }
    }
}
