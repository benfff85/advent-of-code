package com.adventofcode.year2024.day5;

import java.util.*;
import java.util.stream.Collectors;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.math3.util.Pair;
import org.springframework.stereotype.Component;
import com.adventofcode.common.DailyAnswer;
import com.adventofcode.common.SolutionController;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component("controller-2024-5")
public class Controller extends SolutionController {

    public DailyAnswer execute() {

        int split = puzzleInput.indexOf("");
        Set<Pair<Integer, Integer>> allRuleSet = puzzleInput.stream().limit(split).map(s -> new Pair<>(Integer.parseInt(s.split("\\|")[0]), Integer.parseInt(s.split("\\|")[1]))).collect(Collectors.toSet());
        List<List<Integer>> pageUpdateLists = puzzleInput.stream().skip(split + 1L).map(s -> inputHelper.parseStringToIntList(s)).toList();

        int sumOfMiddleNumbers = 0;
        boolean isCorrectOrder = true;
        List<List<Integer>> incorrectlyOrderedPageUpdateLists = new ArrayList<>();
        for (List<Integer> pageUpdateList : pageUpdateLists) {
            for (int i = 0; i < pageUpdateList.size(); i++) {
                for (int j = i + 1; j < pageUpdateList.size(); j++) {
                    if (allRuleSet.contains(new Pair<>(pageUpdateList.get(j), pageUpdateList.get(i)))) {
                        isCorrectOrder = false;
                        break;
                    }
                }
            }

            if (isCorrectOrder) {
                sumOfMiddleNumbers += pageUpdateList.get(pageUpdateList.size() / 2);
            } else {
                incorrectlyOrderedPageUpdateLists.add(pageUpdateList);
            }
            isCorrectOrder = true;

        }

        answer.setPart1(sumOfMiddleNumbers);
        log.info("Part 1: {}", answer.getPart1());

        sumOfMiddleNumbers = 0;
        for (List<Integer> pageUpdateList : incorrectlyOrderedPageUpdateLists) {

            // Create frequency map with a key of page number and value of the number of times it appears on the left side of a rule
            Map<Integer, Integer> ruleFrequencyMap = CollectionUtils.getCardinalityMap(
                    allRuleSet.stream()
                            .filter(pair -> new HashSet<>(pageUpdateList).containsAll(List.of(pair.getKey(), pair.getValue())))
                            .map(Pair::getKey)
                            .toList());

            // Frequency map will not be populated for page numbers with no corresponding rules
            pageUpdateList.forEach(pageNum -> ruleFrequencyMap.putIfAbsent(pageNum, 0));

            // Sort the frequency map in descending order
            List<Map.Entry<Integer, Integer>> sortedList = ruleFrequencyMap.entrySet()
                    .stream()
                    .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                    .toList();

            sumOfMiddleNumbers += sortedList.get(sortedList.size() / 2).getKey();
        }

        answer.setPart2(sumOfMiddleNumbers);
        log.info("Part 2: {}", answer.getPart2());

        return answer;
    }

}
