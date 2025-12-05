package com.adventofcode.year2023.day16;

import java.awt.Point;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.stereotype.Component;
import com.adventofcode.common.*;
import com.adventofcode.common.grid.Direction;
import com.adventofcode.common.grid.GridUtility;
import lombok.extern.slf4j.Slf4j;


@Slf4j
@Component("controller-2023-16")
public class Controller extends SolutionController {

    public DailyAnswer execute() {

        Map<Point, GridElement> grid = GridUtility.constructGrid(puzzleInput, GridElement.class);

        answer.setPart1(calculateEnergizedPoints(grid, new Point(-1, GridUtility.getMaxY(grid)), Direction.R));
        log.info("Part 1: {}", answer.getPart1());

        AtomicInteger max = new AtomicInteger();
        int maxX = GridUtility.getMaxX(grid);
        int maxY = GridUtility.getMaxY(grid);
        IntStream.rangeClosed(0, maxY).map(v -> calculateEnergizedPoints(grid, new Point(-1, v), Direction.R)).max().ifPresent(max::set);
        IntStream.rangeClosed(0, maxY).map(v -> calculateEnergizedPoints(grid, new Point(maxX + 1, v), Direction.L)).max().ifPresent(max::set);
        IntStream.rangeClosed(0, maxX).map(v -> calculateEnergizedPoints(grid, new Point(v, -1), Direction.U)).max().ifPresent(max::set);
        IntStream.rangeClosed(0, maxX).map(v -> calculateEnergizedPoints(grid, new Point(v, maxY + 1), Direction.D)).max().ifPresent(max::set);

        answer.setPart2(max.get());
        log.info("Part 2: {}", answer.getPart2());

        return answer;

    }

    private int calculateEnergizedPoints(Map<Point, GridElement> grid, Point startingPoint, Direction staqrtingDirection) {


        Set<Pair<Point, Direction>> activeBeamNodes = new HashSet<>();
        Set<Pair<Point, Direction>> visitedPoints = new HashSet<>();
        activeBeamNodes.add(Pair.of(startingPoint, staqrtingDirection));

        while (!activeBeamNodes.isEmpty()) {
            Pair<Point, Direction> pair = activeBeamNodes.iterator().next();
            activeBeamNodes.remove(pair);
            visitedPoints.add(pair);

            Map.Entry<Point, GridElement> targetPoint = GridUtility.getAdjacentElement(grid, pair.getLeft(), pair.getRight());

            if (targetPoint == null) {
                continue;
            } else if (targetPoint.getValue() == GridElement.EMPTY) {
                activeBeamNodes.add(Pair.of(targetPoint.getKey(), pair.getRight()));
            } else if (targetPoint.getValue() == GridElement.SPLITTER1) { // |
                if (List.of(Direction.R, Direction.L).contains(pair.getRight())) {
                    activeBeamNodes.add(Pair.of(targetPoint.getKey(), Direction.U));
                    activeBeamNodes.add(Pair.of(targetPoint.getKey(), Direction.D));
                } else {
                    activeBeamNodes.add(Pair.of(targetPoint.getKey(), pair.getRight()));
                }
            } else if (targetPoint.getValue() == GridElement.SPLITTER2) { // -
                if (List.of(Direction.R, Direction.L).contains(pair.getRight())) {
                    activeBeamNodes.add(Pair.of(targetPoint.getKey(), pair.getRight()));
                } else {
                    activeBeamNodes.add(Pair.of(targetPoint.getKey(), Direction.L));
                    activeBeamNodes.add(Pair.of(targetPoint.getKey(), Direction.R));
                }
            } else if (targetPoint.getValue() == GridElement.SHIFT1) { // /
                if (Direction.R.equals(pair.getRight())) {
                    activeBeamNodes.add(Pair.of(targetPoint.getKey(), Direction.U));
                } else if (Direction.L.equals(pair.getRight())) {
                    activeBeamNodes.add(Pair.of(targetPoint.getKey(), Direction.D));
                } else if (Direction.U.equals(pair.getRight())) {
                    activeBeamNodes.add(Pair.of(targetPoint.getKey(), Direction.R));
                } else if (Direction.D.equals(pair.getRight())) {
                    activeBeamNodes.add(Pair.of(targetPoint.getKey(), Direction.L));
                }
            } else if (targetPoint.getValue() == GridElement.SHIFT2) { // \
                if (Direction.R.equals(pair.getRight())) {
                    activeBeamNodes.add(Pair.of(targetPoint.getKey(), Direction.D));
                } else if (Direction.L.equals(pair.getRight())) {
                    activeBeamNodes.add(Pair.of(targetPoint.getKey(), Direction.U));
                } else if (Direction.U.equals(pair.getRight())) {
                    activeBeamNodes.add(Pair.of(targetPoint.getKey(), Direction.L));
                } else if (Direction.D.equals(pair.getRight())) {
                    activeBeamNodes.add(Pair.of(targetPoint.getKey(), Direction.R));
                }
            } else {
                throw new AdventOfCodeException("Unknown grid element: " + targetPoint.getValue());
            }

            activeBeamNodes.removeAll(visitedPoints);

        }
        return visitedPoints.stream().map(Pair::getLeft).collect(Collectors.toSet()).size() - 1;
    }

}
