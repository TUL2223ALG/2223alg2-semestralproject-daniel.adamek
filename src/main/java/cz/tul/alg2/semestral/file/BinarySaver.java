package cz.tul.alg2.semestral.file;

import cz.tul.alg2.semestral.errorhandle.ErrorLogger;
import cz.tul.alg2.semestral.transportation.CityTransport;
import cz.tul.alg2.semestral.transportation.Line;
import cz.tul.alg2.semestral.transportation.Station;
import cz.tul.alg2.semestral.utilities.Pair;

import java.io.BufferedOutputStream;
import java.io.DataOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;

public class BinarySaver implements ISaver {
    private final CityTransport transport;

    public BinarySaver(CityTransport transport) {
        this.transport = transport;
    }

    @Override
    public void saveTransport(String path) {
        try (DataOutputStream outputStream = new DataOutputStream(new BufferedOutputStream(new FileOutputStream(path)))) {
            // Saving stations
            outputStream.writeInt(transport.stations().size());
            HashMap<Station, Integer> substitutionMap = new HashMap<>(transport.stations().size());
            for (Station station : transport.stations().values()) {
                outputStream.writeInt(substitutionMap.size());
                outputStream.writeUTF(station.getPrettyName());
                outputStream.writeUTF(station.getZoneID() != null ? station.getZoneID() : "");
                substitutionMap.put(station, substitutionMap.size());
            }
            // Save neighbours
            for (Station station : transport.stations().values()) {
                outputStream.writeInt(substitutionMap.get(station));
                outputStream.writeInt(station.getNeighbours().size());
                for (Pair<Station, Integer> neighbour : station.getNeighbours()) {
                    outputStream.writeInt(substitutionMap.get(neighbour.first));
                    outputStream.writeInt(neighbour.second);
                }
            }

            // Saving lines
            outputStream.writeInt(transport.lines().size());
            for (Line line : transport.lines().values()) {
                outputStream.writeUTF(line.getName());
                outputStream.writeUTF(line.getLineType().name());
                outputStream.writeInt(line.getStations().size());
                for (Station station : line.getStations()) {
                    outputStream.writeInt(substitutionMap.get(station));
                }
            }

            System.out.println("Data uložena do souboru " + path);
        } catch (IOException e) {
            new ErrorLogger("error.log").logError("Error while saving data", e);
        }
    }
}
