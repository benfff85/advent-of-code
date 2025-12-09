package com.adventofcode.year2025.day9;

import java.awt.Point;
import java.util.List;

public class PolygonUtility {

    private PolygonUtility() {
        // Private constructor for utility class
    }

    public static boolean isRectangleInsidePolygon(Point p1, Point p2, List<Point> polygon) {
        int minX = Math.min(p1.x, p2.x);
        int maxX = Math.max(p1.x, p2.x);
        int minY = Math.min(p1.y, p2.y);
        int maxY = Math.max(p1.y, p2.y);

        // 1. Check all 4 corners
        if (!isInsideOrOnBoundary(new Point(minX, minY), polygon) ||
                !isInsideOrOnBoundary(new Point(maxX, minY), polygon) ||
                !isInsideOrOnBoundary(new Point(maxX, maxY), polygon) ||
                !isInsideOrOnBoundary(new Point(minX, maxY), polygon)) {
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

    private static boolean isInsideOrOnBoundary(Point p, List<Point> polygon) {
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

    private static boolean isOnSegment(Point p, Point a, Point b) {
        return p.x >= Math.min(a.x, b.x) && p.x <= Math.max(a.x, b.x) &&
                p.y >= Math.min(a.y, b.y) && p.y <= Math.max(a.y, b.y) &&
                (long) (b.x - a.x) * (p.y - a.y) == (long) (b.y - a.y) * (p.x - a.x);
    }

    private static boolean edgeIntersectsRectangleInterior(Point a, Point b, int minX, int maxX, int minY, int maxY) {
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
