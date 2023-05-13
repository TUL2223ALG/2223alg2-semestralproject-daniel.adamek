package cz.tul.alg2.semestral;

import cz.tul.alg2.semestral.file.BinaryLoader;
import cz.tul.alg2.semestral.transportation.CityTransport;
import cz.tul.alg2.semestral.userinterface.Menu;
import cz.tul.alg2.semestral.utilities.PathBuilder;

/**
 * The type Main.
 */
public class Main {
    /**
     * The entry point of application.
     *
     * @param args the input arguments
     */
    public static void main(String[] args) {
        System.out.println(
                "\n  _   _ _     _____ ____    _  _____ ___  ____  \n" +
                " | | | | |   | ____|  _ \\  / \\|_   _/ _ \\|  _ \\ \n" +
                " | |_| | |   |  _| | | | |/ _ \\ | || | | | |_) |\n" +
                " |  _  | |___| |___| |_| / ___ \\| || |_| |  _ < \n" +
                " |_| |_|_____|_____|____/_/   \\_\\_| \\___/|_| \\_\\\n\n" +
                "       - CityTransportation Path Finder -\n" +
                "               VÍTEJTE V HLEDATORU\n");

        //ILoader loader = LoaderSelector.getLoaderMethod();

        //GTFSLoader loader = new GTFSLoader();
        //loader.loadFile(PathBuilder.joinPath("data", "PID_GTFS.zip"));
        BinaryLoader loader = new BinaryLoader();
        loader.loadFile(PathBuilder.joinPath("data", "pid.ser"));
        //System.out.printf("\nÚspěšně načteno: %d stanic a %d linek.\n", loader.getAllStations().size(), loader.getAllLines().size());

        CityTransport cityTransport = new CityTransport(
                loader.getAllStations(),
                loader.getAllLines()
        );

        Menu menu = new Menu(loader);
        menu.mainMenu();
        int a = 1;
        if (a == 1) return;


        //BinarySaver bs = new BinarySaver(cityTransport);
        //bs.saveTransport(PathBuilder.joinPath("data", "pid.ser"));

        //TextSaver ts = new TextSaver(cityTransport);
        //ts.saveTransport(PathBuilder.joinPath("data", "pid.txt"));

        /*
        BFS pf1 = new BFS(cityTransport);
        InteractiveGetter sg = new InteractiveGetter(cityTransport);
        Station from, to;
        while (true) {
            System.out.println("\nHledátor v Pražské integrované dopravě");
            System.out.println("Zadejte počáteční stanici");
            from = sg.getStation();
            if (from == null) break;

            System.out.println("Zadejte koncovou stanici");
            to = sg.getStation();
            if (to == null) break;

            long startTime = System.nanoTime();
            List<PathSegment> path = pf1.findShortestPath(
                    from.getName(),
                    to.getName());
            long endTime = System.nanoTime();

            int total = 0;
            for (int j = 0; j < path.size(); j++) {
                PathSegment segment = path.get(j);
                for (Line line : segment.getLines()) {
                    System.out.println(line.getLineType() + " " + line.getName());
                }
                List<Pair<Station, Integer>> stations = segment.getStations();
                for (int i = 0; i < stations.size(); i++) {
                    Pair<Station, Integer> segmentPath = stations.get(i);
                    if (j == 0 && i == 0) System.out.print("  ╔");
                    else if (j == path.size() - 1) System.out.print("  ╚");
                    else System.out.print("  ╠");
                    System.out.print(segmentPath.first.getPrettyName() + " (" + segmentPath.second + " min)\n");
                    total += segmentPath.second;
                }
            }

            System.out.println(total + " minut");
            System.out.println("Čas vyhledávání: " + (endTime - startTime) / 1e6 + " ms");
        }

         */

    }

}