package com.adventofcode.year2022.day14;

import static java.lang.Integer.parseInt;
import java.awt.Point;
import java.util.*;
import org.springframework.stereotype.Component;
import com.adventofcode.common.DailyAnswer;
import com.adventofcode.common.SolutionController;
import com.adventofcode.common.grid.*;
import lombok.extern.slf4j.Slf4j;


@Slf4j
@Component("controller-2022-14")
public class Controller extends SolutionController {

    public DailyAnswer execute() {

        Map<Point, GridElement> grid = initGrid();
        Integer minY = GridUtility.getMinY(grid);
        Point point;
        do {
            point = placeSand(grid, minY);
            grid.put(point, GridElement.SAND);
        } while (point.y > minY);

        answer.setPart1(GridUtility.countValues(grid, GridElement.SAND) - 1);
        log.info("P1: {}", answer.getPart1());


        grid = initGrid();
        minY = GridUtility.getMinY(grid) - 2;
        // Add floor
        for (Point p : PointUtil.getPointsBetween(new Point(0, minY), new Point(1000, minY))) {
            grid.put(p, GridElement.ROCK);
        }
        do {
            point = placeSand(grid, minY);
            grid.put(point, GridElement.SAND);
        } while (!point.equals(new Point(500, 0)));

        answer.setPart2(GridUtility.countValues(grid, GridElement.SAND));
        log.info("P2: {}", answer.getPart2());

        return answer;

    }

    private Map<Point, GridElement> initGrid() {
        Map<Point, GridElement> grid = new HashMap<>();
        for (String s : puzzleInput) {
            List<Point> referencePoints = Arrays.stream(s.split(" -> ")).map(i -> new Point(parseInt(i.split(",")[0]), parseInt(i.split(",")[1]) * -1)).toList();
            for (int i = 1; i < referencePoints.size(); i++) {
                PointUtil.getPointsBetween(referencePoints.get(i - 1), referencePoints.get(i)).forEach(p -> grid.put(p, GridElement.ROCK));

            }
        }
        grid.put(new Point(500, 0), GridElement.SOURCE);
        return grid;
    }

    private Point placeSand(Map<Point, GridElement> grid, Integer minY) {

        Point currentSandPoint;
        Point nextSandPoint = new Point(500, 0);

        do {
            currentSandPoint = nextSandPoint;
            nextSandPoint = calculateNextPoint(grid, currentSandPoint);
        } while (!currentSandPoint.equals(nextSandPoint) && nextSandPoint.y >= minY);

        return currentSandPoint;

    }

    private Point calculateNextPoint(Map<Point, GridElement> grid, Point currentSandPoint) {
        Point adjacentPoint = PointUtil.getAdjacentPoint(currentSandPoint, Direction.D);
        GridElement element = grid.getOrDefault(adjacentPoint, GridElement.AIR);
        if (GridElement.AIR.equals(element)) {
            return adjacentPoint;
        }

        adjacentPoint = PointUtil.getAdjacentPoint(currentSandPoint, Direction.DL);
        element = grid.getOrDefault(adjacentPoint, GridElement.AIR);
        if (GridElement.AIR.equals(element)) {
            return adjacentPoint;
        }

        adjacentPoint = PointUtil.getAdjacentPoint(currentSandPoint, Direction.DR);
        element = grid.getOrDefault(adjacentPoint, GridElement.AIR);
        if (GridElement.AIR.equals(element)) {
            return adjacentPoint;
        }

        return currentSandPoint;

    }
}
