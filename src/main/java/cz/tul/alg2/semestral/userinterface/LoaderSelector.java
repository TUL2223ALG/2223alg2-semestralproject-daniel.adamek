package cz.tul.alg2.semestral.userinterface;

import cz.tul.alg2.semestral.file.BinaryLoader;
import cz.tul.alg2.semestral.file.GTFSLoader;
import cz.tul.alg2.semestral.file.ILoader;
import cz.tul.alg2.semestral.file.TextLoader;
import cz.tul.alg2.semestral.utilities.PathBuilder;

import java.io.File;
import java.time.*;
import java.util.Scanner;

public class LoaderSelector {
    /**
     * The getLoaderMethod function is a static method that returns an ILoader object.
     * It asks the user for a path to the file they want to load, and then it tries
     * to load it using one of three different methods: BinaryLoader, TextLoader or GTFSLoader.
     * If any of these methods fail (i.e., if the file cannot be loaded), then getLoaderMethod() will ask again for another path until either a valid path is given or Ctrl+C is pressed by the user.
     *
     * @return A loader object
     */
    public static ILoader getLoaderMethod() {
        Scanner sc = new Scanner(System.in);
        String str;

        LocalDateTime now = LocalDateTime.now(), lastEdited;
        Duration age;
        System.out.println("Zadejte cestu k souboru, ze kterého chcete načíst MHD: \n - \"*.ser\" pro binární soubory\n - \"*.txt\" pro textové soubory\n - \"*.zip\" pro GTFS formát.");
        System.out.println("------------------------------------------------------------------------");
        System.out.println("| 3 možné testovací varianty:                                          |");

        File file = new File(PathBuilder.joinPath("data", "pid.txt"));
        if (file.exists()) {
            lastEdited = Instant.ofEpochMilli(file.lastModified()).atZone(ZoneId.systemDefault()).toLocalDateTime();
            age = Duration.between(lastEdited, now);
            System.out.printf("|   \"data/pid.txt\"       (rychlé)  Naposledy upraveno: %d dní, %02d:%02d:%02d |\n", age.toDays(), age.toHoursPart(), age.toMinutesPart(), age.toSecondsPart());
        }

        file = new File(PathBuilder.joinPath("data", "pid.txt"));
        if (file.exists()) {
            lastEdited = Instant.ofEpochMilli(file.lastModified()).atZone(ZoneId.systemDefault()).toLocalDateTime();
            age = Duration.between(lastEdited, now);
            System.out.printf("|   \"data/pid.ser\"       (rychlé)  Naposledy upraveno: %d dní, %02d:%02d:%02d |\n", age.toDays(), age.toHoursPart(), age.toMinutesPart(), age.toSecondsPart());
        }

        file = new File(PathBuilder.joinPath("data", "pid.txt"));
        if (file.exists()) {
            lastEdited = Instant.ofEpochMilli(file.lastModified()).atZone(ZoneId.systemDefault()).toLocalDateTime();
            age = Duration.between(lastEdited, now);
            System.out.printf("|   \"data/PID_GTFS.zip\"  (pomalé)  Naposledy upraveno: %d dní, %02d:%02d:%02d |\n", age.toDays(), age.toHoursPart(), age.toMinutesPart(), age.toSecondsPart());
        }
        System.out.println("------------------------------------------------------------------------");
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
            ILoader loader = null;
            if (str.matches("^(.+/)*.+\\.ser$")) {
                loader = new BinaryLoader();
            }
            else if (str.matches("^(.+/)*.+\\.txt$")) {
                loader = new TextLoader();
            }
            else if (str.matches("^(.+/)*.+\\.zip$")) {
                loader = new GTFSLoader();
            } else {
                System.out.println("Neplatný formát. Zadejte znovu. Formát: povolené koncovky: .ser, .txt a .zip");
                continue;
            }

            if (loader.loadFile(
                    PathBuilder.joinPath(
                        str.split("/")
                    )
                )
            ) return loader;
        }
    }
}
