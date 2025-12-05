package com.adventofcode.common.grid;


import java.util.ArrayList;
import java.util.List;
import lombok.Getter;

public enum Direction {

    U(1), D(2), L(3), R(4), UL(5), UR(6), DL(7), DR(8);

    @Getter
    private final Integer value;

    Direction(Integer value) {
        this.value = value;
    }

    public static Direction getFromValue(Integer value) {
        Direction[] directions = Direction.values();
        for (Direction direction : directions) {
            if (direction.value.equals(value)) {
                return direction;
            }
        }
        return null;
    }

    public static List<Direction> allCardinal() {
        return new ArrayList<>(List.of(U, D, L, R));
    }

    public static List<Direction> allDiagonal() {
        return new ArrayList<>(List.of(UL, UR, DL, DR));
    }

    public static List<Direction> all() {
        List<Direction> list = allCardinal();
        list.addAll(allDiagonal());
        return list;
    }

    public Direction opposite() {
        return switch (this) {
            case U -> D;
            case D -> U;
            case L -> R;
            case R -> L;
            case UL -> DR;
            case UR -> DL;
            case DL -> UR;
            case DR -> UL;
        };
    }

    public Direction cw90() {
        return switch (this) {
            case U -> R;
            case R -> D;
            case D -> L;
            case L -> U;
            case UL -> UR;
            case UR -> DR;
            case DR -> DL;
            case DL -> UL;
        };
    }

    public Direction ccw90() {
        return switch (this) {
            case U -> L;
            case L -> D;
            case D -> R;
            case R -> U;
            case UL -> DL;
            case DL -> DR;
            case DR -> UR;
            case UR -> UL;
        };
    }
}
