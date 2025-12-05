package com.adventofcode.year2022.day10;

import static java.lang.Integer.parseInt;
import lombok.Data;

@Data
public class Instruction {

    private final String action;
    private final Integer magnitude;

    public Instruction(String input) {
        action = input.split(" ")[0];
        if (action.equals("addx")) {
            magnitude = parseInt(input.split(" ")[1]);
        } else {
            magnitude = 0;
        }

    }

}
