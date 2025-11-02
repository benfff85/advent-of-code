package com.adventofcode.year2024.day15;

import com.adventofcode.common.DailyAnswer;
import com.adventofcode.common.InputHelper;
import com.adventofcode.common.SolutionController;
import com.adventofcode.common.grid.Direction;
import com.adventofcode.common.grid.GridUtility;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.adventofcode.common.grid.GridUtility.constructGrid;

@Slf4j
@Component("controller-2024-15")
public class Controller extends SolutionController {

    public Controller(InputHelper inputHelper) {
        super(inputHelper, "puzzle-input/2024/day-15.txt");
    }

    public DailyAnswer execute() {


        Map<Point, GridElement> grid = constructGrid(puzzleInput.stream().takeWhile(line -> !line.isEmpty()).toList(), GridElement.class);
        log.info("{}", GridUtility.print(grid));

        List<Direction> directions = puzzleInput.stream().skip(GridUtility.getMaxY(grid) + 1)
                .flatMap(line -> line.chars().mapToObj(c -> {
                    if (c == '<') return Direction.L;
                    if (c == '>') return Direction.R;
                    if (c == '^') return Direction.U;
                    if (c == 'v') return Direction.D;
                    return null;
                }))
                .toList();

        // Print all chars in directions on a single line
        log.info(directions.stream().map(Enum::name).collect(Collectors.joining()));

        List<Point> pointsToMove = new ArrayList<>();
        for (Direction direction : directions) {
            pointsToMove.add(GridUtility.getFirstElementByValue(grid, GridElement.ROBOT).getKey());
            Map.Entry<Point, GridElement> prospectPoint;
            do {
                prospectPoint = GridUtility.getAdjacentElement(grid, pointsToMove.getLast(), direction);
                if (prospectPoint.getValue().equals(GridElement.BOX)) {
                    pointsToMove.add(prospectPoint.getKey());
                }
            } while (prospectPoint.getValue().equals(GridElement.BOX));

            if (prospectPoint.getValue().equals(GridElement.SPACE)) {
                // move all points by popping the last off points to move
                while (!pointsToMove.isEmpty()) {
                    Point pointToMove = pointsToMove.removeLast();
                    GridElement itemToMove = grid.get(pointToMove);
                    Map.Entry<Point, GridElement> itemInTargetPoint = GridUtility.getAdjacentElement(grid, pointToMove, direction);
                    grid.put(itemInTargetPoint.getKey(), itemToMove);
                    grid.put(pointToMove, itemInTargetPoint.getValue());
                }
            }

            pointsToMove.clear();

//            log.info("{}", GridUtility.print(grid));
        }

        int gpsScore = 0;
        int maxY = GridUtility.getMaxY(grid);
        for (Point boxPoint : GridUtility.getElementsByValue(grid, GridElement.BOX).keySet()) {
            gpsScore += ((maxY - boxPoint.y) * 100) + boxPoint.x;

        }
        answer.setPart1(gpsScore);

        return answer;
    }

}
