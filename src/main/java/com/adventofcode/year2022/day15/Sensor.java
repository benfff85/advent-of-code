package com.adventofcode.year2022.day15;

import static java.lang.Math.abs;
import java.awt.Point;
import java.util.Arrays;
import java.util.List;
import org.apache.commons.lang3.CharSet;
import org.apache.commons.lang3.CharSetUtils;
import org.apache.commons.math3.util.Pair;
import com.adventofcode.common.grid.PointUtil;
import lombok.Data;

@Data
public class Sensor {

    private final Point sensorPoint;
    private final Point beaconPoint;
    private final Integer manhattanDistance;

    public Sensor(String input) {

        List<Integer> inputInts = Arrays.stream(CharSetUtils.delete(input, CharSet.ASCII_ALPHA + ":").split("="))
                .skip(1)
                .map(Integer::parseInt)
                .toList();

        sensorPoint = new Point(inputInts.get(0), inputInts.get(1));
        beaconPoint = new Point(inputInts.get(2), inputInts.get(3));
        manhattanDistance = PointUtil.getManhattanDistance(sensorPoint, beaconPoint);

    }

    public Pair<Integer, Integer> getCoveredPointsOnRow(Integer y) {

        int verticalDistanceFromSensor = abs(sensorPoint.y - y);

        if (verticalDistanceFromSensor > manhattanDistance) {
            return new Pair<>(0, 0);
        }

        int xRange = manhattanDistance - verticalDistanceFromSensor;
        return new Pair<>(sensorPoint.x - xRange, sensorPoint.x + xRange);

    }


}
