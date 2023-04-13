package cz.tul.alg2.semestral.transportation;

import java.util.LinkedList;

/**
 * An interface for a transportation route.
 */
public interface IRoute {
    /**
     * Returns the name of the route.
     *
     * @return The name of the route.
     */
    String getName();

    /**
     * Returns a linked list of stations on the route.
     *
     * @return A linked list of stations.
     */
    LinkedList<Station> getStations();

    /**
     * Adds a station to the beginning of the route.
     *
     * @param station The station to add.
     */
    void addStation(Station station);
}