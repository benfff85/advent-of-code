package com.adventofcode.year2022.day16;

import static java.util.Objects.nonNull;
import java.util.*;
import java.util.stream.Collectors;
import org.apache.commons.collections4.map.LRUMap;
import org.springframework.stereotype.Component;
import com.adventofcode.common.DailyAnswer;
import com.adventofcode.common.SolutionController;
import lombok.extern.slf4j.Slf4j;


@Slf4j
@Component("controller-2022-16")
public class Controller extends SolutionController {

    private final Map<CacheKey, Integer> cache = new LRUMap<>(2500000);
    private final Set<String> openValves = new HashSet<>();
    private int maxMinutesRemainingExited = 0;

    public DailyAnswer execute() {

        Map<String, Valve> valves = puzzleInput.stream().map(Valve::new).collect(Collectors.toMap(Valve::getName, valve -> valve));
        valves.values().forEach(v -> v.linkValves(valves));

        answer.setPart1(getMax(30, valves.get("AA")));

        cache.clear();
        openValves.clear();

        answer.setPart2(getMax(26, valves.get("AA"), valves.get("AA")));

        return answer;
    }


    private Integer getMax(int minutesRemaining, Valve currentValve) {

        // Terminal condition
        if (minutesRemaining == 0) {
            return 0;
        }

        // Check if in cache and if so return value
        CacheKey cacheKey = CacheKey.builder()
                .minutesRemaining(minutesRemaining)
                .currentValveA(currentValve.getName())
                .openValves(new HashSet<>(openValves))
                .build();
        Integer cacheResult = cache.get(cacheKey);
        if (nonNull(cacheResult)) {
            return cacheResult;
        }

        // check other options, add the max to the cache and return it
        int maxPressureReleased = 0;

        if (!currentValve.isOpen() && currentValve.getFlowRate() > 0) {
            currentValve.open();
            openValves.add(currentValve.getName());
            maxPressureReleased = (currentValve.getFlowRate() * (minutesRemaining - 1)) + getMax(minutesRemaining - 1, currentValve);
            currentValve.close();
            openValves.remove(currentValve.getName());
        }

        for (Valve nextValve : currentValve.getConnectedValves()) {
            maxPressureReleased = Math.max(maxPressureReleased, getMax(minutesRemaining - 1, nextValve));
        }

        cache.put(cacheKey, maxPressureReleased);
        return maxPressureReleased;

    }

    private Integer getMax(int minutesRemaining, Valve currentValveA, Valve currentValveB) {

        // Terminal condition
        if (minutesRemaining == 0) {
            return 0;
        }

        // Prune options for performance
        if ((minutesRemaining == 22 && openValves.size() <= 1) || (minutesRemaining == 18 && openValves.size() <= 3) || (minutesRemaining == 14 && openValves.size() <= 5) || (minutesRemaining == 10 && openValves.size() <= 7) || (minutesRemaining == 6 && openValves.size() <= 9)) {
            return 0;
        }

        // Check if in cache and if so return value
        CacheKey cacheKey = CacheKey.builder()
                .minutesRemaining(minutesRemaining)
                .currentValveA(currentValveA.getName())
                .currentValveB(currentValveB.getName())
                .openValves(new HashSet<>(openValves))
                .build();
        Integer cacheResult = cache.get(cacheKey);
        if (nonNull(cacheResult)) {
            return cacheResult;
        }

        CacheKey cacheKey2 = CacheKey.builder()
                .minutesRemaining(minutesRemaining)
                .currentValveA(currentValveB.getName())
                .currentValveB(currentValveA.getName())
                .openValves(new HashSet<>(openValves))
                .build();
        Integer cacheResult2 = cache.get(cacheKey2);
        if (nonNull(cacheResult2)) {
            return cacheResult2;
        }

        // check other options, add the max to the cache and return it
        int maxPressureReleased = 0;

        // Both Open
        if (!currentValveA.isOpen() && currentValveA.getFlowRate() > 0 && !currentValveB.isOpen() && currentValveB.getFlowRate() > 0 && !currentValveA.equals(currentValveB)) {
            currentValveA.open();
            currentValveB.open();
            openValves.add(currentValveA.getName());
            openValves.add(currentValveB.getName());
            maxPressureReleased = (currentValveA.getFlowRate() * (minutesRemaining - 1)) + (currentValveB.getFlowRate() * (minutesRemaining - 1)) + getMax(minutesRemaining - 1, currentValveA, currentValveB);
            currentValveA.close();
            currentValveB.close();
            openValves.remove(currentValveA.getName());
            openValves.remove(currentValveB.getName());
        }

        // A Open, B Move
        if (!currentValveA.isOpen() && currentValveA.getFlowRate() > 0) {
            currentValveA.open();
            openValves.add(currentValveA.getName());
            for (Valve nextValveB : currentValveB.getConnectedValves()) {
                maxPressureReleased = Math.max(maxPressureReleased, (currentValveA.getFlowRate() * (minutesRemaining - 1)) + getMax(minutesRemaining - 1, currentValveA, nextValveB));
            }
            currentValveA.close();
            openValves.remove(currentValveA.getName());
        }

        // B Open, A Move
        if (!currentValveB.isOpen() && currentValveB.getFlowRate() > 0) {
            currentValveB.open();
            openValves.add(currentValveB.getName());
            for (Valve nextValveA : currentValveA.getConnectedValves()) {
                maxPressureReleased = Math.max(maxPressureReleased, (currentValveB.getFlowRate() * (minutesRemaining - 1)) + getMax(minutesRemaining - 1, nextValveA, currentValveB));
            }
            currentValveB.close();
            openValves.remove(currentValveB.getName());
        }

        // Both Move
        for (Valve nextValveA : currentValveA.getConnectedValves()) {
            for (Valve nextValveB : currentValveB.getConnectedValves()) {
                maxPressureReleased = Math.max(maxPressureReleased, getMax(minutesRemaining - 1, nextValveA, nextValveB));
            }
        }

        if (minutesRemaining > maxMinutesRemainingExited && maxPressureReleased != 0) {
            maxMinutesRemainingExited = minutesRemaining;
            log.info("Exited one instance for minutes remaining: {}", minutesRemaining);
        }

        cache.put(cacheKey, maxPressureReleased);
        return maxPressureReleased;
    }

}
