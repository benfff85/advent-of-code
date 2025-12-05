package com.adventofcode.year2019.day9;

import java.math.BigInteger;
import java.util.LinkedList;
import java.util.List;
import org.springframework.stereotype.Component;
import com.adventofcode.common.*;
import com.adventofcode.year2019.day5.IntComputer;
import com.adventofcode.year2019.day5.IntComputerContext;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component("controller-day-9")
public class Controller extends SolutionController {

    private final IntComputer intComputer;

    // TODO remove constructor
    public Controller(InputHelper inputHelper) {
        super(inputHelper, "puzzle-input/2019/day-9.txt");
        intComputer = new IntComputer();
    }

    public DailyAnswer execute() {
        List<BigInteger> input = inputHelper.parseStringToBigIntList(puzzleInput.get(0));
        IntComputerContext context = intComputer.process(IntComputerContext.builder()
                .instructions(new LinkedList<>(input))
                .instructionIndex(0)
                .inputs(new LinkedList<>(List.of(BigInteger.ONE)))
                .outputs(new LinkedList<>())
                .relativeBase(0)
                .build());
        answer.setPart1(context.getOutputs().getLast());
        log.info("Output: {}", answer.getPart1());

        context = intComputer.process(IntComputerContext.builder()
                .instructions(new LinkedList<>(input))
                .instructionIndex(0)
                .inputs(new LinkedList<>(List.of(BigInteger.TWO)))
                .outputs(new LinkedList<>())
                .relativeBase(0)
                .build());
        answer.setPart2(context.getOutputs().getLast());
        log.info("Output: {}", answer.getPart2());

        return answer;
    }
}
