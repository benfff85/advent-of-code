package com.adventofcode.year2019.day3;

import java.util.ArrayList;
import java.util.List;
import com.adventofcode.common.grid.Direction;
import lombok.Getter;


@Getter
public class Wire {

    private final List<Instruction> instructionList;
    private List<Coordinate> path;

    public Wire(List<Instruction> instructionList) {
        this.instructionList = instructionList;
        path = calculatePath(instructionList);
    }

    private List<Coordinate> calculatePath(List<Instruction> instructionList) {

        path = new ArrayList<>(List.of(new Coordinate(0, 0)));
        for (Instruction instruction : instructionList) {
            processInstruction(instruction, path);
        }
        return path;

    }

    private void processInstruction(Instruction instruction, List<Coordinate> path) {
        Integer x = path.get(path.size() - 1).x();
        Integer y = path.get(path.size() - 1).y();
        for (int i = 0; i < instruction.getMagnitude(); i++) {
            if (Direction.L.equals(instruction.getDirection())) {
                path.add(new Coordinate(--x, y));
            } else if (Direction.R.equals(instruction.getDirection())) {
                path.add(new Coordinate(++x, y));
            } else if (Direction.D.equals(instruction.getDirection())) {
                path.add(new Coordinate(x, --y));
            } else if (Direction.U.equals(instruction.getDirection())) {
                path.add(new Coordinate(x, ++y));
            }
        }
    }

}
