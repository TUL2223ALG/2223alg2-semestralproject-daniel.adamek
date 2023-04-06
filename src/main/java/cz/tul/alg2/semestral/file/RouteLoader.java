package cz.tul.alg2.semestral.file;

import cz.tul.alg2.semestral.exception.InvalidFileFormatException;
import cz.tul.alg2.semestral.transportation.Station;
import cz.tul.alg2.semestral.transportation.Transport;
import cz.tul.alg2.semestral.transportation.TypeOfTransportation;
import cz.tul.alg2.semestral.utilities.Pair;
import cz.tul.alg2.semestral.utilities.TextNormalization;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class RouteLoader {

    private final HashMap<String, Station> allStations = new HashMap<>();
    public ArrayList<String> loadFiles(String path) {
        ArrayList<String> foundRoutes = new ArrayList<>();

        File dir = new File(path);
        if (!dir.exists()) {
            System.out.println("Folder does not exist.");
        } else {
            File[] files = dir.listFiles((dir1, name) -> name.endsWith(".txt"));
            for (File file : files)
                if (file.isFile())
                    foundRoutes.add(file.getPath());
        }

        return foundRoutes;
    }
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
