package cz.tul.alg2.semestral.file;

import cz.tul.alg2.semestral.transportation.CityTransport;
import cz.tul.alg2.semestral.utilities.PathBuilder;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TextLoaderTest {
    @Test
    void testPIDTextLoader() {
        GTFSLoader gtfs = new GTFSLoader();
        gtfs.loadFile(PathBuilder.joinPath(new String[]{"data", "pid-gtfs"}));
        CityTransport pid = new CityTransport(gtfs.getAllStations(), gtfs.getAllLines());

        TextSaver ts = new TextSaver(pid);
        TextLoader tl = new TextLoader();

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



    }
}