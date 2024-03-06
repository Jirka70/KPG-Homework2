package org.kpg2;

import java.util.ArrayList;
import java.util.List;

public class Route {
    private final List<Position> route = new ArrayList<>();
    public void addPosition(Position position) {
        route.add(position);
    }
    public List<Position> getRoute() {
        return route;
    }


}
