package com.adventofcode.year2025.day8;

import java.util.HashSet;
import java.util.Set;
import lombok.Getter;

@Getter
public class Circuit {

    private final Set<JunctionBox> junctionBoxes;

    public Circuit(JunctionBox initialBox) {
        this.junctionBoxes = new HashSet<>();
        this.junctionBoxes.add(initialBox);
    }

    /**
     * Merge another circuit into this one.
     */
    public void merge(Circuit other) {
        this.junctionBoxes.addAll(other.junctionBoxes);
    }

    /**
     * Check if this circuit contains a given junction box.
     */
    public boolean contains(JunctionBox box) {
        return junctionBoxes.contains(box);
    }

    /**
     * Get the size of this circuit.
     */
    public int size() {
        return junctionBoxes.size();
    }

    // Use identity-based equals/hashCode (default Object implementation)
    // This is important because Circuit's contents change during merging,
    // and we need stable identity for Set membership.

}
