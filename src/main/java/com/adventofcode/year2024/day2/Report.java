package com.adventofcode.year2024.day2;

import java.util.*;
import java.util.stream.IntStream;

public class Report {

    private final List<Integer> levels;

    public Report(String input) {
        levels = new LinkedList<>(Arrays.stream(input.split(" ")).map(Integer::parseInt).toList());
    }

    // Check if list of levels is "safe" being all either ascending or descending and with maximum difference of 3 between levels
    public boolean isSafe() {
        boolean isDescending = levels.getFirst() > levels.getLast();

        return IntStream.range(1, levels.size())
                .allMatch(i -> {
                    int diff = levels.get(i) - levels.get(i - 1);
                    return isDescending ? (diff < 0 && diff >= -3) : (diff > 0 && diff <= 3);
                });
    }

    public boolean isSafeWithOneLevelRemoved() {
        if (isSafe())
            return true;

        // Check if removing any single level makes it safe
        return IntStream.range(0, levels.size())
                .anyMatch(i -> {
                    Integer levelToRemove = levels.remove(i);
                    try {
                        return isSafe();
                    } finally {
                        levels.add(i, levelToRemove); // Ensure original list is restored
                    }
                });
    }

}
