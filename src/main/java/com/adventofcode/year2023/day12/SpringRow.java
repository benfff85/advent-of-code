package com.adventofcode.year2023.day12;

import java.util.*;
import java.util.stream.IntStream;
import org.apache.commons.math3.util.Combinations;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Data
public class SpringRow {

    private List<SpringState> springs = new ArrayList<>();
    private List<Integer> knownDamagedSpringGroupings = new ArrayList<>();

    private List<Integer> indexesOfUnknownSprings = new ArrayList<>();
    private int unknownCount;
    private int brokenCount;
    private int knownTotalBrokenCount;
    private int unknownThatMustBeBrokenCount;

    private List<List<Integer>> possibleCombinations;
    private Integer combinationsCount = 0;


    public SpringRow(String input, boolean shouldUnfold) {

        log.info("Constructing SpringRow {}", input);

        Arrays.stream(input.split(" ")[0].split("")).map(SpringState::fromString).forEach(springs::add);
        Arrays.stream(input.split(" ")[1].split(",")).map(Integer::parseInt).forEach(knownDamagedSpringGroupings::add);

        if (shouldUnfold) {
            List<SpringState> originalSprings = new ArrayList<>(springs);
            List<Integer> originalKnownDamagedSpringGroupings = new ArrayList<>(knownDamagedSpringGroupings);
            for (int i = 0; i < 4; i++) {
                springs.add(SpringState.UNKNOWN);
                springs.addAll(originalSprings);
                knownDamagedSpringGroupings.addAll(originalKnownDamagedSpringGroupings);
            }
        }

        IntStream.range(0, springs.size()).filter(i -> springs.get(i).equals(SpringState.UNKNOWN)).forEach(indexesOfUnknownSprings::add);

        unknownCount = indexesOfUnknownSprings.size();
        brokenCount = (int) springs.stream().filter(springState -> springState.equals(SpringState.BROKEN)).count();

        knownTotalBrokenCount = (int) knownDamagedSpringGroupings.stream().mapToLong(Integer::longValue).sum();
        unknownThatMustBeBrokenCount = knownTotalBrokenCount - brokenCount;

        log.info("({}|{})", unknownCount, unknownThatMustBeBrokenCount);

        // TODO need to optimize for part 2
        if (shouldUnfold) {
            return;
        }

        for (int[] combination : new Combinations(unknownCount, unknownThatMustBeBrokenCount)) {
            // Apply combination
            for (int j : combination) {
                springs.set(indexesOfUnknownSprings.get(j), SpringState.BROKEN);
            }

            // Check if it matches the knownDamagedSpringGroupings
            if (matchesKnownDamagedSpringGroupings()) {
                combinationsCount++;
            }

            // Revert
            for (int j : combination) {
                springs.set(indexesOfUnknownSprings.get(j), SpringState.UNKNOWN);
            }

        }

    }

    private boolean matchesKnownDamagedSpringGroupings() {

        int count = 0;
        int index = 0;
        for (SpringState springState : springs) {
            if (springState.equals(SpringState.BROKEN)) {
                count++;
            } else {
                if (count > 0) {
                    if (count != knownDamagedSpringGroupings.get(index)) {
                        return false;
                    }
                    count = 0;
                    index++;
                }
            }
        }
        return count <= 0 || (count == knownDamagedSpringGroupings.get(index));

    }

}
