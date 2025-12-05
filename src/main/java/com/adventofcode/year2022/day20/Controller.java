package com.adventofcode.year2022.day20;

import java.util.List;
import org.springframework.stereotype.Component;
import com.adventofcode.common.DailyAnswer;
import com.adventofcode.common.SolutionController;
import lombok.extern.slf4j.Slf4j;


@Slf4j
@Component("controller-2022-20")
public class Controller extends SolutionController {

    public DailyAnswer execute() {

        answer.setPart1(process(1L, 1));
        log.info("Part 1: {}", answer.getPart1());

        answer.setPart2(process(811589153L, 10));
        log.info("Part 2: {}", answer.getPart2());

        return answer;
    }

    private long process(long multiplier, int mixCount) {
        List<Node> inputNodes = puzzleInput.stream().map(s -> new Node(s, multiplier)).toList();
        LoopingList loopingList = new LoopingList(inputNodes);

        for (int i = 0; i < mixCount; i++) {
            for (Node node : inputNodes) {
                loopingList.slide(node, node.getValue());
            }
        }
        return loopingList.getNodeGivenOffset(0L, 1000L).getValue() +
                loopingList.getNodeGivenOffset(0L, 2000L).getValue() +
                loopingList.getNodeGivenOffset(0L, 3000L).getValue();
    }

}
