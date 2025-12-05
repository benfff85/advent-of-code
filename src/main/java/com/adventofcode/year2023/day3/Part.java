package com.adventofcode.year2023.day3;

import java.awt.Point;
import java.util.*;
import java.util.stream.Collectors;
import com.adventofcode.common.grid.PrintableGridElement;
import lombok.Data;

@Data
public class Part {

    private final Set<String> symbolSet = Set.of("+", "*", "#", "$", "%", "/", "@", "-", "=", "&");

    private List<Point> points = new LinkedList<>();
    private List<Integer> values = new LinkedList<>();

    private Map<Point, PrintableGridElement> adjacentSymbols;


    public void addElement(Point point, int value) {
        points.add(point);
        values.add(value);
    }

    public int calculatePartNumber() {
        int number = 0;
        int multiplier = 1;
        for (int i = values.size() - 1; i >= 0; i--) {
            number += values.get(i) * multiplier;
            multiplier *= 10;
        }
        return number;
    }

    public void addSurrounding(Map<Point, PrintableGridElement> surroundingElements) {
        this.adjacentSymbols = surroundingElements.entrySet().parallelStream()
                .filter(entry -> symbolSet.contains(entry.getValue().print()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    public boolean isValidPart() {
        return !adjacentSymbols.isEmpty();
    }

}
