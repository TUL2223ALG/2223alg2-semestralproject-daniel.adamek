package cz.tul.alg2.semestral.transportation;

import cz.tul.alg2.semestral.utilities.Pair;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * The type Line.
 */
public class Line {
    private final String name;
    private final TransportationType lineType;
    private List<Pair<Station, Integer>> stations;

    /**
     * Instantiates a new Line.
     *
     * @param name     the name
     * @param lineType the line type
     * @param stations the stations
     */
    public Line(String name, TransportationType lineType, List<Pair<Station, Integer>> stations) {
        this.name = name;
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

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null) return false;
        if (getClass() != obj.getClass()) return false;
        Line other = (Line) obj;
        return this.name.equals(other.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    @Override
    public String toString() {
        String stationsToString = stations.stream().map(s -> s.first.getName() + "," + s.second + ";").collect(Collectors.joining());
        return "Line{" +
                "name='" + name + '\'' +
                ", lineType=" + lineType +
                ", stations={" + stationsToString +
                "}}";
    }
}
