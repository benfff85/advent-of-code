package com.adventofcode.year2025.day3;

import java.util.List;
import org.springframework.stereotype.Component;
import com.adventofcode.common.DailyAnswer;
import com.adventofcode.common.InputHelper;
import com.adventofcode.common.SolutionController;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component("controller-2025-3")
public class Controller extends SolutionController {

    public Controller(InputHelper inputHelper) {
        super(inputHelper, "puzzle-input/2025/day-3.txt");
    }

    public DailyAnswer execute() {

        List<BatteryBank> batteryBanks = puzzleInput.stream().map(BatteryBank::new).toList();

        int totalOutput = 0;
        for (BatteryBank batteryBank : batteryBanks) {
            log.info("Battery bank: {}", batteryBank);
            log.info("Max joltage: {}", batteryBank.getMaxJoltage());
            totalOutput += batteryBank.getMaxJoltage();
        }
        log.info("Total output: {}", totalOutput);
        answer.setPart1(totalOutput);



        answer.setPart2(null);

        return answer;

    }

}
