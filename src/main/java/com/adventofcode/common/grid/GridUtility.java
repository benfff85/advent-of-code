package com.adventofcode.common.grid;

import static com.adventofcode.common.grid.PointUtil.getAdjacentPoint;
import static java.util.Collections.emptyMap;
import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;
import java.awt.Point;
import java.util.*;
import java.util.function.Predicate;
import java.util.stream.*;
import org.apache.commons.math3.util.Pair;
import com.adventofcode.common.AdventOfCodeException;

public class GridUtility {

    private GridUtility() {
        // Masking default constructor
    }

    public static <T extends PrintableGridElement> String print(Map<Point, T> grid) {
        return print(grid, emptyMap(), SimplePrintableGridElement.of(" "));
    }

    // Prints the grid starting with MinX,MinY in the top left
    public static <T extends PrintableGridElement> String printVerticallyMirrored(Map<Point, T> grid, Map<Point, T> overrides, PrintableGridElement defaultElement) {

        int minX = getMinX(grid);
        int maxX = getMaxX(grid);
        int minY = getMinY(grid);
        int maxY = getMaxY(grid);

        Point point;
        StringBuilder sb = new StringBuilder().append("\n\n");
        PrintableGridElement element;
        for (int i = minY; i <= maxY; i++) {
            for (int j = minX; j <= maxX; j++) {
                point = new Point(j, i);
                element = overrides.getOrDefault(point, grid.getOrDefault(point, null));
                // Above catches if point is missing from grid, below catches if point is present but has a null value
                if (element == null) {
                    element = defaultElement;
                }
                sb.append(element.print());
            }
            sb.append("\n");
        }
        return sb.toString();
    }


    // Prints the grid starting with MinX,MaxY in the top left
    public static <T extends PrintableGridElement> String print(Map<Point, T> grid, Map<Point, T> overrides, PrintableGridElement defaultElement) {

        int minX = getMinX(grid);
        int maxX = getMaxX(grid);
        int minY = getMinY(grid);
        int maxY = getMaxY(grid);

        Point point;
        StringBuilder sb = new StringBuilder().append("\n\n");
        PrintableGridElement element;
        for (int i = maxY; i >= minY; i--) {
            for (int j = minX; j <= maxX; j++) {
                point = new Point(j, i);
                element = overrides.getOrDefault(point, grid.getOrDefault(point, null));
                // Above catches if point is missing from grid, below catches if point is present but has a null value
                if (element == null) {
                    element = defaultElement;
                }
                sb.append(element.print());
            }
            sb.append("\n");
        }
        return sb.toString();
    }

    public static <T> Map<Point, T> getSurroundingElements(Map<Point, T> grid, Point point, SurroundingType surroundingType) {

        return PointUtil.getSurroundingPoints(point, surroundingType)
                .stream()
                .map(p -> Pair.create(p, grid.get(p)))
                .filter(e -> nonNull(e.getValue()))
                .collect(Collectors.toMap(Pair::getKey, Pair::getValue));

    }

    public static <T> Map<Point, T> getAllSurroundingElementsExcludingProvided(Map<Point, T> grid, List<Point> points, SurroundingType surroundingType) {
        return getAllSurroundingElements(grid, points, surroundingType, false);
    }

    public static <T> Map<Point, T> getAllSurroundingElementsIncludingProvided(Map<Point, T> grid, List<Point> points, SurroundingType surroundingType) {
        return getAllSurroundingElements(grid, points, surroundingType, true);
    }

    private static <T> Map<Point, T> getAllSurroundingElements(Map<Point, T> grid, List<Point> points, SurroundingType surroundingType, boolean shouldIncludeProvided) {

        return points
                .stream()
                .flatMap(point -> getSurroundingElements(grid, point, surroundingType).entrySet().stream())
                .filter(entry -> shouldIncludeProvided || !points.contains(entry.getKey()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (a, b) -> b));
    }

