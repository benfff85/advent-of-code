package com.adventofcode.year2024.day8;

import java.awt.Point;
import java.util.*;
import org.apache.commons.math3.util.Combinations;
import org.springframework.stereotype.Component;
import com.adventofcode.common.DailyAnswer;
import com.adventofcode.common.SolutionController;
import com.adventofcode.common.grid.GridUtility;
import com.adventofcode.common.grid.SimplePrintableGridElement;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component("controller-2024-8")
public class Controller extends SolutionController {

    private final Map<Point, SimplePrintableGridElement> grid = new HashMap<>();

    public DailyAnswer execute() {

        initializeGrid();

        Set<SimplePrintableGridElement> frequencies = new HashSet<>(grid.values());
        frequencies.remove(SimplePrintableGridElement.of("."));

        Set<Point> antinode = new HashSet<>();
        for (SimplePrintableGridElement frequency : frequencies) {
            List<Point> frequencyPoints = new ArrayList<>(GridUtility.getElementsByValue(grid, frequency).keySet());
            Combinations combinations = new Combinations(frequencyPoints.size(), 2);
            for (int[] combination : combinations) {
                Point p1 = frequencyPoints.get(combination[0]);
                Point p2 = frequencyPoints.get(combination[1]);
                antinode.add(new Point(p2.x + (p2.x - p1.x), p2.y + (p2.y - p1.y)));
                antinode.add(new Point(p1.x + (p1.x - p2.x), p1.y + (p1.y - p2.y)));
            }

        }

        answer.setPart1(getCountOfUniqueAntinodePoints(antinode));
        log.info("Part 1: {}", answer.getPart1());


        antinode.clear();
        for (SimplePrintableGridElement frequency : frequencies) {
            List<Point> frequencyPoints = new ArrayList<>(GridUtility.getElementsByValue(grid, frequency).keySet());
            Combinations combinations = new Combinations(frequencyPoints.size(), 2);
            for (int[] combination : combinations) {
                Point p1 = frequencyPoints.get(combination[0]);
                Point p2 = frequencyPoints.get(combination[1]);
                int xDiff = p2.x - p1.x;
                int yDiff = p2.y - p1.y;
                for (int i = 0; i <= 50; i++) {
                    antinode.add(new Point(p2.x + (xDiff * i), p2.y + (yDiff * i)));
                    antinode.add(new Point(p1.x - (xDiff * i), p1.y - (yDiff * i)));
                }

            }

        }

        answer.setPart2(getCountOfUniqueAntinodePoints(antinode));
        log.info("Part 2: {}", answer.getPart2());

        return answer;
    }

    private void initializeGrid() {
        for (int i = 0; i < puzzleInput.size(); i++) {
            String row = puzzleInput.get(i);
            for (int j = 0; j < row.length(); j++) {
                grid.put(new Point(j, puzzleInput.size() - (i + 1)), SimplePrintableGridElement.of(row.charAt(j) + ""));
            }
        }
    }

    private long getCountOfUniqueAntinodePoints(Set<Point> antinode) {
        return antinode.stream()
                .filter(p -> p.x <= GridUtility.getMaxX(grid))
                .filter(p -> p.x >= GridUtility.getMinX(grid))
                .filter(p -> p.y <= GridUtility.getMaxY(grid))
                .filter(p -> p.y >= GridUtility.getMinY(grid))
                .count();
    }


}
