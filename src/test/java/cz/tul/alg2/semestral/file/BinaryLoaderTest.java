package cz.tul.alg2.semestral.file;

import cz.tul.alg2.semestral.transportation.CityTransport;
import cz.tul.alg2.semestral.transportation.Station;
import cz.tul.alg2.semestral.utilities.PathBuilder;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class BinaryLoaderTest {
    @Test
    void testPIDBinaryLoader() {
        GTFSLoader gtfs = new GTFSLoader();
        gtfs.loadFile(PathBuilder.joinPath("data", "pid-gtfs"));
        CityTransport transport = new CityTransport(gtfs.getAllStations(), gtfs.getAllLines());

        BinarySaver bs = new BinarySaver(transport);
        BinaryLoader bl = new BinaryLoader();
        assertEquals(0, bl.allStations.size());
        assertNotSame(bl.allStations, gtfs.allStations);
        assertEquals(0, bl.allStations.size());
        assertNotSame(bl.allLines, gtfs.allLines);

        try {
            File tempFile = File.createTempFile("tempFile_", ".ser");

            bs.saveTransport(tempFile.getAbsolutePath());

            bl.loadFile(tempFile.getAbsolutePath());
            tempFile.deleteOnExit();
        } catch (IOException e) {
            e.printStackTrace();
        }

        assertEquals(gtfs.getAllLines().toString(), bl.getAllLines().toString());
        assertEquals(gtfs.getAllStations().toString(), bl.getAllStations().toString());

        int zeroLinesCount = 0;
        for (Station station: gtfs.getAllStations().values()) if (station.getLines().size() != 0) zeroLinesCount++;

        // Same
        assertNotSame(bl.allStations, gtfs.allStations);
        assertEquals(bl.allStations.toString(), gtfs.allStations.toString());
        assertNotSame(bl.allLines, gtfs.allLines);
        assertEquals(bl.allLines.toString(), gtfs.allLines.toString());

        // Same size on content
        assertNotEquals(0, zeroLinesCount);
        assertEquals(bl.getAllStations().size(), zeroLinesCount);



    }
}