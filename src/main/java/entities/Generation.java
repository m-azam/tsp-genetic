package entities;

import org.apache.commons.math3.distribution.EnumeratedIntegerDistribution;
import utils.PairingUtils;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.IntStream;

public class Generation {

    private static final double MAX_PROBABILITY_DISTRIBUTION = 0.5;
    public ArrayList<Route> routes = new ArrayList<>();
    Route bestRoute;

    public Generation(int populationSize, World world) {
        for (int iterator = 0; iterator < populationSize; iterator++) {
            routes.add(new Route(world.cities.size(), world.distanceMatrix));
        }
        getBestRoute();
    }

    public Generation(Generation previousGeneration, World world, boolean mostDistinct) {
        ArrayList<Route> previousGenerationRoutes = new ArrayList<>(previousGeneration.routes);
        // sorted according to distance
        previousGenerationRoutes.sort(Comparator.comparingDouble(Route::getTotalDistance));
        this.applyElitism(previousGenerationRoutes);
        // Setting probability distribution
        int[] range = IntStream.range(0, previousGenerationRoutes.size()).toArray();
        double[] probabilityDistribution = generateProbabilities(previousGenerationRoutes.size());
        EnumeratedIntegerDistribution distribution = new EnumeratedIntegerDistribution(range, probabilityDistribution);
        // Picking and crossover
        for (int i = 0; i < previousGenerationRoutes.size() - 200; i++) {
            int randomIndexOne = distribution.sample();
            int randomIndexTwo = distribution.sample();
            Route parentOne = previousGenerationRoutes.get(randomIndexOne);
            Route parentTwo = getSecondParent(randomIndexTwo, parentOne, previousGenerationRoutes, mostDistinct);
            Route childRoute = new Route(parentOne, parentTwo, world.distanceMatrix);
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

    private void applyElitism(ArrayList<Route> previousGenerationRoutes) {
        this.routes.addAll(previousGenerationRoutes.subList(0, 200));
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

    private Route getSecondParent(int randomIndexTwo, Route parentOne, ArrayList<Route> previousGenerationRoutes, boolean mostDistinct) {
        if (mostDistinct) {
            return PairingUtils.getMostDistinctPair(parentOne, previousGenerationRoutes);
        } else {
            return previousGenerationRoutes.get(randomIndexTwo);
        }
    }
}
