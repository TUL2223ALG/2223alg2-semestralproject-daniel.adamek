package cz.tul.alg2.semestral.userinterface;

import cz.tul.alg2.semestral.file.ILoader;
import cz.tul.alg2.semestral.pathfinding.BFS;
import cz.tul.alg2.semestral.pathfinding.PathFinder;
import cz.tul.alg2.semestral.pathfinding.PathSegment;
import cz.tul.alg2.semestral.transportation.CityTransport;
import cz.tul.alg2.semestral.transportation.Line;
import cz.tul.alg2.semestral.transportation.Station;
import cz.tul.alg2.semestral.transportation.TransportationType;
import cz.tul.alg2.semestral.utilities.LangFormatter;
import cz.tul.alg2.semestral.utilities.Pair;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Menu {
    ILoader loader;
    CityTransport transport;
    Scanner sc = new Scanner(System.in);
    StringBuilder sb = new StringBuilder();
    InteractiveGetter ig;

    /**
     * The Menu function is the main function of this class. It displays a menu
     * to the user, and allows them to choose from a list of options.
     *
     * @param loader Load the data from a file
     */
    public Menu(ILoader loader) {
        this.loader = loader;
        this.transport = new CityTransport(
                loader.getAllStations(),
                loader.getAllLines()
        );
        this.ig = new InteractiveGetter(this.transport, this.sc);
    }
    /**
     * The mainMenu function is the main menu of the program.
     * It allows you to choose between searching for a path,
     * searching in stations and lines (menu), loading new stations, or exiting.
     */
    public void mainMenu() {
        String str;
        while (true) {
            System.out.println(
                    "Vyberte jednu z možností:\n" +
                    "H    Hledat cestu\n" + // TODO
                    "HU   Hledat cestu s možností uložení výpisu\n" + // TODO
                    "P    Prohledávat stanice a linky (menu)\n" +
                    "N    Načíst nové stanice\n" +
                    "V    Zkontrolovat validitu souboru\n" + // TODO
                    "O    Opustit program");
            System.out.print("> ");
            str = sc.nextLine().toLowerCase();
            switch (str) {
                case "h" -> findPathMenu();
                case "p" -> transportViewerMenu();
                case "n" -> {
                    this.loader = LoaderSelector.getLoaderMethod();
                    System.out.println(
                            "Načteno " + this.loader.getAllStations().size() +
                            " stanic a " + this.loader.getAllLines().size() + " linek." );
                    this.transport = new CityTransport(
                            loader.getAllStations(),
                            loader.getAllLines()
                    );
                }
                case "o", "opustit" -> {
                    System.out.println("Opouštíte program.");
                    return;
                }
                default -> System.out.println("\nNeplatná volba.\n");
            }

        }
    }
    private void findPathMenu() {
        Station from, to;
        int tmpCharCounter, lineCharCounter, totalTravelTime = 0;
        while (true) {
            sb.setLength(0);
            // Get from
            from = ig.getStation();
            if (from == null) break;
            // Get to
            to = ig.getStation();
            if (to == null) break;

            // Find path
            PathFinder pf = new BFS(this.transport);
            List<PathSegment> path = pf.findShortestPath(from, to);

            Pair<Station, Integer>  prevStation = null;
            lineCharCounter = 0;
            for (PathSegment ps : path) {
                sb.append("Úsek: \n  ");

                if (prevStation != null) {
                    sb.append(prevStation.first.getPrettyName())
                        .append(" [")
                        .append(prevStation.second)
                        .append(" min] -> ");
                    lineCharCounter += sb.length();
                }

                for (Pair<Station, Integer> part : ps.getStations()) {
                    totalTravelTime += part.second;
                    // length of stations pretty name + time digits + 3 as braces and spaces
                    if (lineCharCounter + part.first.getPrettyName().length() + part.second.toString().length() + 3 > 70) {
                        sb.append("\n  ");
                        lineCharCounter = 0;
                    }
                    tmpCharCounter = sb.length();

                    sb.append(part.first.getPrettyName())
                        .append(" [")
                        .append(part.second)
                        .append(" min] -> ");
                    lineCharCounter += sb.length() - tmpCharCounter;
                    prevStation = part;
                }
                sb.delete(sb.length()-4, sb.length()-1)
                    .append("\nLze jet linkami: \n  ");

                lineCharCounter = 0;
                for (Line line : ps.getLines()
                                    .stream()
                                    .sorted(Comparator.comparing(Line::getName))
                                    .toList()) {
                    lineCharCounter = getLineCharCounter(lineCharCounter, line);
                }
                sb.delete(sb.length()-2, sb.length()).append("\n\n");
            }
            sb.delete(sb.length()-1, sb.length());

            System.out.println("---------------------------[VÝSLEDEK HLEDÁNÍ]---------------------------");
            System.out.println( sb.toString() );
            System.out.println("CELKOVÁ DOBA TRASY: " + LangFormatter.formatCzechMinutes(totalTravelTime) + "." );
            System.out.println("------------------------------------------------------------------------");
        }
    }
    /**
     * Calculates the character counter for the line information and appends the line name to the StringBuilder.
     * If the lineCharCounter exceeds the maximum limit of 70 characters, a line break is added.
     *
     * @param lineCharCounter the current character counter for the line information
     * @param line the line object representing the public transportation line
     * @return the updated lineCharCounter after appending the line name
     */
    private int getLineCharCounter(int lineCharCounter, Line line) {
        int tmpCharCounter;
        if (lineCharCounter + line.getName().length()+1 > 70) {
            sb.append("\n  ");
            lineCharCounter = 0;
        }
        tmpCharCounter = sb.length();
        sb.append(line.getName()).append(", ");
        lineCharCounter += sb.length() - tmpCharCounter;
        return lineCharCounter;
    }

    /**
     * The transportViewerMenu function is the menu of the program.
     * It allows you to choose between searching stations or lines.
     */
    private void transportViewerMenu() {
        String str;
        boolean br = true;
        while (br) {
            System.out.println(
                    "Vyberte jednu z možností:\n" +
                    "S    Prohlížet stanice\n" +
                    "L    Prohlížet linky\n" +
                    "Z    Vrátit se zpátky");
            System.out.print("> ");
            str = sc.nextLine().toLowerCase();
            switch (str) {
                case "s" -> stationViewer();
                case "l" -> lineViewer();
                case "z", "zpet" -> br = false;
                default -> System.out.println("\nNeplatná volba.\n");
            }
        }
    }

    /**
     * The stationViewer function is the menu of the program.
     * It allows you to find information about specific station.
     */
    private void stationViewer() {
        Station station;

        while (true) {
            station = ig.getStation();
            if (station == null) break;
            printStationInfo(station);
        }
    }

    /**
     * Method for sorting lines by transportation type.
     */
    private List<Map.Entry<TransportationType, List<Line>>> sortLinesByTransportationType(Station station) {
        return station
                .getLines()
                .stream()
                .collect(Collectors.groupingBy(Line::getLineType))
                .entrySet()
                .stream()
                .sorted(Map.Entry.comparingByKey())
                .toList();

    }

    /**
     * Method for printing station information.
     */
    private void printStationInfo(Station station) {
        sb.setLength(0);
        int lineCharCounter;
        List<Map.Entry<TransportationType, List<Line>>> transportTypeGroups = sortLinesByTransportationType(station);

        for (Map.Entry<TransportationType, List<Line>> entry : transportTypeGroups) {
            sb.append(" ").append(entry.getKey()).append(":\n  ");
                lineCharCounter = 0;
                for (Line l : entry.getValue()) {
                    // length of line's name + 1 space
                    lineCharCounter = getLineCharCounter(lineCharCounter, l);
                }
                sb.delete(sb.length()-2, sb.length()).append("\n");
        }

        System.out.println("---------------------------[PROHLÍŽEČ STANIC]---------------------------");
        System.out.println(
                "Jméno: " + station.getPrettyName() +
                        "\nNormalizované jméno: " + station.getName() +
                        "\nZóna: " + station.getZoneID() +
                        "\nLinky: \n" + sb.toString()
        );
        System.out.println("------------------------------------------------------------------------");
    }

    /**
     * The lineViewer function is the menu of the program.
     * It allows you to find information about specific line.
     */
    private void lineViewer() {
        Line line;
        while (true) {
            sb.setLength(0);
            line = ig.getLine();
            if (line == null) break;

            // Pretty list the stations
            // sort by type
            for (Pair<Station, Integer> station : line.getStations()) {
                sb.append(" ").append(station.first.getPrettyName());
                if (station.second != 0)
                    sb  .append("\n   -> ")
                        .append(LangFormatter.formatCzechMinutes(station.second))
                        .append("\n");
            }

            System.out.println("---------------------------[PROHLÍŽEČ LINEK]----------------------------");
            System.out.println(
                    "Jméno: " + line.getName() +
                    "\nDruh dopravy: " + line.getLineType() +
                    "\nStanice: \n" + sb.toString()
            );
            System.out.println("------------------------------------------------------------------------");
        }
    }
}