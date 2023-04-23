package cz.tul.alg2.semestral.file;

import cz.tul.alg2.semestral.errorhandle.ErrorLogger;
import cz.tul.alg2.semestral.transportation.Line;
import cz.tul.alg2.semestral.transportation.Station;
import cz.tul.alg2.semestral.transportation.TransportationType;
import cz.tul.alg2.semestral.utilities.Pair;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class TextLoader implements ILoader {
    HashMap<String, Station> allStations = new HashMap<>();
    HashMap<String, Line> allLines = new HashMap<>();
    /**
     * Returns a list of file paths that end with ".txt" in the specified directory path.
     *
     * @param path the path of the directory to search for files
     */
    @Override
    public void loadFile(String path) {
        HashMap<Integer, Station> indexToStationMap = new HashMap<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(path))) {
            String line;
            boolean readingStations = false;
            boolean readingNeighbours = false;
            boolean readingLines = false;

            while ((line = reader.readLine()) != null) {
                if (line.equals("STATIONS")) {
                    readingStations = true;
                    readingNeighbours = false;
                    readingLines = false;
                } else if (line.equals("NEIGHBOURS")) {
                    readingStations = false;
                    readingNeighbours = true;
                    readingLines = false;
                } else if (line.equals("LINES")) {
                    readingStations = false;
                    readingNeighbours = false;
                    readingLines = true;
                } else {
                    if (readingStations) {
                        String[] parts = line.split("\\|");
                        int index = Integer.parseInt(parts[0]);
                        String name = parts[1];
                        String zoneID = parts.length > 2 ? parts[2] : null;
                        Station station = zoneID != null ? new Station(name, zoneID) : new Station(name);
                        allStations.put(station.getName(), station);
                        indexToStationMap.put(index, station);
                    } else if (readingNeighbours) {
                        String[] parts = line.split("\\|");
                        if (parts.length < 2) continue;
                        int stationIndex = Integer.parseInt(parts[0]);
                        Station station = indexToStationMap.get(stationIndex);
                        String[] neighbours = parts[1].split(";");
                        for (String neighbour : neighbours) {
                            String[] neighbourParts = neighbour.split(",");
                            int neighbourIndex = Integer.parseInt(neighbourParts[0]);
                            int travelTime = Integer.parseInt(neighbourParts[1]);
                            station.addNeighbour(new Pair<>(indexToStationMap.get(neighbourIndex), travelTime));
                        }
                    } else if (readingLines) {
                        String[] parts = line.split("\\|");
                        String lineName = parts[0];
                        TransportationType lineType = TransportationType.valueOf(parts[1]);
                        String[] stationIndexes = parts[2].split(",");
                        List<Station> lineStations = new ArrayList<>();
                        for (String stationIndex : stationIndexes) {
                            lineStations.add(indexToStationMap.get(Integer.parseInt(stationIndex)));
                        }
                        Line newLine = new Line(lineName, lineType, lineStations);
                        allLines.put(lineName, newLine);
                    }
                }
            }
        } catch (IOException e) {
            new ErrorLogger("error.log").logError("Error while loading data", e);
        }

    }

    /**
     * Gets a map of all stations that have been loaded by this loader.
     *
     * @return a map of all stations that have been loaded by this route loader
     */
    @Override
    public HashMap<String, Station> getAllStations() {
        return this.allStations;
    }

    /**
     * Gets a map of all lines that have been loaded by this loader.
     *
     * @return a map of all lines that have been loaded by this loader
     */
    @Override
    public HashMap<String, Line> getAllLines() {
        return this.allLines;
    }
}
