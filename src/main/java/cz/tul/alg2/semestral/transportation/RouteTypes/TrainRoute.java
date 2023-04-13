package cz.tul.alg2.semestral.transportation.RouteTypes;

import cz.tul.alg2.semestral.transportation.ARoute;
import cz.tul.alg2.semestral.transportation.IRoute;
import cz.tul.alg2.semestral.userinterface.Colors;

public class TrainRoute extends ARoute implements IRoute {
    public TrainRoute(String name) {
        super(name);
    }
    @Override
    public String getPrettyName() {
        return "[ " + "\uD83D\uDE84" + " ] " + Colors.BASIC_TEXT + getPrettyName();
    }
}
