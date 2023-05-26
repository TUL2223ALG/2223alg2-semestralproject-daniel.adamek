package cz.tul.alg2.semestral.file;

import cz.tul.alg2.semestral.errorhandle.ErrorLogger;
import cz.tul.alg2.semestral.transportation.CityTransport;
import cz.tul.alg2.semestral.transportation.Line;
import cz.tul.alg2.semestral.transportation.Station;
import cz.tul.alg2.semestral.utilities.Pair;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;

/**
 * The type Text saver.
 */
public class TextSaver implements ISaver {

    /**
     * The Transport.
     */
    private final CityTransport transport;

    /**
     * Instantiates a new Text saver.
     *
     * @param transport the transport
     */
    public TextSaver(CityTransport transport) {
        this.transport = transport;
    }

    /**
     * The saveTransport function saves the transport data to a file.
     *
     * @param path Specify the path to the file where we want to save our transport data
     * @return A boolean value
     */
    @Override
    public boolean saveTransport(String path) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(path))) {
            // Saving stations
            writer.write("STATIONS\n");

            // Create substitution map for name -> ID
            HashMap<Station, Integer> substitutionMap = new HashMap<>();

            // Save each station
            for (Station station : transport.stations().values()) {

                // Save ID = size of substitution map, name and zoneID
                writer.write(
                    substitutionMap.size() + "|" +
                    station.getPrettyName() + "|" +
                    station.getZoneID() + "|"
                );

                // save station to substitution map
                substitutionMap.put(station, substitutionMap.size());

                // new line to every station
                writer.write("\n");
            }

            // Saving lines
            writer.write("LINES\n");

            // Save every line
            for (Line line : transport.lines().values()) {

                // Save name, type and count of stations
                writer.write(line.getPrettyName() + "|" + line.getLineType() + "|");

                // Save the stations with the IDs due to substitution and time of shift
                // to the next station
                for (Pair<Station, Integer> station : line.getStations())
                    writer.write(substitutionMap.get(station.first) + "," + station.second + ";");
                writer.write("\n");
            }
        } catch (IOException e) {
            new ErrorLogger("error.log").logError("Chyba při ukládání dat", e);
            return false;
        }
        return true;
    }
}
