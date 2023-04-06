package cz.tul.alg2.semestral.transportation;

import cz.tul.alg2.semestral.utilities.Pair;

import java.util.HashSet;

public class Station {
    private final String name;
    // ZASTAVKA ; ZPŮSOB_DOPRAVY
    private HashSet<Pair<Station, Transport>> neighbours = new HashSet<>();

    public Station(String stationName) {
        this.name = stationName;
    }

    public HashSet<Pair<Station, Transport>> getNeighbours() {
        return neighbours;
    }

    public void addNeighbour(Pair<Station, Transport> neighbour) {
        this.neighbours.add(neighbour);
    }
}
