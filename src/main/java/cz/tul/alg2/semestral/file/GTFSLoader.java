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

public class GTFSLoader implements ILoader {

    private final HashMap<String, Station> allStations = new HashMap<>();
    private final HashMap<String, Line> allLines = new HashMap<>();
    public void loadFile(String path) {
        GtfsReader reader = new GtfsReader();
        GtfsDaoImpl dao = new GtfsDaoImpl();

        reader.setEntityStore(dao);
        try {
            reader.setInputLocation(new File(PathBuilder.joinPath(new String[]{"data", path})));
            reader.run();
        } catch (IOException e) {
            new ErrorLogger("error.log").logError("Error while reading GTFS file", e);
        }
        // Load all lines
            /*
            switch (route.getType()) {
                case 0:
                    yield TransportationType.TRAM;
                case 1:
                    yield TransportationType.METRO;
                case 2:
                    yield TransportationType.TRAIN;
                case 3:
                    yield TransportationType.BUS;
                case 4:
                    yield TransportationType.FERRY;
                case 5:
                    yield TransportationType.AIRPLANE;
                case 6:
                    yield TransportationType.TROLLEY;
                case 7:
                    yield TransportationType.CABLE_CAR;
                default:
                    new ErrorLogger("error.log").logError("WARNING: Invalid type of transportation! Skipping... Route:" + route.toString(), new IllegalArgumentException());
                    yield TransportationType.NONE;
            };
            */

        for (Stop stop : dao.getAllStops()) {
            if (stop.getName() == null) continue;

            allStations.put(
                    TextNormalization.stringNormalize(stop.getName()),
                    new Station(stop.getName(), stop.getZoneId())
            );
        }

        // Vytvoříme mapu tripId -> stopTimes
        HashMap<String, List<StopTime>> tripStopTimesMap = new HashMap<>();
        for (StopTime stopTime : dao.getAllStopTimes()) {
            tripStopTimesMap.computeIfAbsent(stopTime.getTrip().getId().getId(), k -> new ArrayList<>()).add(stopTime);
        }

        // Vytvoříme mapu routeId -> trips
        HashMap<String, List<Trip>> routeTripsMap = new HashMap<>();
        for (Trip trip : dao.getAllTrips()) {
            routeTripsMap.computeIfAbsent(trip.getRoute().getId().getId(), k -> new ArrayList<>()).add(trip);
        }

        for (Route route : dao.getAllRoutes()) {
            String shortName = route.getShortName();
            TransportationType lineType = switch (route.getType()) {
                case 0:
                    yield TransportationType.TRAM;
                case 1:
                    yield TransportationType.METRO;
                case 2:
                    yield TransportationType.TRAIN;
                case 3:
                    yield TransportationType.BUS;
                case 4:
                    yield TransportationType.FERRY;
                case 5:
                    yield TransportationType.AIRPLANE;
                case 6:
                    yield TransportationType.TROLLEY;
                case 7:
                    yield TransportationType.CABLE_CAR;
                default:
                    new ErrorLogger("error.log").logError("WARNING: Invalid type of transportation! Skipping... Route:" + route, new IllegalArgumentException());
                    yield TransportationType.NONE;
            };

            List<Trip> tripsForRoute = routeTripsMap.get(route.getId().getId());

            for (Trip trip : tripsForRoute) {
                List<StopTime> stopTimesForTrip = tripStopTimesMap.get(trip.getId().getId());
                stopTimesForTrip.sort(Comparator.comparingInt(StopTime::getStopSequence));
                List<Station> lineStations = new ArrayList<>();

                for (int i = 0; i < stopTimesForTrip.size(); i++) {
                    StopTime stopTime = stopTimesForTrip.get(i);
                    Station station = allStations.get(TextNormalization.stringNormalize(stopTime.getStop().getName()));
                    lineStations.add(station);
                    station.addRoute(new Line(shortName, lineType, lineStations));

                    if (i > 0) {
                        StopTime prevStopTime = stopTimesForTrip.get(i - 1);
                        Station prevStation = allStations.get(TextNormalization.stringNormalize(prevStopTime.getStop().getName()));
                        int travelTime = stopTime.getArrivalTime() - prevStopTime.getDepartureTime();

                        Pair<Station, Integer> neighbour = new Pair<>(prevStation, travelTime);
                        station.getNeighbours().add(neighbour);
                        Pair<Station, Integer> reverseNeighbour = new Pair<>(station, travelTime);
                        prevStation.getNeighbours().add(reverseNeighbour);
                    }
                }

                Line line = new Line(shortName, lineType, lineStations);
                allLines.put(shortName, line);
            }
        }
    }

    public HashMap<String, Station> getAllStations() {
        return this.allStations;
    }

    public HashMap<String, Line> getAllLines() {
        return this.allLines;
    }
}
