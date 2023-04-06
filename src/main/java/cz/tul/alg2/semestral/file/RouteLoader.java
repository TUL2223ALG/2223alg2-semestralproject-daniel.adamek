package cz.tul.alg2.semestral.file;

import cz.tul.alg2.semestral.transportation.Station;
import cz.tul.alg2.semestral.transportation.Transport;
import cz.tul.alg2.semestral.transportation.TypeOfTransportation;
import cz.tul.alg2.semestral.utilities.Pair;

import java.io.*;
import java.time.Duration;
import java.util.HashSet;
import java.util.MissingFormatArgumentException;

public class RouteLoader {
    private final HashSet<Pair<Station, Transport>> allStations = new HashSet<>();
    public RouteLoader(String path, TypeOfTransportation typeOfTransportation) {
        try {
            // Load file from resources folder
            InputStream inputStream = RouteLoader.class.getClassLoader().getResourceAsStream(path);
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            String line, time;
            int duration;

            // Station loading
            String stationName;
            Station station;
            Transport transport;
            Pair<Station, Transport> prev = null, actual;

            while (true) {
                line = reader.readLine();
                if (line == null) break;

                time = reader.readLine();
                if (time == null || time.equals("")) {
                    if (prev == null) {
                        System.out.println("Error while loading file " + path + ". Skipping...");
                        break;
                    }
                    actual = new Pair<>(new Station(line), prev.second);
                    break;
                }
                else {
                    duration = Integer.parseInt(time);
                    actual = new Pair<>(new Station(line), new Transport(typeOfTransportation, duration));
                }

                allStations.add(actual);

                if (prev != null) {
                    prev.first.addNeighbour(actual);
                    actual.first.addNeighbour(prev);
                }
                prev = actual;
            }

            // FIle closing
            reader.close();
            inputStream.close();

        } catch (IOException e) {
            System.out.println("An error occured.");
            e.printStackTrace();
        }
    }
}
