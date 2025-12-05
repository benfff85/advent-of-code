package com.adventofcode.year2023.day4;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import org.springframework.stereotype.Component;
import com.adventofcode.common.DailyAnswer;
import com.adventofcode.common.SolutionController;
import lombok.extern.slf4j.Slf4j;


@Slf4j
@Component("controller-2023-4")
public class Controller extends SolutionController {

    public DailyAnswer execute() {

        List<Card> cards = puzzleInput.stream().map(Card::new).toList();

        answer.setPart1(cards.stream().map(Card::getScore).mapToInt(Integer::intValue).sum());
        log.info("Part 1: {}", answer.getPart1());


        Map<Integer, Integer> cardCountMap = IntStream.rangeClosed(1, cards.size()).boxed().collect(Collectors.toMap(i -> i, i -> 1));
        for (int i = 1; i <= cards.size(); i++) {
            for (int j = 1; j <= cards.get(i - 1).getCountOfIntersectingNumbers(); j++) {
                cardCountMap.put(i + j, cardCountMap.get(i + j) + cardCountMap.get(i));
            }
        }

        answer.setPart2(cardCountMap.values().stream().mapToInt(Integer::intValue).sum());
        log.info("Part 2: {}", answer.getPart2());

        return answer;
    }


}
