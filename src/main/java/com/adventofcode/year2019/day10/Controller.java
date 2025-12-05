package com.adventofcode.year2019.day10;

import java.util.*;
import org.springframework.stereotype.Component;
import com.adventofcode.common.DailyAnswer;
import com.adventofcode.common.SolutionController;
import lombok.extern.slf4j.Slf4j;


@Slf4j
@Component("controller-day-10")
public class Controller extends SolutionController {

    public DailyAnswer execute() {

        List<Asteroid> asteroids = parseAsteroidList();

        for (Asteroid asteroidA : asteroids) {
            for (Asteroid asteroidB : asteroids) {
                if (!asteroidA.equals(asteroidB)) {
                    assessVisibility(asteroidA, asteroidB);
                }
            }
        }

        Asteroid maxVisibleAsteroid = asteroids.get(0);
        for (Asteroid asteroid : asteroids) {
            if (asteroid.getVisibleAsteroids().size() > maxVisibleAsteroid.getVisibleAsteroids().size()) {
                maxVisibleAsteroid = asteroid;
            }
        }
        answer.setPart1(maxVisibleAsteroid.getVisibleAsteroids().size());
        log.info("Max Visible Asteroids: {}", answer.getPart1());

        Asteroid vaporizedAsteroid = find200thVaporizedAsteroid(maxVisibleAsteroid);
        answer.setPart2((100 * vaporizedAsteroid.getXCord()) + vaporizedAsteroid.getYCord());

        return answer;
    }


    private List<Asteroid> parseAsteroidList() {
        List<Asteroid> asteroids = new ArrayList<>();
        for (int i = 0; i < puzzleInput.size(); i++) {
            for (int j = 0; j < puzzleInput.get(i).length(); j++) {
                if (puzzleInput.get(i).charAt(j) == '#') {
                    asteroids.add(new Asteroid(j, i));
                }
            }
        }
        return asteroids;
    }

    private void assessVisibility(Asteroid asteroidA, Asteroid asteroidB) {
        Integer distance = Math.abs(asteroidA.getXCord() - asteroidB.getXCord()) + Math.abs(asteroidA.getYCord() - asteroidB.getYCord());
        Double degrees = getRadialDegrees(asteroidA, asteroidB);
        asteroidA.addVisibleAsteroid(degrees, AsteroidRelationship.builder().asteroid(asteroidB).distance(distance).build());
    }

    private Double getRadialDegrees(Asteroid asteroidA, Asteroid asteroidB) {
        double degrees = Math.toDegrees(Math.atan2((double) asteroidB.getXCord() - asteroidA.getXCord(), (double) asteroidA.getYCord() - asteroidB.getYCord()));
        if (degrees < 0) {
            degrees += 360;
        }
        return degrees;
    }

    private Asteroid find200thVaporizedAsteroid(Asteroid asteroid) {
        int vaporizedAsteroidCount = 0;
        Asteroid vaporizedAsteroid = null;
        Map<Double, Queue<AsteroidRelationship>> asteroidMap = asteroid.getVisibleAsteroids();
        for (Queue<AsteroidRelationship> asteroidRelationshipQueue : asteroidMap.values()) {
            if (!asteroidRelationshipQueue.isEmpty()) {
                vaporizedAsteroidCount++;
                vaporizedAsteroid = asteroidRelationshipQueue.poll().getAsteroid();
                if (vaporizedAsteroidCount == 200) {
                    break;
                }
            }
        }
        return vaporizedAsteroid;
    }

}
