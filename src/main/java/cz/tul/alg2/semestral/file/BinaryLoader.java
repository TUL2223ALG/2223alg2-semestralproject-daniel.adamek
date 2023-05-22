package cz.tul.alg2.semestral.file;

import cz.tul.alg2.semestral.errorhandle.ErrorLogger;
import cz.tul.alg2.semestral.transportation.Line;
import cz.tul.alg2.semestral.transportation.Station;
import cz.tul.alg2.semestral.transportation.TransportationType;
import cz.tul.alg2.semestral.utilities.Pair;

import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * The type Binary loader.
 */
public class BinaryLoader implements ILoader {
    /**
     * The stations.
     */
    HashMap<String, Station> allStations = new HashMap<>();
    /**
     * The lines.
     */
    HashMap<String, Line> allLines = new HashMap<>();

    /**
     * The loadFile function loads the data from a file into the program.
     *
     * @param path Specify the path to the file that is being loaded
     * @return True if the file was loaded successfully, false otherwise
     */
    public boolean loadFile(String path) {
        try (DataInputStream reader = new DataInputStream(new FileInputStream(path))) {
            // Loading stations
            String stationsSection = reader.readUTF();
            if (!stationsSection.equals("STATIONS")) {
                new ErrorLogger(ErrorLogger.ERROR_FILE).logError("", new IOException("Invalid file format"));
                return false;
            }
            int stationCount = reader.readInt();
            HashMap<Integer, Station> substitutionMap = new HashMap<>();

            for (int i = 0; i < stationCount; i++) {
                int stationId = reader.readInt();
                String prettyName = reader.readUTF();
                String zoneId = reader.readUTF();
                Station station = new Station(prettyName, zoneId);
                substitutionMap.put(stationId, station);
                allStations.put(station.getName(), station);
            }

            // Loading lines
            String linesSection = reader.readUTF();
            if (!linesSection.equals("LINES")) {
                new ErrorLogger(ErrorLogger.ERROR_FILE).logError("Chyba při načítání dat", new IOException("Invalid file format"));
                return false;
            }
            int lineCount = reader.readInt();

            for (int i = 0; i < lineCount; i++) {
                String lineName = reader.readUTF();
                TransportationType lineType = TransportationType.valueOf(reader.readUTF());
                int stationPairCount = reader.readInt();
                List<Pair<Station, Integer>> lineStations = new ArrayList<>();

                for (int j = 0; j < stationPairCount; j++) {
                    int stationId = reader.readInt();
                    int travelTime = reader.readInt();
                    Station station = substitutionMap.get(stationId);
                    Pair<Station, Integer> stationPair = new Pair<>(station, travelTime);
                    lineStations.add(stationPair);
                }

                Line line = new Line(lineName, lineType, lineStations);
                allLines.put(lineName, line);
            }

        } catch (IOException e) {
            new ErrorLogger(ErrorLogger.ERROR_FILE).logError("Chyba při načítání dat", e);
            return false;
        }

        // Compute neighbours
        ILoader.computeNeighbours(allLines);
        return true;
    }

    /**
     * Gets a map of all stations that have been loaded by this loader.
     * @return a map of all stations that have been loaded by this route loader
     */
    @Override
    public HashMap<String, Station> getAllStations() {
        return this.allStations;
    }

    /**
     * Gets a map of all lines that have been loaded by this loader.
     * @return a map of all lines that have been loaded by this loader
     */
    @Override
    public HashMap<String, Line> getAllLines() {
        return this.allLines;
    }
}
