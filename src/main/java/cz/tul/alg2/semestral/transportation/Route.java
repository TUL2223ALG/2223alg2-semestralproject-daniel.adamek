package cz.tul.alg2.semestral.transportation;

import java.util.LinkedList;

public class Route {
    private final String name;
    private final TypeOfTransportation type;
    private final LinkedList<Station> stations = new LinkedList<>();

    public Route(String name, TypeOfTransportation type) {
        this.name = name;
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public TypeOfTransportation getType() {
        return type;
    }

    public LinkedList<Station> getStations() {
        return stations;
    }
    public void addStation(Station station) {
        this.stations.addFirst(station);
    }
}
