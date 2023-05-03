package cz.tul.alg2.semestral.pathfinding;

import cz.tul.alg2.semestral.transportation.Line;
import cz.tul.alg2.semestral.transportation.Station;
import cz.tul.alg2.semestral.utilities.Pair;

import java.util.List;
import java.util.Set;

/**
 * The type Path segment.
 */
public class PathSegment {
    private final Set<Line> lines;
    private final List<Pair<Station, Integer>> stations;

    /**
     * Instantiates a new Path segment.
     *
     * @param lines    the lines
     * @param stations the stations
     */
    public PathSegment(Set<Line> lines, List<Pair<Station, Integer>> stations) {
        this.lines = lines;
        this.stations = stations;
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
     * Gets stations.
     *
     * @return the stations
     */
    public List<Pair<Station, Integer>> getStations() {
        return stations;
    }

    /**
     * The toString function is used to print out the contents of a PathSegment object.
     *
     * @return The lines and stations that are in the pathsegment
     */
    @Override
    public String toString() {
        return "PathSegment{" +
                "lines=" + lines +
                ", stations=" + stations +
                '}';
    }
}
