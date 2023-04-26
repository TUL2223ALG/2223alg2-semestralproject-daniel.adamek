package cz.tul.alg2.semestral.pathfinding;

import cz.tul.alg2.semestral.transportation.Line;
import cz.tul.alg2.semestral.transportation.Station;
import cz.tul.alg2.semestral.utilities.Pair;

import java.util.List;
import java.util.Set;

public class PathSegment {
    private final Set<Line> lines;
    private final List<Pair<Station, Integer>> stations;

    public PathSegment(Set<Line> lines, List<Pair<Station, Integer>> stations) {
        this.lines = lines;
        this.stations = stations;
    }

    public Set<Line> getLines() {
        return lines;
    }

    public List<Pair<Station, Integer>> getStations() {
        return stations;
    }

    @Override
    public String toString() {
        return "PathSegment{" +
                "lines=" + lines +
                ", stations=" + stations +
                '}';
    }
}
