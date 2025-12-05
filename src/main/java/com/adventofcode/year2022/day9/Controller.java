package com.adventofcode.year2022.day9;

import static java.lang.Integer.parseInt;
import java.awt.Point;
import java.util.*;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import com.adventofcode.common.DailyAnswer;
import com.adventofcode.common.SolutionController;
import com.adventofcode.common.grid.*;
import lombok.extern.slf4j.Slf4j;


@Slf4j
@Component("controller-2022-9")
public class Controller extends SolutionController {

    public DailyAnswer execute() {

        answer.setPart1(calculateCountOfTailVisitedPoints(1));
        log.info("P1: {}", answer.getPart1());

        answer.setPart2(calculateCountOfTailVisitedPoints(9));
        log.info("P2: {}", answer.getPart2());

        return answer;

    }

    private List<Direction> createDirectionList(List<String> input) {
        Direction direction;
        List<Direction> directionList = new ArrayList<>();
        for (String s : input) {
            direction = Direction.valueOf(s.split(" ")[0]);
            for (int i = 0; i < parseInt(s.split(" ")[1]); i++) {
                directionList.add(direction);
            }
        }
        return directionList;
    }

    private Integer calculateCountOfTailVisitedPoints(Integer ropeSegmentCount) {
        Map<Point, PrintableGridElement> grid = new HashMap<>();

        List<Direction> directionList = createDirectionList(puzzleInput);

        Set<Point> tailVisitedPoints = new HashSet<>();

        List<RopeSegment> rope = new ArrayList<>();
        for (int i = 0; i < ropeSegmentCount; i++) {
            rope.add(new RopeSegment(String.valueOf(i + 1)));
        }

        grid.put(new Point(0, 0), SimplePrintableGridElement.of("H"));
        log.trace("Before {}", GridUtility.print(grid));

        RopeSegment ropeSegment;
        for (Direction d : directionList) {

            for (int i = 0; i < ropeSegmentCount; i++) {
                ropeSegment = rope.get(i);
                if (i == 0) {
                    ropeSegment.move(PointUtil.getAdjacentPoint(ropeSegment.getHead(), d));
                } else {
                    ropeSegment.move(rope.get(i - 1).getTail());
                }
            }
            tailVisitedPoints.add(Objects.requireNonNull(CollectionUtils.lastElement(rope)).getTail());

            // For printing / debugging purposes only
            if (log.isTraceEnabled()) {

                grid.replaceAll((p, v) -> SimplePrintableGridElement.of("."));
                for (int i = rope.size() - 1; i >= 0; i--) {
                    grid.put(rope.get(i).getTail(), SimplePrintableGridElement.of(rope.get(i).getName()));
                }
                grid.put(rope.get(0).getHead(), SimplePrintableGridElement.of("H"));

                log.trace("After Move {}", GridUtility.print(grid));
            }

        }
        return tailVisitedPoints.size();
    }

}
