package cz.tul.alg2.semestral.file;

import cz.tul.alg2.semestral.transportation.CityTransport;
import cz.tul.alg2.semestral.transportation.Station;
import cz.tul.alg2.semestral.utilities.PathBuilder;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class TextLoaderTest {
    @Test
    void testPIDTextLoader() {
        GTFSLoader gtfs = new GTFSLoader();
        gtfs.loadFile(PathBuilder.joinPath("data", "pid-gtfs"));
        CityTransport pid = new CityTransport(gtfs.getAllStations(), gtfs.getAllLines());

        TextSaver ts = new TextSaver(pid);
        TextLoader tl = new TextLoader();
        assertEquals(0, tl.allStations.size());
        assertNotSame(tl.allStations, gtfs.allStations);
        assertEquals(0, tl.allStations.size());
        assertNotSame(tl.allLines, gtfs.allLines);

        try {
            File tempFile = File.createTempFile("tempFile_", ".txt");

            ts.saveTransport(tempFile.getAbsolutePath());

            tl.loadFile(tempFile.getAbsolutePath());
            tempFile.deleteOnExit();
        } catch (IOException e) {
            e.printStackTrace();
        }

        assertEquals(tl.getAllStations().toString(), gtfs.getAllStations().toString());
        assertEquals(tl.getAllLines().toString(), gtfs.getAllLines().toString());

        int zeroLinesCount = 0;
        for (Station station: gtfs.getAllStations().values()) if (station.getLines().size() != 0) zeroLinesCount++;

        // Same
        assertNotSame(tl.allStations, gtfs.allStations);
        assertEquals(tl.allStations.toString(), gtfs.allStations.toString());
        assertNotSame(tl.allLines, gtfs.allLines);
        assertEquals(tl.allLines.toString(), gtfs.allLines.toString());

        // Same size of content
        assertNotEquals(0, zeroLinesCount);
        assertEquals(tl.getAllStations().size(), zeroLinesCount);

    }
}