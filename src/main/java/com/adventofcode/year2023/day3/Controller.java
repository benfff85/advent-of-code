package com.adventofcode.year2023.day3;

import java.awt.Point;
import java.util.*;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import com.adventofcode.common.DailyAnswer;
import com.adventofcode.common.SolutionController;
import com.adventofcode.common.grid.*;
import lombok.extern.slf4j.Slf4j;


@Slf4j
@Component("controller-2023-3")
public class Controller extends SolutionController {

    private final Map<Point, PrintableGridElement> grid = new HashMap<>();

    public DailyAnswer execute() {

        initializeGrid();

        List<Part> parts = identifyParts();

        parts.forEach(part -> part.addSurrounding(GridUtility.getAllSurroundingElementsExcludingProvided(grid, part.getPoints(), SurroundingType.ALL)));

        answer.setPart1(parts.parallelStream().filter(Part::isValidPart).mapToInt(Part::calculatePartNumber).sum());
        log.info("Part 1: {}", answer.getPart1());


        LinkedMultiValueMap<Point, Integer> gearMap = new LinkedMultiValueMap<>();
        parts.stream()
                .flatMap(part -> part.getAdjacentSymbols().entrySet().stream()
                        .filter(entry -> entry.getValue().print().equals("*"))
                        .map(entry -> Pair.of(entry.getKey(), part.calculatePartNumber())))
                .forEach(entry -> gearMap.add(entry.getLeft(), entry.getRight()));

        int gearSum = gearMap.values().parallelStream()
                .filter(entry -> entry.size() == 2)
                .mapToInt(entry -> entry.parallelStream().reduce(1, (a, b) -> a * b))
                .sum();

        answer.setPart2(gearSum);
        log.info("Part 2: {}", answer.getPart2());

        return answer;
    }


    private void initializeGrid() {
        for (int i = 0; i < puzzleInput.size(); i++) {
            String row = puzzleInput.get(i);
            for (int j = 0; j < row.length(); j++) {
                grid.put(new Point(j, i), SimplePrintableGridElement.of(row.charAt(j) + ""));
            }
        }
    }

    private List<Part> identifyParts() {

        Integer maxY = GridUtility.getMaxY(grid);
        Integer maxX = GridUtility.getMaxX(grid);

        List<Part> parts = new LinkedList<>();
        Part part = null;
        for (int y = 0; y <= maxY; y++) {

            boolean isOngoingPartNumber = false;
            for (int x = 0; x <= maxX; x++) {

                Point point = new Point(x, y);
                PrintableGridElement gridElement = grid.get(point);

                if (StringUtils.isNumeric(gridElement.print())) {
                    if (!isOngoingPartNumber) {
                        part = new Part();
                        part.addElement(point, Integer.parseInt(gridElement.print()));
                        parts.add(part);
                        isOngoingPartNumber = true;
                    } else if (part != null) {
                        part.addElement(point, Integer.parseInt(gridElement.print()));
                    }

                } else {
                    isOngoingPartNumber = false;
                }
            }
        }
        return parts;
    }

}
