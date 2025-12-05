package com.adventofcode.year2024.day10;

import java.awt.Point;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Trailhead {

    private Point startingPoint;
    private Long score;

}
