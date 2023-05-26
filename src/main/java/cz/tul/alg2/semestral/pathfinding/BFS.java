package cz.tul.alg2.semestral.pathfinding;

import cz.tul.alg2.semestral.errorhandle.ErrorLogger;
import cz.tul.alg2.semestral.transportation.CityTransport;
import cz.tul.alg2.semestral.transportation.Line;
import cz.tul.alg2.semestral.transportation.Station;
import cz.tul.alg2.semestral.utilities.Pair;
import cz.tul.alg2.semestral.utilities.TextNormalization;

import java.util.*;

/**
 * The type Bfs.
 */
public class BFS implements PathFinder{
    /**
     * The Transport.
     */
    private final CityTransport transport;

    /**
     * The BFS function takes in a starting city and an ending city, and returns the shortest path between them.
     *
     * @param transport Pass the citytransport object to the bfs class
     */
    public BFS(CityTransport transport) {
        this.transport = transport;
    }

    /**
     * The findShortestPath function finds the shortest path between two stations.
     *
     * @param start Start station in the transport as string
     * @param end   End station in the transport as string
     * @return A list of pathsegment objects that represent the shortest path between two stations
     */
    public List<PathSegment> findShortestPath(String start, String end) {
            start = TextNormalization.stringNormalize(start);
            end = TextNormalization.stringNormalize(end);
            if (start.equals(end))
                return new LinkedList<>();

            Station startStation;
            Station endStation;
            startStation = this.transport.stations().get(start);
            endStation = this.transport.stations().get(start);

            if (startStation == null || endStation == null) {
                new ErrorLogger("error.log").logError("Invalid start/end station, cannot find path then.", new IllegalArgumentException());
                return null;
            }

            return findShortestPath(startStation, endStation);
    }

    /**
     * The findShortestPath function finds the shortest path between two stations.
     *
     * @param start Start station in the transport
     * @param end   End station in the transport
     * @return A list of pathsegment objects that represent the shortest path between two stations
     */
    public List<PathSegment> findShortestPath(Station start, Station end) {
        // Initialize a map of visited stations, and a queue for the BFS traversal
        Map<String, Pair<Station, Integer>> visited = new HashMap<>();
        Queue<Station> queue = new LinkedList<>();

        // If the start and end stations are the same, return an empty path
        if (start.equals(end))
            return new LinkedList<>();

        // Mark the start station as visited and enqueue it
        visited.put(start.getName(), new Pair<>(null, 0));
        queue.add(start);

        // While there are still stations to visit
        while (!queue.isEmpty()) {
            // Take the next station from the queue
            Station current = queue.poll();

            // If the current station is the destination, we have found a path
            if (current.equals(end)) {
                // Build and return the path from start to end
                return buildPath(visited, start, current);
            }

            // If the current station is not the destination, visit its neighbours
            for (Pair<Station, Integer> neighbour : current.getNeighbours()) {
                Station neighbourStation = neighbour.first;
                String neighbourName = neighbourStation.getName();

                // If the neighbour has not been visited, mark it as visited and enqueue it
                if (!visited.containsKey(neighbourName)) {
                    visited.put(neighbourName, new Pair<>(current, neighbour.second));
                    queue.add(neighbourStation);
                }
            }
        }

        // If no path was found, return null
        return null;
    }

    /**
     * The buildPath function takes in a map of visited stations, the start station, and the end station.
     * It then creates a linked list of pairs that contains all the stations on our path and their distances from
     * each other. Then it iterates through this list to create PathSegments which contain information about what lines
     * are used for each segment as well as what stations are included in that segment. This is done by checking if there
     * is any common line between two adjacent segments (if not we know we have to change lines). If there is no common line
     * between two adjacent segments then we add all previous
     *
     * @param visited      Store the visited stations and their parent station
     * @param startStation Determine the starting point of the path
     * @param endStation   Get the station name
     * @return A list of pathsegments
     */
    private List<PathSegment> buildPath(Map<String, Pair<Station, Integer>> visited, Station startStation, Station endStation) {
        // Create a LinkedList to store the path from the end station to the start station
        LinkedList<Pair<Station, Integer>> fullPath = new LinkedList<>();
        Station current = endStation;

        // Traverse back from the end station to the start station, adding each station to the path
        while (current != null && !current.equals(startStation)) {
            Pair<Station, Integer> pathInfo = visited.get(current.getName());
            fullPath.addFirst(new Pair<>(current, pathInfo.second));
            current = pathInfo.first;
        }

        // If the start station is reached, add it to the path
        if (current != null) {
            fullPath.addFirst(new Pair<>(startStation, 0));
        }

        // Initialize a list to store the path segments and a set to store the current lines
        List<PathSegment> pathSegments = new ArrayList<>();
        Set<Line> currentLines = null;
        List<Pair<Station, Integer>> segmentStations = new ArrayList<>();

        try {
            // Iterate through the full path
            for (int i = 0; i < fullPath.size() - 1; i++) {
                Pair<Station, Integer> currentPair = fullPath.get(i);
                Station currentStation = currentPair.first;
                // Find the common lines between the current station and the next station
                Set<Line> nextLines = findCommonLines(currentStation, fullPath.get(i + 1).first);

                // If this is the first iteration or the lines have changed, update the current lines
                if (currentLines == null && nextLines != null && !nextLines.isEmpty()) {
                    currentLines = nextLines;
                }

                // If there are lines for the current station, add it to the current segment
                if (nextLines != null && !nextLines.isEmpty()) {
                    segmentStations.add(currentPair);

                    // If the lines have changed, create a new path segment and start a new segment
                    if (!currentLines.equals(nextLines)) {
                        pathSegments.add(new PathSegment(currentLines, segmentStations));
                        segmentStations = new ArrayList<>();
                        currentLines = nextLines;
                    }
                }
            }
        } catch (NullPointerException e) {
            new ErrorLogger("error.log").logError("V nalezené cestě je chyba, nelze vytvořit cestu.", e);
            return Collections.emptyList();
        }

        // If the full path is not empty and there are lines for the last station, add the last segment
        if (!fullPath.isEmpty() && currentLines != null && !currentLines.isEmpty()) {
            segmentStations.add(fullPath.getLast());
            pathSegments.add(new PathSegment(currentLines, segmentStations));
        }

        // Return the list of path segments
        return pathSegments;
    }

    /**
     * The findCommonLines function takes two stations and returns a set of lines that are common to both.
     *
     * @param station1 Find the lines that are common between station 1 and 2
     * @param station2 Find the common lines between station 1 and 2
     * @return A set of all the lines that are common to both stations, or null if there are no common lines
     */
    private Set<Line> findCommonLines(Station station1, Station station2) {
        // Initialize commonLines with lines from station1
        Set<Line> commonLines = new HashSet<>(station1.getLines());

        // Retain lines common to both station1 and station2
        commonLines.retainAll(station2.getLines());

        // Return commonLines if not empty, else null
        return (commonLines.isEmpty()) ? null : commonLines;
    }

}
