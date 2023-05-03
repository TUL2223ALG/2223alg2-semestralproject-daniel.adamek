package cz.tul.alg2.semestral.file;

import cz.tul.alg2.semestral.transportation.CityTransport;
import cz.tul.alg2.semestral.utilities.PathBuilder;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotSame;

class BinaryLoaderTest {
    @Test
    void testPIDBinaryLoader() {
        GTFSLoader gtfs = new GTFSLoader();
        gtfs.loadFile(PathBuilder.joinPath("data", "PID_GTFS.zip"));
        CityTransport transport = new CityTransport(gtfs.getAllStations(), gtfs.getAllLines());

        BinarySaver bs = new BinarySaver(transport);
        BinaryLoader bl = new BinaryLoader();
        assertEquals(0, bl.allStations.size());
        assertNotSame(bl.allStations, gtfs.getAllStations());
        assertEquals(0, bl.allStations.size());
        assertNotSame(bl.allLines, gtfs.getAllLines());

        try {
            File tempFile = File.createTempFile("tempFile_", ".ser");

            bs.saveTransport(tempFile.getAbsolutePath());

            bl.loadFile(tempFile.getAbsolutePath());
            tempFile.deleteOnExit();
        } catch (IOException e) {
            e.printStackTrace();
        }

        assertEquals(transport.stations(), bl.getAllStations());
        assertEquals(transport.lines(), bl.getAllLines());
    }
}