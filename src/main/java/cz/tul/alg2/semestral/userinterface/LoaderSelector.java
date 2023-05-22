package cz.tul.alg2.semestral.userinterface;

import cz.tul.alg2.semestral.file.BinaryLoader;
import cz.tul.alg2.semestral.file.GTFSLoader;
import cz.tul.alg2.semestral.file.ILoader;
import cz.tul.alg2.semestral.file.TextLoader;
import cz.tul.alg2.semestral.utilities.PathBuilder;
import cz.tul.alg2.semestral.utilities.TextNormalization;

import java.io.File;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
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
    public static ILoader getLoaderMethod(boolean possibleReturn) {
        Scanner sc = new Scanner(System.in);
        String str;

        while (true) {
            System.out.println(
                    """
                    Zadejte cestu k souboru, ze kterého chcete načíst MHD.
                     - "*.ser" pro binární soubory
                     - "*.txt" pro textové soubory
                     - "*.zip" pro GTFS formát.""");
            if (possibleReturn) System.out.println("Pro opuštění menu, napište ZPĚT");

            suggestActualFiles();
            System.out.print("> ");
            str = sc.nextLine();

            if (TextNormalization.stringNormalize(str).equals("zpet") && possibleReturn) return null;

            // exists?
            if (str.matches("^(.+/)*.+\\.(ser|txt|zip)$")) {
                if (!new File(str).exists()) {
                    System.out.println(ConsoleColors.RED_BOLD + "Soubor na takovéto cestě neexistuje.  Formát: cesta/k/souboru" + ConsoleColors.RESET);
                    continue;
                }
            } else {
                System.out.println(ConsoleColors.RED_BOLD + "Neplatný formát. Zadejte znovu. Formát: povolené koncovky: .ser, .txt a .zip" + ConsoleColors.RESET);
                continue;
            }
            ILoader loader;
            if (str.matches("^(.+/)*.+\\.ser$")) {
                loader = new BinaryLoader();
            } else if (str.matches("^(.+/)*.+\\.txt$")) {
                loader = new TextLoader();
            } else {
                loader = new GTFSLoader();
            }

            if (loader.loadFile(
                    PathBuilder.joinPath(
                        str.split("/")
                    )
                )
            ) return loader;
            System.out.println(ConsoleColors.RED_BOLD + "Nepodařilo se načíst soubor, je pravděpodobně porouchaný. Zkuste jiný soubor." + ConsoleColors.RESET);
        }
    }

    /**
     * The suggestActualFiles function is a static function that prints out the three possible test files
     * that can be used to run the program.
     */
    public static void suggestActualFiles() {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime lastEdited;
        Duration age;

        System.out.println("--------------------------------------------------------------------------");
        System.out.println("| 3 možné testovací varianty:                                            |");
        File file = new File(PathBuilder.joinPath("data", "pid.txt"));
        if (file.exists()) {
            lastEdited = Instant.ofEpochMilli(file.lastModified()).atZone(ZoneId.systemDefault()).toLocalDateTime();
            age = Duration.between(lastEdited, now);
            System.out.printf("|   \"data/pid.txt\"        (rychlé)  Naposledy upraveno: %2d dní, %02d:%02d:%02d |%n", age.toDays(), age.toHoursPart(), age.toMinutesPart(), age.toSecondsPart());
        }

        file = new File(PathBuilder.joinPath("data", "pid-broken.txt"));
        if (file.exists()) {
            lastEdited = Instant.ofEpochMilli(file.lastModified()).atZone(ZoneId.systemDefault()).toLocalDateTime();
            age = Duration.between(lastEdited, now);
            System.out.printf("|   \"data/pid-broken.txt\" (rychlé)  Naposledy upraveno: %2d dní, %02d:%02d:%02d |%n", age.toDays(), age.toHoursPart(), age.toMinutesPart(), age.toSecondsPart());
        }

        file = new File(PathBuilder.joinPath("data", "pid.ser"));
        if (file.exists()) {
            lastEdited = Instant.ofEpochMilli(file.lastModified()).atZone(ZoneId.systemDefault()).toLocalDateTime();
            age = Duration.between(lastEdited, now);
            System.out.printf("|   \"data/pid.ser\"        (rychlé)  Naposledy upraveno: %2d dní, %02d:%02d:%02d |%n", age.toDays(), age.toHoursPart(), age.toMinutesPart(), age.toSecondsPart());
        }

        file = new File(PathBuilder.joinPath("data", "pid-broken.ser"));
        if (file.exists()) {
            lastEdited = Instant.ofEpochMilli(file.lastModified()).atZone(ZoneId.systemDefault()).toLocalDateTime();
            age = Duration.between(lastEdited, now);
            System.out.printf("|   \"data/pid-broken.ser\" (rychlé)  Naposledy upraveno: %2d dní, %02d:%02d:%02d |%n", age.toDays(), age.toHoursPart(), age.toMinutesPart(), age.toSecondsPart());
        }

        file = new File(PathBuilder.joinPath("data", "PID_GTFS.zip"));
        if (file.exists()) {
            lastEdited = Instant.ofEpochMilli(file.lastModified()).atZone(ZoneId.systemDefault()).toLocalDateTime();
            age = Duration.between(lastEdited, now);
            System.out.printf("|   \"data/PID_GTFS.zip\"   (pomalé)  Naposledy upraveno: %2d dní, %02d:%02d:%02d |%n", age.toDays(), age.toHoursPart(), age.toMinutesPart(), age.toSecondsPart());
        }
        System.out.println("--------------------------------------------------------------------------");

    }
}
