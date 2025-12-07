package com.adventofcode.year2025.day6;

import java.util.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import com.adventofcode.common.DailyAnswer;
import com.adventofcode.common.SolutionController;
import lombok.extern.slf4j.Slf4j;


@Slf4j
@Component("controller-2025-6")
public class Controller extends SolutionController {

    public DailyAnswer execute() {


        List<Problem> problems = new ArrayList<>();
        List<String> inputRowStrings;
        for (String inputRow : puzzleInput) {
            inputRowStrings = Arrays.asList(inputRow.trim().split("\\s+"));

            // Initialize problems at time of first row processing
            if (problems.isEmpty()) {
                inputRowStrings.forEach(inputRowString -> problems.add(new Problem()));
            }

            for (int i = 0; i < inputRowStrings.size(); i++) {
                if (StringUtils.isNumeric(inputRowStrings.get(i))) {
                    problems.get(i).addNumber(Long.parseLong(inputRowStrings.get(i)));
                } else {
                    problems.get(i).setOperator(inputRowStrings.get(i));
                }
            }

        }

        answer.setPart1(problems.stream().mapToLong(Problem::evaluate).sum());
        answer.setPart2(null);
        return answer;
    }

}
