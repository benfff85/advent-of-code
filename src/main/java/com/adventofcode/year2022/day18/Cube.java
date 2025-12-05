package com.adventofcode.year2022.day18;

import lombok.Data;

@Data
public class Cube {

    private int x;
    private int y;
    private int z;

    public Cube(String input) {
        String[] splitString = input.split(",");
        // Padding by one to keep away from the edge
        x = Integer.parseInt(splitString[0]) + 1;
        y = Integer.parseInt(splitString[1]) + 1;
        z = Integer.parseInt(splitString[2]) + 1;
    }

    public Cube(int x, int y, int z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

}
