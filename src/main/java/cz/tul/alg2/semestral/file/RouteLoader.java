package cz.tul.alg2.semestral.file;

import cz.tul.alg2.semestral.exception.InvalidFileFormatException;
import cz.tul.alg2.semestral.transportation.Station;
import cz.tul.alg2.semestral.transportation.Transport;
import cz.tul.alg2.semestral.transportation.TypeOfTransportation;
import cz.tul.alg2.semestral.utilities.Pair;
import cz.tul.alg2.semestral.utilities.TextNormalization;

import java.io.*;
import java.util.HashMap;

public class RouteLoader {

    private final HashMap<String, Station> allStations = new HashMap<>();
    public void loadRoute(String path, TypeOfTransportation typeOfTransportation) {
        try {
            // Load file from resources folder
            InputStream inputStream = RouteLoader.class.getClassLoader().getResourceAsStream(path);
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            String time;

            // Station loading
            String stationName;
            Transport transport;
            Station prev, actual;

            stationName = reader.readLine();
            if (stationName == null || stationName.equals("")) {
                throw new InvalidFileFormatException("Error while loading file " + path + ". Skipping...");
            };
            prev = new Station(stationName);
            allStations.put(prev.getName(), prev);

            while (true) {
                time = reader.readLine();
                if (time == null) {
                    break;
                } else if (time.equals("")) {
                    throw new InvalidFileFormatException("Error while loading file " + path + ". Skipping...");
                }

                transport = new Transport(typeOfTransportation, Integer.parseInt(time));

                stationName = reader.readLine();
                if (stationName == null || stationName.equals("")) {
                    throw new InvalidFileFormatException("Error while loading file " + path + ". Skipping...");
                };
                actual = allStations.get(TextNormalization.stringNormalize(stationName));
                if (actual == null)
                    actual = new Station(stationName);

                prev.addNeighbour(new Pair<>(actual, transport));
                actual.addNeighbour(new Pair<>(prev, transport));

                allStations.put(actual.getName(), actual);
                prev = actual;
            }

            // File closing
            reader.close();
            inputStream.close();

        } catch (IOException | InvalidFileFormatException e) {
            System.out.println("An error occured.");
            e.printStackTrace();
        }
    }
    public HashMap<String, Station> getAllStations() {
        return allStations;
    }
}
