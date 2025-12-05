package com.adventofcode.year2023.day7;

import java.util.List;
import java.util.stream.IntStream;
import org.springframework.stereotype.Component;
import com.adventofcode.common.DailyAnswer;
import com.adventofcode.common.SolutionController;
import lombok.extern.slf4j.Slf4j;


@Slf4j
@Component("controller-2023-7")
public class Controller extends SolutionController {

    public DailyAnswer execute() {

        answer.setPart1(process(false));
        log.info("Part 1: {}", answer.getPart1());

        answer.setPart2(process(true));
        log.info("Part 2: {}", answer.getPart2());

        return answer;

    }

    private int process(boolean isJokerWild) {
        final List<Hand> hands = new java.util.LinkedList<>(puzzleInput.stream().map(s -> new Hand(s, isJokerWild)).sorted().toList());
        return IntStream.range(0, hands.size()).map(i -> hands.get(i).getBid() * (i + 1)).sum();
    }

}
