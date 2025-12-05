package com.adventofcode.year2022.day15;

import static java.util.Objects.nonNull;
import java.util.Comparator;
import java.util.List;
import org.apache.commons.math3.util.Pair;
import org.springframework.stereotype.Component;
import com.adventofcode.common.DailyAnswer;
import com.adventofcode.common.SolutionController;
import lombok.extern.slf4j.Slf4j;


@Slf4j
@Component("controller-2022-15")
public class Controller extends SolutionController {

    public DailyAnswer execute() {

        List<Sensor> sensors = puzzleInput.stream().map(Sensor::new).toList();

        answer.setPart1(getCoveredCountForRow(getCoveredRangeListForGivenRow(sensors, 2000000)));
        log.info("P1: {}", answer.getPart1());

        for (int y = 0; y <= 4000000; y++) {
            Integer uncoveredXCord = getUncoveredXCord(getCoveredRangeListForGivenRow(sensors, y));
            if (nonNull(uncoveredXCord)) {
                answer.setPart2((uncoveredXCord * 4000000L) + y);
                break;
            }
        }
        log.info("P2: {}", answer.getPart2());

        return answer;

    }

    private int getCoveredCountForRow(List<Pair<Integer, Integer>> rangeList) {
        int coveredCount = 0;
        int index = rangeList.get(0).getFirst();
        for (Pair<Integer, Integer> range : rangeList) {
            if (range.getSecond() > index) {
                coveredCount += range.getSecond() - index;
                index = range.getSecond();
            }
        }
        return coveredCount;
    }

    private Integer getUncoveredXCord(List<Pair<Integer, Integer>> rangeList) {
        int index = rangeList.get(0).getFirst();
        for (Pair<Integer, Integer> range : rangeList) {
            if (range.getSecond() > index) {
                if (range.getFirst() >= index + 2) {
                    return index + 1;
                }
                index = range.getSecond();
            }
        }
        return null;
    }

    private List<Pair<Integer, Integer>> getCoveredRangeListForGivenRow(List<Sensor> sensors, int y) {
        return sensors.stream().map(s -> s.getCoveredPointsOnRow(y)).filter(p -> p.getFirst() != 0 || p.getSecond() != 0).sorted(Comparator.comparing(Pair::getFirst)).toList();
    }

}
