package com.adventofcode.year2025.day12;

import java.util.*;
import org.springframework.stereotype.Component;
import com.adventofcode.common.DailyAnswer;
import com.adventofcode.common.SolutionController;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component("controller-2025-12")
public class Controller extends SolutionController {

    private Map<Integer, PresentShape> shapes;
    private List<Region> regions;

    public DailyAnswer execute() {
        shapes = new HashMap<>();
        regions = new ArrayList<>();
        parseInput(puzzleInput);

        long part1 = 0;
        for (Region region : regions) {
            if (canFit(region)) {
                part1++;
            }
        }

        answer.setPart1(part1);
        answer.setPart2(null);
        return answer;
    }

    private void parseInput(List<String> input) {
        int i = 0;
        while (i < input.size()) {
            String line = input.get(i);
            if (line.trim().isEmpty()) {
                i++;
                continue;
            }

            if (line.matches("\\d+:")) {
                int id = Integer.parseInt(line.replace(":", ""));
                i++;
                List<String> shapeLines = new ArrayList<>();
                while (i < input.size() && !input.get(i).trim().isEmpty()) {
                    shapeLines.add(input.get(i));
                    i++;
                }
                shapes.put(id, new PresentShape(id, shapeLines));
            } else if (line.matches("\\d+x\\d+:.*")) {
                String[] parts = line.split(":");
                String[] dims = parts[0].split("x");
                int w = Integer.parseInt(dims[0]);
                int h = Integer.parseInt(dims[1]);
                Region region = new Region(w, h);

                String[] counts = parts[1].trim().split("\\s+");
                for (int id = 0; id < counts.length; id++) {
                    int count = Integer.parseInt(counts[id]);
                    if (count > 0) {
                        region.addRequirement(id, count);
                    }
                }
                regions.add(region);
                i++;
            } else {
                i++;
            }
        }
    }

    private boolean canFit(Region region) {
        List<PresentShape> presentsToFit = new ArrayList<>();
        for (Map.Entry<Integer, Integer> entry : region.getRequirements().entrySet()) {
            PresentShape shape = shapes.get(entry.getKey());
            for (int k = 0; k < entry.getValue(); k++) {
                presentsToFit.add(shape);
            }
        }

        // Optimization: Sort by size (area) descending
        presentsToFit.sort((a, b) -> Integer.compare(getArea(b), getArea(a)));

        // Basic area check
        int totalArea = presentsToFit.stream().mapToInt(this::getArea).sum();
        if (totalArea > region.getWidth() * region.getHeight()) {
            return false;
        }

        boolean[][] grid = new boolean[region.getHeight()][region.getWidth()];
        return backtrack(grid, presentsToFit, 0);
    }

    private int getArea(PresentShape shape) {
        boolean[][] grid = shape.getVariants().get(0);
        int area = 0;
        for (boolean[] row : grid) {
            for (boolean cell : row) {
                if (cell)
                    area++;
            }
        }
        return area;
    }

    private boolean backtrack(boolean[][] grid, List<PresentShape> presents, int index) {
        if (index == presents.size()) {
            return true;
        }

        PresentShape shape = presents.get(index);

        // Optimization: If previous present was SAME shape, we can restrict search to start after previous position?
        // Only if we track that. For now, keep it simple. Symmetries of identical items might slow it down.
        // But let's stick to basic backtracking first.

        for (boolean[][] variant : shape.getVariants()) {
            int h = variant.length;
            int w = variant[0].length;

            // Try all positions
            for (int r = 0; r <= grid.length - h; r++) {
                for (int c = 0; c <= grid[0].length - w; c++) {
                    if (canPlace(grid, variant, r, c)) {
                        place(grid, variant, r, c, true);
                        if (backtrack(grid, presents, index + 1)) {
                            return true;
                        }
                        place(grid, variant, r, c, false); // backtrack
                    }
                }
            }
        }
        return false;
    }

    private boolean canPlace(boolean[][] grid, boolean[][] variant, int r, int c) {
        for (int i = 0; i < variant.length; i++) {
            for (int j = 0; j < variant[0].length; j++) {
                if (variant[i][j]) {
                    if (grid[r + i][c + j]) {
                        return false; // Overlap
                    }
                }
            }
        }
        return true;
    }

    private void place(boolean[][] grid, boolean[][] variant, int r, int c, boolean val) {
        for (int i = 0; i < variant.length; i++) {
            for (int j = 0; j < variant[0].length; j++) {
                if (variant[i][j]) {
                    grid[r + i][c + j] = val;
                }
            }
        }
    }
}
