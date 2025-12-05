package com.adventofcode.year2024.day12;

import java.awt.Point;
import java.util.Set;
import com.adventofcode.common.grid.Direction;
import com.adventofcode.common.grid.PointUtil;
import lombok.Data;

@Data
public class Region {

    private String value;
    private Set<Point> points;
    private Integer area;
    private Integer perimeter;
    private Integer price;
    private Integer sideCount;
    private Integer bulkPrice;

    public Region(Set<Point> points, String value) {
        this.points = points;
        this.value = value;

        area = points.size();

        perimeter = calculatePerimeter(points);

        price = area * perimeter;

        sideCount = calculateSideCount(points);

        bulkPrice = area * sideCount;

    }

    private Integer calculatePerimeter(Set<Point> points) {
        Integer p = 0;
        for (Point point : points) {
            Point adjacentPoint = PointUtil.getAdjacentPoint(point, Direction.U);
            if (!points.contains(adjacentPoint)) {
                p++;
            }
            adjacentPoint = PointUtil.getAdjacentPoint(point, Direction.D);
            if (!points.contains(adjacentPoint)) {
                p++;
            }
            adjacentPoint = PointUtil.getAdjacentPoint(point, Direction.L);
            if (!points.contains(adjacentPoint)) {
                p++;
            }
            adjacentPoint = PointUtil.getAdjacentPoint(point, Direction.R);
            if (!points.contains(adjacentPoint)) {
                p++;
            }
        }
        return p;
    }

    private Integer calculateSideCount(Set<Point> points) {
        Integer s = 0;
        for (Point point : points) {
            // Is end of a right side (going down)
            // Nothing on right side
            // And nothing below _|
            // Or something below and below right |_
            if (!points.contains(PointUtil.getAdjacentPoint(point, Direction.R)) &&
                    (!points.contains(PointUtil.getAdjacentPoint(point, Direction.D))
                            || (points.contains(PointUtil.getAdjacentPoint(point, Direction.D)) && points.contains(PointUtil.getAdjacentPoint(point, Direction.DR))))) {
                s++;
            }

            // Is end of a left side (going down)
            if (!points.contains(PointUtil.getAdjacentPoint(point, Direction.L)) &&
                    (!points.contains(PointUtil.getAdjacentPoint(point, Direction.D))
                            || (points.contains(PointUtil.getAdjacentPoint(point, Direction.D)) && points.contains(PointUtil.getAdjacentPoint(point, Direction.DL))))) {
                s++;
            }

            // Is end of a top side (going right)
            if (!points.contains(PointUtil.getAdjacentPoint(point, Direction.U)) &&
                    (!points.contains(PointUtil.getAdjacentPoint(point, Direction.R))
                            || (points.contains(PointUtil.getAdjacentPoint(point, Direction.R)) && points.contains(PointUtil.getAdjacentPoint(point, Direction.UR))))) {
                s++;
            }

            // Is end of a bottom side (going right)
            if (!points.contains(PointUtil.getAdjacentPoint(point, Direction.D)) &&
                    (!points.contains(PointUtil.getAdjacentPoint(point, Direction.R))
                            || (points.contains(PointUtil.getAdjacentPoint(point, Direction.R)) && points.contains(PointUtil.getAdjacentPoint(point, Direction.DR))))) {
                s++;
            }

        }
        return s;
    }

}
