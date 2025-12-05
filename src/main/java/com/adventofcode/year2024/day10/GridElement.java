package com.adventofcode.year2024.day10;

import com.adventofcode.common.grid.ConstructableGridElement;
import lombok.Getter;

@Getter
public enum GridElement implements ConstructableGridElement<GridElement> {

    ZERO(0, "0"), ONE(1, "1"), TWO(2, "2"), THREE(3, "3"), FOUR(4, "4"), FIVE(5, "5"), SIX(6, "6"), SEVEN(7, "7"), EIGHT(8, "8"), NINE(9, "9");

    private final Integer value;

    private final String string;

    GridElement(Integer value, String string) {
        this.value = value;
        this.string = string;
    }

    @Override
    public String print() {
        return string;
    }

}
