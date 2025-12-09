package com.adventofcode.year2025.day9;

import java.awt.Point;
import java.util.List;
import org.springframework.stereotype.Component;
import com.adventofcode.common.DailyAnswer;
import com.adventofcode.common.SolutionController;
import lombok.extern.slf4j.Slf4j;


@Slf4j
@Component("controller-2025-9")
public class Controller extends SolutionController {

    public DailyAnswer execute() {

        // Parse input into list of points
        List<Point> redTiles = puzzleInput.stream()
                .map(line -> {
                    String[] parts = line.split(",");
                    return new Point(Integer.parseInt(parts[0]), Integer.parseInt(parts[1]));
                })
                .toList();

        long maxAreaPart1 = 0;
        long maxAreaPart2 = 0;

        for (int i = 0; i < redTiles.size(); i++) {
            for (int j = i + 1; j < redTiles.size(); j++) {
                Point p1 = redTiles.get(i);
                Point p2 = redTiles.get(j);

                // Calculate rectangle dimensions (must have different x AND y to form a rectangle)
                long width = Math.abs(p2.x - p1.x);
                long height = Math.abs(p2.y - p1.y);
                long area = (width + 1) * (height + 1);

                maxAreaPart1 = Math.max(maxAreaPart1, area);

                if (PolygonUtility.isRectangleInsidePolygon(p1, p2, redTiles)) {
                    maxAreaPart2 = Math.max(maxAreaPart2, area);
                }
            }
        }

        answer.setPart1(maxAreaPart1);
        log.info("Part 1: {}", answer.getPart1());

        answer.setPart2(maxAreaPart2);
        log.info("Part 2: {}", answer.getPart2());

        return answer;
    }

}
