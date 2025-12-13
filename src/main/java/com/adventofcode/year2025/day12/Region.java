package com.adventofcode.year2025.day12;

import java.util.HashMap;
import java.util.Map;

public class Region {
    private final int width;
    private final int height;
    private final Map<Integer, Integer> requirements;

    public Region(int width, int height) {
        this.width = width;
        this.height = height;
        this.requirements = new HashMap<>();
    }

    public void addRequirement(int shapeId, int count) {
        requirements.put(shapeId, count);
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public Map<Integer, Integer> getRequirements() {
        return requirements;
    }
}
