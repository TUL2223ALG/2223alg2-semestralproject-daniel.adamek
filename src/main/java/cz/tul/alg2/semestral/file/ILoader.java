package cz.tul.alg2.semestral.file;

import cz.tul.alg2.semestral.transportation.Line;
import cz.tul.alg2.semestral.transportation.Station;
import cz.tul.alg2.semestral.utilities.Pair;

import java.util.HashMap;
import java.util.List;

public interface ILoader {
    boolean loadFile(String path);

    /**
     * Gets a map of all stations that have been loaded by this loader.
     *
     * @return a map of all stations that have been loaded by this route loader
     */
    HashMap<String, Station> getAllStations();
    /**
     * Gets a map of all lines that have been loaded by this loader.
     *
     * @return a map of all lines that have been loaded by this loader
     */
    HashMap<String, Line> getAllLines();
    static void computeNeighbours(HashMap<String, Line> allLines) {
        for (Line line : allLines.values()) {
            List<Pair<Station, Integer>> lineStations = line.getStations();

            // Iterate over each station pair in the line
            for (int i = 0; i < lineStations.size(); i++) {
                Pair<Station, Integer> currentPair = lineStations.get(i);
                Station currentStation = currentPair.first;
                int currentTravelTime = currentPair.second;

                // Add the current line to the station's set of lines
                currentStation.addLine(line);

                // Update neighbors for the current station
                if (i > 0) {
                    Pair<Station, Integer> previousPair = lineStations.get(i - 1);
                    Station previousStation = previousPair.first;
                    Pair<Station, Integer> neighbor = new Pair<>(previousStation, currentTravelTime);
                    currentStation.getNeighbours().add(neighbor);
                }
                if (i < lineStations.size() - 1) {
                    Pair<Station, Integer> nextPair = lineStations.get(i + 1);
                    Station nextStation = nextPair.first;
                    int nextTravelTime = nextPair.second;
                    Pair<Station, Integer> neighbor = new Pair<>(nextStation, nextTravelTime);
                    currentStation.getNeighbours().add(neighbor);
                }
            }
        }
    }

}
