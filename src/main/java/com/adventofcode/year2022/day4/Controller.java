package com.adventofcode.year2022.day4;

import java.util.List;
import org.springframework.stereotype.Component;
import com.adventofcode.common.DailyAnswer;
import com.adventofcode.common.SolutionController;
import lombok.extern.slf4j.Slf4j;


@Slf4j
@Component("controller-2022-4")
public class Controller extends SolutionController {

    public DailyAnswer execute() {

        List<Pair> pairs = puzzleInput.stream().map(Pair::new).toList();

        answer.setPart1(pairs.stream().filter(Pair::isSubset).count());
        log.info("P1: {}", answer.getPart1());

        answer.setPart2(pairs.stream().filter(Pair::isOverlap).count());
        log.info("P2: {}", answer.getPart2());

        return answer;
    }

}
