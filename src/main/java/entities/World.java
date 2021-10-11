package entities;

import java.util.ArrayList;

public class World {

    public ArrayList<City> cities = new ArrayList<>();
    double[][] distanceMatrix;
    int xBound = 3000;
    int yBound = 3000;
    public double elitismRatio;
    public int mutationChance;

    public World(int numberOfCities, double elitismRatio, int mutationChance) {
        distanceMatrix = new double[numberOfCities][numberOfCities];
        for (int iterator = 0; iterator < numberOfCities; iterator++) {
            cities.add(new City(xBound, yBound));
        }
        this.elitismRatio = elitismRatio;
        this.mutationChance = mutationChance;
        calculateDistanceMatrix();
    }

    private void calculateDistanceMatrix() {
        for (int source = 0; source < cities.size(); source++) {
            for (int destination = 0; destination < cities.size(); destination++) {
                if (source == destination) {
                    distanceMatrix[source][destination] = 0.0;
                } else {
                    double dx = cities.get(destination).x - cities.get(source).x;
                    double dy = cities.get(destination).y - cities.get(source).y;
                    distanceMatrix[source][destination] = Math.sqrt(Math.pow(dx, 2) + Math.pow(dy, 2));
                }
            }
        }
    }

}
