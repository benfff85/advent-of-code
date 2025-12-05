package com.adventofcode.year2022.day19;

import java.util.EnumMap;
import java.util.Map;
import lombok.Data;

@Data
public class Blueprint {

    private Map<RobotType, Map<RobotType, Integer>> robotManifests;

    public Blueprint(String input) {
        String[] inputArray = input.split(" ");
        robotManifests = new EnumMap<>(RobotType.class);
        robotManifests.put(RobotType.ORE, Map.of(RobotType.ORE, Integer.parseInt(inputArray[6])));
        robotManifests.put(RobotType.CLAY, Map.of(RobotType.ORE, Integer.parseInt(inputArray[12])));
        robotManifests.put(RobotType.OBSIDIAN, Map.of(RobotType.ORE, Integer.parseInt(inputArray[18]), RobotType.CLAY, Integer.parseInt(inputArray[21])));
        robotManifests.put(RobotType.GEODE, Map.of(RobotType.ORE, Integer.parseInt(inputArray[27]), RobotType.OBSIDIAN, Integer.parseInt(inputArray[30])));

    }

}
