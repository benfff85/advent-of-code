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

    public void addNumber(Long number) {
        numbers.add(number);
    }

    public Long evaluatePart1() {
        Long result = numbers.get(0);
        for (int i = 1; i < numbers.size(); i++) {
            if (operator.equals("+")) {
                result += numbers.get(i);
            } else if (operator.equals("*")) {
                result *= numbers.get(i);
            }
        }
        return result;
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

        if (operator.equals("+")) {
            return resultNumbers.stream().mapToLong(Long::longValue).sum();
        }

        return resultNumbers.stream().mapToLong(Long::longValue).reduce(1, (a, b) -> a * b);


    }


}
