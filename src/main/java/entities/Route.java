package entities;

import utils.PairingUtils;

import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

public class Route {

    ArrayList<Integer> sequence = new ArrayList<>();
    double totalDistance = 0.0;
    ArrayList<Double> distances = new ArrayList<>();
    double fitness;

    public Route(int numberOfCities, double[][] distanceMatrix) {
        sequence.addAll(ThreadLocalRandom.current().ints(0, numberOfCities).distinct().limit(numberOfCities).boxed().collect(Collectors.toList()));
        calculateTotalDistance(distanceMatrix);
        calculateFitness();
    }

    public Route(Route parentOne, Route parentTwo, double[][] distanceMatrix, int mutationChance) {
        int window1 = ThreadLocalRandom.current().nextInt(0, parentOne.sequence.size());
        int window2 = ThreadLocalRandom.current().nextInt(0, parentOne.sequence.size());
        if (window1 > window2) {
            int temp = window1;
            window1 = window2;
            window2 = temp;
        }
        this.sequence.addAll(PairingUtils.mutate(PairingUtils
                .getOrderCrossover(parentOne, parentTwo, window1, window2), mutationChance));
        calculateTotalDistance(distanceMatrix);
        calculateFitness();
    }

    private void calculateTotalDistance(double[][] distanceMatrix) {
        for (int city = 1; city < sequence.size(); city++) {
            totalDistance = totalDistance + distanceMatrix[sequence.get(city - 1)][sequence.get(city)];
        }
        totalDistance = totalDistance + distanceMatrix[sequence.get(sequence.size() - 1)][sequence.get(0)];
    }

    public void calculateFitness() {
        this.fitness = 1 / totalDistance;
    }

    public ArrayList<Integer> getSequence() {
        return sequence;
    }

    public double getTotalDistance() {
        return totalDistance;
    }

    public double getFitness() {
        return fitness;
    }

}
