package utils;

import entities.Route;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;

public class PairingUtils {

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
        for (int i = window1; i <= window2; i++) {
            sequence.add(i, parentOne.getSequence().get(i));
        }
        int windowSize = window2 - window1 + 1;
        int i = 0;
        for (int element : parentTwo.getSequence()) {
            if (!sequence.contains(element)) {
                if (sequence.get(i) == null) {
                    sequence.add(i, element);
                } else {
                    sequence.add(i + windowSize, element);
                }
            }
        }
        return sequence;
    }

}
