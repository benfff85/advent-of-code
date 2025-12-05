package com.adventofcode.year2025.day1;

import com.adventofcode.common.grid.Direction;
import lombok.Data;

@Data
public class Instructions {

    private final Direction direction;
    private final int steps;

    public Instructions(String input) {
        this.direction = Direction.valueOf(String.valueOf(input.charAt(0)));
        steps = Integer.parseInt(input.substring(1));
    }

    public String toString() {
        return direction.name() + steps;
    }

}
