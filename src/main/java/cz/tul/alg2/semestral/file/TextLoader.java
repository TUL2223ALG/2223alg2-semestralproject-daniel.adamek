package cz.tul.alg2.semestral.file;

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

public class TextLoader {
    HashMap<String, Station> allStations = new HashMap<>();
    HashMap<String, Line> allLines = new HashMap<>();

    public boolean loadFile(String path) {
        try (BufferedReader reader = new BufferedReader(new FileReader(path))) {
            String line;
            HashMap<Integer, Station> substitutionMap = new HashMap<>();

            // Read stations
            if (!"STATIONS".equals(reader.readLine())) {
                throw new IOException("Invalid file format");
            }

            while ((line = reader.readLine()) != null && !line.equals("LINES")) {
                String[] parts = line.split("\\|");
                int stationID = Integer.parseInt(parts[0]);
                String prettyName = parts[1];
                String zoneID = parts[2];

                Station station = new Station(prettyName, zoneID);
                substitutionMap.put(stationID, station);
                allStations.put(station.getName(), station);
            }

            // Read lines
            int stationID, travelTime;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("\\|");
                String lineName = parts[0];
                TransportationType lineType = TransportationType.valueOf(parts[1]);

                List<Pair<Station, Integer>> stationList = new ArrayList<>();
                String[] stationPairs = parts[2].split(";");
                for (String pair : stationPairs) {
                    String[] pairParts = pair.split(",");
                    stationID = Integer.parseInt(pairParts[0]);
                    travelTime = Integer.parseInt(pairParts[1]);

                    Pair<Station, Integer> stationPair = new Pair<>(substitutionMap.get(stationID), travelTime);
                    stationList.add(stationPair);
                }
                Line currentLine = new Line(lineName, lineType, stationList);

                allLines.put(currentLine.getName(), currentLine);
            }

        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }

        // Compute neighbours
        ILoader.computeNeighbours(allLines);

        return true;
    }


    /**
     * Gets a map of all stations that have been loaded by this loader.
     *
     * @return a map of all stations that have been loaded by this route loader
     */
    HashMap<String, Station> getAllStations() { return this.allStations; }

    /**
     * Gets a map of all lines that have been loaded by this loader.
     *
     * @return a map of all lines that have been loaded by this loader
     */
    HashMap<String, Line> getAllLines() { return this.allLines; }


}
