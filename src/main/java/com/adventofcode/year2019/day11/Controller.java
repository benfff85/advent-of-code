package com.adventofcode.year2019.day11;

import static com.adventofcode.year2019.day11.Color.BLACK;
import static com.adventofcode.year2019.day11.Color.WHITE;
import java.awt.Point;
import java.math.BigInteger;
import java.util.*;
import org.springframework.stereotype.Component;
import com.adventofcode.common.DailyAnswer;
import com.adventofcode.common.SolutionController;
import com.adventofcode.common.grid.GridUtility;
import com.adventofcode.common.grid.PrintableGridElement;
import lombok.extern.slf4j.Slf4j;


@Slf4j
@Component("controller-day-11")
public class Controller extends SolutionController {

    public DailyAnswer execute() {

        List<BigInteger> input = inputHelper.parseStringToBigIntList(puzzleInput.get(0));

        com.adventofcode.year2019.day11.Robot robot = new com.adventofcode.year2019.day11.Robot();
        answer.setPart1(robot.process(input, BLACK).values().stream().filter(com.adventofcode.year2019.day11.Panel::isHasBeenPainted).count());

        robot = new Robot();
        Map<Point, Panel> panels = robot.process(input, WHITE);
        Map<Point, PrintableGridElement> grid = new HashMap<>(panels);
        answer.setPart2(GridUtility.print(grid));
        log.info("{}", answer.getPart2());

        return answer;

    }

}
