package com.adventofcode.year2022.day18;

import org.springframework.stereotype.Component;
import com.adventofcode.common.DailyAnswer;
import com.adventofcode.common.SolutionController;
import lombok.extern.slf4j.Slf4j;


@Slf4j
@Component("controller-2022-18")
public class Controller extends SolutionController {

    public DailyAnswer execute() {

        LavaDroplet droplet = new LavaDroplet();
        puzzleInput.stream().map(Cube::new).forEach(droplet::addCube);

        answer.setPart1(droplet.calculateSurfaceArea());
        log.info("P1: {}", answer.getPart1());

        answer.setPart2(droplet.calculateContiguousSurfaceArea());
        log.info("P2: {}", answer.getPart2());

        return answer;
    }

}
