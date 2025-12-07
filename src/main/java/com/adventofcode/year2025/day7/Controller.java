package com.adventofcode.year2025.day7;

import static com.adventofcode.common.grid.GridUtility.constructGrid;
import java.awt.Point;
import java.util.Map;
import org.springframework.stereotype.Component;
import com.adventofcode.common.DailyAnswer;
import com.adventofcode.common.SolutionController;
import lombok.extern.slf4j.Slf4j;


@Slf4j
@Component("controller-2025-7")
public class Controller extends SolutionController {

    public DailyAnswer execute() {

        Map<Point, GridElement> grid = constructGrid(puzzleInput, GridElement.class);
        TachyonManifold manifold = new TachyonManifold(grid);

        log.info("Start point: {}", manifold.getStartPoint());

        long splitCount = manifold.countSplits();
        log.info("Total splits: {}", splitCount);
        answer.setPart1(splitCount);

        long timelineCount = manifold.countTimelines();
        log.info("Total timelines: {}", timelineCount);
        answer.setPart2(timelineCount);

        return answer;
    }

}
