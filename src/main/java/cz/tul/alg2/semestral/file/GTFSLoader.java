package cz.tul.alg2.semestral.file;

import cz.tul.alg2.semestral.errorhandle.ErrorLogger;
import cz.tul.alg2.semestral.transportation.Line;
import cz.tul.alg2.semestral.transportation.Station;
import cz.tul.alg2.semestral.transportation.TransportationType;
import cz.tul.alg2.semestral.utilities.Pair;
import cz.tul.alg2.semestral.utilities.PathBuilder;
import cz.tul.alg2.semestral.utilities.TextNormalization;
import org.onebusaway.gtfs.impl.GtfsDaoImpl;
import org.onebusaway.gtfs.model.Route;
import org.onebusaway.gtfs.model.Stop;
import org.onebusaway.gtfs.model.StopTime;
import org.onebusaway.gtfs.model.Trip;
import org.onebusaway.gtfs.serialization.GtfsReader;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

public class GTFSLoader {

    private final HashMap<String, Station> allStations = new HashMap<>();
    private final HashMap<String, Line> allLines = new HashMap<>();
    private static void addNeighborsForLine(Line line) {
        List<Station> stations = line.getStations();
        for (int i = 0; i < stations.size() - 1; i++) {
            Station station1 = stations.get(i);
            Station station2 = stations.get(i + 1);
            int travelTime = 1; // Předpokládejme základní cestovní čas 1 mezi sousedními stanicemi, pokud není k dispozici jiná informace
            station1.addNeighbour(new Pair<>(station2, travelTime));
            station2.addNeighbour(new Pair<>(station1, travelTime));
        }
    }
    public boolean loadFile(String path) throws IOException {
        GtfsReader reader = new GtfsReader();
        GtfsDaoImpl dao = new GtfsDaoImpl();

        reader.setEntityStore(dao);
        try {
            reader.setInputLocation(new File(PathBuilder.joinPath(new String[]{"data", path})));
        } catch (IOException e) {
            new ErrorLogger("error.log").logError("Error while reading GTFS file", e);
        }
        reader.run();

        // Load all stations
        for (Stop stop : dao.getAllStops()) {
            if (stop.getName() == null) continue;

            allStations.put(
                    TextNormalization.stringNormalize(stop.getName()),
                    new Station(stop.getName(), stop.getZoneId())
            );
        }

        // Load all lines
            /*
            switch (route.getType()) {
                case 0:
                    yield transportationType.METRO;
                case 1:
                    yield transportationType.TRAM;
                case 2:
                    yield transportationType.TRAIN;
                case 3:
                    yield transportationType.BUS;
                case 4:
                    yield transportationType.AIRPLANE;
                case 5:
                    yield transportationType.CABLE_CAR;
                case 6:
                    yield transportationType.TROLLEY;
                case 7:
                    yield transportationType.FERRY;
                default:
                    new ErrorLogger("error.log").logError("WARNING: Invalid type of transportation! Skipping... Route:" + route.toString(), new IllegalArgumentException());
                    yield transportationType.NONE;
            };
            */

        // Vytvoříme mapu tripId -> stopTimes
        HashMap<String, List<StopTime>> tripStopTimesMap = new HashMap<>();
        for (StopTime stopTime : dao.getAllStopTimes()) {
            tripStopTimesMap.computeIfAbsent(stopTime.getTrip().getId().getId(), k -> new ArrayList<>()).add(stopTime);
        }

        for (Route route : dao.getAllRoutes()) {
            String id = route.getId().getId();
            TransportationType lineType =
            switch (route.getType()) {
                case 0:
                    yield TransportationType.METRO;
                case 1:
                    yield TransportationType.TRAM;
                case 2:
                    yield TransportationType.TRAIN;
                case 3:
                    yield TransportationType.BUS;
                case 4:
                    yield TransportationType.AIRPLANE;
                case 5:
                    yield TransportationType.CABLE_CAR;
                case 6:
                    yield TransportationType.TROLLEY;
                case 7:
                    yield TransportationType.FERRY;
                default:
                    new ErrorLogger("error.log").logError("WARNING: Invalid type of transportation! Skipping... Route:" + route.toString(), new IllegalArgumentException());
                    yield TransportationType.NONE;
            };

            for (Trip trip : dao.getAllTrips()) {
                if (!trip.getRoute().getId().getId().equals(id)) {
                    continue;
                }

                List<StopTime> stopTimesForTrip = tripStopTimesMap.get(trip.getId().getId());
                stopTimesForTrip.sort(Comparator.comparingInt(StopTime::getStopSequence));
                List<Station> lineStations = new ArrayList<>();

                for (int i = 0; i < stopTimesForTrip.size(); i++) {
                    StopTime stopTime = stopTimesForTrip.get(i);
                    // Vyhledávání stanice pomocí TextNormalization.stringNormalize(stopTime.getStop().getName())
                    Station station = allStations.get(TextNormalization.stringNormalize(stopTime.getStop().getName()));
                    lineStations.add(station);
                    station.addRoute(new Line(id, lineType, lineStations));

                    if (i > 0) {
                        StopTime prevStopTime = stopTimesForTrip.get(i - 1);
                        Station prevStation = allStations.get(TextNormalization.stringNormalize(prevStopTime.getStop().getName()));
                        int travelTime = (stopTime.getArrivalTime() - prevStopTime.getDepartureTime())/60;

                        Pair<Station, Integer> neighbour = new Pair<>(prevStation, travelTime);
                        station.getNeighbours().add(neighbour);
                        Pair<Station, Integer> reverseNeighbour = new Pair<>(station, travelTime);
                        prevStation.getNeighbours().add(reverseNeighbour);
                    }
                }

                Line line = new Line(id, lineType, lineStations);
                allLines.put(id, line);
            }
        }
        return true;
    }
    public HashMap<String, Station> getAllStations() {
        return this.allStations;
    }

    public HashMap<String, Line> getAllLines() {
        return this.allLines;
    }
}
