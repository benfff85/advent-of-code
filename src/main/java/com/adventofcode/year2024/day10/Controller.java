package com.adventofcode.year2024.day10;

import static com.adventofcode.common.grid.GridUtility.constructGrid;
import java.awt.Point;
import java.util.*;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;
import com.adventofcode.common.DailyAnswer;
import com.adventofcode.common.SolutionController;
import com.adventofcode.common.grid.GridUtility;
import com.adventofcode.common.grid.SurroundingType;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component("controller-2024-10")
public class Controller extends SolutionController {

    public DailyAnswer execute() {

        Map<Point, GridElement> grid = constructGrid(puzzleInput, GridElement.class);
        log.info("{}", GridUtility.print(grid));
        List<Trailhead> trailheadList = grid.entrySet().stream().filter(e -> e.getValue().equals(GridElement.ZERO)).map(e -> Trailhead.builder().startingPoint(e.getKey()).build()).toList();

        int scoreSum = 0;
        for (Trailhead trailhead : trailheadList) {
            calculateScore(trailhead, grid);
            scoreSum += trailhead.getScore();
        }

        answer.setPart1(scoreSum);
        log.info("Part 1: {}", answer.getPart1());

        scoreSum = 0;
        for (Trailhead trailhead : trailheadList) {
            calculateScoreUniquePaths(trailhead, grid);
            scoreSum += trailhead.getScore();
        }

        answer.setPart2(scoreSum);
        log.info("Part 2: {}", answer.getPart2());

        return answer;
    }

    private void calculateScore(Trailhead trailhead, Map<Point, GridElement> grid) {
        Set<Point> allEncounteredPoints = new HashSet<>();
        Queue<Point> pointsNewEncounteredForFirstTime = new ArrayDeque<>();

        allEncounteredPoints.add(trailhead.getStartingPoint());
        pointsNewEncounteredForFirstTime.add(trailhead.getStartingPoint());

        while (!pointsNewEncounteredForFirstTime.isEmpty()) {
            Point currentPoint = pointsNewEncounteredForFirstTime.remove();

            if (grid.get(currentPoint).equals(GridElement.NINE)) {
                continue;
            }
            Set<Point> navigablePoints = GridUtility.getSurroundingElements(grid, currentPoint, SurroundingType.CARDINAL)
                    .entrySet()
                    .stream()
                    .filter(e -> e.getValue().getValue().equals(grid.get(currentPoint).getValue() + 1))
                    .map(Map.Entry::getKey)
                    .collect(Collectors.toSet());

            navigablePoints.stream().filter(e -> !allEncounteredPoints.contains(e)).forEach(pointsNewEncounteredForFirstTime::add);
            allEncounteredPoints.addAll(navigablePoints);

        }
        trailhead.setScore(allEncounteredPoints.stream().filter(e -> grid.get(e).equals(GridElement.NINE)).count());
    }

    private void calculateScoreUniquePaths(Trailhead trailhead, Map<Point, GridElement> grid) {
        long count = 0;
        Queue<Point> pointsNewEncounteredForFirstTime = new ArrayDeque<>();

        pointsNewEncounteredForFirstTime.add(trailhead.getStartingPoint());

        while (!pointsNewEncounteredForFirstTime.isEmpty()) {
            Point currentPoint = pointsNewEncounteredForFirstTime.remove();

            if (grid.get(currentPoint).equals(GridElement.NINE)) {
                count++;
                continue;
            }
            Set<Point> navigablePoints = GridUtility.getSurroundingElements(grid, currentPoint, SurroundingType.CARDINAL)
                    .entrySet()
                    .stream()
                    .filter(e -> e.getValue().getValue().equals(grid.get(currentPoint).getValue() + 1))
                    .map(Map.Entry::getKey)
                    .collect(Collectors.toSet());

            pointsNewEncounteredForFirstTime.addAll(navigablePoints);

        }
        trailhead.setScore(count);
    }

}
