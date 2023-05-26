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
            // Read stations
            // check file format
            String stationsSection = reader.readUTF();
            // Check the file format by verifying the first string is "STATIONS"
            if (!stationsSection.equals("STATIONS")) {
                throw new IOException("Neplatný formát souboru");
            }
            // Create a map for storing the station objects with their respective IDs
            int stationCount = reader.readInt();
            HashMap<Integer, Station> substitutionMap = new HashMap<>();

            // Loop through each station
            for (int i = 0; i < stationCount; i++) {
                // Read the station data
                int stationId = reader.readInt();
                String prettyName = reader.readUTF();
                String zoneId = reader.readUTF();

                // Create a station object and add it to the maps
                Station station = new Station(prettyName, zoneId);
                substitutionMap.put(stationId, station);
                allStations.put(station.getName(), station);
            }

            // Loading lines
            String linesSection = reader.readUTF();
            // Check the file format by verifying the next string is "LINES"
            if (!linesSection.equals("LINES")) {
                throw new IOException("Neplatný formát souboru");
            }
            // Read the number of lines from the file
            int lineCount = reader.readInt();

            // Loop through each line
            for (int i = 0; i < lineCount; i++) {
                // Read the line data
                String lineName = reader.readUTF();
                TransportationType lineType = TransportationType.valueOf(reader.readUTF());

                // Read the number of station pairs in the line
                int stationPairCount = reader.readInt();
                List<Pair<Station, Integer>> lineStations = new ArrayList<>();

                // Loop through each station pair in the line
                for (int j = 0; j < stationPairCount; j++) {
                    // Read the station pair data
                    int stationId = reader.readInt();
                    int travelTime = reader.readInt();

                    // Get the station from the previously created station map
                    Station station = substitutionMap.get(stationId);

                    // Create a station pair object and add it to the line's station list
                    Pair<Station, Integer> stationPair = new Pair<>(station, travelTime);
                    lineStations.add(stationPair);
                }

                // Create a line object and add it to the map of all lines
                Line line = new Line(lineName, lineType, lineStations);
                allLines.put(lineName, line);
            }

            // Compute neighbours of stations on lines
            ILoader.computeNeighbours(allLines);

        } catch (IOException e) {
            new ErrorLogger(ErrorLogger.ERROR_FILE).logError("Chyba při načítání dat", e);
            return false;
        }
        return true;
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
