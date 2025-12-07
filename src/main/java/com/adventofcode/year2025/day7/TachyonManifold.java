package com.adventofcode.year2025.day7;

import static com.adventofcode.common.grid.GridUtility.getAdjacentElement;
import static com.adventofcode.common.grid.GridUtility.getFirstElementByValue;
import java.awt.Point;
import java.util.*;
import com.adventofcode.common.grid.Direction;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

/**
 * Represents a tachyon manifold with beam simulation capabilities. Encapsulates the grid and provides methods to count splits and timelines.
 */
@Slf4j
@Getter
public class TachyonManifold {

    private final Map<Point, GridElement> grid;
    private final Point startPoint;

    public TachyonManifold(Map<Point, GridElement> grid) {
        this.grid = grid;
        this.startPoint = getFirstElementByValue(grid, GridElement.START).getKey();
    }

    /**
     * Count total splits when beams merge at overlapping positions.
     */
    public long countSplits() {
        List<Beam> beams = new ArrayList<>();
        Set<Point> beamPositions = new HashSet<>();

        Point initialBeamPoint = new Point(startPoint.x, startPoint.y - 1);
        if (grid.containsKey(initialBeamPoint)) {
            beams.add(new Beam(initialBeamPoint, Direction.D));
            beamPositions.add(initialBeamPoint);
        }

        long splitCount = 0;

        while (beams.stream().anyMatch(Beam::isActiveIndicator)) {
            List<Beam> newBeams = new ArrayList<>();

            for (Beam beam : beams) {
                if (!beam.isActiveIndicator()) {
                    continue;
                }

                Point currentPos = beam.getCurrentPosition();
                GridElement element = grid.get(currentPos);

                if (element == GridElement.SPLIT) {
                    beam.setActiveIndicator(false);
                    splitCount++;

                    Point leftPoint = new Point(currentPos.x - 1, currentPos.y);
                    if (grid.containsKey(leftPoint) && !beamPositions.contains(leftPoint)) {
                        newBeams.add(new Beam(leftPoint, Direction.D));
                        beamPositions.add(leftPoint);
                    }

                    Point rightPoint = new Point(currentPos.x + 1, currentPos.y);
                    if (grid.containsKey(rightPoint) && !beamPositions.contains(rightPoint)) {
                        newBeams.add(new Beam(rightPoint, Direction.D));
                        beamPositions.add(rightPoint);
                    }
                } else {
                    Map.Entry<Point, GridElement> nextEntry = getAdjacentElement(grid, currentPos, Direction.D);
                    if (nextEntry != null && nextEntry.getValue() != null) {
                        Point nextPoint = nextEntry.getKey();
                        if (!beamPositions.contains(nextPoint)) {
                            beam.addToPath(nextPoint);
                            beamPositions.add(nextPoint);
                        } else {
                            beam.setActiveIndicator(false);
                        }
                    } else {
                        beam.setActiveIndicator(false);
                    }
                }
            }

            beams.addAll(newBeams);
        }

        return splitCount;
    }

    /**
     * Count total timelines using many-worlds interpretation. Uses memoization for efficiency with large grids.
     */
    public long countTimelines() {
        Map<Point, Long> memo = new HashMap<>();

        Point initialPoint = new Point(startPoint.x, startPoint.y - 1);
        if (!grid.containsKey(initialPoint)) {
            return 1;
        }

        return countTimelinesFromPoint(initialPoint, memo);
    }

    private long countTimelinesFromPoint(Point point, Map<Point, Long> memo) {
        if (memo.containsKey(point)) {
            return memo.get(point);
        }

        GridElement element = grid.get(point);
        long result;

        if (element == GridElement.SPLIT) {
            long leftTimelines = 0;
            long rightTimelines = 0;

            Point leftPoint = new Point(point.x - 1, point.y);
            if (grid.containsKey(leftPoint)) {
                leftTimelines = countTimelinesFromPoint(leftPoint, memo);
            } else {
                leftTimelines = 1;
            }

            Point rightPoint = new Point(point.x + 1, point.y);
            if (grid.containsKey(rightPoint)) {
                rightTimelines = countTimelinesFromPoint(rightPoint, memo);
            } else {
                rightTimelines = 1;
            }

            result = leftTimelines + rightTimelines;
        } else {
            Map.Entry<Point, GridElement> nextEntry = getAdjacentElement(grid, point, Direction.D);
            if (nextEntry != null && nextEntry.getValue() != null) {
                result = countTimelinesFromPoint(nextEntry.getKey(), memo);
            } else {
                result = 1;
            }
        }

        memo.put(point, result);
        return result;
    }

}
