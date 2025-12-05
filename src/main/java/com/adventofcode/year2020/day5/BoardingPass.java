package com.adventofcode.year2020.day5;

import static java.lang.Integer.parseInt;
import lombok.Data;

@Data
public class BoardingPass {

    private final int row;
    private final int column;
    private final int seatId;

    public BoardingPass(String input) {
        row = parseInt(input.substring(0, 7).replace('B', '1').replace('F', '0'), 2);
        column = parseInt(input.substring(7, 10).replace('R', '1').replace('L', '0'), 2);
        seatId = (row * 8) + column;
    }
}
