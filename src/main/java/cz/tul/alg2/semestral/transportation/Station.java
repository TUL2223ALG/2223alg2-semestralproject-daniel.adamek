package cz.tul.alg2.semestral.transportation;

import cz.tul.alg2.semestral.utilities.Pair;
import cz.tul.alg2.semestral.utilities.TextNormalization;

import java.util.*;
import java.util.stream.Collectors;

/**
 * The type Station.
 */
public class Station implements Comparable<Station> {

    /**
     * The Name.
     */
    private final String name;
    /**
     * The Pretty name.
     */
    private final String prettyName;
    /**
     * The Zone id.
     */
    private String zoneID = null;
    /**
     * The Lines.
     */
    private final Set<Line> lines = new TreeSet<>(Comparator.comparing(Line::getName));
    /**
     * The Neighbours.
     */
    private final Set<Pair<Station, Integer>> neighbours = new TreeSet<>(Comparator.comparing((Pair<Station, Integer> obj) -> obj.first));

    /**
     * Instantiates a new Station.
     *
     * @param stationName the station name
     * @param zoneID      the zone id
     */
    public Station(String stationName, String zoneID) {
        this.prettyName = stationName;
        this.name = TextNormalization.stringNormalize(stationName);
        this.zoneID = zoneID;
    }

    /**
     * Instantiates a new Station.
     *
     * @param stationName the station name
     */
    public Station(String stationName) {
        this.prettyName = stationName;
        this.name = TextNormalization.stringNormalize(stationName);
    }

    /**
     * Gets name.
     *
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * Gets pretty name.
     *
     * @return the pretty name
     */
    public String getPrettyName() {
        return prettyName;
    }

    /**
     * Gets lines.
     *
     * @return the lines
     */
    public Set<Line> getLines() {
        return lines;
    }

    /**
     * Add line.
     *
     * @param line the line
     */
    public void addLine(Line line) {
        this.lines.add(line);
    }

    /**
     * Remove line.
     *
     * @param line the line
     */
    public void removeLine(Line line) {
        this.lines.remove(line);
        this.neighbours.clear();

        /*
        for (Line l : this.lines) {
            for (int i = 0; i < l.getStations().size(); i++) {
                if (l.getStations().get(i).name.equals(this.name)) {
                    if (i > 0)
                        this.neighbours
                }
            }
        }

         */
    }

    /**
     * Gets neighbours.
     *
     * @return the neighbours
     */
    public Set<Pair<Station, Integer>> getNeighbours() {
        return neighbours;
    }

    /**
     * Add neighbour.
     *
     * @param neighbour the neighbour
     */
    public void addNeighbour(Pair<Station, Integer> neighbour) {
        this.neighbours.add(neighbour);
    }

    /**
     * Gets zone id.
     *
     * @return the zone id
     */
    public String getZoneID() {
        return (zoneID == null) ? "-" : zoneID ;
    }

    /**
     * Equals boolean.
     *
     * @param obj the obj
     * @return the boolean
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null) return false;
        if (getClass() != obj.getClass()) return false;
        Station other = (Station) obj;
        return this.name.equals(other.name);
    }

    /**
     * Hash code int.
     *
     * @return the int
     */
    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    /**
     * To string string.
     *
     * @return the string
     */
    @Override
    public String toString() {
        String linesToString = lines.stream().map(l -> l.getName() + ",").collect(Collectors.joining());
        String neighboursToString = neighbours.stream().map(n -> n.first.getName() + ",").collect(Collectors.joining());
        return "Station{" +
                "name='" + name + '\'' +
                ", prettyName='" + prettyName + '\'' +
                ", zoneID='" + zoneID + '\'' +
                ", lines={" + linesToString +
                "}, neighbours={" + neighboursToString +
                "}}";
    }

    /**
     * Comarator
     *
     * @param station the station
     * @return int
     */
    @Override
    public int compareTo(Station station) {
        return this.name.compareTo(station.name);
    }

}
