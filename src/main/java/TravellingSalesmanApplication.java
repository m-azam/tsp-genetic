import entities.Generation;
import entities.World;
import org.jfree.ui.RefineryUtilities;
import org.jfree.util.TableOrder;

import java.util.ArrayList;
import java.util.Objects;
import java.util.Scanner;

public class TravellingSalesmanApplication {
    public static void main(String[] args) {
        int numberOfCities = 100;
        int populationSize = 2000;
        int numberOfGenerations = 80;
        double elitismRatio = 0.1;
        int mutationChance = 2;
        boolean mostDistinctPair = false;

        Scanner scanner = new Scanner(System.in);
        System.out.println("Press enter for default values.");
        System.out.println("Enter number of cities, default is 100:");
        String citiesInput = scanner.nextLine();
        if (!Objects.equals(citiesInput, "")) {
            numberOfCities = Integer.parseInt(citiesInput);
        }
        System.out.println("Enter population size, default is 2000:");
        String populationInput = scanner.nextLine();
        if (!Objects.equals(populationInput, "")) {
            populationSize = Integer.parseInt(populationInput);
        }
        System.out.println("Enter generation size, default is 80:");
        String generationInput = scanner.nextLine();
        if (!Objects.equals(generationInput, "")) {
            numberOfGenerations = Integer.parseInt(generationInput);
        }
        System.out.println("Enter elitism ratio, default is 0.1:");
        String elitismInput = scanner.nextLine();
        if (!Objects.equals(elitismInput, "")) {
            elitismRatio = Double.parseDouble(elitismInput);
        }
        System.out.println("Enter mutation chance, default is 2 (Integer 0-100):");
        String mutationInput = scanner.nextLine();
        if (!Objects.equals(mutationInput, "")) {
            mutationChance = Integer.parseInt(mutationInput);
        }
        System.out.println("Do you want to enable most distinct pair algorithm?(Y/N)");
        String distinctInput = scanner.nextLine();
        if (!Objects.equals(distinctInput, "")) {
            if (Objects.equals(distinctInput, "Y") || Objects.equals(distinctInput, "y") || Objects.equals(distinctInput, "yes")) {
                mostDistinctPair = true;
            }
        }

        World world = new World(numberOfCities, elitismRatio, mutationChance);
        ArrayList<Generation> generations = new ArrayList<>();
        Generation firstGeneration = new Generation(populationSize, world);
        generations.add(firstGeneration);

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
        TourGraph tourGraph = new TourGraph(world, generations.get(generations.size() - 1).getBestRoute());
        tourGraph.pack();
        RefineryUtilities.centerFrameOnScreen(tourGraph);
        tourGraph.setVisible(true);
    }
}