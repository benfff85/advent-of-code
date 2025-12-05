package com.adventofcode.year2019.day15;

import static com.adventofcode.common.grid.Direction.*;
import java.awt.Point;
import java.util.*;
import com.adventofcode.common.grid.PointUtil;
import com.adventofcode.common.grid.PrintableGridElement;

public class OxygenSimulator {


    public int run(Map<Point, PrintableGridElement> grid) {
        int count = 0;
        List<Point> pointsToFlip = new LinkedList<>();

        do {
            count++;
            pointsToFlip.clear();

            // Loop through all points and find which to flip
            Point point;
            for (Map.Entry<Point, PrintableGridElement> entry : grid.entrySet()) {
                point = entry.getKey();
                if (entry.getValue().equals(GridElement.EMPTY) && atLeastOneAdjacentPointIsOxygen(grid, point)) {
                    pointsToFlip.add(entry.getKey());
                }
            }

            // Flip said points
            for (Point pointToFlip : pointsToFlip) {
                grid.put(pointToFlip, GridElement.OXYGEN);
            }

        } while (!pointsToFlip.isEmpty());
        count--;

        return count;

    }

    // TODO use getSurroundingPoints
    private boolean atLeastOneAdjacentPointIsOxygen(Map<Point, PrintableGridElement> grid, Point point) {
        return grid.getOrDefault(PointUtil.getAdjacentPoint(point, U), GridElement.WALL).equals(GridElement.OXYGEN)
                || grid.getOrDefault(PointUtil.getAdjacentPoint(point, D), GridElement.WALL).equals(GridElement.OXYGEN)
                || grid.getOrDefault(PointUtil.getAdjacentPoint(point, L), GridElement.WALL).equals(GridElement.OXYGEN)
                || grid.getOrDefault(PointUtil.getAdjacentPoint(point, R), GridElement.WALL).equals(GridElement.OXYGEN);
    }

}
