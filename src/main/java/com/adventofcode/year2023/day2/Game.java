package com.adventofcode.year2023.day2;

import lombok.Getter;

@Getter
public class Game {

    private final Integer id;
    private Integer maxBlue = 0;
    private Integer maxRed = 0;
    private Integer maxGreen = 0;

    public Game(String input) {

        this.id = Integer.parseInt(input.split(" ")[1].split(":")[0]);

        int value;
        for (String roundInput : input.split(":")[1].split(";")) {
            for (String colorDrawing : roundInput.split(",")) {
                value = Integer.parseInt(colorDrawing.split(" ")[1]);
                if (colorDrawing.contains("blue")) {
                    maxBlue = Math.max(maxBlue, value);
                } else if (colorDrawing.contains("red")) {
                    maxRed = Math.max(maxRed, value);
                } else if (colorDrawing.contains("green")) {
                    maxGreen = Math.max(maxGreen, value);
                }
            }
        }
    }

    public Integer power() {
        return maxBlue * maxRed * maxGreen;
    }

}
