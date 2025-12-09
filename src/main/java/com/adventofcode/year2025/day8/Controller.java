package com.adventofcode.year2025.day8;

import java.util.*;
import org.springframework.stereotype.Component;
import com.adventofcode.common.DailyAnswer;
import com.adventofcode.common.SolutionController;
import lombok.extern.slf4j.Slf4j;


@Slf4j
@Component("controller-2025-8")
public class Controller extends SolutionController {

    public DailyAnswer execute() {

        // Parse all junction boxes
        List<JunctionBox> junctionBoxes = puzzleInput.stream()
                .filter(line -> !line.isBlank())
                .map(JunctionBox::new)
                .toList();

        log.info("Loaded {} junction boxes", junctionBoxes.size());

        // Create initial circuits - each junction box starts in its own circuit
        Map<JunctionBox, Circuit> boxToCircuit = new HashMap<>();
        Set<Circuit> circuits = new HashSet<>();
        for (JunctionBox box : junctionBoxes) {
            Circuit circuit = new Circuit(box);
            boxToCircuit.put(box, circuit);
            circuits.add(circuit);
        }

        // Pre-compute all pairs with their distances
        List<JunctionPair> pairs = new ArrayList<>();
        for (int i = 0; i < junctionBoxes.size(); i++) {
            for (int j = i + 1; j < junctionBoxes.size(); j++) {
                JunctionBox box1 = junctionBoxes.get(i);
                JunctionBox box2 = junctionBoxes.get(j);
                double distance = box1.distanceTo(box2);
                pairs.add(new JunctionPair(box1, box2, distance));
            }
        }

        // Sort pairs by distance (closest first)
        pairs.sort(Comparator.comparingDouble(JunctionPair::distance));

        log.info("Generated {} pairs, sorted by distance", pairs.size());

        // Process pairs - Part 1: first 1000, Part 2: until one circuit
        int connectionsToMake = 1000;
        int connectionsMade = 0;
        long part1Result = 0;
        JunctionPair lastUnifyingPair = null;

        for (JunctionPair pair : pairs) {
            Circuit circuit1 = boxToCircuit.get(pair.box1());
            Circuit circuit2 = boxToCircuit.get(pair.box2());

            // If they're in different circuits, merge them
            if (circuit1 != circuit2) {
                // Merge circuit2 into circuit1
                circuit1.merge(circuit2);
                circuits.remove(circuit2);

                // Update all boxes that were in circuit2 to point to circuit1
                for (JunctionBox box : circuit2.getJunctionBoxes()) {
                    boxToCircuit.put(box, circuit1);
                }

                // Track last pair that caused a merge (for Part 2)
                lastUnifyingPair = pair;
            }
            // If they're already in the same circuit, nothing happens

            connectionsMade++;

            // Calculate Part 1 result after 1000 connections
            if (connectionsMade == connectionsToMake) {
                List<Integer> sizes = circuits.stream()
                        .map(Circuit::size)
                        .sorted(Comparator.reverseOrder())
                        .toList();

                log.info("Part 1 - Made {} connections, now have {} circuits", connectionsMade, circuits.size());
                log.info("Circuit sizes (top 10): {}", sizes.subList(0, Math.min(10, sizes.size())));

                part1Result = 1;
                for (int i = 0; i < Math.min(3, sizes.size()); i++) {
                    part1Result *= sizes.get(i);
                }
                log.info("Part 1 - Product of three largest circuits: {}", part1Result);
            }

            // Stop once we have only one circuit (Part 2)
            if (circuits.size() == 1) {
                break;
            }
        }

        log.info("Part 2 - All boxes unified after {} connections", connectionsMade);
        log.info("Last unifying pair: ({},{},{}) and ({},{},{})",
                lastUnifyingPair.box1().getX(), lastUnifyingPair.box1().getY(), lastUnifyingPair.box1().getZ(),
                lastUnifyingPair.box2().getX(), lastUnifyingPair.box2().getY(), lastUnifyingPair.box2().getZ());

        long part2Result = (long) lastUnifyingPair.box1().getX() * lastUnifyingPair.box2().getX();
        log.info("Part 2 - Product of X coordinates: {} * {} = {}",
                lastUnifyingPair.box1().getX(), lastUnifyingPair.box2().getX(), part2Result);

        answer.setPart1(part1Result);
        answer.setPart2(part2Result);

        return answer;
    }

    /**
     * Record to hold a pair of junction boxes and their distance.
     */
    private record JunctionPair(JunctionBox box1, JunctionBox box2, double distance) {
    }

}
