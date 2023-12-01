package com.adventofcode.year2023.day1;

import com.adventofcode.common.DailyAnswer;
import com.adventofcode.common.InputHelper;
import com.adventofcode.common.SolutionController;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;


@Slf4j
@Component("controller-2023-1")
public class Controller extends SolutionController {

    public Controller(InputHelper inputHelper) {
        super(inputHelper, "puzzle-input/2023/day-1.txt");
    }

    public DailyAnswer execute() {

        answer.setPart1(puzzleInput.stream().map(s -> new Line(s, false)).map(Line::getValue).mapToInt(Integer::intValue).sum());
        log.info("Part 1: {}", answer.getPart1());

        answer.setPart2(puzzleInput.stream().map(s -> new Line(s, true)).map(Line::getValue).mapToInt(Integer::intValue).sum());
        log.info("Part 2: {}", answer.getPart2());

        return answer;
    }

}