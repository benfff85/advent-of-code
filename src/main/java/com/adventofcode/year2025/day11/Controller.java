package com.adventofcode.year2025.day11;

import java.util.*;
import org.springframework.stereotype.Component;
import com.adventofcode.common.DailyAnswer;
import com.adventofcode.common.SolutionController;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component("controller-2025-11")
public class Controller extends SolutionController {

    // Memoization cache for part 2
    // Key format: "deviceName-visitedDac-visitedFft"
    private Map<String, Long> memoCache;

    public DailyAnswer execute() {
        Map<String, Device> devices = parseInput();

        // Part 1: Count paths from "you" to "out"
        Device start = devices.get("you");
        Device end = devices.get("out");

        if (start != null && end != null) {
            long pathCount = countPaths(start, end, new HashSet<>());
            answer.setPart1(pathCount);
            log.info("Part 1: {}", answer.getPart1());
        } else {
            log.warn("Part 1: Start or end device not found!");
        }

        // Part 2: Count paths from "svr" to "out" that visit both "dac" and "fft"
        Device start2 = devices.get("svr");
        Device dac = devices.get("dac");
        Device fft = devices.get("fft");
        Device end2 = devices.get("out");

        if (start2 != null && end2 != null && dac != null && fft != null) {
            memoCache = new HashMap<>();
            long pathCountPart2 = countPathsVisitingBoth(start2, end2, dac, fft, new HashSet<>());
            answer.setPart2(pathCountPart2);
            log.info("Part 2: {} (cache size: {})", answer.getPart2(), memoCache.size());
        } else {
            log.warn("Part 2: Required devices not found!");
        }

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

    private long countPathsVisitingBoth(Device current, Device end, Device dac, Device fft,
            Set<Device> visited) {
        return countPathsVisitingBothHelper(current, end, dac, fft, visited, false, false);
    }

    private long countPathsVisitingBothHelper(Device current, Device end, Device dac, Device fft,
            Set<Device> visited, boolean visitedDac, boolean visitedFft) {
        // Check if we've reached the end
        if (current.equals(end)) {
            // Only count this path if we've visited both dac and fft
            return (visitedDac && visitedFft) ? 1 : 0;
        }

        // Create cache key based on current state
        String cacheKey = current.getName() + "-" + visitedDac + "-" + visitedFft;

        // Check if we've already computed this state
        // We can only use cache if this node is not in our current path (not in visited set)
        // This is safe because we're doing DFS with backtracking
        if (!visited.contains(current) && memoCache.containsKey(cacheKey)) {
            return memoCache.get(cacheKey);
        }

        visited.add(current);

        // Update visited flags for dac and fft
        boolean newVisitedDac = visitedDac || current.equals(dac);
        boolean newVisitedFft = visitedFft || current.equals(fft);

        long paths = 0;

        for (Device next : current.getOutputs()) {
            if (!visited.contains(next)) {
                paths += countPathsVisitingBothHelper(next, end, dac, fft, visited,
                        newVisitedDac, newVisitedFft);
            }
        }

        visited.remove(current);

        // Cache the result only after we've completed exploring from this node
        memoCache.put(cacheKey, paths);

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
