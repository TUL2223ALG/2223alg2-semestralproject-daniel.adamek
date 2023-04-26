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
    private CityTransport transport;
    public StationGetter(CityTransport transport) {
        this.transport = transport;
    }

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
                System.out.print(
                        transport.stations().get(pq.get(pq.size()-1 - i).first).getPrettyName() +
                        "[" +
                        pq.get(pq.size()-1 - i).second +
                        "]"
                );
                if (i < SUGGEST_LEN-1) System.out.print(", ");
            }
            System.out.println("?");

        }
    }
}
