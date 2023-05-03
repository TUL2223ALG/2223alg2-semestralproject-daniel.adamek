package cz.tul.alg2.semestral.file;

import cz.tul.alg2.semestral.errorhandle.ErrorLogger;
import cz.tul.alg2.semestral.transportation.Line;
import cz.tul.alg2.semestral.transportation.Station;
import cz.tul.alg2.semestral.transportation.TransportationType;
import cz.tul.alg2.semestral.utilities.Pair;
import cz.tul.alg2.semestral.utilities.TextNormalization;
import org.onebusaway.gtfs.impl.GtfsDaoImpl;
import org.onebusaway.gtfs.model.Route;
import org.onebusaway.gtfs.model.Stop;
import org.onebusaway.gtfs.model.StopTime;
import org.onebusaway.gtfs.model.Trip;
import org.onebusaway.gtfs.serialization.GtfsReader;

import java.io.File;
import java.io.IOException;
import java.util.*;

import static cz.tul.alg2.semestral.file.ILoader.*;

public class GTFSLoader implements ILoader {
    private final HashMap<String, Station> allStations = new HashMap<>();
    private final HashMap<String, Line> allLines = new HashMap<>();

    /**
     * The loadFile function is used to load a GTFS file into the program.
     * It uses the GtfsReader class from OpenTripPlanner to read in all the data, and then it parses that data into our own classes.
     * The function returns true if everything went well, or false if there was an error while reading in the file.

     *
     * @param path Specify the path to the gtfs file
     *
     * @return True if the file is loaded successfully, otherwise false
     *
     */
    public boolean loadFile(String path) {
        GtfsReader reader = new GtfsReader();
        GtfsDaoImpl dao = new GtfsDaoImpl();

        // Set the entity store for the reader
        reader.setEntityStore(dao);
        try {
            reader.setInputLocation(new File(path));
            reader.run();
        } catch (IOException e) {
            new ErrorLogger("error.log").logError("Error while reading GTFS file", e);
            return false;
        }

        // Populate allStations map
        for (Stop stop : dao.getAllStops()) {
            if (stop.getName() == null) continue;

            allStations.put(
                    TextNormalization.stringNormalize(stop.getName()),
                    new Station(stop.getName(), stop.getZoneId())
            );
        }

        // Map trip IDs to their corresponding stop times
        HashMap<String, List<StopTime>> tripStopTimesMap = new HashMap<>(80000);
        for (StopTime stopTime : dao.getAllStopTimes()) {
            tripStopTimesMap.computeIfAbsent(stopTime.getTrip().getId().getId(), k -> new ArrayList<>()).add(stopTime);
        }

        // Map route IDs to their corresponding trips
        HashMap<String, List<Trip>> routeTripsMap = new HashMap<>(800);
        for (Trip trip : dao.getAllTrips()) {
            routeTripsMap.computeIfAbsent(trip.getRoute().getId().getId(), k -> new ArrayList<>()).add(trip);
        }

        // Iterate over all routes
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
            if (lineType == TransportationType.NONE) continue;

            // Get trips for the current route
            List<Trip> tripsForRoute = routeTripsMap.get(route.getId().getId());

            // Iterate over trips of the route
            for (Trip trip : tripsForRoute) {
                List<StopTime> stopTimesForTrip = tripStopTimesMap.get(trip.getId().getId());
                stopTimesForTrip.sort(Comparator.comparingInt(StopTime::getStopSequence));
                List<Pair<Station, Integer>> lineStations = new ArrayList<>(stopTimesForTrip.size());

                // Create a list of station-distance pair for the lines
                for (int i = 0; i < stopTimesForTrip.size(); i++) {
                    StopTime stopTime = stopTimesForTrip.get(i);
                    Station station = allStations.get(TextNormalization.stringNormalize(stopTime.getStop().getName()));

                    if (i < stopTimesForTrip.size() - 1) {
                        StopTime nextStopTime = stopTimesForTrip.get(i + 1);
                        int travelTime = (int) Math.ceil((double) (nextStopTime.getArrivalTime() - stopTime.getDepartureTime()) / 60);
                        Pair<Station, Integer> stationWithDistance = new Pair<>(station, travelTime);
                        lineStations.add(stationWithDistance);
                    } else {
                        Pair<Station, Integer> stationWithDistance = new Pair<>(station, 0);
                        lineStations.add(stationWithDistance);
                    }
                }
                // Get or create the Line object for the current shortName
                Line tmpLine = allLines.get(shortName);
                if (tmpLine == null) {
                    tmpLine = new Line(shortName, lineType, lineStations);
                } else {
                    if (lineStations.size() > tmpLine.getStations().size() && new HashSet<>(lineStations).containsAll(tmpLine.getStations()))
                        tmpLine.setStations(lineStations);
                    else
                        continue;
                }
                allLines.put(shortName, tmpLine);
            }

        }
        // Compute neighbours
        computeNeighbours(allLines);

        // Remove unreachable stations or lines = DUMP or
        List<Station> toRemoveStations = new ArrayList<>();
        for (Station s : allStations.values()) if (s.getLines().size() == 0 && s.getNeighbours().size() == 0) toRemoveStations.add(s);
        for (Station s : toRemoveStations) allStations.remove(s.getName());

        List<Line> toRemoveLines = new ArrayList<>();
        for (Line l : allLines.values()) if (l.getStations().size() == 0) toRemoveLines.add(l);
        for (Line l : toRemoveLines) allLines.remove(l.getName());
        return true;
    }

    /**
     * Gets a map of all stations that have been loaded by this loader
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
