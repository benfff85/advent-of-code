package com.adventofcode.year2025.day7;

import static com.adventofcode.common.grid.GridUtility.*;
import java.awt.Point;
import java.util.*;
import org.springframework.stereotype.Component;
import com.adventofcode.common.DailyAnswer;
import com.adventofcode.common.SolutionController;
import com.adventofcode.common.grid.Direction;
import com.adventofcode.common.grid.GridUtility;
import lombok.extern.slf4j.Slf4j;


@Slf4j
@Component("controller-2025-7")
public class Controller extends SolutionController {

    public DailyAnswer execute() {

        Map<Point, GridElement> grid = constructGrid(puzzleInput, GridElement.class);
        log.info("Grid:\n{}", GridUtility.print(grid));

        // Find starting point
        Point startPoint = getFirstElementByValue(grid, GridElement.START).getKey();
        log.info("Start point: {}", startPoint);

        // Part 1: Count total splits (beams merge when overlapping)
        int splitCount = countSplits(grid, startPoint);
        log.info("Total splits: {}", splitCount);
        answer.setPart1(splitCount);

        // Part 2: Count total timelines (many-worlds - each particle takes one path)
        long timelineCount = countTimelines(grid, startPoint);
        log.info("Total timelines: {}", timelineCount);
        answer.setPart2(timelineCount);

        return answer;
    }

    private int countSplits(Map<Point, GridElement> grid, Point startPoint) {
        List<Beam> beams = new ArrayList<>();
        Set<Point> beamPositions = new HashSet<>();

        Point initialBeamPoint = new Point(startPoint.x, startPoint.y - 1);
        if (grid.containsKey(initialBeamPoint)) {
            Beam initialBeam = new Beam(initialBeamPoint, Direction.D);
            beams.add(initialBeam);
            beamPositions.add(initialBeamPoint);
        }

        int splitCount = 0;

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
                        Beam leftBeam = new Beam(leftPoint, Direction.D);
                        newBeams.add(leftBeam);
                        beamPositions.add(leftPoint);
                    }

                    Point rightPoint = new Point(currentPos.x + 1, currentPos.y);
                    if (grid.containsKey(rightPoint) && !beamPositions.contains(rightPoint)) {
                        Beam rightBeam = new Beam(rightPoint, Direction.D);
                        newBeams.add(rightBeam);
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

    private long countTimelines(Map<Point, GridElement> grid, Point startPoint) {
        // Each particle follows a single path; at each splitter it creates two separate timelines
        // Count total particles that complete their journey (exit the grid)

        List<Beam> particles = new ArrayList<>();

        Point initialPoint = new Point(startPoint.x, startPoint.y - 1);
        if (grid.containsKey(initialPoint)) {
            particles.add(new Beam(initialPoint, Direction.D));
        }

        long completedTimelines = 0;

        while (!particles.isEmpty()) {
            List<Beam> newParticles = new ArrayList<>();

            for (Beam particle : particles) {
                if (!particle.isActiveIndicator()) {
                    continue;
                }

                Point currentPos = particle.getCurrentPosition();
                GridElement element = grid.get(currentPos);

                if (element == GridElement.SPLIT) {
                    // Particle splits into two separate timelines
                    particle.setActiveIndicator(false);

                    // Left timeline
                    Point leftPoint = new Point(currentPos.x - 1, currentPos.y);
                    if (grid.containsKey(leftPoint)) {
                        newParticles.add(new Beam(leftPoint, Direction.D));
                    } else {
                        completedTimelines++; // Left would exit grid
                    }

                    // Right timeline
                    Point rightPoint = new Point(currentPos.x + 1, currentPos.y);
                    if (grid.containsKey(rightPoint)) {
                        newParticles.add(new Beam(rightPoint, Direction.D));
                    } else {
                        completedTimelines++; // Right would exit grid
                    }
                } else {
                    // Move down
                    Map.Entry<Point, GridElement> nextEntry = getAdjacentElement(grid, currentPos, Direction.D);
                    if (nextEntry != null && nextEntry.getValue() != null) {
                        particle.addToPath(nextEntry.getKey());
                    } else {
                        // Particle exits grid - this timeline is complete
                        particle.setActiveIndicator(false);
                        completedTimelines++;
                    }
                }
            }

            // Remove inactive particles and add new ones
            particles.removeIf(p -> !p.isActiveIndicator());
            particles.addAll(newParticles);
        }

        return completedTimelines;
    }

}