    /**
     * Returns all elements in a specified direction from the specific point. Path terminates when point is not in grid or when element of point is null. Specific element will be first in the list.
     */
    public static <T> List<Map.Entry<Point, T>> getAdjacentElements(Map<Point, T> grid, Point point, Direction direction) {
        return getAdjacentElements(grid, point, direction, Integer.MAX_VALUE);
    }

    /**
     * Returns all elements in a specified direction from the specific point. Path terminates when point is not in grid or when element of point is null or when maxElementCount is reached. Specific element will be first in the list.
     */
    public static <T> List<Map.Entry<Point, T>> getAdjacentElements(Map<Point, T> grid, Point point, Direction direction, Integer maxElementCount) {

        List<Map.Entry<Point, T>> elementList = new ArrayList<>();

        Point trackingPoint = point;
        T trackingElement = grid.get(point);
        int count = 1;

        while (nonNull(trackingElement) && count <= maxElementCount) {
            elementList.add(Map.entry(trackingPoint, trackingElement));
            trackingPoint = getAdjacentPoint(trackingPoint, direction);
            trackingElement = grid.get(trackingPoint);
            count++;
        }

        return elementList;

    }

    /**
     * Returns a map of matched input patterns with the map consisting of the anchor point and then having a value of the matched pattern map. Input patters are in the form of a grid anchored at 0,0 with other values being relative to the anchor.
     */
    public static <T> Map<Point, Map<Point, T>> getPatternMatches(Map<Point, T> grid, Map<Point, T> pattern) {

        Map<Point, Map<Point, T>> matchedPatternMaps = new HashMap<>();
        Map<Point, T> matchedPatternMap = new HashMap<>();

        // For every point in the grid check if there is a matching pattern anchored here
        for (Point point : grid.keySet()) {

            boolean isMatch = true;
            for (Map.Entry<Point, T> patternElement : pattern.entrySet()) {
                Point gridPoint = new Point(point.x + patternElement.getKey().x, point.y + patternElement.getKey().y);
                if (!patternElement.getValue().equals(grid.get(gridPoint))) {
                    isMatch = false;
                    break;
                }
                matchedPatternMap.put(gridPoint, grid.get(gridPoint));
            }

            if (isMatch) {
                matchedPatternMaps.put(point, new HashMap<>(matchedPatternMap));
            }

            matchedPatternMap.clear();

        }

        return matchedPatternMaps;

    }

    /**
     * Rotates a grid clockwise with the top right quadrant moving to the bottom right and so on.
     *
     * @param grid the grid to rotate
     */
    public static <T> void rotateGridClockwise(Map<Point, T> grid) {
        Map<Point, T> pointsToMove = new HashMap<>(grid);
        pointsToMove.forEach((key, value) -> grid.remove(key));
        pointsToMove.forEach((key, value) -> grid.put(new Point(key.y, key.x * -1), value));
    }

    /**
     * Get the map entry (point and element) directly adjacent to the given point in the specified direction. Returns null if key not present.
     */
    public static <T> Map.Entry<Point, T> getAdjacentElement(Map<Point, T> grid, Point point, Direction direction) {
        try {
            Point targetPoint = getAdjacentPoint(point, direction);
            return Map.entry(targetPoint, grid.get(targetPoint));
        } catch (Exception e) {
            // Ignore
        }
        return null;
    }

