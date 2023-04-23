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

public class TextSaver implements ISaver {
    private final CityTransport transport;

    /**
     * @param transport
     */
    public TextSaver(CityTransport transport) {
        this.transport = transport;
    }

    @Override
    public void saveTransport(String path) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(path))) {
            // Saving stations
            writer.write("STATIONS\n");
            HashMap<Station, Integer> substitutionMap = new HashMap<>();
            for (Station station : transport.getStations().values()) {
                writer.write(
                    substitutionMap.size() + "|" +
                    station.getPrettyName() + "|" +
                    station.getZoneID() + "|"
                );
                substitutionMap.put(station, substitutionMap.size());
                writer.write("\n");
            }
            // Save neighbours
            writer.write("NEIGHBOURS\n");
            for (Station station : transport.getStations().values()) {
                writer.write(substitutionMap.get(station) + "|");
                for (Pair<Station, Integer> neighbour : station.getNeighbours()) {
                    writer.write( substitutionMap.get(neighbour.first) + "," + neighbour.second/60 + ";");
                }
                writer.write("\n");
            }

            // Saving lines
            writer.write("LINES\n");
            for (Line line : transport.getLines().values()) {
                writer.write(line.getName() + "|" + line.getLineType() + "|");
                for (Station station : line.getStations()) {
                    writer.write(substitutionMap.get(station) + ",");
                }
                writer.write("\n");
            }

            System.out.println("Data uložena do souboru " + path);
        } catch (IOException e) {
            new ErrorLogger("error.log").logError("Error while saving data", e);
        }
    }
}
