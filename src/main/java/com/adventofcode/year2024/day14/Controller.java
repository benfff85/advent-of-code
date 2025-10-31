package com.adventofcode.year2024.day14;

import com.adventofcode.common.DailyAnswer;
import com.adventofcode.common.InputHelper;
import com.adventofcode.common.SolutionController;
import com.adventofcode.common.grid.GridUtility;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Component("controller-2024-14")
public class Controller extends SolutionController {

    public Controller(InputHelper inputHelper) {
        super(inputHelper, "puzzle-input/2024/day-14.txt");
    }

    public DailyAnswer execute() {

        List<Robot> robots = puzzleInput.stream().map(Robot::new).toList();

        int gridSizeMaxX = 101;
        int gridSizeMaxY = 103;
        int stepCount = 100;

        robots.forEach(robot -> robot.advanceFixedSeconds(stepCount, gridSizeMaxX, gridSizeMaxY));

        answer.setPart1(calculateSafetyScore(robots, gridSizeMaxX, gridSizeMaxY));

        robots = puzzleInput.stream().map(Robot::new).toList();
        Map<Point, GridElement> grid = new HashMap<>();
        Map<Point, GridElement> pattern = GridUtility.constructGridWithoutSelectElements(List.of(
                "XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX",
                "X.............................X",
                "X.............................X",
                "X.............................X",
                "X.............................X",
                "X..............X..............X",
                "X.............XXX.............X",
                "X............XXXXX............X",
                "X...........XXXXXXX...........X",
                "X..........XXXXXXXXX..........X",
                "X............XXXXX............X",
                "X...........XXXXXXX...........X",
                "X..........XXXXXXXXX..........X",
                "X.........XXXXXXXXXXX.........X",
                "X........XXXXXXXXXXXXX........X",
                "X..........XXXXXXXXX..........X",
                "X.........XXXXXXXXXXX.........X",
                "X........XXXXXXXXXXXXX........X",
                "X.......XXXXXXXXXXXXXXX.......X",
                "X......XXXXXXXXXXXXXXXXX......X",
                "X........XXXXXXXXXXXXX........X",
                "X.......XXXXXXXXXXXXXXX.......X",
                "X......XXXXXXXXXXXXXXXXX......X",
                "X.....XXXXXXXXXXXXXXXXXXX.....X",
                "X....XXXXXXXXXXXXXXXXXXXXX....X",
                "X.............XXX.............X",
                "X.............XXX.............X",
                "X.............XXX.............X",
                "X.............................X",
                "X.............................X",
                "X.............................X",
                "X.............................X",
                "XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX"
        ), GridElement.class, List.of(GridElement.SPACE));
        GridUtility.rotateGridClockwise(pattern);
        GridUtility.rotateGridClockwise(pattern);

        for (int i = 0; i < 10000; i++) {

            robots.forEach(robot -> grid.put(new Point(robot.getX(), robot.getY()), GridElement.ROBOT));
            if (!GridUtility.getPatternMatches(grid, pattern).isEmpty()) {
                answer.setPart2(i);
                break;
            }
            robots.forEach(robot -> grid.remove(new Point(robot.getX(), robot.getY())));
            robots.forEach(robot -> robot.advanceFixedSeconds(1, gridSizeMaxX, gridSizeMaxY));

        }
        return answer;
    }

    private int calculateSafetyScore(List<Robot> robots, int gridSizeMaxX, int gridSizeMaxY) {
        List<Integer> quadrantMap = new ArrayList<>(List.of(0, 0, 0, 0));
        robots.stream().map(r -> new Point(r.getX(), r.getY())).forEach(point -> {
            if (point.x < gridSizeMaxX / 2 && point.y < gridSizeMaxY / 2) {
                quadrantMap.set(0, quadrantMap.get(0) + 1);
            } else if (point.x < gridSizeMaxX / 2 && point.y > gridSizeMaxY / 2) {
                quadrantMap.set(1, quadrantMap.get(1) + 1);
            } else if (point.x > gridSizeMaxX / 2 && point.y < gridSizeMaxY / 2) {
                quadrantMap.set(2, quadrantMap.get(2) + 1);
            } else if (point.x > gridSizeMaxX / 2 && point.y > gridSizeMaxY / 2) {
                quadrantMap.set(3, quadrantMap.get(3) + 1);
            }
        });
        return quadrantMap.stream().reduce(1, Math::multiplyExact);
    }

}
