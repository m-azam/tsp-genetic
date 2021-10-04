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
        int window1 = ThreadLocalRandom.current().nextInt(0, parentOne.sequence.size());
        int window2 = ThreadLocalRandom.current().nextInt(0, parentOne.sequence.size());
        if (window1 > window2) {
            int temp = window1;
            window1 = window2;
            window2 = temp;
        }

    }

    private void calculateTotalDistance(double[][] distanceMatrix) {
        for (int city = 1; city < sequence.size(); city++) {
            totalDistance = totalDistance + distanceMatrix[sequence.get(city - 1)][sequence.get(city)];
        }
        totalDistance = totalDistance + distanceMatrix[sequence.get(sequence.size() - 1)][sequence.get(0)];
    }

    public ArrayList<Integer> getSequence() {
        return sequence;
    }

    public double getTotalDistance() {
        return totalDistance;
    }

}
