package cz.tul.alg2.semestral.transportation;

import cz.tul.alg2.semestral.utilities.Pair;

import java.util.HashSet;
import java.util.Objects;

public class Station {

    private final String name;
    // ZASTAVKA ; ZPŮSOB_DOPRAVY
    private HashSet<Pair<Station, Transport>> neighbours = new HashSet<>();

    public Station(String stationName) {
        this.name = stationName;
    }

    public String getName() {
        return name;
    }

    public HashSet<Pair<Station, Transport>> getNeighbours() {
        return neighbours;
    }

    public void addNeighbour(Pair<Station, Transport> neighbour) {
        this.neighbours.add(neighbour);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null) return false;
        if (getClass() != obj.getClass()) return false;
        Station other = (Station) obj;
        return this.name.equals(other.name);
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }
}
