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

    @Override
    public void loadFile(String path) {
        try (DataInputStream inputStream = new DataInputStream(new BufferedInputStream(new FileInputStream(path)))) {
            int stationCount = inputStream.readInt();
            HashMap<Integer, String> stationIdToName = new HashMap<>(stationCount);

            for (int i = 0; i < stationCount; i++) {
                int id = inputStream.readInt();
                String name = inputStream.readUTF();
                String zoneID = inputStream.readUTF();
                Station station = zoneID.isEmpty() ? new Station(name) : new Station(name, zoneID);
                allStations.put(station.getName(), station);
                stationIdToName.put(id, station.getName());
            }

            for (int i = 0; i < stationCount; i++) {
                int stationId = inputStream.readInt();
                String stationName = stationIdToName.get(stationId);
                Station station = allStations.get(stationName);

                int neighbourCount = inputStream.readInt();
                for (int j = 0; j < neighbourCount; j++) {
                    int neighbourId = inputStream.readInt();
                    int distance = inputStream.readInt();
                    String neighbourName = stationIdToName.get(neighbourId);
                    Station neighbour = allStations.get(neighbourName);
                    station.addNeighbour(new Pair<>(neighbour, distance));
                }
            }

            int lineCount = inputStream.readInt();
            for (int i = 0; i < lineCount; i++) {
                String name = inputStream.readUTF();
                TransportationType lineType = TransportationType.valueOf(inputStream.readUTF());

                int stationCountInLine = inputStream.readInt();
                List<Station> stations = new ArrayList<>(stationCountInLine);
                for (int j = 0; j < stationCountInLine; j++) {
                    int stationId = inputStream.readInt();
                    String stationName = stationIdToName.get(stationId);
                    stations.add(allStations.get(stationName));
                }

                Line line = new Line(name, lineType, stations);
                allLines.put(line.getName(), line);
            }
        } catch (IOException e) {
            new ErrorLogger("error.log").logError("Error while loading binary data", e);
        }
    }
}
