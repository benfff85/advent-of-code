package com.adventofcode.year2022.day17;

import java.awt.Point;
import java.util.List;
import java.util.Map;
import org.apache.commons.collections4.CollectionUtils;
import com.adventofcode.common.grid.Direction;
import com.adventofcode.common.grid.PointUtil;
import lombok.Getter;

@Getter
public class Rock {

    private List<Point> points;
    private final RockType rockType;

    public Rock(Integer highestRock, RockType rockType) {
        int startingCordY = highestRock + 4;
        this.rockType = rockType;

        points = switch (rockType) {
            case DASH -> List.of(new Point(3, startingCordY), new Point(4, startingCordY), new Point(5, startingCordY), new Point(6, startingCordY));
            case PLUS -> List.of(new Point(3, startingCordY + 1), new Point(4, startingCordY + 2), new Point(4, startingCordY + 1), new Point(4, startingCordY), new Point(5, startingCordY + 1));
            case ANGLE -> List.of(new Point(3, startingCordY), new Point(4, startingCordY), new Point(5, startingCordY), new Point(5, startingCordY + 1), new Point(5, startingCordY + 2));
            case LINE -> List.of(new Point(3, startingCordY), new Point(3, startingCordY + 1), new Point(3, startingCordY + 2), new Point(3, startingCordY + 3));
            case SQUARE -> List.of(new Point(3, startingCordY), new Point(3, startingCordY + 1), new Point(4, startingCordY), new Point(4, startingCordY + 1));
        };

    }

    public boolean move(Direction direction, Map<Point, GridElement> grid) {
        List<Point> targetPoints = PointUtil.getAdjacentPoints(points, direction);
        if (CollectionUtils.containsAny(grid.keySet(), targetPoints)) {
            return false;
        }

        points = targetPoints;
        return true;
    }

}
