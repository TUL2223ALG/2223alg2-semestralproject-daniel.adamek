package cz.tul.alg2.semestral.userinterface;

import cz.tul.alg2.semestral.patternmatcher.HirschbergMatching;
import cz.tul.alg2.semestral.transportation.CityTransport;
import cz.tul.alg2.semestral.transportation.Station;
import cz.tul.alg2.semestral.utilities.Pair;
import cz.tul.alg2.semestral.utilities.TextNormalization;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

public class StationGetter {
    private final CityTransport transport;

    /**
     * The StationGetter function is used to get the station name from a given line and stop number.
     *
     * @param transport Pass the citytransport object to the stationgetter class
     */
    public StationGetter(CityTransport transport) {
        this.transport = transport;
    }

    /**
     * The getStation function is used to get a station from the user.
     * It first checks if the input string matches any of the stations in our database, and if it does, returns that station.
     * If not, it uses Hirschberg's algorithm to find similar strings and suggests them to the user.
     *
     * @return The station that the user wants to find
     *
     */
    public Station getStation() {
        Scanner sc = new Scanner(System.in);
        String str;
        List<Pair<String, Double>> pq = new ArrayList<>();
        double similarity;
        double SUGGEST_LEN = 4;
        int subStringCounter;
        while (true) {
            pq.clear();
            str = "";
            subStringCounter = 0;
            System.out.print("> ");
            while (str.length() == 0) {
                str = TextNormalization.stringNormalize(sc.nextLine());
                if (str.length() == 0) {
                    System.out.print("Neplatný vstup. Zadejte prosím znovu.\n> ");
                }
            }
            // Detect stop
            if (str.equals("exit")) return null;

            if (transport.stations().containsKey(str)) {
                System.out.println("Nalezeno!");
                return transport.stations().get(str);
            }

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

            if (subStringCounter == 1) {
                System.out.println("Domysleli jsme si: " + transport.stations().get(pq.get(pq.size()-1).first).getPrettyName());
                return transport.stations().get(pq.get(pq.size()-1).first);
            }

            System.out.print("Jejda, nic takového jsme nenašli\nNeměli jste na mysli: ");
            for (int i = 0; i < SUGGEST_LEN; i++) {
                System.out.printf("%s [%.2f]%%",
                        transport.stations().get(pq.get(pq.size()-1 - i).first).getPrettyName(),
                        pq.get(pq.size()-1 - i).second
                );
                if (i < SUGGEST_LEN-1) System.out.print(", ");
            }
            System.out.println("?");

        }
    }
}
