package cz.tul.alg2.semestral.file;

import cz.tul.alg2.semestral.transportation.CityTransport;
import cz.tul.alg2.semestral.utilities.PathBuilder;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

class BinaryLoaderTest {
    @Test
    void testPIDBinaryLoader() {
        GTFSLoader gtfs = new GTFSLoader();
        gtfs.loadFile(PathBuilder.joinPath(new String[]{"data", "pid-gtfs"}));
        CityTransport pid = new CityTransport(gtfs.getAllStations(), gtfs.getAllLines());

        BinarySaver bs = new BinarySaver(pid);
        BinaryLoader bl = new BinaryLoader();

        try {
            File tempFile = File.createTempFile("tempFile_", ".ser");

            bs.saveTransport(tempFile.getAbsolutePath());

            bl.loadFile(tempFile.getAbsolutePath());
            tempFile.deleteOnExit();
        } catch (IOException e) {
            e.printStackTrace();
        }

        assertEquals(bl.getAllStations().toString(), gtfs.getAllStations().toString());
        assertEquals(bl.getAllLines().toString(), gtfs.getAllLines().toString());



    }
}