package com.adventofcode.year2022.day6;

import java.util.*;
import org.springframework.stereotype.Component;
import com.adventofcode.common.DailyAnswer;
import com.adventofcode.common.SolutionController;
import lombok.extern.slf4j.Slf4j;


@Slf4j
@Component("controller-2022-6")
public class Controller extends SolutionController {

    public DailyAnswer execute() {

        List<String> signal = Arrays.asList(puzzleInput.get(0).split(""));

        answer.setPart1(findSignalEnd(signal, 4));
        log.info("P1: {}", answer.getPart1());

        answer.setPart2(findSignalEnd(signal, 14));
        log.info("P2: {}", answer.getPart2());

        return answer;
    }

    private int findSignalEnd(List<String> signal, int markerSize) {
        for (int i = markerSize; i < signal.size(); i++) {
            int size = Set.copyOf(signal.subList(i - markerSize, i)).size();
            if (size == markerSize) {
                return i;
            }
        }
        return -1;
    }

}
