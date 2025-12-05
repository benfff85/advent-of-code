package com.adventofcode.year2024.day6;

import java.awt.Point;
import java.util.Set;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class GuardAnalysisResult {

    private Set<Point> visitedSpaces;
    private boolean isCyclic;

}
