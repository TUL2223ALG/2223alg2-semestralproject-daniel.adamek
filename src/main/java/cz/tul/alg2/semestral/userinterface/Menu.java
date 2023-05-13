package cz.tul.alg2.semestral.userinterface;

import cz.tul.alg2.semestral.file.ILoader;
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
        this.ig = new InteractiveGetter(this.transport);
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
                case "p" -> {
                    transportViewerMenu();
                }
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

    /**
     * The transportViewerMenu function is the menu of the program.
     * It allows you to choose between searching stations or lines.
     */
    public void transportViewerMenu() {
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
    public void stationViewer() {
        Station station;
        int tmpCharCounter, lineCharCounter;
        while (true) {
            sb.setLength(0);
            station = ig.getStation(sc);
            if (station == null) break;

            // Pretty list the lines
            // Group lines by transportation type
            Map<TransportationType, List<Line>> transportTypeGroups = station.getLines()
                                                                        .stream()
                                                                        .collect(Collectors.groupingBy(Line::getLineType));
            // sort by type
            for (Map.Entry<TransportationType, List<Line>> entry : transportTypeGroups
                                                                    .entrySet()
                                                                    .stream()
                                                                    .sorted(Comparator.comparing(Map.Entry::getKey))
                                                                    .toList()) {
                sb.append(" ").append(entry.getKey()).append(":\n  ");
                lineCharCounter = 0;
                for (Line l : entry.getValue()) {
                    if (lineCharCounter + l.getName().length()+1 > 70) {
                        sb.append("\n  ");
                        lineCharCounter = 0;
                    }
                    tmpCharCounter = sb.length();
                    sb.append(l.getName()).append(", ");
                    lineCharCounter += sb.length() - tmpCharCounter;
                }
                sb.delete(sb.length()-2, sb.length()-1).append("\n");
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
    }

    /**
     * The lineViewer function is the menu of the program.
     * It allows you to find information about specific line.
     */
    public void lineViewer() {
        Line line;
        while (true) {
            sb.setLength(0);
            line = ig.getLine(sc);
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