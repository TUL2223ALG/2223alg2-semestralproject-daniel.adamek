package cz.tul.alg2.semestral.transportation.RouteTypes;

import cz.tul.alg2.semestral.transportation.ARoute;
import cz.tul.alg2.semestral.transportation.IRoute;
import cz.tul.alg2.semestral.userinterface.Colors;

public class TramRoute extends ARoute implements IRoute {
    public TramRoute(String name) {
        super(name);
    }
    @Override
    public String getPrettyName() {
        return "[ " + "\uD83D\uDE8B" + " ] " + Colors.BASIC_TEXT + getPrettyName();
    }
}
