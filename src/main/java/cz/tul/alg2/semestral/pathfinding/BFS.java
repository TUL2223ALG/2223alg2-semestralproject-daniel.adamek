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
    public List<Station> findPath(String from, String to) {
        Station fromStation = allStations.get(TextNormalization.stringNormalize(from));
        Station toStation = allStations.get(TextNormalization.stringNormalize(to));

        Queue<Station> queue = new LinkedList<>();
        Set<Station> visited = new HashSet<>();
        HashMap<Station, Station> parents = new HashMap<>();

        queue.add(fromStation);
        visited.add(fromStation);

        while (!queue.isEmpty()) {
            Station current = queue.remove();

            if (current.equals(toStation)) {
                // Reconstruct path from start to end
                List<Station> path = new ArrayList<>();
                Station station = toStation;
                while (!station.equals(fromStation)) {
                    path.add(station);
                    station = parents.get(station);
                }
                path.add(fromStation);
                Collections.reverse(path);
                return path;
            }

            for (Pair<Station, Transport> neighbour : current.getNeighbours()) {
                Station neighbourStation = neighbour.first;
                if (!visited.contains(neighbourStation)) {
                    visited.add(neighbourStation);
                    parents.put(neighbourStation, current);
                    queue.add(neighbourStation);
                }
            }
        }

        // No path found
        return null;

    }
}
