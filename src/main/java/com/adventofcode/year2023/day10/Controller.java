package com.adventofcode.year2023.day10;

import static com.adventofcode.common.grid.Direction.D;
import static com.adventofcode.common.grid.Direction.U;
import static com.adventofcode.year2023.day10.GridElement.*;
import java.awt.Point;
import java.util.*;
import java.util.stream.Collectors;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.stereotype.Component;
import com.adventofcode.common.DailyAnswer;
import com.adventofcode.common.SolutionController;
import com.adventofcode.common.grid.*;
import lombok.extern.slf4j.Slf4j;


@Slf4j
@Component("controller-2023-10")
public class Controller extends SolutionController {

    private static final List<GridElement> upSupportedPipes = List.of(UD_PIPE, DR_PIPE, LD_PIPE);
    private static final List<GridElement> rightSupportedPipes = List.of(LR_PIPE, LD_PIPE, UL_PIPE);
    private static final List<GridElement> downSupportedPipes = List.of(UD_PIPE, RU_PIPE, UL_PIPE);
    private static final List<GridElement> leftSupportedPipes = List.of(LR_PIPE, RU_PIPE, DR_PIPE);

    public DailyAnswer execute() {

        Map<Point, GridElement> grid = initMap();

        Point point = GridUtility.getFirstElementByValue(grid, START).getKey();
        Point nextPoint;
        List<Point> path = new ArrayList<>();
        path.add(point);

        while (true) {

            Point finalPoint = point;
            nextPoint = GridUtility.getSurroundingElements(grid, point, SurroundingType.CARDINAL).entrySet().stream()
                    .filter(e -> !path.contains(e.getKey()))
                    .filter(e -> !e.getValue().equals(AIR))
                    .filter(e -> isConnected(grid, finalPoint, e))
                    .map(Map.Entry::getKey)
                    .findFirst().orElse(null);

            if (nextPoint == null) {
                break;
            }

            path.add(nextPoint);
            point = nextPoint;

        }

        answer.setPart1((path.size() + 1) / 2);
        log.info("Part 1: {}", answer.getPart1());

        // Explode Grid
        Map<Point, GridElement> explodedGrid = grid.entrySet().stream().map(e -> Pair.of(new Point(e.getKey().x * 2, e.getKey().y * 2), e.getValue())).collect(Collectors.toMap(Pair::getLeft, Pair::getRight));
        path.add(GridUtility.getFirstElementByValue(grid, START).getKey());
        List<Point> explodedPath = new ArrayList<>();
        for (int i = 0; i < path.size() - 1; i++) {
            Point p1 = new Point(path.get(i).x * 2, path.get(i).y * 2);
            Point p2 = new Point(path.get(i + 1).x * 2, path.get(i + 1).y * 2);

            explodedPath.add(p1);
            Point mid = PointUtil.getPointsBetween(p1, p2).get(1);
            explodedPath.add(mid);
            if (List.of(U, D).contains(PointUtil.getDirection(p1, mid))) {
                explodedGrid.put(mid, UD_PIPE);
            } else {
                explodedGrid.put(mid, LR_PIPE);
            }
        }

        // Fill with empty spaces
        int maxX = GridUtility.getMaxX(explodedGrid);
        int maxY = GridUtility.getMaxY(explodedGrid);
        for (int x = 0; x <= maxX; x++) {
            for (int y = 0; y <= maxY; y++) {
                explodedGrid.putIfAbsent(new Point(x, y), EMPTY);
            }
        }

        // Flood Fill
        int countOfElements = 0;
        Set<Point> processedPoints = new HashSet<>();
        Queue<Point> pointsToProcess = new LinkedList<>();
        pointsToProcess.add(PointUtil.getAdjacentPoint(explodedPath.getFirst(), D));
        while (!pointsToProcess.isEmpty()) {
            Point p = pointsToProcess.poll();

            if (!explodedGrid.get(p).equals(EMPTY)) {
                countOfElements++;
                explodedGrid.put(p, STAR);
            } else {
                explodedGrid.put(p, INTERIOR);
            }

            processedPoints.add(p);

            GridUtility.getSurroundingElements(explodedGrid, p, SurroundingType.ALL).entrySet().stream()
                    .filter(e -> !processedPoints.contains(e.getKey()))
                    .filter(e -> !pointsToProcess.contains(e.getKey()))
                    .filter(e -> !explodedPath.contains(e.getKey()))
                    .forEach(e -> pointsToProcess.add(e.getKey()));

        }

        log.info("{}", GridUtility.print(explodedGrid));

        answer.setPart2(countOfElements);
        log.info("Part 2: {}", answer.getPart2());

        return answer;

    }

    private boolean isConnected(Map<Point, GridElement> grid, Point point, Map.Entry<Point, GridElement> e) {
        Direction direction = PointUtil.getDirection(point, e.getKey());
        assert direction != null;
        GridElement lastElement = grid.get(point);

        if (lastElement.equals(START)) {
            return switch (direction) {
                case U -> upSupportedPipes.contains(e.getValue());
                case R -> rightSupportedPipes.contains(e.getValue());
                case D -> downSupportedPipes.contains(e.getValue());
                case L -> leftSupportedPipes.contains(e.getValue());
                default -> false;
            };
        }

        if (lastElement.equals(LR_PIPE)) {
            return switch (direction) {
                case R -> rightSupportedPipes.contains(e.getValue());
                case L -> leftSupportedPipes.contains(e.getValue());
                default -> false;
            };
        }

        if (lastElement.equals(UD_PIPE)) {
            return switch (direction) {
                case U -> upSupportedPipes.contains(e.getValue());
                case D -> downSupportedPipes.contains(e.getValue());
                default -> false;
            };
        }

        if (lastElement.equals(DR_PIPE)) {
            return switch (direction) {
                case R -> rightSupportedPipes.contains(e.getValue());
                case D -> downSupportedPipes.contains(e.getValue());
                default -> false;
            };
        }

        if (lastElement.equals(LD_PIPE)) {
            return switch (direction) {
                case D -> downSupportedPipes.contains(e.getValue());
                case L -> leftSupportedPipes.contains(e.getValue());
                default -> false;
            };
        }

        if (lastElement.equals(RU_PIPE)) {
            return switch (direction) {
                case U -> upSupportedPipes.contains(e.getValue());
                case R -> rightSupportedPipes.contains(e.getValue());
                default -> false;
            };
        }

        if (lastElement.equals(UL_PIPE)) {
            return switch (direction) {
                case U -> upSupportedPipes.contains(e.getValue());
                case L -> leftSupportedPipes.contains(e.getValue());
                default -> false;
            };
        }

        return false;

    }

    private Map<Point, GridElement> initMap() {
        Map<Point, GridElement> map = new HashMap<>();

        String[] row;
        for (int y = puzzleInput.size() - 1; y >= 0; y--) {
            row = puzzleInput.get(puzzleInput.size() - (y + 1)).split("");
            for (int x = 0; x < puzzleInput.getFirst().length(); x++) {
                map.put(new Point(x, y), fromString(row[x]));
            }
        }
        return map;
    }
}
