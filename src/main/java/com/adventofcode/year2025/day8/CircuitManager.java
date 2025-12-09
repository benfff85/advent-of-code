package com.adventofcode.year2025.day8;

import java.util.*;
import lombok.Getter;

/**
 * Manages circuits of junction boxes using a union-find approach. Tracks circuit membership and handles merging operations.
 */
@Getter
public class CircuitManager {

    private final Map<JunctionBox, Circuit> boxToCircuit = new HashMap<>();
    private final Set<Circuit> circuits = new HashSet<>();

    public CircuitManager(List<JunctionBox> junctionBoxes) {
        for (JunctionBox box : junctionBoxes) {
            Circuit circuit = new Circuit(box);
            boxToCircuit.put(box, circuit);
            circuits.add(circuit);
        }
    }

    /**
     * Attempt to connect two junction boxes.
     * 
     * @return true if the boxes were in different circuits and were merged
     */
    public boolean connect(JunctionBox box1, JunctionBox box2) {
        Circuit circuit1 = boxToCircuit.get(box1);
        Circuit circuit2 = boxToCircuit.get(box2);

        if (circuit1 == circuit2) {
            return false;
        }

        // Merge smaller circuit into larger for efficiency
        if (circuit1.size() < circuit2.size()) {
            Circuit temp = circuit1;
            circuit1 = circuit2;
            circuit2 = temp;
        }

        circuit1.merge(circuit2);
        circuits.remove(circuit2);

        for (JunctionBox box : circuit2.getJunctionBoxes()) {
            boxToCircuit.put(box, circuit1);
        }

        return true;
    }

    public int circuitCount() {
        return circuits.size();
    }

    public boolean isUnified() {
        return circuits.size() == 1;
    }

    /**
     * Get circuit sizes sorted in descending order.
     */
    public List<Integer> getSortedCircuitSizes() {
        return circuits.stream()
                .map(Circuit::size)
                .sorted(Comparator.reverseOrder())
                .toList();
    }

    /**
     * Calculate product of the N largest circuit sizes.
     */
    public long productOfLargestCircuits(int n) {
        List<Integer> sizes = getSortedCircuitSizes();
        long product = 1;
        for (int i = 0; i < Math.min(n, sizes.size()); i++) {
            product *= sizes.get(i);
        }
        return product;
    }

}
