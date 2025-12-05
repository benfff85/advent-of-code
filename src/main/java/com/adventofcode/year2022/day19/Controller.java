package com.adventofcode.year2022.day19;

import static java.util.Objects.nonNull;
import java.util.*;
import java.util.stream.IntStream;
import org.apache.commons.collections4.map.LRUMap;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;
import com.adventofcode.common.*;
import lombok.extern.slf4j.Slf4j;


@Slf4j
@Component("controller-2022-19")
public class Controller extends SolutionController {

    private final List<Blueprint> blueprints = puzzleInput.stream().map(Blueprint::new).toList();
    private Blueprint activeBlueprint;
    private int maxGeode;
    private Map<CacheKey, Integer> cache;
    private final List<Integer> maxPossibleGeodesFromYetToBeCreatedRobots = new ArrayList<>();
    private Integer cacheCutoff;
    private Integer stepCount;

    // TODO Remove constructor
    public Controller(InputHelper inputHelper) {
        super(inputHelper, "puzzle-input/2022/day-19.txt");

        for (int i = 0; i < 32; i++) {
            maxPossibleGeodesFromYetToBeCreatedRobots.add(IntStream.range(0, i).sum());
        }
    }


    public DailyAnswer execute() {

        solvePart1();
        solvePart2();
        return answer;
    }

    private void solvePart1() {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();

        Map<RobotType, Integer> activeRobots;
        Map<RobotType, Integer> resourceInventory;
        cacheCutoff = 19;
        stepCount = 24;
        int totalQualityLevel = 0;

        log.info("Beginning processing");
        for (int i = 0; i < blueprints.size(); i++) {
            activeBlueprint = blueprints.get(i);
            maxGeode = 0;
            activeRobots = new EnumMap<>(Map.of(RobotType.ORE, 1, RobotType.CLAY, 0, RobotType.OBSIDIAN, 0, RobotType.GEODE, 0));
            resourceInventory = new EnumMap<>(Map.of(RobotType.ORE, 0, RobotType.CLAY, 0, RobotType.OBSIDIAN, 0, RobotType.GEODE, 0));
            cache = new LRUMap<>(2500000);
            processStep(1, activeRobots, resourceInventory, null);
            totalQualityLevel += (maxGeode * (i + 1));
            log.info("Blueprint {}, Max {}", i + 1, maxGeode);
        }

        answer.setPart1(totalQualityLevel);
        log.info("P1: {}", answer.getPart1());
        stopWatch.stop();
        log.info("Time taken: {}s", stopWatch.getTotalTimeSeconds());
    }

    private void solvePart2() {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();

        Map<RobotType, Integer> activeRobots;
        Map<RobotType, Integer> resourceInventory;
        cacheCutoff = 23;
        stepCount = 32;
        int totalQualityLevel = 1;

        log.info("Beginning processing");
        for (int i = 0; i < 3; i++) {
            activeBlueprint = blueprints.get(i);
            maxGeode = 0;
            activeRobots = new EnumMap<>(Map.of(RobotType.ORE, 1, RobotType.CLAY, 0, RobotType.OBSIDIAN, 0, RobotType.GEODE, 0));
            resourceInventory = new EnumMap<>(Map.of(RobotType.ORE, 0, RobotType.CLAY, 0, RobotType.OBSIDIAN, 0, RobotType.GEODE, 0));
            cache = new LRUMap<>(2500000);
            processStep(1, activeRobots, resourceInventory, null);
            totalQualityLevel *= maxGeode;
            log.info("Blueprint {}, Max {}", i + 1, maxGeode);
        }

        answer.setPart2(totalQualityLevel);
        log.info("P2: {}", answer.getPart2());
        stopWatch.stop();
        log.info("Time taken: {}s", stopWatch.getTotalTimeSeconds());
    }


    private void processStep(int step, Map<RobotType, Integer> activeRobots, Map<RobotType, Integer> resourceInventory, RobotType robotTypeToBuild) {

        CacheKey cacheKey = null;
        if (step <= cacheCutoff) {
            cacheKey = new CacheKey(step, activeRobots, resourceInventory, robotTypeToBuild);
            if (cache.containsKey(cacheKey)) {
                return;
            }
        }


        // Try to build robot, if unable to afford just return
        if (nonNull(robotTypeToBuild) && (!purchaseRobot(resourceInventory, robotTypeToBuild))) {
            return;
        }

        // Allow robots to generate resources
        processResourceCreation(activeRobots, resourceInventory);

        // If we hit last step check if new max and return
        if (step == stepCount) {
            maxGeode = Math.max(maxGeode, resourceInventory.get(RobotType.GEODE));
            return;
        }

        // Add newly build robot to inventory
        if (nonNull(robotTypeToBuild)) {
            activeRobots.put(robotTypeToBuild, activeRobots.get(robotTypeToBuild) + 1);
        }

        if (earlyTermination(step, activeRobots.get(RobotType.GEODE), resourceInventory.get(RobotType.GEODE), maxGeode)) {
            return;
        }

        // Otherwise check possible next steps
        processStep(step + 1, new EnumMap<>(activeRobots), new EnumMap<>(resourceInventory), RobotType.GEODE);
        processStep(step + 1, new EnumMap<>(activeRobots), new EnumMap<>(resourceInventory), RobotType.OBSIDIAN);
        processStep(step + 1, new EnumMap<>(activeRobots), new EnumMap<>(resourceInventory), RobotType.CLAY);
        processStep(step + 1, new EnumMap<>(activeRobots), new EnumMap<>(resourceInventory), RobotType.ORE);
        processStep(step + 1, new EnumMap<>(activeRobots), new EnumMap<>(resourceInventory), null);

        if (nonNull(cacheKey)) {
            cache.put(cacheKey, 0);
        }

    }

    private boolean earlyTermination(int step, Integer geodeRobotCount, Integer currentGeodes, int maxGeode) {
        if (maxGeode == 0) {
            return false;
        }

        int remainingSteps = (stepCount - step);
        int maxPossibleGeodes = maxPossibleGeodesFromYetToBeCreatedRobots.get(remainingSteps) + // Geodes produced by yet-to-be-created robots
                currentGeodes + // Current geodes
                (geodeRobotCount * remainingSteps); // Geodes produced by existing robots
        return maxPossibleGeodes <= maxGeode;
    }

    private boolean purchaseRobot(Map<RobotType, Integer> resourceInventory, RobotType robotTypeToBuild) {
        Map<RobotType, Integer> robotTypeManifest = activeBlueprint.getRobotManifests().get(robotTypeToBuild);
        for (Map.Entry<RobotType, Integer> requirement : robotTypeManifest.entrySet()) {
            if (requirement.getValue() > resourceInventory.get(requirement.getKey())) {
                return false;
            }
            resourceInventory.put(requirement.getKey(), resourceInventory.get(requirement.getKey()) - requirement.getValue());
        }
        return true;
    }

    private void processResourceCreation(Map<RobotType, Integer> activeRobots, Map<RobotType, Integer> resourceInventory) {
        resourceInventory.replaceAll((t, v) -> resourceInventory.get(t) + activeRobots.get(t));
    }

}
