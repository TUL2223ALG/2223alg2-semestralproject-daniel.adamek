package cz.tul.alg2.semestral.file;

import cz.tul.alg2.semestral.transportation.CityTransport;
import cz.tul.alg2.semestral.utilities.PathBuilder;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class TextLoaderTest {
    @Test
    void testPIDTextLoader() {
        GTFSLoader gtfs = new GTFSLoader();
        gtfs.loadFile(PathBuilder.joinPath("data", "PID_GTFS.zip"));
        CityTransport transport = new CityTransport(gtfs.getAllStations(), gtfs.getAllLines());

        TextSaver ts = new TextSaver(transport);
        TextLoader tl = new TextLoader();
        assertEquals(0, tl.getAllStations().size());
        assertNotSame(tl.getAllStations(), gtfs.getAllStations());
        assertEquals(0, tl.getAllStations().size());
        assertNotSame(tl.getAllLines(), gtfs.getAllLines());

        try {
            File tempFile = File.createTempFile("tempFile_", ".txt");

            ts.saveTransport(tempFile.getAbsolutePath());

            tl.loadFile(tempFile.getAbsolutePath());
            tempFile.deleteOnExit();
        } catch (IOException e) {
            e.printStackTrace();
        }

        assertEquals(transport.stations(), tl.getAllStations());
        assertEquals(transport.lines(), tl.getAllLines());
    }
}