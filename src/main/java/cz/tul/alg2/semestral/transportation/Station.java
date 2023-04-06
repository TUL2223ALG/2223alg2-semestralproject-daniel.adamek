package cz.tul.alg2.semestral.transportation;

import cz.tul.alg2.semestral.utilities.Pair;

import java.util.HashSet;

public class Station {
    private final String name;
    // ZASTAVKA ; ZPŮSOB_DOPRAVY
    private HashSet<Pair<Station, String>> neighbours = new HashSet<>();

    public Station(String stationName) {
        this.name = stationName;
    }

    public HashSet<Pair<Station, String>> getNeighbours() {
        return neighbours;
    }

    public void addNeighbour(Pair<Station, String> neighbour) {
        this.neighbours.add(neighbour);
    }
}
