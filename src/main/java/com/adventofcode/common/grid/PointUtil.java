package com.adventofcode.common.grid;

import static java.lang.Math.*;
import static org.apache.commons.collections4.IterableUtils.toList;
import java.awt.Point;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.math3.util.IntegerSequence;
import com.adventofcode.common.AdventOfCodeException;

public class PointUtil {

    private PointUtil() {
        // Masking public constructor
    }

    public static List<Point> getSurroundingPoints(Point point, SurroundingType surroundingType) {

        List<Direction> directions = switch (surroundingType) {
            case CARDINAL -> Direction.allCardinal();
            case DIAGONAL -> Direction.allDiagonal();
            case ALL -> Direction.all();
        };

        return directions.stream().map(direction -> getAdjacentPoint(point, direction)).toList();

    }

    public static Point getAdjacentPoint(Point point, Direction direction) {
        return switch (direction) {
            case U -> new Point(point.x, point.y + 1);
            case D -> new Point(point.x, point.y - 1);
            case L -> new Point(point.x - 1, point.y);
            case R -> new Point(point.x + 1, point.y);
            case UL -> new Point(point.x - 1, point.y + 1);
            case UR -> new Point(point.x + 1, point.y + 1);
            case DL -> new Point(point.x - 1, point.y - 1);
            case DR -> new Point(point.x + 1, point.y - 1);
        };
    }

    public static List<Point> getAdjacentPoints(List<Point> points, Direction direction) {
        return points.stream().map(p -> PointUtil.getAdjacentPoint(p, direction)).toList();
    }

    public static Integer getManhattanDistance(Point p1, Point p2) {
        return abs(p1.x - p2.x) + abs(p1.y - p2.y);
    }

    // Returns the direction needed to get from point1 to point2, points must be adjacent
    public static Direction getDirection(Point point1, Point point2) {
        for (Direction direction : Direction.all()) {
            if (getAdjacentPoint(point1, direction).equals(point2)) {
                return direction;
            }
        }
        return null;
    }

    public static List<Point> getPointsBetween(Point p1, Point p2) {
        if (p1.x != p2.x && p1.y != p2.y) {
            throw new AdventOfCodeException("Can only get points between two points that share an X or Y axis");
        }

        List<Point> points = new ArrayList<>();
        if (p1.x == p2.x) {
            for (Integer i : toList(new IntegerSequence.Range(min(p1.y, p2.y), max(p1.y, p2.y), 1))) {
                points.add(new Point(p1.x, i));
            }
        }

        if (p1.y == p2.y) {
            for (Integer i : toList(new IntegerSequence.Range(min(p1.x, p2.x), max(p1.x, p2.x), 1))) {
                points.add(new Point(i, p1.y));
            }
        }

        return points;

    }

}
