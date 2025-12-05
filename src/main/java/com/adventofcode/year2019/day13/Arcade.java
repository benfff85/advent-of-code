package com.adventofcode.year2019.day13;

import static java.lang.Boolean.TRUE;
import static java.math.BigInteger.ONE;
import static java.math.BigInteger.ZERO;
import java.awt.Point;
import java.math.BigInteger;
import java.util.*;
import com.adventofcode.year2019.day5.IntComputer;
import com.adventofcode.year2019.day5.IntComputerContext;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Arcade {

    private final IntComputer intComputer = new IntComputer();
    private final IntComputerContext intComputerContext;

    private final Map<Point, Integer> grid = new HashMap<>();

    public Arcade(List<BigInteger> instructions) {
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

    public void play() {
        intComputer.process(intComputerContext);
        applyOutputToGrid(intComputerContext.getOutputs());
    }

    public void play2() {
        intComputerContext.getInstructions().set(0, BigInteger.TWO);

        while (intComputerContext.isRunning()) {
            intComputer.process(intComputerContext);
            applyOutputToGrid(intComputerContext.getOutputs());
            log.debug("{}", print());
            calculateJoystickInput();
        }

    }

    private void applyOutputToGrid(Deque<BigInteger> output) {
        while (!output.isEmpty()) {
            grid.put(new Point(output.pop().intValue(), output.pop().intValue()), output.pop().intValue());
        }
    }

    private void calculateJoystickInput() {
        int xPositionOfBall = grid.entrySet().stream().filter(e -> e.getValue().equals(4)).findFirst().orElseThrow().getKey().x;
        int xPositionOfPaddle = grid.entrySet().stream().filter(e -> e.getValue().equals(3)).findFirst().orElseThrow().getKey().x;
        if (xPositionOfPaddle == xPositionOfBall) {
            intComputerContext.getInputs().add(ZERO);
        } else {
            intComputerContext.getInputs().add(xPositionOfPaddle < xPositionOfBall ? ONE : BigInteger.valueOf(-1));
        }
    }

    public long getCountOfBlocks() {
        return grid.values().stream().filter(t -> t.equals(2)).count();
    }

    public long getScore() {
        return grid.get(new Point(-1, 0)).longValue();
    }

    private String print() {
        int maxX = grid.keySet().stream().mapToInt(p -> p.x).max().orElseThrow();
        int maxY = grid.keySet().stream().mapToInt(p -> p.y).max().orElseThrow();

        int value;
        StringBuilder sb = new StringBuilder();
        sb.append("\n\n");
        for (int i = 0; i <= maxY; i++) {
            for (int j = 0; j <= maxX; j++) {
                value = grid.get(new Point(j, i));


                if (0 == value) {
                    sb.append(" ");
                } else if (1 == value) {
                    sb.append("X");
                } else if (2 == value) {
                    sb.append("0");
                } else if (3 == value) {
                    sb.append("_");
                } else if (4 == value) {
                    sb.append("*");
                }

            }
            sb.append("\n");
        }

        return sb.toString();
    }


}
