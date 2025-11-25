package com.adventofcode.year2024.day15;

import com.adventofcode.common.grid.ConstructableGridElement;
import lombok.Getter;

@Getter
public enum GridElement implements ConstructableGridElement<GridElement> {

    WALL("#"), SPACE("."), ROBOT("@"), BOX("O");

    private final String string;

    GridElement(String string) {
        this.string = string;
    }

    @Override
    public String print() {
        return string;
    }

}
