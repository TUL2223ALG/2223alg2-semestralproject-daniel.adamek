package cz.tul.alg2.semestral.file;

import cz.tul.alg2.semestral.transportation.CityTransport;

public interface ISaver {
    CityTransport transport = null;

    boolean saveTransport(String path);
}
