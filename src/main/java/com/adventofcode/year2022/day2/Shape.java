package com.adventofcode.year2022.day2;

import java.util.Map;
import lombok.Getter;

public enum Shape {

    ROCK(1, "A", "X"), PAPER(2, "B", "Y"), SCISSORS(3, "C", "Z");

    @Getter
    private final Integer value;
    private final String opponentKey;
    private final String personalKey;
    private static final Map<Shape, Shape> shapeRelationships = Map.of(ROCK, SCISSORS, PAPER, ROCK, SCISSORS, PAPER);

    Shape(Integer value, String opponentKey, String personalKey) {
        this.value = value;
        this.opponentKey = opponentKey;
        this.personalKey = personalKey;
    }

    public static Shape mapFromOpponentKey(String value) {
        Shape[] shapes = Shape.values();
        for (Shape shape : shapes) {
            if (shape.opponentKey.equals(value)) {
                return shape;
            }
        }
        return null;
    }

    public static Shape mapFromPersonalKey(String value) {
        Shape[] shapes = Shape.values();
        for (Shape shape : shapes) {
            if (shape.personalKey.equals(value)) {
                return shape;
            }
        }
        return null;
    }

    public static Shape getShapeWhichWillBeatOpponent(Shape opponentsShape) {
        for (Map.Entry<Shape, Shape> entry : shapeRelationships.entrySet()) {
            if (entry.getValue().equals(opponentsShape)) {
                return entry.getKey();
            }
        }
        return null;
    }

    public static Shape getShapeWhichWillLoseToOpponent(Shape opponentsShape) {
        return shapeRelationships.get(opponentsShape);
    }

}
