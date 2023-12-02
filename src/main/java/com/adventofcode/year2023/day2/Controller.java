package com.adventofcode.year2023.day2;

import com.adventofcode.common.DailyAnswer;
import com.adventofcode.common.InputHelper;
import com.adventofcode.common.SolutionController;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;


@Slf4j
@Component("controller-2023-2")
public class Controller extends SolutionController {

    public Controller(InputHelper inputHelper) {
        super(inputHelper, "puzzle-input/2023/day-2.txt");
    }

    public DailyAnswer execute() {

        List<Game> gameList = puzzleInput.stream().map(Game::new).toList();

        answer.setPart1(gameList.stream().filter(this::isGamePossible).map(Game::getId).mapToInt(Integer::intValue).sum());
        log.info("Part 1: {}", answer.getPart1());

        answer.setPart2(gameList.stream().map(Game::power).mapToInt(Integer::intValue).sum());
        log.info("Part 2: {}", answer.getPart2());

        return answer;
    }

    private boolean isGamePossible(Game game) {
        return game.getMaxBlue() <= 14 && game.getMaxGreen() <= 13 && game.getMaxRed() <= 12;
    }

}