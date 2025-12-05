package com.adventofcode.year2020.day2;

import java.util.stream.Stream;
import org.springframework.stereotype.Component;
import com.adventofcode.common.DailyAnswer;
import com.adventofcode.common.SolutionController;
import lombok.extern.slf4j.Slf4j;


@Slf4j
@Component("controller-2020-2")
public class Controller extends SolutionController {

    public DailyAnswer execute() {

        process(puzzleInput.stream().map(PasswordPolicyB::new));

        answer.setPart1(process(puzzleInput.stream().map(PasswordPolicyA::new)));
        log.info("Part 1: {}", answer.getPart1());

        answer.setPart2(process(puzzleInput.stream().map(PasswordPolicyB::new)));
        log.info("Part 2: {}", answer.getPart2());

        return answer;
    }

    private long process(Stream<PasswordPolicy> policies) {
        return policies.filter(PasswordPolicy::isValid).count();
    }

}
