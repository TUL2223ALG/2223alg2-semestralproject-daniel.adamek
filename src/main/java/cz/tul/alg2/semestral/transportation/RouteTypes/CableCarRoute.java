package cz.tul.alg2.semestral.transportation.RouteTypes;

import cz.tul.alg2.semestral.transportation.ARoute;
import cz.tul.alg2.semestral.transportation.IRoute;
import cz.tul.alg2.semestral.userinterface.Colors;

public class CableCarRoute extends ARoute implements IRoute {
    public CableCarRoute(String name) {
        super(name);
    }
    @Override
    public String getPrettyName() {
        return "[ " + "\uD83D\uDEA1" + " ] " + Colors.BASIC_TEXT + getPrettyName();
    }
}
