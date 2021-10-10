package entities;

import org.apache.commons.math3.distribution.EnumeratedIntegerDistribution;
import utils.PairingUtils;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.stream.IntStream;

public class Generation {

    private static final double MAX_PROBABILITY_DISTRIBUTION = 0.5;
    ArrayList<Route> routes = new ArrayList<>();
    Route bestRoute;

    public Generation(int populationSize, World world) {
        for (int iterator = 0; iterator < populationSize; iterator++) {
            routes.add(new Route(world.cities.size(), world.distanceMatrix));
        }
        getBestRoute();
    }

    public Generation(Generation previousGeneration, World world) {
        this.applyElitism(previousGeneration);
        ArrayList<Route> previousGenerationRoutes = new ArrayList<>(previousGeneration.routes);
        // sorted according to distance
        previousGenerationRoutes.sort(Comparator.comparingDouble(Route::getTotalDistance));
        // Setting probability distribution
        int[] range = IntStream.range(0, world.cities.size()).toArray();
        double[] probabilityDistribution = generateProbabilities(world.cities.size());
        EnumeratedIntegerDistribution distribution = new EnumeratedIntegerDistribution(range, probabilityDistribution);
        // Picking and crossover
        for (int i = 0; i < previousGenerationRoutes.size(); i++) {
            int randomCityIndex = distribution.sample();
            Route parentOne = previousGenerationRoutes.get(randomCityIndex);
            Route parentTwo = PairingUtils.getMostDistinctPair(parentOne, previousGenerationRoutes);
            Route childRoute = new Route(parentOne, parentTwo, world.distanceMatrix);
            this.routes.add(childRoute);
        }
//        while (previousGenerationRoutes.size() > 0) {
//            Route parentOne = previousGenerationRoutes.get(0);
//            previousGenerationRoutes.remove(parentOne);
//            //Route parentTwo = PairingUtils.getMostDistinctPair(parentOne, previousGenerationRoutes);
//            Route parentTwo = PairingUtils.getRandomPair(previousGenerationRoutes);
//            previousGenerationRoutes.remove(parentTwo);
//            Route childRoute = new Route(parentOne, parentTwo, world.distanceMatrix);
//            this.routes.add(childRoute);
//        }
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

    private double[] generateProbabilities(int numberOfCities) {
        double prob = MAX_PROBABILITY_DISTRIBUTION;
        double delta = MAX_PROBABILITY_DISTRIBUTION / (double) numberOfCities;
        double[] probabilityDistribution = new double[numberOfCities];
        for (int i = 0; i < numberOfCities; i++) {
            probabilityDistribution[i] = prob;
            prob = prob - delta;
        }
        return probabilityDistribution;
    }
}
