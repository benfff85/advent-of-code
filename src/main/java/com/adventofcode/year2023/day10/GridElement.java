package com.adventofcode.year2023.day10;


import java.util.Arrays;
import com.adventofcode.common.grid.PrintableGridElement;
import lombok.Getter;

@Getter
public enum GridElement implements PrintableGridElement {

    LR_PIPE("-"), UD_PIPE("|"), DR_PIPE("F"), LD_PIPE("7"), RU_PIPE("L"), UL_PIPE("J"), AIR("."), START("S"), EMPTY("X"), INTERIOR(" "), STAR("*");

    private final String string;

    GridElement(String string) {
        this.string = string;
    }

    public static GridElement fromString(String string) {
        return Arrays.stream(GridElement.values()).filter(gridElement -> gridElement.getString().equals(string)).findFirst().orElseThrow();
    }

    @Override
    public String print() {
        return string;
    }

}
