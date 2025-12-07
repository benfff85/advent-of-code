package com.adventofcode.year2025.day6;

import java.util.ArrayList;
import java.util.List;
import lombok.Data;

@Data
public class Problem {

    private List<Long> numbers = new ArrayList<>();
    private String operator;

    public void addNumber(Long number) {
        numbers.add(number);
    }

    public Long evaluate() {
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

}
