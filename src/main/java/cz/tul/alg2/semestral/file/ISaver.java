package cz.tul.alg2.semestral.file;

import cz.tul.alg2.semestral.transportation.CityTransport;

import java.io.IOException;

public interface ISaver {
    CityTransport transport = null;

    void saveTransport(String path) throws IOException;
}
