import entities.Generation;
import entities.World;

import java.util.ArrayList;

public class TravellingSalesmanApplication {
    public static void main(String[] args) {

        // Input number of cities
        int numberOfCities = 10;
        // Input Population Size
        int populationSize = 8;
        World world = new World(numberOfCities);
        ArrayList<Generation> generations = new ArrayList<>();
        Generation firstGeneration = new Generation(populationSize, world);
        generations.add(firstGeneration);
    }
}