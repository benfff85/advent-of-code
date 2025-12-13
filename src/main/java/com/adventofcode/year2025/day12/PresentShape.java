package com.adventofcode.year2025.day12;

import java.util.*;

public class PresentShape {
    private final int id;
    private final List<boolean[][]> variants;

    public PresentShape(int id, List<String> lines) {
        this.id = id;
        this.variants = generateVariants(lines);
    }

    private List<boolean[][]> generateVariants(List<String> lines) {
        int rows = lines.size();
        int cols = lines.get(0).length();
        boolean[][] base = new boolean[rows][cols];
        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                base[r][c] = lines.get(r).charAt(c) == '#';
            }
        }

        List<boolean[][]> allVariants = new ArrayList<>();
        // 4 rotations
        boolean[][] current = base;
        for (int i = 0; i < 4; i++) {
            addVariantUnique(allVariants, current);
            current = rotate(current);
        }
        // Flip and 4 rotations
        current = flip(base);
        for (int i = 0; i < 4; i++) {
            addVariantUnique(allVariants, current);
            current = rotate(current);
        }
        return allVariants;
    }

    private void addVariantUnique(List<boolean[][]> variants, boolean[][] candidate) {
        // Normalize (trim empty rows/cols)?
        // For now, let's keep it simple and just trim/normalize if needed,
        // but the problem says "regions into which they need to fit are all measured in standard units".
        // The shape might need to be trimmed to be minimal bounding box to avoid varying offsets.
        // Actually, the input shapes seem tightly packed. But rotation might introduce whitespace if not careful.
        // Let's implement trimming to be safe.

        boolean[][] trimmed = trim(candidate);

        for (boolean[][] existing : variants) {
            if (isSame(existing, trimmed)) {
                return;
            }
        }
        variants.add(trimmed);
    }

    private boolean[][] trim(boolean[][] grid) {
        int minR = grid.length, maxR = -1;
        int minC = grid[0].length, maxC = -1;

        for (int r = 0; r < grid.length; r++) {
            for (int c = 0; c < grid[0].length; c++) {
                if (grid[r][c]) {
                    minR = Math.min(minR, r);
                    maxR = Math.max(maxR, r);
                    minC = Math.min(minC, c);
                    maxC = Math.max(maxC, c);
                }
            }
        }

        int h = maxR - minR + 1;
        int w = maxC - minC + 1;
        boolean[][] newGrid = new boolean[h][w];
        for (int r = 0; r < h; r++) {
            for (int c = 0; c < w; c++) {
                newGrid[r][c] = grid[minR + r][minC + c];
            }
        }
        return newGrid;
    }

    private boolean isSame(boolean[][] a, boolean[][] b) {
        if (a.length != b.length || a[0].length != b[0].length)
            return false;
        for (int r = 0; r < a.length; r++) {
            if (!Arrays.equals(a[r], b[r]))
                return false;
        }
        return true;
    }

    private boolean[][] rotate(boolean[][] grid) {
        int r = grid.length;
        int c = grid[0].length;
        boolean[][] newGrid = new boolean[c][r];
        for (int i = 0; i < r; i++) {
            for (int j = 0; j < c; j++) {
                newGrid[j][r - 1 - i] = grid[i][j];
            }
        }
        return newGrid;
    }

    private boolean[][] flip(boolean[][] grid) {
        int r = grid.length;
        int c = grid[0].length;
        boolean[][] newGrid = new boolean[r][c];
        for (int i = 0; i < r; i++) {
            newGrid[i] = grid[r - 1 - i].clone();
        }
        return newGrid;
    }

    public List<boolean[][]> getVariants() {
        return variants;
    }

    public int getId() {
        return id;
    }
}
