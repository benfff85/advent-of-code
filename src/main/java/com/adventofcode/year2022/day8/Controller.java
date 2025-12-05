package com.adventofcode.year2022.day8;

import static java.lang.Integer.parseInt;
import static java.lang.Math.max;
import java.awt.Point;
import java.util.*;
import org.springframework.stereotype.Component;
import com.adventofcode.common.DailyAnswer;
import com.adventofcode.common.SolutionController;
import lombok.extern.slf4j.Slf4j;


@Slf4j
@Component("controller-2022-8")
public class Controller extends SolutionController {

    public DailyAnswer execute() {

        Map<Point, Integer> grid = new HashMap<>();
        int rowCount = puzzleInput.size();
        int columnCount = puzzleInput.get(0).length();

        populateTreeMap(grid, rowCount, columnCount);

        answer.setPart1(determineCountOfVisibleTreesFromOutside(grid, rowCount, columnCount));
        log.info("P1: {}", answer.getPart1());

        answer.setPart2(getMaxScenicScore(grid, rowCount, columnCount));
        log.info("P2: {}", answer.getPart2());

        return answer;
    }



    private void populateTreeMap(Map<Point, Integer> grid, int rowCount, int columnCount) {
        // Bottom left is 0,0
        String row;
        for (int i = rowCount - 1; i >= 0; i--) {
            row = puzzleInput.get((rowCount - i) - 1);
            for (int j = 0; j < columnCount; j++) {
                grid.put(new Point(j, i), parseInt(String.valueOf(row.charAt(j))));
            }
        }
    }

    private int determineCountOfVisibleTreesFromOutside(Map<Point, Integer> grid, int rowCount, int columnCount) {
        Set<Point> visibleTrees = new HashSet<>();
        int maxTreeHeight;

        // L -> R
        for (int i = 0; i < rowCount; i++) {
            maxTreeHeight = -1;
            for (int j = 0; j < columnCount; j++) {
                maxTreeHeight = processTree(grid, visibleTrees, maxTreeHeight, j, i);
            }
        }

        // R -> L
        for (int i = 0; i < rowCount; i++) {
            maxTreeHeight = -1;
            for (int j = columnCount - 1; j >= 0; j--) {
                maxTreeHeight = processTree(grid, visibleTrees, maxTreeHeight, j, i);
            }
        }

        // T -> B
        for (int j = 0; j < columnCount; j++) {
            maxTreeHeight = -1;
            for (int i = rowCount - 1; i >= 0; i--) {
                maxTreeHeight = processTree(grid, visibleTrees, maxTreeHeight, j, i);
            }
        }

        // B -> T
        for (int j = 0; j < columnCount; j++) {
            maxTreeHeight = -1;
            for (int i = 0; i < rowCount; i++) {
                maxTreeHeight = processTree(grid, visibleTrees, maxTreeHeight, j, i);
            }
        }
        return visibleTrees.size();
    }

    private int processTree(Map<Point, Integer> grid, Set<Point> visibleTrees, int maxTreeHeight, int j, int i) {
        Point point = new Point(j, i);
        int treeHeight = grid.get(point);
        if (treeHeight > maxTreeHeight) {
            visibleTrees.add(point);
            maxTreeHeight = treeHeight;
        }
        return maxTreeHeight;
    }

    private int getMaxScenicScore(Map<Point, Integer> grid, int rowCount, int columnCount) {

        int maxScenicScore = 0;
        for (int i = 0; i < rowCount; i++) {
            for (int j = 0; j < columnCount; j++) {
                maxScenicScore = max(maxScenicScore, getScenicScore(grid, rowCount, columnCount, i, j));
            }
        }
        return maxScenicScore;

    }

    private int getScenicScore(Map<Point, Integer> grid, int rowCount, int columnCount, int i, int j) {
        int visibleTreeCount;
        int treeHeight;
        int scenicScore;

        // Checking for j,i
        treeHeight = grid.get(new Point(j, i));
        scenicScore = 1;

        // L
        visibleTreeCount = 0;
        for (int x = j - 1; x >= 0; x--) {
            visibleTreeCount++;
            if (grid.get(new Point(x, i)) >= treeHeight) {
                break;
            }
        }
        scenicScore *= visibleTreeCount;


        // R
        visibleTreeCount = 0;
        for (int x = j + 1; x < columnCount; x++) {
            visibleTreeCount++;
            if (grid.get(new Point(x, i)) >= treeHeight) {
                break;
            }
        }
        scenicScore *= visibleTreeCount;


        // U
        visibleTreeCount = 0;
        for (int y = i + 1; y < rowCount; y++) {
            visibleTreeCount++;
            if (grid.get(new Point(j, y)) >= treeHeight) {
                break;
            }
        }
        scenicScore *= visibleTreeCount;


        // D
        visibleTreeCount = 0;
        for (int y = i - 1; y >= 0; y--) {
            visibleTreeCount++;
            if (grid.get(new Point(j, y)) >= treeHeight) {
                break;
            }
        }
        scenicScore *= visibleTreeCount;
        return scenicScore;
    }

}
