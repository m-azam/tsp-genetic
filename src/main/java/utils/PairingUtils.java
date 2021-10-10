package utils;

import entities.Route;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

public class PairingUtils {

    private static final int MUTATION_CHANCE = 2;

    // Function picks pair with the most distinct available in list
    public static Route getMostDistinctPair(Route route, List<Route> routes) {
        int maxCommonPairCount = -1;
        Route mostDistinctRouteCandidate = null;
        Set<String> pairsInRoute = new HashSet<>();
        for (int iterator = 1; iterator < route.getSequence().size() - 1; iterator++) {
            String pair = route.getSequence().get(iterator - 1) + "," + route.getSequence().get(iterator);
            pairsInRoute.add(pair);
        }
        for (Route routeCandidate : routes) {
            ArrayList<Integer> candidateSequence = routeCandidate.getSequence();
            int commonPairCount = 0;
            for (int iterator = 1; iterator < candidateSequence.size() - 1; iterator++) {
                String pair = candidateSequence.get(iterator - 1) + "," + candidateSequence.get(iterator);
                if (pairsInRoute.contains(pair)) {
                    commonPairCount++;
                }
            }
            if (commonPairCount > maxCommonPairCount) {
                maxCommonPairCount = commonPairCount;
                mostDistinctRouteCandidate = routeCandidate;
            }
        }
        return mostDistinctRouteCandidate;
    }

    public static Route getRandomPair(ArrayList<Route> routes) {
        int randomIndex = ThreadLocalRandom.current().nextInt(0, routes.size());
        return routes.get(randomIndex);
    }

    public static ArrayList<Integer> getOrderCrossover(Route parentOne, Route parentTwo, int window1, int window2) {
        ArrayList<Integer> sequence = new ArrayList<>();
        for (int i = 0; i < parentOne.getSequence().size(); i++) {
            sequence.add(null);
        }
        for (int i = window1; i <= window2; i++) {
            sequence.set(i, parentOne.getSequence().get(i));
        }
        int windowSize = window2 - window1 + 1;
        int i = 0;
        for (int element : parentTwo.getSequence()) {
            if (!sequence.contains(element)) {
                if (sequence.get(i) == null) {
                    sequence.set(i, element);
                } else {
                    sequence.set(i + windowSize, element);
                }
                i++;
            }
        }
        return mutate(sequence);
    }

    private static ArrayList<Integer> mutate(ArrayList<Integer> sequence) {
        int indexOne = ThreadLocalRandom.current().nextInt(0, sequence.size());
        int indexTwo = ThreadLocalRandom.current().nextInt(0, sequence.size());
        int chance = ThreadLocalRandom.current().nextInt(0, 100);
        if (chance < MUTATION_CHANCE) {
            Integer temp = sequence.get(indexOne);
            sequence.set(indexOne, sequence.get(indexTwo));
            sequence.set(indexTwo, temp);
        }
        return sequence;
    }

}
