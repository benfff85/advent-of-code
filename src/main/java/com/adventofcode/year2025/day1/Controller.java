package com.adventofcode.year2025.day1;

import java.util.List;

import org.springframework.stereotype.Component;

import com.adventofcode.common.DailyAnswer;
import com.adventofcode.common.InputHelper;
import com.adventofcode.common.SolutionController;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component("controller-2025-1")
public class Controller extends SolutionController {

    public Controller(InputHelper inputHelper) {
        super(inputHelper, "puzzle-input/2025/day-1.txt");
    }

    public DailyAnswer execute() {

        List<Instructions> instructions = puzzleInput.stream().map(Instructions::new).toList();
        log.info("Instructions: {}", instructions);

        Dial dial = new Dial(0, 99, 50);
        dial.processInstructions(instructions);

        answer.setPart1(dial.getCountOfZeroAtEndOfInstruction());
        answer.setPart2(dial.getCountOfZeroAtEndOfStep());

        return answer;
    }

}
