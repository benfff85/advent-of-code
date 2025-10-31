package com.adventofcode.year2024.day14;

import lombok.Data;

@Data
public class Robot {

    private final int xVelocity;
    private final int yVelocity;
    private int x;
    private int y;

    public Robot(String input) {
        // Split by space to get position and velocity parts: "p=0,4 v=3,-3"
        String[] parts = input.split(" ");

        // Parse position: p=0,4
        String positionPart = parts[0].substring(2); // Remove "p="
        String[] positionCoords = positionPart.split(",");
        this.x = Integer.parseInt(positionCoords[0]);
        this.y = Integer.parseInt(positionCoords[1]);

        // Parse velocity: v=3,-3
        String velocityPart = parts[1].substring(2); // Remove "v="
        String[] velocityCoords = velocityPart.split(",");
        this.xVelocity = Integer.parseInt(velocityCoords[0]);
        this.yVelocity = Integer.parseInt(velocityCoords[1]);
    }

    public void advanceFixedSeconds(int seconds, int maxX, int maxY) {
        this.x = (x + (this.xVelocity * seconds)) % maxX;
        this.y = (y + (this.yVelocity * seconds)) % maxY;

        if (x < 0) {
            this.x = maxX + this.x;
        }

        if (y < 0) {
            this.y = maxY + this.y;
        }
    }
}
