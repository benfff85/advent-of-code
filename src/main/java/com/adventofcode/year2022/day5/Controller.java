package com.adventofcode.year2022.day5;

import java.util.*;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import com.adventofcode.common.DailyAnswer;
import com.adventofcode.common.SolutionController;
import lombok.extern.slf4j.Slf4j;


@Slf4j
@Component("controller-2022-5")
public class Controller extends SolutionController {

    public DailyAnswer execute() {

        List<Instruction> instructions = puzzleInput.stream().map(Instruction::new).toList();

        List<List<String>> stacks = initStacks();
        processInstructions(instructions, stacks, true);
        answer.setPart1(getTopContainers(stacks));
        log.info("P1: {}", answer.getPart1());

        stacks = initStacks();
        processInstructions(instructions, stacks, false);
        answer.setPart2(getTopContainers(stacks));
        log.info("P2: {}", answer.getPart2());

        return answer;
    }

    private void processInstructions(List<Instruction> instructions, List<List<String>> stacks, boolean cratesMoveInReverseOrder) {
        List<String> fromStack;
        List<String> toStack;
        List<String> cratesToMove;
        for (Instruction instruction : instructions) {
            fromStack = stacks.get(instruction.getFromStack());
            toStack = stacks.get(instruction.getToStack());

            cratesToMove = fromStack.subList(fromStack.size() - instruction.getQuantity(), fromStack.size());
            if (cratesMoveInReverseOrder) {
                Collections.reverse(cratesToMove);
            }
            toStack.addAll(cratesToMove);

            stacks.set(instruction.getFromStack(), fromStack.subList(0, fromStack.size() - instruction.getQuantity()));
        }
    }

    private List<List<String>> initStacks() {
        List<List<String>> stacks = new ArrayList<>(10);
        stacks.add(0, null);
        stacks.add(1, new ArrayList<>(List.of("D", "T", "W", "F", "J", "S", "H", "N")));
        stacks.add(2, new ArrayList<>(List.of("H", "R", "P", "Q", "T", "N", "B", "G")));
        stacks.add(3, new ArrayList<>(List.of("L", "Q", "V")));
        stacks.add(4, new ArrayList<>(List.of("N", "B", "S", "W", "R", "Q")));
        stacks.add(5, new ArrayList<>(List.of("N", "D", "F", "T", "V", "M", "B")));
        stacks.add(6, new ArrayList<>(List.of("M", "D", "B", "V", "H", "T", "R")));
        stacks.add(7, new ArrayList<>(List.of("D", "B", "Q", "J")));
        stacks.add(8, new ArrayList<>(List.of("D", "N", "J", "V", "R", "Z", "H", "Q")));
        stacks.add(9, new ArrayList<>(List.of("B", "N", "H", "M", "S")));
        return stacks;
    }

    private String getTopContainers(List<List<String>> stacks) {
        return stacks.stream().filter(Objects::nonNull).map(CollectionUtils::lastElement).collect(Collectors.joining(""));
    }

}
