package com.adventofcode.year2022.day12;

import java.awt.Point;
import java.util.*;
import org.springframework.stereotype.Component;
import com.adventofcode.common.*;
import com.adventofcode.common.grid.GridUtility;
import com.adventofcode.common.grid.SurroundingType;
import lombok.extern.slf4j.Slf4j;


@Slf4j
@Component("controller-2022-12")
public class Controller extends SolutionController {

    private Map<Point, LandSquare> heightMap;

    public DailyAnswer execute() {

        heightMap = initHeightMap(puzzleInput);

        Point start = heightMap.entrySet().stream().filter(x -> x.getValue().getInput().equals("S")).map(Map.Entry::getKey).findFirst().orElseThrow(AdventOfCodeException::new);
        Point destination = heightMap.entrySet().stream().filter(x -> x.getValue().getInput().equals("E")).map(Map.Entry::getKey).findFirst().orElseThrow(AdventOfCodeException::new);

        answer.setPart1(findShortestPath(start, destination));
        log.info("P1: {}", answer.getPart1());

        int minSteps = Integer.MAX_VALUE;
        for (Map.Entry<Point, LandSquare> element : heightMap.entrySet()) {
            if (element.getValue().getLevel() == 0) {
                try {
                    minSteps = Integer.min(findShortestPath(element.getKey(), destination), minSteps);
                } catch (Exception e) {
                    // No action needed
                }
            }
        }

        answer.setPart2(minSteps);
        log.info("P2: {}", answer.getPart2());

        return answer;

    }

    private int findShortestPath(Point start, Point destination) {
        Queue<Point> queue = new ArrayDeque<>();
        Queue<Point> tempQueue = new ArrayDeque<>();
        queue.add(start);
        int count = 0;

        Set<Point> alreadyVisited = new HashSet<>();
        while (!queue.isEmpty()) {
            Point currentPoint = queue.remove();

            if (currentPoint.equals(destination)) {
                return count;
            } else {
                alreadyVisited.add(currentPoint);
                tempQueue.addAll(GridUtility.getSurroundingElements(heightMap, currentPoint, SurroundingType.CARDINAL)
                        .entrySet()
                        .stream()
                        .filter(e -> e.getValue().getLevel() <= heightMap.get(currentPoint).getLevel() + 1) // Square is at most one level higher than current point
                        .filter(e -> !alreadyVisited.contains(e.getKey())) // Square has not already been visited
                        .filter(e -> !queue.contains(e.getKey())) // Square is not already in queue
                        .filter(e -> !tempQueue.contains(e.getKey())) // Square is not already in temp queue
                        .map(Map.Entry::getKey)
                        .toList());
            }
            // Not sure if there is a better way of tracking depth of tree
            if (queue.isEmpty()) {
                queue.addAll(tempQueue);
                tempQueue.clear();
                count++;
            }

        }
        throw new AdventOfCodeException("Unable to make it to destination from given starting position");
    }

    private Map<Point, LandSquare> initHeightMap(List<String> input) {
        Map<Point, LandSquare> map = new HashMap<>();

        String[] row;
        for (int y = input.size() - 1; y >= 0; y--) {
            row = input.get(input.size() - (y + 1)).split("");
            for (int x = 0; x < input.get(0).length(); x++) {
                map.put(new Point(x, y), new LandSquare(row[x]));
            }
        }
        return map;
    }

}
