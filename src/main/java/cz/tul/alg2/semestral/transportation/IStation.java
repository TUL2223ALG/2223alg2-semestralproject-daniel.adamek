package cz.tul.alg2.semestral.transportation;

import cz.tul.alg2.semestral.utilities.Pair;

import java.util.HashSet;

public interface IStation {

    /**
     * Returns normalized name of station.
     */
    String getName();

    /**
     * Returns "pretty" name of station
     */
    String getPrettyName();

    HashSet<ARoute> getRoutes();
    public void addRoute(ARoute route);

    /**
     * Returns neighbour stations of this station
     */
    HashSet<Pair<IStation, Transport>> getNeighbours();

    /**
     * Adds neighbour to this station and type of transportation
     */
    void addNeighbour(Pair<IStation, Transport> neighbour);
}
