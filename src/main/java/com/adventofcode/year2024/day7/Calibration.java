package com.adventofcode.year2024.day7;

import java.util.*;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Data
public class Calibration {

    private final Long result;
    private final List<Integer> numbers;
    private boolean isSolvable;
    private boolean isSolvableWithConcat;

    public Calibration(String input) {
        result = Long.parseLong(input.split(":")[0]);
        numbers = Arrays.stream(input.split(":")[1].split(" ")).skip(1).map(Integer::parseInt).toList();

        isSolvable = determineIsSolvable(numbers, result);
        isSolvableWithConcat = determineIsSolvableWithConcat(numbers, result);

    }

    private static boolean determineIsSolvable(List<Integer> numbers, Long result) {

        // Different options / selected count
        for (List<Integer> combination : generatePermutationsWithReplacement(1, numbers.size() - 1)) {
            Long outcome = Long.valueOf(numbers.getFirst());
            for (int i = 0; i < numbers.size() - 1; i++) {
                if (combination.get(i) == 0) {
                    outcome += numbers.get(i + 1);
                } else if (combination.get(i) == 1) {
                    outcome *= numbers.get(i + 1);
                }
            }

            if (outcome.equals(result)) {
                return true;
            }

        }

        return false;
    }

    private static boolean determineIsSolvableWithConcat(List<Integer> numbers, Long result) {

        // Different options / selected count
        for (List<Integer> combination : generatePermutationsWithReplacement(2, numbers.size() - 1)) {
            Long outcome = Long.valueOf(numbers.getFirst());
            for (int i = 0; i < numbers.size() - 1; i++) {
                if (combination.get(i) == 0) {
                    outcome += numbers.get(i + 1);
                } else if (combination.get(i) == 1) {
                    outcome *= numbers.get(i + 1);
                } else if (combination.get(i) == 2) {
                    outcome = Long.parseLong(String.valueOf(outcome) + String.valueOf(numbers.get(i + 1)));
                }
            }

            if (outcome.equals(result)) {
                return true;
            }

        }

        return false;
    }


    public static List<List<Integer>> generatePermutationsWithReplacement(int n, int k) {
        List<List<Integer>> result = new ArrayList<>();
        generate(new ArrayList<>(), n, k, result);
        return result;
    }

    private static void generate(List<Integer> current, int n, int k, List<List<Integer>> result) {
        if (current.size() == k) {
            result.add(new ArrayList<>(current));
            return;
        }

        for (int i = 0; i <= n; i++) {
            current.add(i);
            generate(current, n, k, result);
            current.removeLast(); // Backtrack
        }
    }


}
