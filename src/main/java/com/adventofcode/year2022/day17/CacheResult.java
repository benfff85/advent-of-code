package com.adventofcode.year2022.day17;

import java.util.List;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CacheResult {

    private final List<Integer> topology;
    private final Integer indexOfOriginalRock;

}
