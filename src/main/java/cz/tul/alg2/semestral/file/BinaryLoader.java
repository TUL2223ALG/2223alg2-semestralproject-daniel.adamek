package cz.tul.alg2.semestral.file;

import cz.tul.alg2.semestral.errorhandle.ErrorLogger;
import cz.tul.alg2.semestral.transportation.Line;
import cz.tul.alg2.semestral.transportation.Station;
import cz.tul.alg2.semestral.transportation.TransportationType;
import cz.tul.alg2.semestral.utilities.Pair;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class BinaryLoader implements ILoader {
    HashMap<String, Station> allStations = new HashMap<>();
    HashMap<String, Line> allLines = new HashMap<>();

    @Override
    public void loadFile(String path) {
        try (DataInputStream inputStream = new DataInputStream(new BufferedInputStream(new FileInputStream(path)))) {
            // Načítání stanic
            int stationCount = inputStream.readInt();
            HashMap<Integer, Station> indexToStationMap = new HashMap<>(stationCount);
            for (int i = 0; i < stationCount; i++) {
                int stationIndex = inputStream.readInt();
                String stationPrettyName = inputStream.readUTF();
                String stationZoneID = inputStream.readUTF();
                if (stationZoneID.isEmpty()) {
                    stationZoneID = null;
                }
                Station station = new Station(stationPrettyName, stationZoneID);
                allStations.put(station.getName(), station);
                indexToStationMap.put(stationIndex, station);
            }

            // Načítání sousedů
            for (int i = 0; i < stationCount; i++) {
                int stationIndex = inputStream.readInt();
                Station station = indexToStationMap.get(stationIndex);
                int neighbourCount = inputStream.readInt();
                for (int j = 0; j < neighbourCount; j++) {
                    int neighbourIndex = inputStream.readInt();
                    int distance = inputStream.readInt();
                    Station neighbour = indexToStationMap.get(neighbourIndex);
                    station.addNeighbour(new Pair<>(neighbour, distance));
                }
            }

            // Načítání linek
            int lineCount = inputStream.readInt();
            for (int i = 0; i < lineCount; i++) {
                String lineName = inputStream.readUTF();
                String lineTypeName = inputStream.readUTF();
                TransportationType lineType = TransportationType.valueOf(lineTypeName);
                int stationsInLineCount = inputStream.readInt();
                List<Station> lineStations = new ArrayList<>();
                for (int j = 0; j < stationsInLineCount; j++) {
                    int stationIndex = inputStream.readInt();
                    Station station = indexToStationMap.get(stationIndex);
                    lineStations.add(station);
                    System.out.println("Načítání stanice " + station.getPrettyName() + " v lince " + lineName);
                }
                Line line = new Line(lineName, lineType, lineStations);
                allLines.put(line.getName(), line);

                // Přidejte linku do stanic
                for (Station station : lineStations) {
                    station.addLine(line);
                }
            }

            System.out.println("Data načtena ze souboru " + path);
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
