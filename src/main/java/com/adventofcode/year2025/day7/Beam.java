package com.adventofcode.year2025.day7;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;
import com.adventofcode.common.grid.Direction;
import lombok.Data;

@Data
public class Beam {

    private List<Point> path;
    private boolean isActiveIndicator;
    private Direction direction;

    public Beam(Point startPoint, Direction direction) {
        this.path = new ArrayList<>();
        this.path.add(startPoint);
        this.isActiveIndicator = true;
        this.direction = direction;
    }

    public Point getCurrentPosition() {
        return path.getLast();
    }

    public void addToPath(Point point) {
        path.add(point);
    }

}
