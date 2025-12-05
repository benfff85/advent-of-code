package com.adventofcode.year2024.day13;

import java.util.ArrayList;
import java.util.List;
import org.apache.commons.math3.util.Pair;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Data
public class ClawMachine {

    private Integer axMagnitude;
    private Integer ayMagnitude;
    private Integer bxMagnitude;
    private Integer byMagnitude;
    private Long prizeLocationX;
    private Long prizeLocationY;
    private List<ButtonPressRecord> buttonPressOptions = new ArrayList<>();
    private List<ButtonPressRecord> buttonPressOptionsPart2 = new ArrayList<>();


    public ClawMachine(List<String> stringList) {
        axMagnitude = Integer.parseInt(stringList.getFirst().split(",")[0].split("\\+")[1]);
        ayMagnitude = Integer.parseInt(stringList.getFirst().split("\\+")[2]);
        bxMagnitude = Integer.parseInt(stringList.get(1).split(",")[0].split("\\+")[1]);
        byMagnitude = Integer.parseInt(stringList.get(1).split("\\+")[2]);
        prizeLocationX = Long.parseLong(stringList.get(2).split(",")[0].split("=")[1]);
        prizeLocationY = Long.parseLong(stringList.get(2).split("=")[2]);

        Pair<Long, Long> result1 = LinearEquationSolver.solveEquations(axMagnitude, bxMagnitude, ayMagnitude, byMagnitude, prizeLocationX, prizeLocationY);
        if (result1 != null) {
            buttonPressOptions.add(new ButtonPressRecord(result1.getFirst(), result1.getSecond()));
        }

        Pair<Long, Long> result2 = LinearEquationSolver.solveEquations(axMagnitude, bxMagnitude, ayMagnitude, byMagnitude, (double) prizeLocationX + 10000000000000L, (double) prizeLocationY + 10000000000000L);
        if (result2 != null) {
            buttonPressOptionsPart2.add(new ButtonPressRecord(result2.getFirst(), result2.getSecond()));
        }

    }


}
