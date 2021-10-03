package entities;

import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

public class Route {

    ArrayList<Long> sequence = new ArrayList<>();
    int totalDistance;
    ArrayList<Integer> distances = new ArrayList<>();

    public Route(long numberOfCities, int[][] distanceMatrix) {
        for (long iterator = 0; iterator < numberOfCities; iterator++) {
            sequence.add(ThreadLocalRandom.current().nextLong(numberOfCities + 1));
        }
        calculateTotalDistance();
    }

    public Route(Route parentOne, Route ParentTwo) {

    }

    private int calculateTotalDistance() {
        return ;
    }

}
