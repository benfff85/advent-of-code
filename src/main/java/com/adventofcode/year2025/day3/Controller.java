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

        long totalOutput = 0;
        long totalOutputWithDisabledSafety = 0;
        for (BatteryBank batteryBank : batteryBanks) {
            log.info("Battery bank: {}", batteryBank);
            totalOutput += batteryBank.getMaxJoltage();
            totalOutputWithDisabledSafety += batteryBank.getMaxJoltageWithDisabledSafety();
        }
        log.info("Total output: {}", totalOutput);
        answer.setPart1(totalOutput);
        log.info("Total output with disabled safety: {}", totalOutputWithDisabledSafety);
        answer.setPart2(totalOutputWithDisabledSafety);

        return answer;

    }

}
