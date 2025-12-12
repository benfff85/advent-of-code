package com.adventofcode.year2025.day11;

import java.util.*;
import org.springframework.stereotype.Component;
import com.adventofcode.common.DailyAnswer;
import com.adventofcode.common.SolutionController;
import lombok.extern.slf4j.Slf4j;


@Slf4j
@Component("controller-2025-11")
public class Controller extends SolutionController {

    public DailyAnswer execute() {
        Map<String, Device> devices = parseInput();
        Device start = devices.get("you");
        Device end = devices.get("out");

        if (start == null || end == null) {
            log.error("Start or end device not found!");
            return answer;
        }

        long pathCount = countPaths(start, end, new HashSet<>());

        answer.setPart1(pathCount);
        log.info("Part 1: {}", answer.getPart1());

        answer.setPart2(null);
        log.info("Part 2: {}", answer.getPart2());

        return answer;
    }

    private long countPaths(Device current, Device end, Set<Device> visited) {
        if (current.equals(end)) {
            return 1;
        }

        visited.add(current);
        long paths = 0;
        for (Device next : current.getOutputs()) {
            if (!visited.contains(next)) {
                paths += countPaths(next, end, visited);
            }
        }
        visited.remove(current);
        return paths;
    }

    private Map<String, Device> parseInput() {
        Map<String, Device> devices = new HashMap<>();

        for (String line : puzzleInput) {
            String[] parts = line.split(": ");
            String name = parts[0];
            devices.putIfAbsent(name, new Device(name));
            Device device = devices.get(name);

            if (parts.length > 1) {
                String[] outputNames = parts[1].split(" ");
                for (String outputName : outputNames) {
                    devices.putIfAbsent(outputName, new Device(outputName));
                    device.getOutputs().add(devices.get(outputName));
                }
            }
        }
        return devices;
    }
}
