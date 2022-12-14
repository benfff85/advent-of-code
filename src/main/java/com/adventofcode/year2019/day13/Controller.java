package com.adventofcode.year2019.day13;

import com.adventofcode.common.DailyAnswer;
import com.adventofcode.common.InputHelper;
import com.adventofcode.common.SolutionController;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.math.BigInteger;
import java.util.List;


@Slf4j
@Component("controller-day-13")
public class Controller extends SolutionController {

    public Controller(InputHelper inputHelper) {
        super(inputHelper, "puzzle-input/2019/day-13.txt");
    }

    public DailyAnswer execute() {

        List<BigInteger> input = inputHelper.parseStringToBigIntList(puzzleInput.get(0));

        Arcade arcade = new Arcade(input);
        arcade.play();
        answer.setPart1(arcade.getCountOfBlocks());
        log.info("Count of blocks: {}", answer.getPart1());

        arcade = new Arcade(input);
        arcade.play2();
        answer.setPart2(arcade.getScore());
        log.info("Score: {}", answer.getPart2());

        return answer;
    }

}
