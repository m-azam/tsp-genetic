package entities;

import java.util.concurrent.ThreadLocalRandom;

public class City {
    int x;
    int y;

    public City(int xBound, int yBound) {
        x = ThreadLocalRandom.current().nextInt(xBound);
        y = ThreadLocalRandom.current().nextInt(yBound);
    }
}
