package entities;

import org.apache.commons.math3.distribution.EnumeratedIntegerDistribution;
import utils.PairingUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
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
        previousGenerationRoutes.sort(Comparator.comparingDouble(Route::getFitness));
        // Reversing to get best route first
        Collections.reverse(previousGenerationRoutes);
        this.applyElitism(previousGenerationRoutes, world.elitismRatio);
        // Setting probability distribution
        int[] range = IntStream.range(0, previousGenerationRoutes.size()).toArray();
        double[] probabilityDistribution = generateProbabilities(previousGenerationRoutes.size());
        EnumeratedIntegerDistribution distribution = new EnumeratedIntegerDistribution(range, probabilityDistribution);
        // Picking and crossover
        int elitismBound = (int) Math.ceil(previousGenerationRoutes.size() * world.elitismRatio);
        for (int i = 0; i < previousGenerationRoutes.size() - elitismBound; i++) {
            int randomIndexOne = distribution.sample();
            int randomIndexTwo = distribution.sample();
            Route parentOne = previousGenerationRoutes.get(randomIndexOne);
            Route parentTwo = getSecondParent(randomIndexTwo, parentOne, previousGenerationRoutes, mostDistinct);
            Route childRoute = new Route(parentOne, parentTwo, world.distanceMatrix, world.mutationChance);
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

    private void applyElitism(ArrayList<Route> previousGenerationRoutes, double elitismRatio) {
        int bound = (int) Math.ceil(previousGenerationRoutes.size() * elitismRatio);
        this.routes.addAll(previousGenerationRoutes.subList(0, bound));
    }

    private double[] generateProbabilities(int populationSize) {
        double prob = MAX_PROBABILITY_DISTRIBUTION;
        double delta = MAX_PROBABILITY_DISTRIBUTION / (double) populationSize;
        double[] probabilityDistribution = new double[populationSize];
        for (int i = 0; i < populationSize; i++) {
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
