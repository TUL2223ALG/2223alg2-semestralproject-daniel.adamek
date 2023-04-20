package cz.tul.alg2.semestral.transportation;

import java.util.HashMap;

public class CityTransport {
    private final HashMap<String, Station> stations;
    private final HashMap<String, Line> lines;

    public CityTransport(HashMap<String, Station> stations, HashMap<String, Line> lines) {
        this.stations = stations;
        this.lines = lines;
    }

    public HashMap<String, Station> getStations() {
        return stations;
    }

    public HashMap<String, Line> getLines() {
        return lines;
    }
}
