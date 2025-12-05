package com.adventofcode.year2025.day1;

import java.util.List;
import java.util.stream.IntStream;
import com.adventofcode.common.CircularList;
import com.adventofcode.common.grid.Direction;
import lombok.Data;

@Data
public class Dial {

    private final CircularList<Integer> dial;
    int countOfZeroAtEndOfInstruction = 0;
    int countOfZeroAtEndOfStep = 0;

    public Dial(int minValue, int maxValue, int startValue) {
        this.dial = new CircularList<>(IntStream.rangeClosed(minValue, maxValue).boxed().toList(), startValue);
    }

    public void processInstructions(List<Instructions> instructions) {
        int movementDirectionMultiplier;
        for (Instructions instruction : instructions) {
            movementDirectionMultiplier = instruction.getDirection() == Direction.L ? -1 : 1;
            for (int i = 0; i < instruction.getSteps(); i++) {
                dial.move(movementDirectionMultiplier);
                if (dial.getCurrent() == 0) {
                    countOfZeroAtEndOfStep++;
                }
            }

            if (dial.getCurrent() == 0) {
                countOfZeroAtEndOfInstruction++;
            }
        }
    }

}
