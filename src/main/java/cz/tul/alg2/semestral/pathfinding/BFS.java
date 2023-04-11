package cz.tul.alg2.semestral.pathfinding;

import cz.tul.alg2.semestral.file.RouteLoader;
import cz.tul.alg2.semestral.transportation.Station;
import cz.tul.alg2.semestral.transportation.Transport;
import cz.tul.alg2.semestral.utilities.Pair;
import cz.tul.alg2.semestral.utilities.TextNormalization;

import java.util.*;

public class BFS {
    private final RouteLoader rl;
    private HashMap<String, Station> allStations = new HashMap<>();
    HashMap<Station, Station> cache = new HashMap<>();

    public BFS(RouteLoader rl) {
        this.rl = rl;
        this.allStations = rl.getAllStations();
    }
    public List<Pair<Station, Integer>> bfs(String startName, String endName) {
        Station start = allStations.get(TextNormalization.stringNormalize(startName));
        Station end = allStations.get(TextNormalization.stringNormalize(endName));

        Map<Station, Integer> distances = new HashMap<>();
        Map<Station, Station> previous = new HashMap<>();
        Map<Station, Transport> transport = new HashMap<>();
        Queue<Station> queue = new LinkedList<>();

        // nastavíme počáteční vzdálenost a přidáme startovní stanici do fronty
        distances.put(start, 0);
        queue.add(start);

        while (!queue.isEmpty()) {
            Station current = queue.poll();
            for (Pair<Station, Transport> neighbor : current.getNeighbours()) {
                Station neighborStation = neighbor.first;
                Transport neighborTransport = neighbor.second;

                int transferTime = transport.getOrDefault(current, neighborTransport).getTransferTime();
                int distance = distances.get(current) + transferTime;

                // přidáme sousední stanici do fronty, pokud ještě nebyla zpracována
                if (!distances.containsKey(neighborStation)) {
                    distances.put(neighborStation, distance);
                    transport.put(neighborStation, neighborTransport);
                    previous.put(neighborStation, current);
                    queue.add(neighborStation);
                } else if (distance < distances.get(neighborStation) ||
                        (distance == distances.get(neighborStation) &&
                                neighborTransport.getTransferTime() < transport.get(neighborStation).getTransferTime())) {
                    // aktualizujeme nejkratší cestu, pokud nová cesta je kratší nebo má nižší celkový čas přestupu
                    distances.put(neighborStation, distance);
                    transport.put(neighborStation, neighborTransport);
                    previous.put(neighborStation, current);
                }
            }
        }

        // rekonstrukce nejkratší cesty
        List<Pair<Station, Integer>> path = new ArrayList<>();
        Station current = end;
        while (previous.containsKey(current)) {
            Station previousStation = previous.get(current);
            path.add(new Pair<>(current, distances.get(current) - distances.get(previousStation)));
            current = previousStation;
        }
        path.add(new Pair<>(start, distances.get(start)));

        Collections.reverse(path);
        return path;
    }
}
