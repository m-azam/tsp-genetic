package entities;

import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

public class Route {

    ArrayList<Integer> sequence = new ArrayList<>();
    double totalDistance = 0.0;
    ArrayList<Double> distances = new ArrayList<>();

    public Route(int numberOfCities, double[][] distanceMatrix) {
        for (int iterator = 0; iterator < numberOfCities; iterator++) {
            sequence.add(ThreadLocalRandom.current().nextInt(numberOfCities + 1));
        }
        calculateTotalDistance(distanceMatrix);
    }

    public Route(Route parentOne, Route ParentTwo) {

    }

    private void calculateTotalDistance(double[][] distanceMatrix) {
        for (int city = 1; city < sequence.size(); city++) {
            totalDistance = totalDistance + distanceMatrix[sequence.get(city - 1)][sequence.get(city)];
        }
        totalDistance = totalDistance + distanceMatrix[sequence.get(sequence.size() - 1)][sequence.get(0)];
    }

}
