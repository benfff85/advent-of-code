package com.adventofcode.year2022.day5;

import static java.lang.Integer.parseInt;
import lombok.Data;

@Data
public class Instruction {

    private Integer quantity;
    private Integer fromStack;
    private Integer toStack;

    public Instruction(String input) {
        String[] x = input.split(" ");
        quantity = parseInt(x[1]);
        fromStack = parseInt(x[3]);
        toStack = parseInt(x[5]);
    }

}
