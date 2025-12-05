package com.adventofcode.year2019.day3;

import static java.lang.Integer.parseInt;
import com.adventofcode.common.grid.Direction;
import lombok.Data;

@Data
public class Instruction {

    private Direction direction;
    private Integer magnitude;

    public Instruction(String input) {
        direction = Direction.valueOf(input.substring(0, 1));
        magnitude = parseInt(input.substring(1));
    }

}
