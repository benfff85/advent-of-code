package com.adventofcode.year2019.day3;

import static java.lang.Math.abs;
import java.util.Arrays;
import java.util.List;
import org.springframework.stereotype.Component;
import com.adventofcode.common.DailyAnswer;
import com.adventofcode.common.SolutionController;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component("controller-day-3")
public class Controller extends SolutionController {

    public DailyAnswer execute() {
        List<Instruction> wireInstructionList1 = Arrays.stream(puzzleInput.get(0).split(",")).map(Instruction::new).toList();
        List<Instruction> wireInstructionList2 = Arrays.stream(puzzleInput.get(1).split(",")).map(Instruction::new).toList();

        // Create wires and calculate paths
        Wire wire1 = new Wire(wireInstructionList1);
        Wire wire2 = new Wire(wireInstructionList2);

        findIntersectionWithShortestManhattanDistance(wire1, wire2);
        findIntersectionWithShortestWireLength(wire1, wire2);
        return answer;
    }


    private void findIntersectionWithShortestWireLength(Wire wire1, Wire wire2) {
        // Find intersection coordinate with the shortest manhattan distance
        int minTotalWireLength = Integer.MAX_VALUE;
        int maxLengthOfWire2;
        Coordinate coordinate;
        int distanceToConvergence;
        for (int wireLength1 = 1; wireLength1 < wire1.getPath().size(); wireLength1++) {
            coordinate = wire1.getPath().get(wireLength1);

            // Only assess if a given coordinate is an intersection point if it stands a chance of having the shortest total wire length to improve performance
            if (wireLength1 < minTotalWireLength) {
                // Only loop through coordinates in the path of wire 2 up and until it would no longer add up to the shortest total wire length
                maxLengthOfWire2 = minTotalWireLength - wireLength1;
                // We can start at the minimum length it would take to reach the desired coordinate and then increment by the manhattan distance since that would be the minimum distance of convergence
                for (int wireLength2 = getManhattanDistance(coordinate); wireLength2 < wire2.getPath().size() && wireLength2 < maxLengthOfWire2; wireLength2 += distanceToConvergence) {
                    if (wire2.getPath().get(wireLength2).equals(coordinate)) {
                        minTotalWireLength = wireLength1 + wireLength2;
                        break;
                    }
                    distanceToConvergence = getManhattanDistance(coordinate, wire2.getPath().get(wireLength2));
                }
            }
        }
        answer.setPart2(minTotalWireLength);
        log.info("Minimum wire length: {}", minTotalWireLength);
    }

    private void findIntersectionWithShortestManhattanDistance(Wire wire1, Wire wire2) {
        // Find intersection coordinate with the shortest manhattan distance
        int manhattanDistance;
        int minManhattanDistance = Integer.MAX_VALUE;
        Coordinate coordinate2;
        int distanceToConvergence;
        for (Coordinate coordinate1 : wire1.getPath()) {
            manhattanDistance = getManhattanDistance(coordinate1);
            // Only assess if a given coordinate is an intersection point if it stands a chance of having the shortest manhattan distance to improve performance
            if (manhattanDistance > 0 && manhattanDistance < minManhattanDistance) {
                for (int wire2Index = manhattanDistance; wire2Index < wire2.getPath().size(); wire2Index += distanceToConvergence) {
                    coordinate2 = wire2.getPath().get(wire2Index);
                    if (coordinate2.equals(coordinate1)) {
                        minManhattanDistance = manhattanDistance;
                        break;
                    }
                    distanceToConvergence = getManhattanDistance(coordinate1, coordinate2);
                }
            }
        }
        answer.setPart1(minManhattanDistance);
        log.info("Minimum manhattan distance: {}", minManhattanDistance);
    }

    private Integer getManhattanDistance(Coordinate coordinate) {
        return abs(coordinate.x()) + abs(coordinate.y());
    }

    private Integer getManhattanDistance(Coordinate coordinate1, Coordinate coordinate2) {
        return abs(coordinate1.x() - coordinate2.x()) + abs(coordinate1.y() - coordinate2.y());
    }

}
