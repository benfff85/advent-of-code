package com.adventofcode.year2024.day4;

import static com.adventofcode.common.grid.GridUtility.constructGrid;
import java.awt.Point;
import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;
import org.springframework.stereotype.Component;
import com.adventofcode.common.DailyAnswer;
import com.adventofcode.common.SolutionController;
import com.adventofcode.common.grid.GridUtility;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component("controller-2024-4")
public class Controller extends SolutionController {

    public DailyAnswer execute() {

        Map<Point, GridElement> grid = constructGrid(puzzleInput, GridElement.class);
        int count;

        Map<Point, GridElement> pattern = GridUtility.constructGridWithoutSelectElements(List.of("XMAS"), GridElement.class, List.of(GridElement.SPACE));
        count = countRotatedPatternMatches(grid, pattern);

        pattern = GridUtility.constructGridWithoutSelectElements(List.of(
                "X   ",
                " M  ",
                "  A ",
                "   S"), GridElement.class, List.of(GridElement.SPACE));
        count += countRotatedPatternMatches(grid, pattern);

        answer.setPart1(count);
        log.info("Part 1: {}", answer.getPart1());


        pattern = GridUtility.constructGridWithoutSelectElements(List.of(
                "M S",
                " A ",
                "M S"), GridElement.class, List.of(GridElement.SPACE));
        count = countRotatedPatternMatches(grid, pattern);

        answer.setPart2(count);
        log.info("Part 2: {}", answer.getPart2());

        return answer;
    }

    private int countRotatedPatternMatches(Map<Point, GridElement> grid, Map<Point, GridElement> pattern) {
        return IntStream.range(0, 4)
                .map(i -> {
                    int matches = GridUtility.getPatternMatches(grid, pattern).size();
                    GridUtility.rotateGridClockwise(pattern);
                    return matches;
                })
                .sum();
    }

}
