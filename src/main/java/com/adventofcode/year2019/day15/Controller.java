package com.adventofcode.year2019.day15;

import java.math.BigInteger;
import java.util.List;
import org.springframework.stereotype.Component;
import com.adventofcode.common.DailyAnswer;
import com.adventofcode.common.SolutionController;
import lombok.extern.slf4j.Slf4j;


@Slf4j
@Component("controller-day-15")
public class Controller extends SolutionController {

    public DailyAnswer execute() {
        List<BigInteger> input = inputHelper.parseStringToBigIntList(puzzleInput.get(0));

        RepairDroid repairDroid = new RepairDroid(input);
        repairDroid.scan();
        answer.setPart1(repairDroid.getPathLength());
        log.info("Distance of path to oxygen is: {}", answer.getPart1());

        OxygenSimulator oxygenSimulator = new OxygenSimulator();
        answer.setPart2(oxygenSimulator.run(repairDroid.getGrid()));
        log.info("Minutes till capsule is full of oxygen: {}", answer.getPart2());

        return answer;
    }

}
