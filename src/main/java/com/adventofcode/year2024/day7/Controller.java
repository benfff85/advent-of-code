package com.adventofcode.year2024.day7;

import java.util.List;
import org.springframework.stereotype.Component;
import com.adventofcode.common.DailyAnswer;
import com.adventofcode.common.SolutionController;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component("controller-2024-7")
public class Controller extends SolutionController {

    public DailyAnswer execute() {

        List<Calibration> calibrations = puzzleInput.stream().map(Calibration::new).toList();

        answer.setPart1(calibrations.stream().filter(Calibration::isSolvable).mapToLong(Calibration::getResult).sum());
        log.info("Part 1: {}", answer.getPart1());


        answer.setPart2(calibrations.stream().filter(Calibration::isSolvableWithConcat).mapToLong(Calibration::getResult).sum());
        log.info("Part 2: {}", answer.getPart2());

        return answer;
    }

}
