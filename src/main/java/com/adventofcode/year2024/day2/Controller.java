package com.adventofcode.year2024.day2;

import java.util.List;
import org.springframework.stereotype.Component;
import com.adventofcode.common.DailyAnswer;
import com.adventofcode.common.SolutionController;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component("controller-2024-2")
public class Controller extends SolutionController {

    public DailyAnswer execute() {

        List<Report> reports = puzzleInput.stream().map(Report::new).toList();

        answer.setPart1(reports.stream().filter(Report::isSafe).count());
        log.info("Part 1: {}", answer.getPart1());

        answer.setPart2(reports.stream().filter(Report::isSafeWithOneLevelRemoved).count());
        log.info("Part 2: {}", answer.getPart2());

        return answer;
    }

}
