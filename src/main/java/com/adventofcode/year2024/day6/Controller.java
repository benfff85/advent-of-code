package com.adventofcode.year2024.day6;

import static com.adventofcode.common.grid.GridUtility.constructGridWithoutSelectElements;
import java.awt.Point;
import java.util.*;
import org.apache.commons.math3.util.Pair;
import org.springframework.stereotype.Component;
import com.adventofcode.common.DailyAnswer;
import com.adventofcode.common.SolutionController;
import com.adventofcode.common.grid.Direction;
import com.adventofcode.common.grid.GridUtility;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component("controller-2024-6")
public class Controller extends SolutionController {

    public DailyAnswer execute() {

        Map<Point, GridElement> grid = constructGridWithoutSelectElements(puzzleInput, GridElement.class, List.of(GridElement.EMPTY));
        Point guardStartingPosition = GridUtility.getFirstElementByValue(grid, GridElement.GUARD).getKey();

        GuardAnalysisResult initialAnalysisResult = analyzeGuardPath(grid, guardStartingPosition, Direction.U);

        answer.setPart1(initialAnalysisResult.getVisitedSpaces().size());
        log.info("Part 1: {}", answer.getPart1());

        int count = 0;
        for (Point p : initialAnalysisResult.getVisitedSpaces()) {

            if (p.equals(guardStartingPosition)) {
                continue;
            }

            grid.put(p, GridElement.OBSTACLE);

            GuardAnalysisResult analysisResult = analyzeGuardPath(grid, guardStartingPosition, Direction.U);
            if (analysisResult.isCyclic()) {
                count++;
            }

            // Reset Grid
            grid.remove(p);
            grid.remove(GridUtility.getFirstElementByValue(grid, GridElement.GUARD).getKey());
            grid.put(guardStartingPosition, GridElement.GUARD);
        }

        answer.setPart2(count);
        log.info("Part 2: {}", answer.getPart2());

        return answer;
    }

    private GuardAnalysisResult analyzeGuardPath(Map<Point, GridElement> grid, Point guardPosition, Direction guardDirection) {
        Set<Point> visitedSpaces = new HashSet<>();
        Set<Pair<Point, Direction>> movementEndingPositioning = new HashSet<>();
        visitedSpaces.add(guardPosition);

        while (guardInGrid(guardPosition, grid)) {

            Integer cutoff = switch (guardDirection) {
                case U -> GridUtility.getMaxY(grid);
                case D -> GridUtility.getMinY(grid);
                case L -> GridUtility.getMinX(grid);
                default -> GridUtility.getMaxX(grid);
            };

            visitedSpaces.addAll(GridUtility.slideElementUntilEncounteringElement(grid, guardPosition, guardDirection, cutoff).keySet());

            Pair<Point, Direction> endingPositioning = new Pair<>(guardPosition, guardDirection);
            if (movementEndingPositioning.contains(endingPositioning)) {
                return GuardAnalysisResult.builder().visitedSpaces(visitedSpaces).isCyclic(true).build();
            }
            movementEndingPositioning.add(endingPositioning);

            guardDirection = guardDirection.cw90();
            guardPosition = GridUtility.getFirstElementByValue(grid, GridElement.GUARD).getKey();
        }


        return GuardAnalysisResult.builder().visitedSpaces(visitedSpaces).isCyclic(false).build();
    }



    private static boolean guardInGrid(Point guardPosition, Map<Point, GridElement> grid) {
        return guardPosition.x > GridUtility.getMinX(grid) &&
                guardPosition.x < GridUtility.getMaxX(grid) &&
                guardPosition.y > GridUtility.getMinY(grid) &&
                guardPosition.y < GridUtility.getMaxY(grid);
    }


}
