package com.adventofcode.year2024.day11;

import java.util.*;
import java.util.stream.Collectors;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Component;
import com.adventofcode.common.DailyAnswer;
import com.adventofcode.common.SolutionController;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component("controller-2024-11")
public class Controller extends SolutionController {

    public DailyAnswer execute() {

        answer.setPart1(processStepsAndFindStoneCount(puzzleInput, 25));
        log.info("Part 1: {}", answer.getPart1());

        answer.setPart2(processStepsAndFindStoneCount(puzzleInput, 75));
        log.info("Part 2: {}", answer.getPart2());

        return answer;
    }

    private Long processStepsAndFindStoneCount(List<String> input, int steps) {

        Map<Long, Long> numberFrequencyMap = CollectionUtils.getCardinalityMap(Arrays.stream(input.getFirst().split(" ")).map(Long::parseLong).toList())
                .entrySet()
                .stream()
                .collect(Collectors.toMap(Map.Entry::getKey, entry -> (long) entry.getValue()));

        Long newNum;
        for (int step = 0; step < steps; step++) {

            Map<Long, Long> stepSpecificFrequencyMap = new HashMap<>();
            for (Map.Entry<Long, Long> entry : numberFrequencyMap.entrySet()) {

                long num = entry.getKey();
                if (num == 0) {
                    newNum = 1L;
                    stepSpecificFrequencyMap.put(newNum, stepSpecificFrequencyMap.getOrDefault(newNum, 0L) + entry.getValue());
                } else if (String.valueOf(num).length() % 2 == 0) {
                    int length = String.valueOf(num).length();

                    newNum = Long.parseLong(String.valueOf(num).substring(0, length / 2));
                    stepSpecificFrequencyMap.put(newNum, stepSpecificFrequencyMap.getOrDefault(newNum, 0L) + entry.getValue());

                    newNum = Long.parseLong(String.valueOf(num).substring(length / 2));
                    stepSpecificFrequencyMap.put(newNum, stepSpecificFrequencyMap.getOrDefault(newNum, 0L) + entry.getValue());

                } else {
                    newNum = num * 2024;
                    stepSpecificFrequencyMap.put(newNum, stepSpecificFrequencyMap.getOrDefault(newNum, 0L) + entry.getValue());
                }

            }
            numberFrequencyMap = stepSpecificFrequencyMap;
        }
        return numberFrequencyMap.values().stream().mapToLong(Long::longValue).sum();
    }

}
