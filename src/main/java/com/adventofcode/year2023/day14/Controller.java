package com.adventofcode.year2023.day14;

import java.awt.Point;
import java.util.*;
import org.springframework.stereotype.Component;
import com.adventofcode.common.DailyAnswer;
import com.adventofcode.common.SolutionController;
import com.adventofcode.common.grid.Direction;
import com.adventofcode.common.grid.GridUtility;
import lombok.extern.slf4j.Slf4j;


@Slf4j
@Component("controller-2023-14")
public class Controller extends SolutionController {

    private Map<Point, GridElement> grid;
    private int maxY;
    private int maxX;

    public DailyAnswer execute() {

        grid = GridUtility.constructGridWithSelectElements(puzzleInput, GridElement.class, List.of(GridElement.MOVING_ROCK, GridElement.FIXED_ROCK));
        maxY = GridUtility.getMaxY(grid);
        maxX = GridUtility.getMaxX(grid);

        slide(Direction.U);

        answer.setPart1(GridUtility.getElementsByValue(grid, GridElement.MOVING_ROCK).keySet().stream().mapToInt(p -> p.y + 1).sum());
        log.info("Part 1: {}", answer.getPart1());


        grid = GridUtility.constructGridWithSelectElements(puzzleInput, GridElement.class, List.of(GridElement.MOVING_ROCK, GridElement.FIXED_ROCK));
        List<Set<Point>> cache = new ArrayList<>();
        cache.add(GridUtility.getElementsByValue(grid, GridElement.MOVING_ROCK).keySet());

        int cycleStart;
        int cycleSize;
        while (true) {

            cycle();

            if (cache.contains(GridUtility.getElementsByValue(grid, GridElement.MOVING_ROCK).keySet())) {
                log.info("Cycle: {} matches {}", cache.size(), cache.indexOf(GridUtility.getElementsByValue(grid, GridElement.MOVING_ROCK).keySet()));
                cycleStart = cache.indexOf(GridUtility.getElementsByValue(grid, GridElement.MOVING_ROCK).keySet());
                cycleSize = cache.size() - cycleStart;
                break;
            }
            cache.add(GridUtility.getElementsByValue(grid, GridElement.MOVING_ROCK).keySet());

        }

        answer.setPart2(cache.get((1000000000 % cycleSize) + cycleSize).stream().mapToInt(p -> p.y + 1).sum());
        log.info("Part 2: {}", answer.getPart2());

        return answer;

    }

    private void cycle() {
        slide(Direction.U);
        slide(Direction.L);
        slide(Direction.D);
        slide(Direction.R);
    }

    private void slide(Direction direction) {
        if (Direction.U.equals(direction)) {
            for (int y = maxY; y >= 0; y--) {
                List<Point> movableRockPoints = GridUtility.getElementsOnRow(grid, y).entrySet().stream()
                        .filter(e -> e.getValue().equals(GridElement.MOVING_ROCK))
                        .map(Map.Entry::getKey).toList();
                for (Point rock : movableRockPoints) {
                    GridUtility.slideElementUntilEncounteringElement(grid, rock, Direction.U, maxY);
                }
            }
        }
        if (Direction.L.equals(direction)) {

            for (int x = 0; x <= maxX; x++) {
                List<Point> movableRockPoints = GridUtility.getElementsInColumn(grid, x).entrySet().stream()
                        .filter(e -> e.getValue().equals(GridElement.MOVING_ROCK))
                        .map(Map.Entry::getKey).toList();
                for (Point rock : movableRockPoints) {
                    GridUtility.slideElementUntilEncounteringElement(grid, rock, Direction.L, 0);
                }
            }
        }
        if (Direction.D.equals(direction)) {

            for (int y = 0; y <= maxY; y++) {
                List<Point> movableRockPoints = GridUtility.getElementsOnRow(grid, y).entrySet().stream()
                        .filter(e -> e.getValue().equals(GridElement.MOVING_ROCK))
                        .map(Map.Entry::getKey).toList();
                for (Point rock : movableRockPoints) {
                    GridUtility.slideElementUntilEncounteringElement(grid, rock, Direction.D, 0);
                }
            }
        }
        if (Direction.R.equals(direction)) {

            for (int x = maxX; x >= 0; x--) {
                List<Point> movableRockPoints = GridUtility.getElementsInColumn(grid, x).entrySet().stream()
                        .filter(e -> e.getValue().equals(GridElement.MOVING_ROCK))
                        .map(Map.Entry::getKey).toList();
                for (Point rock : movableRockPoints) {
                    GridUtility.slideElementUntilEncounteringElement(grid, rock, Direction.R, maxX);
                }
            }
        }
    }

}
