package cz.tul.alg2.semestral.file;

import cz.tul.alg2.semestral.exception.InvalidFileFormatException;
import cz.tul.alg2.semestral.transportation.IStation;
import cz.tul.alg2.semestral.transportation.TypeOfTransportation;

import java.util.HashMap;
import java.util.List;

public interface IRouteLoader {

    /**
     * Returns a list of file paths that end with ".txt" in the specified directory path.
     *
     * @param path the path of the directory to search for files
     * @return a list of file paths that end with ".txt" in the specified directory path
     */
    List<String> loadFiles(String path);

    /**
     * Loads a route from the specified file path with the specified type of transportation.
     *
     * @param path the path of the file to load the route from
     * @param typeOfTransportation the type of transportation of the route
     * @throws InvalidFileFormatException if there is an error while loading the file format
     */
    void loadRoute(String path, TypeOfTransportation typeOfTransportation) throws InvalidFileFormatException;

    /**
     * Gets a map of all stations that have been loaded by this route loader.
     *
     * @return a map of all stations that have been loaded by this route loader
     */
    HashMap<String, IStation> getAllStations();

}
