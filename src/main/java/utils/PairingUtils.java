package utils;

import entities.Route;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;

public class PairingUtils {

    // Function picks pair with the most distinct available in list
    public static Route getMostDistinctPair(Route route, ArrayList<Route> routes) {
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

}
