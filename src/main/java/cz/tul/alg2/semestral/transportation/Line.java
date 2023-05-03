package cz.tul.alg2.semestral.transportation;

import cz.tul.alg2.semestral.utilities.Pair;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class Line {
    private final String name;
    private final TransportationType lineType;
    private List<Pair<Station, Integer>> stations;

    public Line(String name, TransportationType lineType, List<Pair<Station, Integer>> stations) {
        this.name = name;
        this.lineType = lineType;
        this.stations = stations;
    }

    public String getName() {
        return name;
    }

    public TransportationType getLineType() {
        return lineType;
    }

    public List<Pair<Station, Integer>> getStations() {
        return stations;
    }
    public void addStation(Pair<Station, Integer> s) {
        stations.add(s);
    }
    public void setStations(List<Pair<Station, Integer>> list) { stations = list; }

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
