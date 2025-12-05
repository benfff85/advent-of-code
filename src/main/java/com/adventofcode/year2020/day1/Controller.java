package com.adventofcode.year2020.day1;

import static com.google.common.collect.Sets.combinations;
import java.util.HashSet;
import java.util.Set;
import org.springframework.stereotype.Component;
import com.adventofcode.common.DailyAnswer;
import com.adventofcode.common.SolutionController;
import lombok.extern.slf4j.Slf4j;


@Slf4j
@Component("controller-2020-1")
public class Controller extends SolutionController {

    public DailyAnswer execute() {
        Set<Integer> input = new HashSet<>(inputHelper.parseStringListToIntList(puzzleInput));

        answer.setPart1(process(input, 2));
        log.info("Part 1: {}", answer.getPart1());

        answer.setPart2(process(input, 3));
        log.info("Part 2: {}", answer.getPart2());

        return answer;
    }

    private Integer process(Set<Integer> input, Integer size) {
        for (Set<Integer> combination : combinations(input, size)) {
            if (combination.stream().mapToInt(Integer::intValue).sum() == 2020) {
                Integer product = combination.stream().reduce(1, (a, b) -> a * b);
                log.info("Combination: {} | Product: {}", combination, product);
                return product;
            }
        }
        return null;
    }

}
