package com.adventofcode.year2019.day15;

import static com.adventofcode.common.grid.Direction.*;
import static java.lang.Boolean.TRUE;
import java.awt.Point;
import java.math.BigInteger;
import java.util.*;
import com.adventofcode.common.grid.*;
import com.adventofcode.year2019.day5.IntComputer;
import com.adventofcode.year2019.day5.IntComputerContext;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class RepairDroid {

    private final IntComputer intComputer = new IntComputer();
    private final IntComputerContext intComputerContext;
    @Getter
    private final Map<Point, PrintableGridElement> grid = new HashMap<>();
    private final Map<Point, PrintableGridElement> printingOverrides = Map.of(new Point(0, 0), SimplePrintableGridElement.of("X"));
    private final PrintableGridElement defaultPrintingElement = SimplePrintableGridElement.of(" ");
    private Point currentPosition = new Point(0, 0);
    private Point targetPosition;
    @Getter
    private Integer pathLength;

    public RepairDroid(List<BigInteger> instructions) {
        intComputerContext = generateIntComputerContext(instructions);
    }

    private IntComputerContext generateIntComputerContext(List<BigInteger> instructions) {
        return IntComputerContext.builder()
                .instructions(new ArrayList<>(instructions))
                .instructionIndex(0)
                .inputs(new LinkedList<>())
                .outputs(new LinkedList<>())
                .isRunning(TRUE)
                .relativeBase(0)
                .build();
    }


    public void scan() {

        Deque<Point> path = new ArrayDeque<>();
        for (Direction direction : List.of(U, D, L, R)) {
            processMove(direction, path);
        }

    }

    // Kinda wierd mix of recursion and modifying globals
    public void processMove(Direction direction, Deque<Point> path) {

        applyDirectionToIntComputerContext(direction, intComputerContext.getInputs());
        targetPosition = PointUtil.getAdjacentPoint(currentPosition, direction);
        intComputer.process(intComputerContext);
        int output = intComputerContext.getOutputs().pop().intValue();
        applyOutputToGrid(output);
        log.debug("{}", GridUtility.print(grid, printingOverrides, defaultPrintingElement));

        // Hit a wall, return
        if (output == 0) {
            return;
        }

        currentPosition = targetPosition;
        path.push(currentPosition);

        // Found the oxygen, record the distance of the path
        if (output == 2) {
            pathLength = path.size();
        }

        // Try next moves, do not double back by moving the opposite direction that got us here
        List<Direction> nextDirections = Direction.allCardinal();
        nextDirections.remove(direction.opposite());
        for (Direction nextDirection : nextDirections) {
            processMove(nextDirection, path);
        }

        // Rollback position
        applyDirectionToIntComputerContext(direction.opposite(), intComputerContext.getInputs());
        targetPosition = PointUtil.getAdjacentPoint(currentPosition, direction.opposite());
        intComputer.process(intComputerContext);
        intComputerContext.getOutputs().pop();
        currentPosition = targetPosition;
        path.poll();

    }

    private void applyDirectionToIntComputerContext(Direction direction, Queue<BigInteger> inputs) {
        inputs.add(BigInteger.valueOf(direction.getValue()));
    }

    private void applyOutputToGrid(Integer output) {
        grid.put(targetPosition, GridElement.getFromValue(output));
    }

}
