package com.adventofcode.year2022.day21;

import static java.util.Objects.nonNull;
import lombok.Data;

@Data
public class Monkey {

    private final String name;
    private Long value;
    private String leftName;
    private String rightName;
    private String operand;

    public Monkey(String input) {

        this.name = input.split(":")[0];

        String[] inputArray = input.split(" ");
        if (inputArray.length == 2) {
            this.value = Long.parseLong(inputArray[1]);
        } else {
            this.leftName = inputArray[1];
            this.operand = inputArray[2];
            this.rightName = inputArray[3];
        }

    }

    public boolean isResolved() {
        return nonNull(value);
    }

}
