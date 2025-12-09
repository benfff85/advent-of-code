package com.adventofcode.year2025.day8;

import lombok.Data;

@Data
public class JunctionBox {

    private final int x;
    private final int y;
    private final int z;

    public JunctionBox(String line) {
        String[] parts = line.split(",");
        this.x = Integer.parseInt(parts[0]);
        this.y = Integer.parseInt(parts[1]);
        this.z = Integer.parseInt(parts[2]);
    }

    /**
     * Calculate straight-line (Euclidean) distance to another JunctionBox.
     */
    public double distanceTo(JunctionBox other) {
        long dx = (long) this.x - other.x;
        long dy = (long) this.y - other.y;
        long dz = (long) this.z - other.z;
        return Math.sqrt(dx * dx + dy * dy + dz * dz);
    }

}
