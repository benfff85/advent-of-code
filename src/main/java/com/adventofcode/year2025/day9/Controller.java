package com.adventofcode.year2025.day9;

import java.awt.Point;
import java.util.List;
import org.springframework.stereotype.Component;
import com.adventofcode.common.DailyAnswer;
import com.adventofcode.common.SolutionController;
import lombok.extern.slf4j.Slf4j;


@Slf4j
@Component("controller-2025-9")
public class Controller extends SolutionController {

    public DailyAnswer execute() {

        // Parse input into list of points
        List<Point> redTiles = puzzleInput.stream()
                .map(line -> {
                    String[] parts = line.split(",");
                    return new Point(Integer.parseInt(parts[0]), Integer.parseInt(parts[1]));
                })
                .toList();

        // Find the largest rectangle area where any two points are opposite corners
        long maxArea = 0;
        for (int i = 0; i < redTiles.size(); i++) {
            for (int j = i + 1; j < redTiles.size(); j++) {
                Point p1 = redTiles.get(i);
                Point p2 = redTiles.get(j);

                // Calculate rectangle dimensions (must have different x AND y to form a rectangle)
                long width = Math.abs(p2.x - p1.x);
                long height = Math.abs(p2.y - p1.y);

                // Rectangle area is (width + 1) * (height + 1) since points are inclusive corners
                long area = (width + 1) * (height + 1);
                maxArea = Math.max(maxArea, area);
            }
        }

        answer.setPart1(maxArea);
        log.info("Part 1: {}", answer.getPart1());

        long maxAreaPart2 = 0;
        for (int i = 0; i < redTiles.size(); i++) {
            for (int j = i + 1; j < redTiles.size(); j++) {
                Point p1 = redTiles.get(i);
                Point p2 = redTiles.get(j);

                // Calculate rectangle dimensions
                long width = Math.abs(p2.x - p1.x);
                long height = Math.abs(p2.y - p1.y);

                if (isValidPart2(p1, p2, redTiles)) {
                    long area = (width + 1) * (height + 1);
                    maxAreaPart2 = Math.max(maxAreaPart2, area);
                }
            }
        }

        answer.setPart2(maxAreaPart2);
        log.info("Part 2: {}", answer.getPart2());

        return answer;
    }

    private boolean isValidPart2(Point p1, Point p2, List<Point> polygon) {
        int minX = Math.min(p1.x, p2.x);
        int maxX = Math.max(p1.x, p2.x);
        int minY = Math.min(p1.y, p2.y);
        int maxY = Math.max(p1.y, p2.y);

        // 1. Check all 4 corners
        Point c1 = new Point(minX, minY);
        Point c2 = new Point(maxX, minY);
        Point c3 = new Point(maxX, maxY);
        Point c4 = new Point(minX, maxY);

        if (!isInsideOrOnBoundary(c1, polygon) ||
                !isInsideOrOnBoundary(c2, polygon) ||
                !isInsideOrOnBoundary(c3, polygon) ||
                !isInsideOrOnBoundary(c4, polygon)) {
            return false;
        }

        // 2. Check if any polygon edge intersects the interior of the rectangle
        for (int i = 0; i < polygon.size(); i++) {
            Point a = polygon.get(i);
            Point b = polygon.get((i + 1) % polygon.size());

            if (edgeIntersectsRectangleInterior(a, b, minX, maxX, minY, maxY)) {
                return false;
            }
        }

        return true;
    }

    private boolean isInsideOrOnBoundary(Point p, List<Point> polygon) {
        // Ray casting algorithm
        boolean result = false;
        for (int i = 0, j = polygon.size() - 1; i < polygon.size(); j = i++) {
            Point p1 = polygon.get(i);
            Point p2 = polygon.get(j);

            // Check if point is on the segment
            if (isOnSegment(p, p1, p2)) {
                return true;
            }

            if ((p1.y > p.y) != (p2.y > p.y) &&
                    (p.x < (p2.x - p1.x) * (p.y - p1.y) / (double) (p2.y - p1.y) + p1.x)) {
                result = !result;
            }
        }
        return result;
    }

    private boolean isOnSegment(Point p, Point a, Point b) {
        return p.x >= Math.min(a.x, b.x) && p.x <= Math.max(a.x, b.x) &&
                p.y >= Math.min(a.y, b.y) && p.y <= Math.max(a.y, b.y) &&
                (long) (b.x - a.x) * (p.y - a.y) == (long) (b.y - a.y) * (p.x - a.x);
    }

    private boolean edgeIntersectsRectangleInterior(Point a, Point b, int minX, int maxX, int minY, int maxY) {
        // Vertical edge
        if (a.x == b.x) {
            if (a.x > minX && a.x < maxX) {
                int y1 = Math.min(a.y, b.y);
                int y2 = Math.max(a.y, b.y);
                // Check for overlap of (y1, y2) and (minY, maxY)
                return Math.max(y1, minY) < Math.min(y2, maxY);
            }
        }
        // Horizontal edge
        else if (a.y == b.y) {
            if (a.y > minY && a.y < maxY) {
                int x1 = Math.min(a.x, b.x);
                int x2 = Math.max(a.x, b.x);
                // Check for overlap of (x1, x2) and (minX, maxX)
                return Math.max(x1, minX) < Math.min(x2, maxX);
            }
        }
        return false;
    }


}
