package cz.tul.alg2.semestral;

import cz.tul.alg2.semestral.userinterface.Menu;

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
        System.out.printf("\n" +
                "  _   _ _     _____ ____    _  _____ ___  ____  \n" +
                " | | | | |   | ____|  _ \\  / \\|_   _/ _ \\|  _ \\ \n" +
                " | |_| | |   |  _| | | | |/ _ \\ | || | | | |_) |\n" +
                " |  _  | |___| |___| |_| / ___ \\| || |_| |  _ < \n" +
                " |_| |_|_____|_____|____/_/   \\_\\_| \\___/|_| \\_\\\n" +
                "\n" +
                "       - CityTransportation Path Finder -\n" +
                "               VÍTEJTE V HLEDATORU\n" +
                "%n");

        Menu menu = new Menu();
        menu.mainMenu();
    }

}