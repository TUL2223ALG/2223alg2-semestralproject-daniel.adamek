package cz.tul.alg2.semestral.transportation;

import cz.tul.alg2.semestral.utilities.Pair;
import cz.tul.alg2.semestral.utilities.TextNormalization;

import java.util.HashSet;

public class Station {

    private final String name;
    private final String prettyName;
    private final HashSet<Route> routes = new HashSet<>();
    // ZASTAVKA ; ZPŮSOB_DOPRAVY
    private final HashSet<Pair<Station, Transport>> neighbours = new HashSet<>();

    public Station(String stationName) {
        this.prettyName = stationName;
        this.name = TextNormalization.stringNormalize(stationName);
    }

    public String getName() {
        return name;
    }

    public String getPrettyName() {
        return prettyName;
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

    @Override
    public String toString() {
        return "Station{" +
                "name='" + prettyName + '\'' +
                '}';
    }
}
