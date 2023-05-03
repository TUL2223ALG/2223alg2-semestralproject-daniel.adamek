package cz.tul.alg2.semestral.transportation;

import java.util.HashMap;

/**
 * The type City transport.
 */
public record CityTransport(HashMap<String, Station> stations, HashMap<String, Line> lines) {
}
