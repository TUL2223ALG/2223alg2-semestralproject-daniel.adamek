package cz.tul.alg2.semestral.transportation;

import cz.tul.alg2.semestral.utilities.Pair;
import cz.tul.alg2.semestral.utilities.TextNormalization;

import java.util.Comparator;
import java.util.Objects;
import java.util.Set;
import java.util.TreeSet;

public class Station implements Comparable<Station> {

    private final String name;
    private final String prettyName;
    private String zoneID = null;
    private final Set<Line> lines = new TreeSet<>(Comparator.comparing(Line::getName));
    private final Set<Pair<Station, Integer>> neighbours = new TreeSet<>(Comparator.comparing((Pair<Station, Integer> obj) -> obj.first));

    public Station(String stationName, String zoneID) {
        this.prettyName = stationName;
        this.name = TextNormalization.stringNormalize(stationName);
        this.zoneID = zoneID;
    }public Station(String stationName) {
        this.prettyName = stationName;
        this.name = TextNormalization.stringNormalize(stationName);
    }

    public String getName() {
        return name;
    }

    public String getPrettyName() {
        return prettyName;
    }

    public Set<Line> getLines() {
        return lines;
    }
    public void addRoute(Line line) {
        this.lines.add(line);
    }

    public Set<Pair<Station, Integer>> getNeighbours() {
        return neighbours;
    }

    public void addNeighbour(Pair<Station, Integer> neighbour) {
        this.neighbours.add(neighbour);
    }

    public String getZoneID() {
        return zoneID;
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
        return Objects.hash(name);
    }

    @Override
    public String toString() {
        return "Station{" +
                "name='" + prettyName + '\'' +
                '}';
    }

    /**
     * Comarator
     * @param station 
     * @return
     */
    @Override
    public int compareTo(Station station) {
        return this.name.compareTo(station.name);
    }

}
