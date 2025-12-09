package com.adventofcode.year2025.day8;

import java.util.*;
import org.springframework.stereotype.Component;
import com.adventofcode.common.DailyAnswer;
import com.adventofcode.common.SolutionController;
import lombok.extern.slf4j.Slf4j;


@Slf4j
@Component("controller-2025-8")
public class Controller extends SolutionController {

    private static final int PART1_CONNECTIONS = 1000;

    public DailyAnswer execute() {
        List<JunctionBox> junctionBoxes = parseJunctionBoxes();
        List<JunctionPair> pairs = generateSortedPairs(junctionBoxes);

        log.info("Loaded {} junction boxes, generated {} pairs", junctionBoxes.size(), pairs.size());

        CircuitManager manager = new CircuitManager(junctionBoxes);
        ConnectionResult result = processConnections(pairs, manager);

        answer.setPart1(result.part1Result());
        answer.setPart2(result.part2Result());

        log.info("Part 1: {}", answer.getPart1());
        log.info("Part 2: {}", answer.getPart2());

        return answer;
    }

    private List<JunctionBox> parseJunctionBoxes() {
        return puzzleInput.stream()
                .filter(line -> !line.isBlank())
                .map(JunctionBox::new)
                .toList();
    }

    private List<JunctionPair> generateSortedPairs(List<JunctionBox> boxes) {
        List<JunctionPair> pairs = new ArrayList<>();
        for (int i = 0; i < boxes.size(); i++) {
            for (int j = i + 1; j < boxes.size(); j++) {
                pairs.add(new JunctionPair(boxes.get(i), boxes.get(j)));
            }
        }
        pairs.sort(Comparator.comparingDouble(JunctionPair::distance));
        return pairs;
    }

    private ConnectionResult processConnections(List<JunctionPair> pairs, CircuitManager manager) {
        int connectionsMade = 0;
        long part1Result = 0;
        JunctionPair lastUnifyingPair = null;

        for (JunctionPair pair : pairs) {
            if (manager.connect(pair.box1(), pair.box2())) {
                lastUnifyingPair = pair;
            }
            connectionsMade++;

            // Capture Part 1 result after exactly 1000 connections
            if (connectionsMade == PART1_CONNECTIONS) {
                part1Result = manager.productOfLargestCircuits(3);
                log.info("After {} connections: {} circuits, product of top 3: {}",
                        connectionsMade, manager.circuitCount(), part1Result);
            }

            if (manager.isUnified()) {
                log.info("All unified after {} connections: {} × {} = {}",
                        connectionsMade, lastUnifyingPair.box1().getX(), lastUnifyingPair.box2().getX(),
                        (long) lastUnifyingPair.box1().getX() * lastUnifyingPair.box2().getX());
                break;
            }
        }

        long part2Result = (long) lastUnifyingPair.box1().getX() * lastUnifyingPair.box2().getX();
        return new ConnectionResult(part1Result, part2Result);
    }

    private record JunctionPair(JunctionBox box1, JunctionBox box2) {
        double distance() {
            return box1.distanceTo(box2);
        }
    }

    private record ConnectionResult(long part1Result, long part2Result) {
    }

}
