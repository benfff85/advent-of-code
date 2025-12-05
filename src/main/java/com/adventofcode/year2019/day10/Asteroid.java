package com.adventofcode.year2019.day10;

import static java.util.Objects.isNull;
import java.util.*;
import lombok.Getter;

@Getter
public class Asteroid {

    private final Integer xCord;
    private final Integer yCord;
    private final String name;

    private final Map<Double, Queue<AsteroidRelationship>> visibleAsteroids;

    public Asteroid(Integer x, Integer y) {
        xCord = x;
        yCord = y;
        name = x + ":" + y;
        visibleAsteroids = new TreeMap<>();
    }

    public void addVisibleAsteroid(Double degrees, AsteroidRelationship asteroidRelationship) {
        if (isNull(visibleAsteroids.get(degrees))) {
            visibleAsteroids.put(degrees, new PriorityQueue<>());
        }
        visibleAsteroids.get(degrees).add(asteroidRelationship);
    }

}
