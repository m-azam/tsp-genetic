package entities;

import utils.PairingUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class Generation {

    ArrayList<Route> routes = new ArrayList<>();
    Route bestRoute;

    public Generation(int populationSize, World world) {
        for (int iterator = 0; iterator < populationSize; iterator++) {
            routes.add(new Route(world.cities.size(), world.distanceMatrix));
        }
    }

    public Generation(Generation previousGeneration) {
        this.applyElitism(previousGeneration);
        ArrayList<Route> previousGenerationRoutes = new ArrayList<>(previousGeneration.routes);
        // As elitism is applied
        previousGenerationRoutes.remove(previousGeneration.bestRoute);
        // sort to get the best candidate first
        previousGenerationRoutes.sort(Comparator.comparingDouble(Route::getTotalDistance));
        while (previousGenerationRoutes.size() > 0) {
            Route parentOne = previousGenerationRoutes.get(0);
            previousGenerationRoutes.remove(parentOne);
            Route parentTwo = PairingUtils.getMostDistinctPair(parentOne, previousGenerationRoutes);
            previousGenerationRoutes.remove(parentTwo);
            Route childRoute = new Route(parentOne, parentTwo);
            this.routes.add(childRoute);
        }
    }

    public Route getBestRoute() {
        if (bestRoute == null) {
            bestRoute = routes.get(0);
            for (Route route : routes) {
                if (route.totalDistance < bestRoute.totalDistance) {
                    bestRoute = route;
                }
            }
        }
        return bestRoute;
    }

    private void applyElitism(Generation generation) {
        this.routes.add(generation.getBestRoute());
    }
}
