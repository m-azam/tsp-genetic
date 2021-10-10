import entities.Generation;
import entities.World;
import org.jfree.ui.RefineryUtilities;

import java.util.ArrayList;

public class TravellingSalesmanApplication {
    public static void main(String[] args) {

        // Input number of cities
        int numberOfCities = 10;
        // Input Population Size
        int populationSize = 2000;
        World world = new World(numberOfCities);
        ArrayList<Generation> generations = new ArrayList<>();
        Generation firstGeneration = new Generation(populationSize, world);
        generations.add(firstGeneration);
        int numberOfGenerations = 50;
        System.out.println(firstGeneration.getBestRoute().getTotalDistance());
//        for (Route route : firstGeneration.routes)
//            System.out.println(route.getSequence().toString());
        for (int i = 1; i < numberOfGenerations; i++) {
            Generation newGeneration = new Generation(generations.get(i - 1), world);
            generations.add(newGeneration);
//            for (Route route : newGeneration.routes)
//                System.out.println(route.getSequence().toString());
        }
        DistanceGraph distanceGraph = new DistanceGraph(generations);
        distanceGraph.pack();
        RefineryUtilities.centerFrameOnScreen(distanceGraph);
        distanceGraph.setVisible(true);
    }
}