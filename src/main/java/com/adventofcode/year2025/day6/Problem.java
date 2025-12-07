package com.adventofcode.year2025.day6;

import java.util.ArrayList;
import java.util.List;
import lombok.Data;

@Data
public class Problem {

    private List<String> numberStrings = new ArrayList<>();
    private List<Long> numbers = new ArrayList<>();
    private String operator;

    public void addNumber(String numberString) {
        numberStrings.add(numberString);
        numbers.add(Long.parseLong(numberString.trim()));
    }

    public Long evaluatePart1() {
        return evaluateOperator(numbers, operator);
    }

    public Long evaluatePart2() {
        int stringLength = numberStrings.getFirst().length();
        StringBuilder sb = new StringBuilder();
        List<Long> resultNumbers = new ArrayList<>();

        for (int i = stringLength - 1; i >= 0; i--) {
            int finalI = i;
            numberStrings.stream()
                    .map(s -> s.charAt(finalI))
                    .filter(c -> c != ' ')
                    .forEach(sb::append);

            resultNumbers.add(Long.parseLong(sb.toString()));
            sb.setLength(0);
        }

        return evaluateOperator(resultNumbers, operator);
    }

    private Long evaluateOperator(List<Long> nums, String operator) {
        return switch (operator) {
            case "+" -> nums.stream().mapToLong(Long::longValue).sum();
            case "*" -> nums.stream().mapToLong(Long::longValue).reduce(1, (a, b) -> a * b);
            default -> throw new IllegalArgumentException("Invalid operator: " + operator);
        };
    }
}
