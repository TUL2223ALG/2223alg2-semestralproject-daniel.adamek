package cz.tul.alg2.semestral.transportation.RouteTypes;

import cz.tul.alg2.semestral.transportation.ARoute;
import cz.tul.alg2.semestral.transportation.IRoute;
import cz.tul.alg2.semestral.userinterface.Colors;

public class MetroRoute extends ARoute implements IRoute {
    private final Colors color;
    public MetroRoute(String name, Colors color) {
        super(name);
        this.color = color;
    }
    @Override
    public String getPrettyName() {
        return this.color + "[ " + "⛴️" + " ] " + Colors.BASIC_TEXT + getPrettyName();
    }
}
