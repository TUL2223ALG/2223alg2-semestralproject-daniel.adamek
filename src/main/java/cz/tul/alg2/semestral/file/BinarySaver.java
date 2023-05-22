package cz.tul.alg2.semestral.file;

import cz.tul.alg2.semestral.errorhandle.ErrorLogger;
import cz.tul.alg2.semestral.transportation.CityTransport;
import cz.tul.alg2.semestral.transportation.Line;
import cz.tul.alg2.semestral.transportation.Station;
import cz.tul.alg2.semestral.utilities.Pair;

import java.io.DataOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;

/**
 * The type Binary saver.
 */
public class BinarySaver implements ISaver {
    /**
     * The Transport.
     */
    private final CityTransport transport;

    /**
     * Instantiates a new Binary saver.
     *
     * @param transport the transport
     */
    public BinarySaver(CityTransport transport) {
        this.transport = transport;
    }

    /**
     * The saveTransport function saves the transport object to a file.
     *
     * @param path the path
     * @return A boolean value
     */
    @Override
    public boolean saveTransport(String path) {
        try (DataOutputStream writer = new DataOutputStream(new FileOutputStream(path))) {
            // Saving stations
            writer.writeUTF("STATIONS");
            HashMap<Station, Integer> substitutionMap = new HashMap<>();
            writer.writeInt(transport.stations().size());
            for (Station station : transport.stations().values()) {
                writer.writeInt(substitutionMap.size());
                writer.writeUTF(station.getPrettyName());
                writer.writeUTF(station.getZoneID());
                substitutionMap.put(station, substitutionMap.size());
            }

            // Saving lines
            writer.writeUTF("LINES");
            writer.writeInt(transport.lines().size());
            for (Line line : transport.lines().values()) {
                writer.writeUTF(line.getName());
                writer.writeUTF(line.getLineType().name());
                writer.writeInt(line.getStations().size());
                for (Pair<Station, Integer> station : line.getStations()) {
                    writer.writeInt(substitutionMap.get(station.first));
                    writer.writeInt(station.second);
                }
            }
        } catch (IOException e) {
            new ErrorLogger("error.log").logError("Chyba při ukládání dat", e);
            return false;
        }
        return true;
    }
}
