package cz.tul.alg2.semestral.transportation;

import cz.tul.alg2.semestral.utilities.Pair;
import cz.tul.alg2.semestral.utilities.TextNormalization;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * The type Line.
 */
public class Line implements Comparable<Line>{
    /**
     * The Name.
     */
    private final String name;
    /**
     * The Pretty name.
     */
    private final String prettyName;
    /**
     * The Line type.
     */
    private final TransportationType lineType;
    /**
     * The Stations.
     */
    private List<Pair<Station, Integer>> stations;

    /**
     * Instantiates a new Line.
     *
     * @param name     the name
     * @param lineType the line type
     * @param stations the stations
     */
    public Line(String name, TransportationType lineType, List<Pair<Station, Integer>> stations) {
        this.prettyName = name;
        this.name = TextNormalization.stringNormalize(name);
        this.lineType = lineType;
        this.stations = stations;
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
    public String getPrettyName() { return prettyName; }

    /**
     * Gets line type.
     *
     * @return the line type
     */
    public TransportationType getLineType() {
        return lineType;
    }

    /**
     * Gets stations.
     *
     * @return the stations
     */
    public List<Pair<Station, Integer>> getStations() {
        return stations;
    }

    /**
     * Add station.
     *
     * @param s the s
     */
    public void addStation(Pair<Station, Integer> s) {
        stations.add(s);
    }

    /**
     * Sets stations.
     *
     * @param list the list
     */
    public void setStations(List<Pair<Station, Integer>> list) { stations = list; }

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
        Line other = (Line) obj;
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
        String stationsToString = stations.stream().map(s -> s.first.getName() + "," + s.second + ";").collect(Collectors.joining());
        return "Line{" +
                "name='" + name + '\'' +
                ", lineType=" + lineType +
                ", stations={" + stationsToString +
                "}}";
    }

    /**
     * Comarator
     *
     * @param line the line
     * @return int
     */
    @Override
    public int compareTo(Line line) {
        return this.name.compareTo(line.name);
    }
}
