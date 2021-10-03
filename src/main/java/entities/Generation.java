package entities;

import java.util.ArrayList;

public class Generation {

    ArrayList<Route> routes = new ArrayList<>();

    public Generation(int populationSize, World world) {
        for (int iterator = 0; iterator < populationSize; iterator++) {
            routes.add(new Route(world.cities.size(), world.distanceMatrix));
        }
    }

    public Generation(Generation generation) {
        // Random pairing & mating code
    }

    public Route getBestRoute() {

    }
}
