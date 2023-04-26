package cz.tul.alg2.semestral.file;

import cz.tul.alg2.semestral.transportation.Line;
import cz.tul.alg2.semestral.transportation.Station;

import java.util.HashMap;

public interface ILoader {

    /**
     * Returns a list of file paths that end with ".txt" in the specified directory path.
     *
     * @param path the path of the directory to search for files
     */
    void loadFile(String path);

    /**
     * Gets a map of all stations that have been loaded by this loader.
     *
     * @return a map of all stations that have been loaded by this route loader
     */
    HashMap<String, Station> getAllStations();
    /**
     * Gets a map of all lines that have been loaded by this loader.
     *
     * @return a map of all lines that have been loaded by this loader
     */
    HashMap<String, Line> getAllLines();

}
