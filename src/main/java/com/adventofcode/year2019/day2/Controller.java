package com.adventofcode.year2019.day2;

import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Component;
import com.adventofcode.common.DailyAnswer;
import com.adventofcode.common.SolutionController;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component("controller-day-2")
public class Controller extends SolutionController {

    public DailyAnswer execute() {
        List<Integer> input = inputHelper.parseStringToIntList(puzzleInput.get(0));

        List<Integer> list = new ArrayList<>(input);
        process(12, 2, list);
        answer.setPart1(list.get(0));
        log.info("First element value: {}", answer.getPart1());

        for (int i = 0; i <= 99; i++) {
            for (int j = 0; j <= 99; j++) {
                list = new ArrayList<>(input);
                process(i, j, list);
                if (list.get(0) == 19690720) {
                    answer.setPart2((100 * i) + j);
                    log.info("Input pair {}:{}::{}", i, j, answer.getPart2());
                    break;
                }
            }
        }
        return answer;
    }

    private void process(int elementA, int elementB, List<Integer> list) {
        list.set(1, elementA);
        list.set(2, elementB);

        for (int i = 0; i < list.size(); i += 4) {

            if (list.get(i) == 1) {
                processAdd(list, i);
            } else if (list.get(i) == 2) {
                processMultiply(list, i);
            } else if (list.get(i) == 99) {
                return;
            } else {
                log.error("Unexpected operator {} at index {}", list.get(i), i);
            }
        }
    }

    private void processAdd(List<Integer> input, int i) {
        input.set(input.get(i + 3), input.get(input.get(i + 1)) + input.get(input.get(i + 2)));
    }

    private void processMultiply(List<Integer> input, int i) {
        input.set(input.get(i + 3), input.get(input.get(i + 1)) * input.get(input.get(i + 2)));
    }

}
