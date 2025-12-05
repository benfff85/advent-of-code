package com.adventofcode.year2022.day10;

import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Component;
import com.adventofcode.common.DailyAnswer;
import com.adventofcode.common.SolutionController;
import lombok.extern.slf4j.Slf4j;


@Slf4j
@Component("controller-2022-10")
public class Controller extends SolutionController {

    public DailyAnswer execute() {

        List<Instruction> instructions = puzzleInput.stream().map(Instruction::new).toList();

        List<Cycle> cycles = processInstructions(instructions);

        answer.setPart1(calculateSignalStrengthSum(cycles));
        log.info("P1: {}", answer.getPart1());

        answer.setPart2(renderScreen(cycles));
        log.info("P2: \n{}", answer.getPart2());

        return answer;

    }

    private List<Cycle> processInstructions(List<Instruction> instructions) {
        List<Cycle> cycles = new ArrayList<>();
        Integer x = 1;
        Integer cycleCount = 0;
        addCycle(cycles, x, cycleCount);
        cycleCount++;
        for (Instruction instruction : instructions) {
            if (instruction.getAction().equals("noop")) {
                addCycle(cycles, x, cycleCount);
                cycleCount++;
            } else if (instruction.getAction().equals("addx")) {
                addCycle(cycles, x, cycleCount);
                cycleCount++;
                addCycle(cycles, x, cycleCount);
                cycleCount++;
                x += instruction.getMagnitude();
            }
        }
        return cycles;
    }

    private void addCycle(List<Cycle> cycles, Integer x, Integer cycleCount) {
        cycles.add(Cycle.builder().number(cycleCount).x(x).signalStrength(x * cycleCount).build());
    }

    private int calculateSignalStrengthSum(List<Cycle> cycles) {
        int sum = 0;
        for (int i = 20; i <= 220; i += 40) {
            sum += cycles.get(i).getSignalStrength();
        }
        return sum;
    }

    private String renderScreen(List<Cycle> cycles) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 6; i++) {
            for (int pixel = 0; pixel < 40; pixel++) {
                // Pixel 0 = Cycle 1
                Cycle cycle = cycles.get((40 * i) + (pixel + 1));
                if ((cycle.getX() - 1 <= pixel) && (pixel <= cycle.getX() + 1)) {
                    sb.append("#");
                } else {
                    sb.append(".");
                }
            }
            sb.append("\n");
        }
        return sb.toString();
    }

}
