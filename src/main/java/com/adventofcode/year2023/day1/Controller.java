package com.adventofcode.year2023.day1;

import org.springframework.stereotype.Component;
import com.adventofcode.common.DailyAnswer;
import com.adventofcode.common.SolutionController;
import lombok.extern.slf4j.Slf4j;


@Slf4j
@Component("controller-2023-1")
public class Controller extends SolutionController {

    public DailyAnswer execute() {

        answer.setPart1(puzzleInput.stream().map(s -> new Line(s, false)).map(Line::getValue).mapToInt(Integer::intValue).sum());
        log.info("Part 1: {}", answer.getPart1());

        answer.setPart2(puzzleInput.stream().map(s -> new Line(s, true)).map(Line::getValue).mapToInt(Integer::intValue).sum());
        log.info("Part 2: {}", answer.getPart2());

        return answer;
    }

}
