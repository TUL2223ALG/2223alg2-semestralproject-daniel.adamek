package cz.tul.alg2.semestral.pathfinding;

import java.util.List;

/**
 * The interface Path finder.
 */
public interface PathFinder {

    /**
     * Find shortest path list.
     *
     * @param start the start
     * @param end   the end
     * @return the list
     */
    List<PathSegment> findShortestPath(String start, String end);
}