package com.adventofcode.year2024.day6;


import com.adventofcode.common.grid.ConstructableGridElement;
import com.adventofcode.common.grid.PrintableGridElement;
import lombok.Getter;

@Getter
public enum GridElement implements PrintableGridElement, ConstructableGridElement<GridElement> {

    GUARD("^"), OBSTACLE("#"), EMPTY(".");

    private final String string;

    GridElement(String string) {
        this.string = string;
    }

    @Override
    public String print() {
        return string;
    }

}
