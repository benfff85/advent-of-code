package com.adventofcode.year2023.day13;


import com.adventofcode.common.grid.ConstructableGridElement;
import lombok.Getter;

@Getter
public enum GridElement implements ConstructableGridElement<GridElement> {

    ROCK("#"), SAND(".");

    private final String string;

    GridElement(String string) {
        this.string = string;
    }

    @Override
    public String print() {
        return string;
    }

}
