package com.adventofcode.year2023.day9;

import static org.apache.commons.collections4.CollectionUtils.getCardinalityMap;
import java.util.*;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;
import com.adventofcode.common.DailyAnswer;
import com.adventofcode.common.SolutionController;
import lombok.extern.slf4j.Slf4j;


@Slf4j
@Component("controller-2023-9")
public class Controller extends SolutionController {

    public DailyAnswer execute() {

        List<List<Integer>> inputNumberList = parseInput();
        inputNumberList.forEach(l -> l.add(elementToAddToEnd(l)));
        answer.setPart1(inputNumberList.stream().map(List::getLast).mapToInt(Integer::intValue).sum());
        log.info("Part 1: {}", answer.getPart1());

        inputNumberList = parseInput();
        inputNumberList.forEach(l -> l.addFirst(elementToAddToBeginning(l)));
        answer.setPart2(inputNumberList.stream().map(List::getFirst).mapToInt(Integer::intValue).sum());
        log.info("Part 2: {}", answer.getPart2());

        return answer;

    }

    private List<List<Integer>> parseInput() {
        return puzzleInput.stream()
                .map(s -> Arrays.stream(s.split(" "))
                        .map(Integer::parseInt)
                        .collect(Collectors.toList()))
                .toList();
    }

    private Integer elementToAddToEnd(List<Integer> input) {
        List<List<Integer>> tree = generateTree(input);
        tree.getLast().add(0);

        for (int i = tree.size() - 2; i >= 0; i--) {
            tree.get(i).add(tree.get(i + 1).getLast() + tree.get(i).getLast());
        }

        return tree.getFirst().getLast();

    }

    private Integer elementToAddToBeginning(List<Integer> input) {
        List<List<Integer>> tree = generateTree(input);
        tree.getLast().addFirst(0);

        for (int i = tree.size() - 2; i >= 0; i--) {
            tree.get(i).addFirst(tree.get(i).getFirst() - tree.get(i + 1).getFirst());
        }

        return tree.getFirst().getFirst();

    }

    private List<List<Integer>> generateTree(List<Integer> input) {
        List<List<Integer>> tree = new ArrayList<>();
        tree.add(new ArrayList<>(input));
        for (int i = 1; i < input.size(); i++) {
            List<Integer> row = new ArrayList<>(input.size() - i);
            for (int j = 0; j < tree.get(i - 1).size() - 1; j++) {
                row.add(tree.get(i - 1).get(j + 1) - tree.get(i - 1).get(j));
            }
            tree.add(row);
            if (getCardinalityMap(row).getOrDefault(0, 0) == row.size()) {
                break;
            }
        }
        return tree;
    }

}
