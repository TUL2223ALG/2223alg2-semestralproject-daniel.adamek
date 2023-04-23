package cz.tul.alg2.semestral.transportation;

import java.util.HashMap;

public record CityTransport(HashMap<String, Station> stations, HashMap<String, Line> lines) {
}
