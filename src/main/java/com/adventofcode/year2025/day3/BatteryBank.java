package com.adventofcode.year2025.day3;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Data
public class BatteryBank {

    private final String rawBatteryBankString;
    public final List<Integer> batteries;
    private Long maxJoltage;
    private Long maxJoltageWithDisabledSafety;

    public BatteryBank(String rawBatteryBankString) {
        this.rawBatteryBankString = rawBatteryBankString;
        this.batteries = rawBatteryBankString.chars().mapToObj(c -> c - '0').toList();
    }

    public Long getMaxJoltage() {
        if (maxJoltage == null) {
            maxJoltage = calculateMaxJoltage(2);
        }
        return maxJoltage;
    }

    public Long getMaxJoltageWithDisabledSafety() {
        if (maxJoltageWithDisabledSafety == null) {
            maxJoltageWithDisabledSafety = calculateMaxJoltage(12);
        }
        return maxJoltageWithDisabledSafety;
    }

    private Long calculateMaxJoltage(int totalBatteriesToEnable) {

        List<Integer> batteriesToEnable = new ArrayList<>();
        int leftIndexBound = 0;

        for (int i = 0; i < totalBatteriesToEnable; i++) {
            batteriesToEnable.add(Collections.max(batteries.subList(leftIndexBound, batteries.size() - (totalBatteriesToEnable - (batteriesToEnable.size() + 1)))));
            leftIndexBound = leftIndexBound + batteries.subList(leftIndexBound, batteries.size()).indexOf(batteriesToEnable.getLast()) + 1;
        }

        return convertIntegersToConcatenatedString(batteriesToEnable);

    }


    private Long convertIntegersToConcatenatedString(List<Integer> integers) {
        return Long.parseLong(integers.stream().map(String::valueOf).reduce("", String::concat));
    }

}
