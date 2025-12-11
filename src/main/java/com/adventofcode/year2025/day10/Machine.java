package com.adventofcode.year2025.day10;

import java.util.List;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Machine {
    private List<Boolean> targetState;
    private List<List<Integer>> buttons;
    private List<Integer> joltageRequirements;
}
