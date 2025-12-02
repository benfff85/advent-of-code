package com.adventofcode.year2020.day5;

import com.adventofcode.common.DailyAnswer;
import com.adventofcode.common.InputHelper;
import com.adventofcode.common.SolutionController;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component("controller-2020-5")
public class Controller extends SolutionController {

    public Controller(InputHelper inputHelper) {
        super(inputHelper, "puzzle-input/2020/day-5.txt");
    }

    public DailyAnswer execute() {

        List<Integer> sortedSeatIdList = puzzleInput.stream().map(BoardingPass::new).map(BoardingPass::getSeatId).sorted().toList();
        answer.setPart1(sortedSeatIdList.getLast());
        log.info("Part 1: {}", answer.getPart1());

        int previousSeatId = sortedSeatIdList.getFirst() - 1;
        for (Integer seatId : sortedSeatIdList) {
            if (previousSeatId != (seatId - 1)) {
                log.info("My seat number: {}", seatId - 1);
                answer.setPart2(seatId - 1);
                break;
            }
            previousSeatId = seatId;
        }

        log.info("Part 2: {}", answer.getPart2());

        return answer;
    }

}
