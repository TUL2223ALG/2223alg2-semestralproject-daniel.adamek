package cz.tul.alg2.semestral.file;

import cz.tul.alg2.semestral.transportation.CityTransport;

/**
 * The interface Saver.
 */
public interface ISaver {
    /**
     * The constant transport.
     */
    CityTransport transport = null;

    /**
     * Save transport boolean.
     *
     * @param path the path
     * @return the boolean
     */
    boolean saveTransport(String path);
}
