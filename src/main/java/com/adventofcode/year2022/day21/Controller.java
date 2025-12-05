package com.adventofcode.year2022.day21;

import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;
import com.adventofcode.common.*;
import lombok.extern.slf4j.Slf4j;


@Slf4j
@Component("controller-2022-21")
public class Controller extends SolutionController {

    public DailyAnswer execute() {


        answer.setPart1(processPart1());
        log.info("Part 1: {}", answer.getPart1());

        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        answer.setPart2(processPart2());
        log.info("Part 2: {}", answer.getPart2());
        stopWatch.stop();
        log.info("Part 2 took {} ms", stopWatch.getTotalTimeSeconds());

        return answer;
    }

    private Long processPart1() {
        return processMonkeyMap(puzzleInput.stream().map(Monkey::new).collect(Collectors.toMap(Monkey::getName, m -> m))).getValue();
    }

    private Long processPart2() {

        long baseNum = 3582317950000L; // Magic number, I noticed a pattern as 'i' increased the root node would decrease, converging to the desired value, I iterated jumping 100000000 at a time and then 10000 at a time to get close, then used that as a magic number to iterate 1 at a time and find the exact value
        Monkey rootMonkey;
        for (long i = 0; i < 100000; i++) {
            Map<String, Monkey> monkeyMap = puzzleInput.stream().map(Monkey::new).collect(Collectors.toMap(Monkey::getName, m -> m));
            monkeyMap.get("humn").setValue(i + baseNum);

            rootMonkey = processMonkeyMap(monkeyMap);
            if (monkeyMap.get(rootMonkey.getLeftName()).getValue().equals(monkeyMap.get(rootMonkey.getRightName()).getValue())) {
                return i + baseNum;
            }

        }

        return null;

    }

    private Monkey processMonkeyMap(Map<String, Monkey> monkeyMap) {
        Monkey rootMonkey = monkeyMap.get("root");
        Monkey leftMonkey;
        Monkey rightMonkey;

        while (!rootMonkey.isResolved()) {

            for (Monkey monkey : monkeyMap.values()) {
                if (monkey.isResolved()) {
                    continue;
                }
                leftMonkey = monkeyMap.get(monkey.getLeftName());
                rightMonkey = monkeyMap.get(monkey.getRightName());
                if (leftMonkey.isResolved() && rightMonkey.isResolved()) {
                    Long leftValue = leftMonkey.getValue();
                    Long rightValue = rightMonkey.getValue();
                    switch (monkey.getOperand()) {
                        case "+" -> monkey.setValue(leftValue + rightValue);
                        case "*" -> monkey.setValue(leftValue * rightValue);
                        case "-" -> monkey.setValue(leftValue - rightValue);
                        case "/" -> monkey.setValue(leftValue / rightValue);
                        default -> throw new AdventOfCodeException("Unknown operand: " + monkey.getOperand());
                    }
                }
            }

        }
        return rootMonkey;
    }

}
