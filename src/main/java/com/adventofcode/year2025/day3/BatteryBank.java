package com.adventofcode.year2025.day3;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import lombok.Data;

@Data
public class BatteryBank {

    private final String rawBatteryBankString;
    public final List<Integer> batteries;
    private Integer maxJoltage;
    private Integer maxJoltageWithDisabledSafety;

    public BatteryBank(String rawBatteryBankString) {
        this.rawBatteryBankString = rawBatteryBankString;
        this.batteries = rawBatteryBankString.chars().mapToObj(c -> c - '0').toList();
    }

    public Integer getMaxJoltage() {
        if (maxJoltage == null) {
            maxJoltage = calculateMaxJoltage(2);
        }
        return maxJoltage;
    }

    private Integer calculateMaxJoltage(int totalBatteriesToEnable) {

        // List<Integer> batteriesToEnable = new ArrayList<>();

        int leftValue = Collections.max(batteries.subList(0, batteries.size() - 1));
        int leftIndex = batteries.indexOf(leftValue);

        int rightValue = Collections.max(batteries.subList(leftIndex + 1, batteries.size()));
        // int rightIndex = batteries.lastIndexOf(rightValue);

        // return Integer.parseInt(String.valueOf(leftValue) + String.valueOf(rightValue));
        return convertIntegersToConcatenatedString(List.of(leftValue, rightValue));

    }


    private Integer convertIntegersToConcatenatedString(List<Integer> integers) {
        return Integer.parseInt(integers.stream().map(String::valueOf).reduce("", String::concat));
    }

}
