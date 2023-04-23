package cz.tul.alg2.semestral.file;

import cz.tul.alg2.semestral.transportation.CityTransport;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TextLoaderTest {
    @Test
    void testEmptyStrings() {
        GTFSLoader gtfs = new GTFSLoader();
        gtfs.loadFile("pid-gtfs");
        CityTransport pid = new CityTransport(gtfs.getAllStations(), gtfs.getAllLines());

        TextSaver ts = new TextSaver(pid);
        ts.saveTransport("pid-tmp.txt");

        TextLoader tl = new TextLoader();
        tl.loadFile("pid-tmp.txt");

        assertEquals(tl.getAllStations().toString(), gtfs.getAllStations().toString());
        assertEquals(tl.getAllLines().toString(), gtfs.getAllLines().toString());



    }
}