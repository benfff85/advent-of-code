package com.adventofcode.year2024.day12;

import java.awt.Point;
import java.util.*;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;
import com.adventofcode.common.DailyAnswer;
import com.adventofcode.common.SolutionController;
import com.adventofcode.common.grid.GridUtility;
import com.adventofcode.common.grid.SurroundingType;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component("controller-2024-12")
public class Controller extends SolutionController {

    public DailyAnswer execute() {

        Map<Point, GridElement> grid = GridUtility.constructGrid(puzzleInput, GridElement.class);

        Set<Region> regions = findRegions(grid);

        answer.setPart1(regions.stream().mapToInt(Region::getPrice).sum());
        log.info("Part 1: {}", answer.getPart1());

        answer.setPart2(regions.stream().mapToInt(Region::getBulkPrice).sum());
        log.info("Part 2: {}", answer.getPart2());

        return answer;
    }

    private Set<Region> findRegions(Map<Point, GridElement> grid) {

        Set<Point> processedPoints = new HashSet<>();
        Set<Region> regions = new HashSet<>();
        for (Map.Entry<Point, GridElement> entry : grid.entrySet()) {

            // If point has already been mapped to a region skip to the next
            if (processedPoints.contains(entry.getKey())) {
                continue;
            }

            HashSet<Point> regionPoints = new HashSet<>();
            Set<Point> surroundingPointsOfSameValueNotAlreadyInRegion = new HashSet<>();
            surroundingPointsOfSameValueNotAlreadyInRegion.add(entry.getKey());

            while (!surroundingPointsOfSameValueNotAlreadyInRegion.isEmpty()) {

                regionPoints.addAll(surroundingPointsOfSameValueNotAlreadyInRegion);
                surroundingPointsOfSameValueNotAlreadyInRegion.clear();
                for (Point point : regionPoints) {

                    surroundingPointsOfSameValueNotAlreadyInRegion.addAll(GridUtility.getSurroundingElements(grid, point, SurroundingType.CARDINAL)
                            .entrySet()
                            .stream()
                            .filter(e -> e.getValue().equals(entry.getValue()))
                            .filter(e -> !regionPoints.contains(e.getKey()))
                            .map(Map.Entry::getKey)
                            .collect(Collectors.toSet()));

                }

            }

            Region region = new Region(regionPoints, entry.getValue().toString());
            regions.add(region);
            processedPoints.addAll(region.getPoints());
        }

        return regions;
    }


}
