package com.adventofcode.year2019.day17;

import com.adventofcode.common.DailyAnswer;
import com.adventofcode.common.InputHelper;
import com.adventofcode.common.SolutionController;
import com.adventofcode.common.grid.GridUtility;
import com.adventofcode.common.grid.PrintableGridElement;
import com.adventofcode.common.grid.SimplePrintableGridElement;
import com.adventofcode.year2019.day5.IntComputer;
import com.adventofcode.year2019.day5.IntComputerContext;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Component;

import java.awt.*;
import java.math.BigInteger;
import java.util.List;
import java.util.Queue;
import java.util.*;

import static com.adventofcode.common.grid.SurroundingType.CARDINAL;
import static java.lang.Boolean.TRUE;


@Slf4j
@Component("controller-day-17")
public class Controller extends SolutionController {

    public Controller(InputHelper inputHelper) {
        super(inputHelper, "puzzle-input/2019/day-17.txt");
    }

    public DailyAnswer execute() {

        List<BigInteger> input = inputHelper.parseStringToBigIntList(puzzleInput.get(0));

        IntComputer intComputer = new IntComputer();
        Deque<BigInteger> output = intComputer.process(generateIntComputerContext(input)).getOutputs();

        Map<Point, PrintableGridElement> grid = mapOutputToGrid(output);
        log.info(GridUtility.print(grid));

        Map<Point, PrintableGridElement> overlay = findIntersectionPointsAsOverlay(grid);
        log.info(GridUtility.print(grid, overlay, null));

        answer.setPart1(overlay.keySet().stream().map(p -> p.x * p.y * -1).mapToInt(Integer::intValue).sum());
        log.info("Sum of alignment parameters: {}", answer.getPart1());

        IntComputerContext context = generateIntComputerContext(input);
        context.getInstructions().set(0, BigInteger.TWO);
        Queue<BigInteger> inputs = context.getInputs();
        addStringAsInput("A,B,A,B,C,B,C,A,C,C", inputs); // Movement routine
        addStringAsInput("R,12,L,10,L,10", inputs); // A
        addStringAsInput("L,6,L,12,R,12,L,4", inputs); // B
        addStringAsInput("L,12,R,12,L,6", inputs); // C
        addStringAsInput("n", inputs); // Print debugging
        intComputer.process(context);

        log.info(GridUtility.print(mapOutputToGrid(context.getOutputs())));
        answer.setPart2(context.getOutputs().getLast());
        log.info("Dust collected: {}", answer.getPart2());

        return answer;
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

    private Map<Point, PrintableGridElement> mapOutputToGrid(Deque<BigInteger> output) {

        Map<Point, PrintableGridElement> grid = new HashMap<>();
        Point currentPoint = new Point(0, 0);

        for (BigInteger outputElement : output) {
            if (outputElement.equals(BigInteger.valueOf(10))) {
                currentPoint = new Point(0, currentPoint.y - 1);
            } else {
                grid.put(currentPoint, GridElement.getFromValue(outputElement.intValue()));
                currentPoint = new Point(currentPoint.x + 1, currentPoint.y);
            }

        }

        return grid;
    }

    private Map<Point, PrintableGridElement> findIntersectionPointsAsOverlay(Map<Point, PrintableGridElement> grid) {
        Map<PrintableGridElement, Integer> surroundingElementCardinalityMap;
        Map<Point, PrintableGridElement> overlay = new HashMap<>();
        for (Map.Entry<Point, PrintableGridElement> entry : grid.entrySet()) {
            if (entry.getValue().equals(GridElement.SCAFFOLD)) {
                surroundingElementCardinalityMap = CollectionUtils.getCardinalityMap(GridUtility.getSurroundingElements(grid, entry.getKey(), CARDINAL).values());
                // Only one type of surrounding element, which is SCAFFOLD, and occurs 4x
                if (surroundingElementCardinalityMap.size() == 1 && surroundingElementCardinalityMap.containsKey(GridElement.SCAFFOLD) && surroundingElementCardinalityMap.containsValue(4)) {
                    overlay.put(entry.getKey(), SimplePrintableGridElement.of("O"));
                }
            }
        }
        return overlay;
    }

    private void addStringAsInput(String string, Queue<BigInteger> inputs) {
        string.chars().mapToObj(c -> BigInteger.valueOf((long) c)).forEach(inputs::add);
        inputs.add(BigInteger.valueOf(10));
    }

}