    public static <T> Map<Point, T> getElementsOnRow(Map<Point, T> grid, Integer y) {
        return grid.entrySet().stream().filter(p -> p.getKey().y == y).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    public static <T> void addRow(Map<Point, T> grid, int y, int size, T gridElement) {
        shiftRows(grid, y, 1);
        IntStream.rangeClosed(0, size).forEach(x -> grid.put(new Point(x, y), gridElement));
    }

    /**
     * For a grid with (0,0) in the bottom left this will shift rows up
     */
    public static <T> void shiftRows(Map<Point, T> grid, int y, int shiftMagnitude) {
        Map<Point, T> pointsToMove = getAllElementsInDirectionRelativeToPoint(grid, new Point(0, y), Direction.U);
        pointsToMove.forEach((key, value) -> grid.remove(key));
        pointsToMove.forEach((key, value) -> grid.put(new Point(key.x, key.y + shiftMagnitude), value));
    }

    public static <T> Map<Point, T> getElementsInColumn(Map<Point, T> grid, Integer x) {
        return grid.entrySet().stream().filter(p -> p.getKey().x == x).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    public static <T> void addColumn(Map<Point, T> grid, int x, int size, T gridElement) {
        shiftColumns(grid, x, 1);
        IntStream.rangeClosed(0, size).forEach(y -> grid.put(new Point(x, y), gridElement));
    }

    /**
     * For a grid with (0,0) in the bottom left this will shift columns to the right
     */
    public static <T> void shiftColumns(Map<Point, T> grid, int x, int shiftMagnitude) {
        Map<Point, T> pointsToMove = getAllElementsInDirectionRelativeToPoint(grid, new Point(x, 0), Direction.R);
        pointsToMove.forEach((key, value) -> grid.remove(key));
        pointsToMove.forEach((key, value) -> grid.put(new Point(key.x + shiftMagnitude, key.y), value));
    }

    /**
     * Returns all elements in a specified direction from a specific point, including the point itself. This is not bound to a row or column but will consider the full grid.
     *
     * @param grid
     * @param point
     * @param direction
     * @return
     */
    public static <T> Map<Point, T> getAllElementsInDirectionRelativeToPoint(Map<Point, T> grid, Point point, Direction direction) {
        return switch (direction) {
            case U -> grid.entrySet().stream().filter(e -> e.getKey().y >= point.y).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
            case D -> grid.entrySet().stream().filter(e -> e.getKey().y <= point.y).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
            case L -> grid.entrySet().stream().filter(e -> e.getKey().x <= point.x).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
            case R -> grid.entrySet().stream().filter(e -> e.getKey().x >= point.x).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
            default -> throw new AdventOfCodeException("Invalid direction");
        };
    }

    public static <T> Long countValues(Map<Point, T> grid, T value) {
        return grid.values().stream().filter(v -> v.equals(value)).count();
    }

    public static <T> Integer getMinX(Map<Point, T> grid) {
        return grid.keySet().stream().mapToInt(p -> p.x).min().orElseThrow();
    }

    public static <T> Integer getMaxX(Map<Point, T> grid) {
        return grid.keySet().stream().mapToInt(p -> p.x).max().orElseThrow();
    }

    public static <T> Integer getMinY(Map<Point, T> grid) {
        return grid.keySet().stream().mapToInt(p -> p.y).min().orElseThrow();
    }

    public static <T> Integer getMaxY(Map<Point, T> grid) {
        return grid.keySet().stream().mapToInt(p -> p.y).max().orElseThrow();
    }

    public static <T> Integer getMaxY(Map<Point, T> grid, Predicate<Map.Entry<Point, T>> predicate, Integer defaultResult) {
        return grid.entrySet().stream().filter(predicate).mapToInt(e -> e.getKey().y).max().orElse(defaultResult);
    }

    public static <T> Map<Point, T> getElementsByValue(Map<Point, T> grid, T input) {
        return getElementStreamByValue(grid, input).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    public static <T> Map.Entry<Point, T> getFirstElementByValue(Map<Point, T> grid, T input) {
        return getElementStreamByValue(grid, input).findFirst().orElseThrow();
    }

    private static <T> Stream<Map.Entry<Point, T>> getElementStreamByValue(Map<Point, T> grid, T input) {
        return grid.entrySet().stream().filter(e -> e.getValue().equals(input));
    }


    /**
     * Constructs a square Grid, with (0,0) in the bottom, all elements must be specified in the elementClass but only the elements on the includeElements list will be included in the grid
     *
     * @param input input List of Strings, usually puzzle input
     * @param elementClass the class of the enum containing the elements and string values, for example GridElement.class
     * @param includeElements list of elements from the input to include in the grid, for example List.of(GridElement.ROCK)
     * @return A grid with elements of the specified type
     */
    public static <T extends ConstructableGridElement<T>> Map<Point, T> constructGridWithSelectElements(List<String> input, Class<T> elementClass, List<T> includeElements) {
        return constructGrid(input, elementClass, includeElements, true);
    }

    /**
     * Constructs a square Grid, with (0,0) in the bottom, all elements must be specified in the elementClass but only the elements not on the excludedElements list will be included in the grid
     *
     * @param input input List of Strings, usually puzzle input
     * @param elementClass the class of the enum containing the elements and string values, for example GridElement.class
     * @param excludeElements list of elements from the input to not include in the grid, for example List.of(GridElement.ROCK)
     * @return A grid with elements of the specified type
     */
    public static <T extends ConstructableGridElement<T>> Map<Point, T> constructGridWithoutSelectElements(List<String> input, Class<T> elementClass, List<T> excludeElements) {
        return constructGrid(input, elementClass, excludeElements, false);
    }

    /**
     * Constructs a square Grid, with (0,0) in the bottom left
     *
     * @param input input List of Strings, usually puzzle input
     * @param elementClass the class of the enum containing the elements and string values, for example GridElement.class
     * @return A grid with elements of the specified type
     */
    public static <T extends ConstructableGridElement<T>> Map<Point, T> constructGrid(List<String> input, Class<T> elementClass) {
        return constructGrid(input, elementClass, List.of(elementClass.getEnumConstants()), true);
    }

    /**
     * Constructs a square Grid, with (0,0) in the bottom left
     *
     * @param input input List of Strings, usually puzzle input
     * @param elementClass the class of the enum containing the elements and string values, for example GridElement.class
     * @param elementList list of elements from the input to include or exclude in the grid, for example List.of(GridElement.ROCK)
     * @param shouldInclude if true, only elements in the elementList will be included in the grid, if false, only elements not in the elementList will be included in the grid
     * @return A grid with elements of the specified type
     */
    private static <T extends ConstructableGridElement<T>> Map<Point, T> constructGrid(List<String> input, Class<T> elementClass, List<T> elementList, boolean shouldInclude) {
        Map<Point, T> grid = new HashMap<>();
        String[] row;
        T gridElement;
        for (int y = input.size() - 1; y >= 0; y--) {
            row = input.get(input.size() - (y + 1)).split("");
            for (int x = 0; x < input.getFirst().length(); x++) {
                gridElement = ConstructableGridElement.createFromString(elementClass, row[x]);
                if ((shouldInclude && elementList.contains(gridElement)) || (!shouldInclude && !elementList.contains(gridElement))) {
                    grid.put(new Point(x, y), gridElement);
                }
            }
        }
        return grid;
    }

    /**
     * Slides an element in a specified direction until it encounters another element, or until it reaches the cutOff point
     *
     * @param grid the grid to modify
     * @param point the point of the element to slide
     * @param direction the direction to slide the element
     * @param cutOff the value to stop sliding at
     * @return
     */
    public static <T> Map<Point, T> slideElementUntilEncounteringElement(Map<Point, T> grid, Point point, Direction direction, Integer cutOff) {

        Point targetPoint = point;
        Point testPoint = getAdjacentPoint(targetPoint, direction);
        Map<Point, T> coveredSpaces = new HashMap<>();
        while (isNull(getAdjacentElement(grid, targetPoint, direction))) {
            if ((Direction.U.equals(direction) && testPoint.y > cutOff) ||
                    (Direction.D.equals(direction) && testPoint.y < cutOff) ||
                    (Direction.L.equals(direction) && testPoint.x < cutOff) ||
                    (Direction.R.equals(direction) && testPoint.x > cutOff)) {
                break;
            }
            targetPoint = testPoint;
            coveredSpaces.put(targetPoint, grid.get(targetPoint));
            testPoint = getAdjacentPoint(targetPoint, direction);
        }

        if (point.equals(targetPoint)) {
            return coveredSpaces;
        }

        T element = grid.get(point);
        grid.remove(point);
        grid.put(targetPoint, element);

        return coveredSpaces;
    }
}
