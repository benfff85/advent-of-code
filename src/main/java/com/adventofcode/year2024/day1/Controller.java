package com.adventofcode.year2024.day1;

import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import com.adventofcode.common.DailyAnswer;
import com.adventofcode.common.SolutionController;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component("controller-2024-1")
public class Controller extends SolutionController {

    public DailyAnswer execute() {

        List<Integer> locationList1 = getLocationList(puzzleInput, 0);
        List<Integer> locationList2 = getLocationList(puzzleInput, 1);

        answer.setPart1(IntStream.range(0, locationList1.size())
                .map(i -> Math.abs(locationList1.get(i) - locationList2.get(i)))
                .sum());
        log.info("Part 1: {}", answer.getPart1());

        Map<Integer, Integer> cardinalityMap = CollectionUtils.getCardinalityMap(locationList2);
        answer.setPart2(locationList1.stream()
                .mapToInt(i -> i * cardinalityMap.getOrDefault(i, 0))
                .sum());
        log.info("Part 2: {}", answer.getPart2());

        return answer;
    }

    private List<Integer> getLocationList(List<String> input, int index) {
        return input.stream()
                .map(StringUtils::normalizeSpace)
                .map(line -> line.split(" ")[index])
                .map(Integer::parseInt)
                .sorted()
                .toList();
    }


}
