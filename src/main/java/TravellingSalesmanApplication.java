import entities.Generation;
import entities.World;
import org.jfree.ui.RefineryUtilities;

import java.util.ArrayList;

public class TravellingSalesmanApplication {
    public static void main(String[] args) {

        // Input number of cities
        int numberOfCities = 100;
        // Input Population Size
        int populationSize = 2000;
        World world = new World(numberOfCities);
        ArrayList<Generation> generations = new ArrayList<>();
        Generation firstGeneration = new Generation(populationSize, world);
        generations.add(firstGeneration);
        int numberOfGenerations = 80;
        boolean mostDistinctPair = true;
        System.out.println(firstGeneration.getBestRoute().getTotalDistance());
        for (int i = 1; i < numberOfGenerations; i++) {
            Generation newGeneration = new Generation(generations.get(i - 1), world, mostDistinctPair);
            generations.add(newGeneration);
            System.out.println(newGeneration.getBestRoute().getTotalDistance());
        }
        System.out.println("Calculation complete");
        DistanceGraph distanceGraph = new DistanceGraph(generations);
        distanceGraph.pack();
        RefineryUtilities.centerFrameOnScreen(distanceGraph);
        distanceGraph.setVisible(true);
    }
}