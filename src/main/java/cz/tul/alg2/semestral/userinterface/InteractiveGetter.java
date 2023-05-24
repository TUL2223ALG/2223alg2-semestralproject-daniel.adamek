package cz.tul.alg2.semestral.userinterface;

import cz.tul.alg2.semestral.patternmatcher.HirschbergMatching;
import cz.tul.alg2.semestral.transportation.CityTransport;
import cz.tul.alg2.semestral.transportation.Line;
import cz.tul.alg2.semestral.transportation.Station;
import cz.tul.alg2.semestral.utilities.Pair;
import cz.tul.alg2.semestral.utilities.TextNormalization;

import java.io.File;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

/**
 * The type Interactive getter.
 */
public class InteractiveGetter {
    /**
     * The Transport.
     */
    private final CityTransport transport;
    /**
     * The Sc.
     */
    private final Scanner sc;

    /**
     * The StationGetter function is used to get the station name from a given line and stop number.
     *
     * @param transport Pass the citytransport object to the stationgetter class
     * @param sc        the sc
     */
    public InteractiveGetter(CityTransport transport, Scanner sc) {
        this.transport = transport;
        this.sc = sc;
    }

    /**
     * The getStation function is used to get a station from the user.
     * It first checks if the input string matches any of the stations in our database, and if it does, returns that station.
     * If not, it uses Hirschberg's algorithm to find similar strings and suggests them to the user.
     *
     * @return The station that the user wants to find
     */
    public Station getStation() {
        String str;
        List<Pair<String, Double>> pq = new ArrayList<>();
        double similarity;
        double SUGGEST_LEN = 4;
        int subStringCounter;
        while (true) {
            pq.clear();
            str = "";
            subStringCounter = 0;
            System.out.println("Zadejte jméno stanice (Pro opuštění napište ZPET)");
            System.out.print("> ");
            while (str.length() == 0) {
                str = TextNormalization.stringNormalize(sc.nextLine());
                if (str.length() == 0) {
                    System.out.print(ConsoleColors.RED_BOLD + "Neplatný vstup. Zadejte prosím znovu.\n> " + ConsoleColors.RESET);
                }
            }
            // Detect stop
            if (TextNormalization.stringNormalize(str).equals("zpet")) return null;

            // Found exactly
            if (transport.stations().containsKey(str)) {
                System.out.println("Nalezeno!");
                return transport.stations().get(str);
            }

            // Find common parts and probability
            for (String name: transport.stations().keySet()) {
                // Similarity weight
                similarity = HirschbergMatching.similarity(str, name);
                // Substring detection
                if (name.contains(str)) {
                    similarity += (double) str.length() / name.length();
                    if (similarity >= 1) similarity = 0.95;
                    subStringCounter++;
                }
                pq.add(new Pair<>(name, similarity));
            }
            pq.sort(Comparator.comparing(a -> a.second));

            // If there is only one with >0.95 similarity -> its typo
            if (subStringCounter == 1) {
                System.out.println("Domysleli jsme si: " + transport.stations().get(pq.get(pq.size()-1).first).getPrettyName());
                return transport.stations().get(pq.get(pq.size()-1).first);
            }

            // Not found, print possible answers
            System.out.print("Jejda, nic takového jsme nenašli\nNeměli jste na mysli: ");
            for (int i = 0; i < SUGGEST_LEN; i++) {
                System.out.printf("%s",
                        transport.stations()
                            .get(pq.get(pq.size()-1 - i).first)
                            .getPrettyName()
                );
                if (i < SUGGEST_LEN-1) System.out.print(", ");
            }
            System.out.println("?");

        }
    }

    /**
     * The getLine function is used to get a line from the user.
     * It first checks if the input is an existing line, and returns it if so.
     * If not, it uses HirschbergMatching to find similar lines and suggests them to the user.
     *
     * @return A line with the given name
     */
    public Line getLine() {
        String str;
        List<Pair<String, Double>> pq = new ArrayList<>();
        double similarity;
        double SUGGEST_LEN = 4;
        int subStringCounter;
        while (true) {
            pq.clear();
            str = "";
            subStringCounter = 0;
            System.out.println("Zadejte linku (Pro opuštění napište ZPET)");
            System.out.print("> ");
            while (str.length() == 0) {
                str = TextNormalization.stringNormalize(sc.nextLine());
                if (str.length() == 0) {
                    System.out.print(ConsoleColors.RED_BOLD + "Neplatný vstup. Zadejte prosím znovu.\n> " + ConsoleColors.RESET);
                }
            }
            // Detect stop
            if (TextNormalization.stringNormalize(str).equals("zpet")) return null;

            // Found exactly
            if (transport.lines().containsKey(str)) {
                System.out.println("Nalezeno!");
                return transport.lines().get(str);
            }

            // Find common parts and probability
            for (String name: transport.lines().keySet()) {
                // Similarity weight
                similarity = HirschbergMatching.similarity(str, name);
                // Substring detection
                if (name.contains(str)) {
                    similarity += (double) str.length() / name.length();
                    if (similarity >= 1) similarity = 0.95;
                    subStringCounter++;
                }
                pq.add(new Pair<>(name, similarity));
            }
            pq.sort(Comparator.comparing(a -> a.second));

            // If there is only one with >0.95 similarity -> its typo
            if (subStringCounter == 1) {
                System.out.println("Domysleli jsme si: " + transport.lines().get(pq.get(pq.size()-1).first).getName());
                return transport.lines().get(pq.get(pq.size()-1).first);
            }

            // Not found, print possible answers
            System.out.print("Jejda, nic takového jsme nenašli\nNeměli jste na mysli: ");
            for (int i = 0; i < SUGGEST_LEN; i++) {
                System.out.printf("%s",
                        transport.lines().get(pq.get(pq.size()-1 - i).first).getPrettyName()
                );
                if (i < SUGGEST_LEN-1) System.out.print(", ");
            }
            System.out.println("?");

        }
    }

    /**
     * Prompts the user to enter a path for a new file.
     * The method will continue to ask for a path until a valid one is entered, or until the user enters "ZPET" to cancel.
     * The method checks if a file already exists at the given path and asks for re-entry if it does.
     *
     * @param checkFileExists the check file exists
     * @return A File object representing the new file at the entered path.         Returns null if the user chooses to cancel by entering "ZPET".
     */
    public File getFile(boolean checkFileExists) {
        File file;
        String str;
        while (true) {
            str = "";
            System.out.println("Zadejte cestu k souboru. Zadejte ZPET pro zrušení volby.");
            System.out.print("> ");
            while (str.length() == 0) {
                str = sc.nextLine();
                if (str.length() == 0) System.out.print(ConsoleColors.RED_BOLD + "Neplatný vstup. Zadejte prosím znovu.\n> " + ConsoleColors.RESET);
            }
            // Detect stop
            if (TextNormalization.stringNormalize(str).equals("zpet")) return null;

            // Create file object and check, if file exist
            file = new File(str);
            if (checkFileExists && file.exists()) {
                System.out.println(ConsoleColors.RED_BOLD + "Na této cestě již soubor existuje. Zadejte jinou cestu." + ConsoleColors.RESET);
                continue;
            }
            return file;
        }
    }
}
