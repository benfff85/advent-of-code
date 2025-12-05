package com.adventofcode.year2025.day4;

import java.util.Map;
import java.util.Set;
import org.springframework.stereotype.Component;
import com.adventofcode.common.DailyAnswer;
import com.adventofcode.common.InputHelper;
import com.adventofcode.common.SolutionController;
import com.adventofcode.common.grid.GridUtility;
import com.adventofcode.common.grid.SurroundingType;
import lombok.extern.slf4j.Slf4j;
import java.awt.*;
import static com.adventofcode.common.grid.GridUtility.constructGrid;


@Slf4j
@Component("controller-2025-4")
public class Controller extends SolutionController {

    public Controller(InputHelper inputHelper) {
        super(inputHelper, "puzzle-input/2025/day-4.txt");
    }

    public DailyAnswer execute() {

        Map<Point, GridElement> grid = constructGrid(puzzleInput, GridElement.class);


        int count = 0;
        Set<Point> rolls = GridUtility.getElementsByValue(grid, GridElement.ROLL).keySet();
        for (Point point : rolls) {
            Map<Point, GridElement> surroundingElements = GridUtility.getSurroundingElements(grid, point, SurroundingType.ALL);
            long rollCount = surroundingElements.values().stream().filter(e -> e == GridElement.ROLL).count();
            if (rollCount < 4) {
                count++;
            }
        }
        answer.setPart1(count);

        count = 0;
        int prevCount = 0;
        do {

            prevCount = count;

            for (Point point : rolls) {
                Map<Point, GridElement> surroundingElements = GridUtility.getSurroundingElements(grid, point, SurroundingType.ALL);
                long rollCount = surroundingElements.values().stream().filter(e -> e == GridElement.ROLL).count();
                if (rollCount < 4) {
                    count++;
                    grid.replace(point, GridElement.EMPTY);
                }

            }

            rolls = GridUtility.getElementsByValue(grid, GridElement.ROLL).keySet();

        } while (prevCount != count);

        answer.setPart2(count);

        return answer;

    }

}
