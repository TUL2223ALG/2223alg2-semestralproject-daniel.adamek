package cz.tul.alg2.semestral.pathfinding;

import cz.tul.alg2.semestral.transportation.CityTransport;
import cz.tul.alg2.semestral.transportation.Line;
import cz.tul.alg2.semestral.transportation.Station;
import cz.tul.alg2.semestral.utilities.Pair;
import cz.tul.alg2.semestral.utilities.TextNormalization;

import java.util.*;

public class BFS implements PathFinder{
    private final CityTransport transport;

    public BFS(CityTransport transport) {
        this.transport = transport;
    }
    //@Override
    public List<PathSegment> findShortestPath(String start, String end) {
        Map<String, Pair<Station, Integer>> visited = new HashMap<>();
        Queue<Station> queue = new LinkedList<>();
        start = TextNormalization.stringNormalize(start);
        end = TextNormalization.stringNormalize(end);

        if (start.equals(end))
            return new LinkedList<>();

        Station startStation = transport.stations().get(start);
        visited.put(startStation.getName(), new Pair<>(null, 0));
        queue.add(startStation);

        while (!queue.isEmpty()) {
            Station current = queue.poll();
            String currentName = current.getName();

            if (currentName.equals(end)) {
                return buildPath(visited, startStation, current);
            }

            for (Pair<Station, Integer> neighbour : current.getNeighbours()) {
                Station neighbourStation = neighbour.first;
                String neighbourName = neighbourStation.getName();

                if (!visited.containsKey(neighbourName)) {
                    visited.put(neighbourName, new Pair<>(current, neighbour.second));
                    queue.add(neighbourStation);
                }
            }
        }

        return null; // Path not found
    }

private List<PathSegment> buildPath(Map<String, Pair<Station, Integer>> visited, Station startStation, Station endStation) {
    LinkedList<Pair<Station, Integer>> fullPath = new LinkedList<>();
    Station current = endStation;

    while (current != null && !current.equals(startStation)) {
        Pair<Station, Integer> pathInfo = visited.get(current.getName());
        fullPath.addFirst(new Pair<>(current, pathInfo.second));
        current = pathInfo.first;
    }

    if (current != null) {
        fullPath.addFirst(new Pair<>(startStation, 0));
    }

    List<PathSegment> pathSegments = new ArrayList<>();
    Set<Line> currentLines = null;
    List<Pair<Station, Integer>> segmentStations = new ArrayList<>();

    for (int i = 0; i < fullPath.size() - 1; i++) {
        Pair<Station, Integer> currentPair = fullPath.get(i);
        Station currentStation = currentPair.first;
        Set<Line> nextLines = findCommonLines(currentStation, fullPath.get(i + 1).first);

        if (currentLines == null && nextLines != null && !nextLines.isEmpty()) {
            currentLines = nextLines;
        }

        if (nextLines != null && !nextLines.isEmpty()) {
            segmentStations.add(currentPair);

            if (!currentLines.equals(nextLines)) {
                pathSegments.add(new PathSegment(currentLines, segmentStations));
                segmentStations = new ArrayList<>();
                currentLines = nextLines;
            }
        }
    }

    if (!fullPath.isEmpty() && currentLines != null && !currentLines.isEmpty()) {
        segmentStations.add(fullPath.getLast());
        pathSegments.add(new PathSegment(currentLines, segmentStations));
    }

    return pathSegments;
}

    private Set<Line> findCommonLines(Station station1, Station station2) {
        Set<Line> commonLines = new HashSet<>(station1.getLines());
        commonLines.retainAll(station2.getLines());
        return (commonLines.isEmpty()) ? null : commonLines;
    }

    private Line findCommonLine(Station station1, Station station2) {
        Set<Line> commonLines = new HashSet<>(station1.getLines());
        commonLines.retainAll(station2.getLines());
        if (!commonLines.isEmpty()) {
            return commonLines.iterator().next();
        }
        return null; // No common line found, should not happen in a correct input
    }

}
