package cz.tul.alg2.semestral.transportation;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class Line {
    private final String name;
    private final TransportationType lineType;
    private final List<Station> stations;

    public Line(String name, TransportationType lineType, List<Station> stations) {
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

    public List<Station> getStations() {
        return stations;
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    @Override
    public String toString() {
        String stationsToString = stations.stream().map(s -> s.getName() + ",").collect(Collectors.joining());
        return "Line{" +
                "name='" + name + '\'' +
                ", lineType=" + lineType +
                ", stations={" + stationsToString +
                "}}";
    }
}
