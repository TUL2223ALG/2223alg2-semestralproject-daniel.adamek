package cz.tul.alg2.semestral.userinterface;

import cz.tul.alg2.semestral.file.*;
import cz.tul.alg2.semestral.pathfinding.BFS;
import cz.tul.alg2.semestral.pathfinding.PathFinder;
import cz.tul.alg2.semestral.pathfinding.PathSegment;
import cz.tul.alg2.semestral.transportation.CityTransport;
import cz.tul.alg2.semestral.transportation.Line;
import cz.tul.alg2.semestral.transportation.Station;
import cz.tul.alg2.semestral.transportation.TransportationType;
import cz.tul.alg2.semestral.utilities.LangFormatter;
import cz.tul.alg2.semestral.utilities.Pair;

import java.io.File;
import java.util.*;
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
    }

    /**
     * The Menu function is the constructor for the Menu class.
     * It loads all the data from a menu.
     */
    public Menu() {
        do {
            loadTransportMenu(false);
            if (this.loader == null) System.out.println("Je nutné, načíst nějaká data!");
        } while (this.loader == null);
        this.ig = new InteractiveGetter(this.transport, this.sc);
    }

    /**
     * The getTransport function returns the transport object of a CityTransport.
     */
    public CityTransport getTransport() {
        return transport;
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
                    """
                    Vyberte jednu z možností:
                    H    Hledat cestu
                    HU   Hledat cestu s možností uložení výpisu
                    P    Prohledávat stanice a linky (menu)
                    N    Načíst nové stanice
                    V    Zkontrolovat validitu souboru
                    O    Opustit program""");
            System.out.print("> ");
            str = sc.nextLine().toLowerCase();
            switch (str) {
                case "h" -> findPathMenu(false);
                case "hu" -> findPathMenu(true);
                case "p" -> transportViewerMenu();
                case "n" -> loadTransportMenu(true);
                case "v" -> checkValidityOfFileMenu();
                case "o", "opustit" -> {
                    System.out.println("Opouštíte program.");
                    return;
                }
                default -> System.out.println(ConsoleColors.RED_BOLD + "\nNeplatná volba.\n" + ConsoleColors.RESET);
            }

        }
    }

    public void checkValidityOfFileMenu() {
        while (true) {
            String fileName;
            File file;
            while (true) {
                System.out.println("""
                        Ověřte validitu souboru!
                         - "*.ser" pro binární soubory
                         - "*.txt" pro textové soubory""");
                LoaderSelector.suggestActualFiles();
                file = ig.getFile(false);
                if (file == null) return;

                // Validate .ser or .txt
                fileName = file.getName();
                if (fileName.matches("^(.+/)*.+\\.(ser|txt)$"))
                    break;
                System.out.println(ConsoleColors.RED_BOLD + "Neplatný druh souboru! Povoleny jsou pouze *.ser pro binární a *.txt pro textové" + ConsoleColors.RESET);
            }

            IValidator validator;
            if (fileName.matches("^(.+/)*.+\\.ser$"))
                validator = new BinaryValidator();
            else
                validator = new TextValidator();

            // Check validity by loading
            if (validator.validateFile(file))
                System.out.println(ConsoleColors.YELLOW_BOLD + "Soubor je správně zapsán!" + ConsoleColors.RESET);
            else
                System.out.println(ConsoleColors.YELLOW_BOLD + "Soubor je porouchaný!" + ConsoleColors.RESET);
        }
    }

    /**
     * The loadDataMenu function is used to load the data from a file.
     * It uses the LoaderSelector class to determine which loader should be used, and then loads all the stations
     * and lines into a CityTransport object.
     */
    public void loadTransportMenu(boolean possibleReturn) {
        ILoader newLoader = LoaderSelector.getLoaderMethod(possibleReturn);
        if (newLoader == null) {
            System.out.println("Nic nového nebylo načteno.");
            return;
        }

        this.loader = newLoader;
        System.out.println(
                "Načteno " + this.loader.getAllStations().size() +
                " stanic a " + this.loader.getAllLines().size() + " linek." );
        this.transport = new CityTransport(
                loader.getAllStations(),
                loader.getAllLines()
        );
    }

    /**
     * Interactive menu of path finding between stations
     * @param save save to file?
     */
    private void findPathMenu(boolean save) {
        Station from;
        Station to;
        while (true) {
            System.out.println("Vyhledejte spojení v Pražské MHD!");

            // Get from
            System.out.println("Zadejte počáteční stanici");
            from = ig.getStation();
            if (from == null) break;

            // Get to
            System.out.println("Zadejte koncovou stanici");
            to = ig.getStation();
            if (to == null) break;

            // Find path
            PathFinder pf = new BFS(this.transport);
            List<PathSegment> path = pf.findShortestPath(from, to);

            String description = generatePathFindReport(path);

            System.out.println("---------------------------[VÝSLEDEK HLEDÁNÍ]---------------------------");
            System.out.println( description );
            System.out.println("------------------------------------------------------------------------");

            if (save) {
                File file = ig.getFile(false);
                if (file != null) DescriptionSaver.saveTextToFile(file, description);
            }
        }
    }

    /**
     * Generates a report of the given path in the form of a formatted string.
     *
     * @param path The list of PathSegments representing the path.
     * @return The report in string format.
     *
     * The report includes each segment of the path with the following details:
     *  - Each station on the segment, with the station's pretty name and the travel time to the station.
     *  - A list of all lines that can be used on the segment, sorted by line name.
     */
    private String generatePathFindReport(List<PathSegment> path) {
            // clear string buffer
            sb.setLength(0);

            Pair<Station, Integer>  prevStation = null;
            int lineCharCounter = 0;
            int totalTravelTime = 0;
            int tmpCharCounter = 0;

            for (PathSegment ps : path) {
                sb.append("Úsek: \n  ");

                if (prevStation != null) {
                    sb.append(prevStation.first.getPrettyName())
                        .append(" [")
                        .append(prevStation.second)
                        .append(" min] -> ");
                    lineCharCounter += sb.length();
                }

                // Print stations on this part
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

                // Print possible lines on this part
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
            sb.append("\nCELKOVÁ DOBA TRASY: ").append(LangFormatter.formatCzechMinutes(totalTravelTime)).append(".");
            return sb.toString();
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
        sb.append(line.getPrettyName()).append(", ");
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
                    """
                    Vyberte jednu z možností:
                    "S    Prohlížet stanice
                    "L    Prohlížet linky
                    "Z    Vrátit se zpátky""");
            System.out.print("> ");
            str = sc.nextLine().toLowerCase();
            switch (str) {
                case "s" -> stationViewer();
                case "l" -> lineViewer();
                case "z", "zpet" -> br = false;
                default -> System.out.println(ConsoleColors.RED_BOLD + "\nNeplatná volba.\n" + ConsoleColors.RESET);
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
        List<Map.Entry<TransportationType, List<Line>>> transportTypeGrouped = sortLinesByTransportationType(station);

        List<Line> lines;
        for (Map.Entry<TransportationType, List<Line>> entry : transportTypeGrouped) {
            sb.append(" ").append(entry.getKey()).append(":\n  ");
                lineCharCounter = 0;
                lines = entry.getValue();
                Collections.sort(lines);
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

            // sort stations
            List<Pair<Station, Integer>> stations = line.getStations();
            Collections.sort(stations);

            // Pretty list the stations
            for (Pair<Station, Integer> station : line.getStations()) {
                sb.append(" ").append(station.first.getPrettyName());
                if (station.second != 0)
                    sb  .append("\n   -> ")
                        .append(LangFormatter.formatCzechMinutes(station.second))
                        .append("\n");
            }

            System.out.println("---------------------------[PROHLÍŽEČ LINEK]----------------------------");
            System.out.println(
                    "Jméno: " + line.getPrettyName() +
                    "\nDruh dopravy: " + line.getLineType() +
                    "\nStanice: \n" + sb.toString()
            );
            System.out.println("------------------------------------------------------------------------");
        }
    }
}