package com.adventofcode.year2025.day9;

import com.adventofcode.common.grid.ConstructableGridElement;
import lombok.Getter;

@Getter
public enum GridElement implements ConstructableGridElement<GridElement> {

    RED_TILE("#"), EMPTY(".");

    private final String string;

    GridElement(String string) {
        this.string = string;
    }

    @Override
    public String print() {
        return string;
    }

}
