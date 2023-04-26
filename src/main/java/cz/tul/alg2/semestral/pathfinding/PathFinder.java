package cz.tul.alg2.semestral.pathfinding;

import java.util.List;

public interface PathFinder {

    List<PathSegment> findShortestPath(String start, String end);
}