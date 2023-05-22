package cz.tul.alg2.semestral.pathfinding;

import cz.tul.alg2.semestral.transportation.Station;

import java.util.List;

/**
 * The interface Path finder.
 */
public interface PathFinder {

    /**
     * Find the shortest path list.
     *
     * @param start the start
     * @param end   the end
     * @return the list
     */
    List<PathSegment> findShortestPath(String start, String end);

    /**
     * Find the shortest path list.
     *
     * @param start the start
     * @param end   the end
     * @return the list
     */
    List<PathSegment> findShortestPath(Station start, Station end);
}