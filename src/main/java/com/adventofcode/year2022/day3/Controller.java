package com.adventofcode.year2022.day3;

import java.util.List;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Component;
import com.adventofcode.common.DailyAnswer;
import com.adventofcode.common.SolutionController;
import lombok.extern.slf4j.Slf4j;


@Slf4j
@Component("controller-2022-3")
public class Controller extends SolutionController {

    public DailyAnswer execute() {


        List<Sack> sacks = puzzleInput.stream().map(Sack::new).toList();

        answer.setPart1(sacks.stream().map(Sack::getPriorityOfTypeInBothCompartments).mapToInt(Integer::intValue).sum());
        log.info("P1: {}", answer.getPart1());

        int sum = 0;
        for (int i = 0; i < sacks.size(); i += 3) {
            sum += TypeUtil.getPriority(CollectionUtils.intersection(CollectionUtils.intersection(sacks.get(i).getAllUniqueTypes(), sacks.get(i + 1).getAllUniqueTypes()), sacks.get(i + 2).getAllUniqueTypes()).iterator().next());
        }
        answer.setPart2(sum);
        log.info("P2: {}", answer.getPart2());

        return answer;
    }

}
