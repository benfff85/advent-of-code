package com.adventofcode.year2023.day8;

import com.adventofcode.common.DailyAnswer;
import com.adventofcode.common.InputHelper;
import com.adventofcode.common.SolutionController;
import com.adventofcode.common.grid.Direction;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.iterators.LoopingIterator;
import org.apache.commons.math3.util.ArithmeticUtils;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Component("controller-2023-8")
public class Controller extends SolutionController {

    public Controller(InputHelper inputHelper) {
        super(inputHelper, "puzzle-input/2023/day-8.txt");
    }

    private static long getSteps(LoopingIterator<Direction> directionIterator, Node startNode, String endNodeId) {
        long steps = 0;
        while (!startNode.getId().endsWith(endNodeId)) {
            startNode = startNode.getChildNode(directionIterator.next());
            steps++;
        }
        return steps;
    }

    public DailyAnswer execute() {

        List<Direction> directions = Arrays.stream(puzzleInput.getFirst().split("")).map(Direction::valueOf).toList();

        Map<String, Node> nodes = createNodes();

        answer.setPart1(getSteps(new LoopingIterator<>(directions), nodes.get("AAA"), "ZZZ"));
        log.info("Part 1: {}", answer.getPart1());

        Long stepCount = nodes.values()
                .parallelStream()
                .filter(n -> n.getId().endsWith("A"))
                .mapToLong(n -> getSteps(new LoopingIterator<>(directions), n, "Z"))
                .reduce(1L, ArithmeticUtils::lcm);

        answer.setPart2(stepCount);
        log.info("Part 2: {}", answer.getPart2());

        return answer;

    }

    private Map<String, Node> createNodes() {
        Map<String, Node> nodes = puzzleInput.stream().skip(2).map(Node::new).collect(Collectors.toMap(Node::getId, n -> n));
        nodes.values().forEach(n -> n.setChildNodes(nodes.get(n.getChildNodeIds().getLeft()), nodes.get(n.getChildNodeIds().getRight())));
        return nodes;
    }

}
