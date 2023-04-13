package cz.tul.alg2.semestral.transportation;

import java.util.LinkedList;

public abstract class ARoute {
    private final String name;
    private final LinkedList<Station> stations = new LinkedList<>();

    public ARoute(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public LinkedList<Station> getStations() {
        return stations;
    }
    public void addStation(Station station) {
        this.stations.addFirst(station);
    }

    public abstract String getPrettyName();
}
