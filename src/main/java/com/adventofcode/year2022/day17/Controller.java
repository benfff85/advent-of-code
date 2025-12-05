package com.adventofcode.year2022.day17;

import static com.adventofcode.year2022.day17.RockType.*;
import java.awt.Point;
import java.util.*;
import java.util.stream.Collectors;
import org.apache.commons.collections4.IteratorUtils;
import org.apache.commons.collections4.ResettableListIterator;
import org.springframework.stereotype.Component;
import com.adventofcode.common.DailyAnswer;
import com.adventofcode.common.SolutionController;
import com.adventofcode.common.grid.Direction;
import com.adventofcode.common.grid.GridUtility;
import lombok.extern.slf4j.Slf4j;


@Slf4j
@Component("controller-2022-17")
public class Controller extends SolutionController {

    public DailyAnswer execute() {
        solvePart1();
        solvePart2();
        return answer;
    }

    private void solvePart1() {
        ResettableListIterator<Direction> directions = IteratorUtils.loopingListIterator(Arrays.stream(puzzleInput.get(0).split("")).map(DirectionMapper::getDirection).toList());
        ResettableListIterator<RockType> rockTypes = IteratorUtils.loopingListIterator(List.of(DASH, PLUS, ANGLE, LINE, SQUARE));

        Map<Point, GridElement> grid = new HashMap<>();
        initBottomWall(grid);
        addSideWalls(grid);

        Rock rock;
        // Loop through rocks
        for (int i = 0; i < 2022; i++) {
            addSideWalls(grid);
            rock = new Rock(findHeightOfRocks(grid), rockTypes.next());

            do {
                rock.move(directions.next(), grid);
            } while (rock.move(Direction.D, grid));

            grid.putAll(rock.getPoints().stream().collect(Collectors.toMap(p -> p, p -> GridElement.ROCK)));
        }

        answer.setPart1(findHeightOfRocks(grid));
        log.info("P1: {}", answer.getPart1());
    }


    private void initBottomWall(Map<Point, GridElement> grid) {
        for (int i = 0; i < 9; i++) {
            grid.put(new Point(i, 0), GridElement.WALL);
        }
    }

    private void addSideWalls(Map<Point, GridElement> grid) {
        Integer wallHeight = findHeightOfWalls(grid);
        int rockHeight = findHeightOfRocks(grid);

        for (int i = wallHeight; i <= rockHeight + 10; i++) {
            grid.put(new Point(0, i), GridElement.WALL);
            grid.put(new Point(8, i), GridElement.WALL);
        }

    }

    private Integer findHeightOfWalls(Map<Point, GridElement> grid) {
        return GridUtility.getAdjacentElements(grid, new Point(0, 0), Direction.U).size();
    }

    private Integer findHeightOfRocks(Map<Point, GridElement> grid) {
        return GridUtility.getMaxY(grid, gridEntry -> gridEntry.getValue().equals(GridElement.ROCK), 0);
    }

    private void solvePart2() {
        ResettableListIterator<Direction> directions = IteratorUtils.loopingListIterator(Arrays.stream(puzzleInput.get(0).split("")).map(DirectionMapper::getDirection).toList());
        ResettableListIterator<RockType> rockTypes = IteratorUtils.loopingListIterator(List.of(DASH, PLUS, ANGLE, LINE, SQUARE));

        Map<Point, GridElement> grid = new HashMap<>();
        initBottomWall(grid);
        addSideWalls(grid);

        Map<RockType, Map<List<Integer>, Map<List<Direction>, CacheResult>>> cache = new EnumMap<>(RockType.class);

        Rock rock;
        Direction direction;
        // Loop through rocks
        // TODO systemically identify cycle and calculate result.
        for (int i = 0; i < 1875; i++) {
            addSideWalls(grid);
            rock = new Rock(findHeightOfRocks(grid), rockTypes.next());
            List<Integer> preActionTopology = constructTopology(grid);
            List<Direction> gustDirections = new ArrayList<>();
            do {
                direction = directions.next();
                rock.move(direction, grid);
                gustDirections.add(direction);
            } while (rock.move(Direction.D, grid));

            grid.putAll(rock.getPoints().stream().collect(Collectors.toMap(p -> p, p -> GridElement.ROCK)));
            List<Integer> postActionTopology = constructTopology(grid);

            addToCache(cache, rock.getRockType(), preActionTopology, gustDirections, postActionTopology, i);
        }

        answer.setPart2(findHeightOfRocks(grid));
        log.info("P2: {}", answer.getPart2());
    }

    private void addToCache(Map<RockType, Map<List<Integer>, Map<List<Direction>, CacheResult>>> cache, RockType rockType, List<Integer> initialTopology, List<Direction> gustDirections, List<Integer> postActionTopology, int i) {

        // Add Rock Type
        cache.putIfAbsent(rockType, new HashMap<>());

        // Add topology
        cache.get(rockType).putIfAbsent(initialTopology, new HashMap<>());

        // If gusts already exist confirm the target topology is the same, if gusts don't yet exist in cache add them along with the target topology
        if (cache.get(rockType).get(initialTopology).containsKey(gustDirections)) {
            log.info("Original rock index: {}", cache.get(rockType).get(initialTopology).get(gustDirections).getIndexOfOriginalRock());
            if (!cache.get(rockType).get(initialTopology).get(gustDirections).getTopology().equals(postActionTopology)) {
                log.error("ERROR, found in cache but differing target topology");
            }
        } else {
            log.info("Cache miss for rock index {}", i);
            cache.get(rockType).get(initialTopology).putIfAbsent(gustDirections, CacheResult.builder().topology(postActionTopology).indexOfOriginalRock(i).build());
        }

    }

    private List<Integer> constructTopology(Map<Point, GridElement> grid) {
        // Get Topology from Grid as Integer List
        List<Integer> topology = new ArrayList<>(7);
        int maxY = findHeightOfRocks(grid);
        for (int i = 1; i <= 7; i++) {
            int finalI = i;
            topology.add(maxY - GridUtility.getMaxY(grid, gridEntry -> gridEntry.getValue().equals(GridElement.ROCK) && gridEntry.getKey().x == finalI, 0));
        }
        return topology;
    }


}
