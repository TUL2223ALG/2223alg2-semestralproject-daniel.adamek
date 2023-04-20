package cz.tul.alg2.semestral.transportation;

import java.util.List;
import java.util.Objects;

public class Line {
    private final String name;
    private final transportationType lineType;
    private final List<Station> stations;

    public Line(String name, transportationType lineType, List<Station> stations) {
        this.name = name;
        this.lineType = lineType;
        this.stations = stations;
    }

    public String getName() {
        return name;
    }

    public transportationType getLineType() {
        return lineType;
    }

    public List<Station> getStations() {
        return stations;
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}
