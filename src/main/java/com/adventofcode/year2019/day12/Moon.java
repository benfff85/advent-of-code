package com.adventofcode.year2019.day12;

import java.util.Arrays;
import java.util.List;
import org.springframework.util.StringUtils;
import lombok.Getter;

@Getter
public class Moon {

    private final Velocity currentVelocity = new Velocity(0, 0, 0);
    private final Coordinate currentCoordinate;

    public Moon(String input) {
        List<Integer> values = Arrays.stream(StringUtils.deleteAny(input, "<>zyx= ").split(",")).map(Integer::parseInt).toList();
        currentCoordinate = new Coordinate(values.get(0), values.get(1), values.get(2));
    }

    public Vector getVector(Axis axis) {
        if (Axis.X.equals(axis)) {
            return new Vector(currentCoordinate.getX(), currentVelocity.getX());
        } else if (Axis.Y.equals(axis)) {
            return new Vector(currentCoordinate.getY(), currentVelocity.getY());
        } else {
            return new Vector(currentCoordinate.getZ(), currentVelocity.getZ());
        }
    }

}
