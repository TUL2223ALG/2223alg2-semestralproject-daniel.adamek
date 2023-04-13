package cz.tul.alg2.semestral.transportation.RouteTypes;

import cz.tul.alg2.semestral.transportation.ARoute;
import cz.tul.alg2.semestral.transportation.IRoute;
import cz.tul.alg2.semestral.userinterface.Colors;

public class BusRoute extends ARoute implements IRoute {
    public BusRoute(String name, boolean nightRoute) {
        super(name);
    }
    @Override
    public String getPrettyName() {
        return "[ " + "\uD83D\uDE8C" + " ] " + Colors.BASIC_TEXT + getPrettyName();
    }
}
