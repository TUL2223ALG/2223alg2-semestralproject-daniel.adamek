package cz.tul.alg2.semestral.transportation.RouteTypes;

import cz.tul.alg2.semestral.transportation.ARoute;
import cz.tul.alg2.semestral.transportation.IRoute;
import cz.tul.alg2.semestral.userinterface.Colors;

public class FerryRoute extends ARoute implements IRoute {
    public FerryRoute(String name) {
        super(name);
    }
    @Override
    public String getPrettyName() {
        return "[ " + "⛴️" + " ] " + Colors.BASIC_TEXT + getPrettyName();
    }
}
