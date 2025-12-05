package com.adventofcode.common;

import java.util.ArrayList;
import java.util.List;

public class CircularList<T> {

    private final List<T> elements;
    private int currentIndex;

    public CircularList(List<T> elements) {
        this(elements, 0);
    }

    public CircularList(List<T> elements, int startIndex) {
        if (elements == null || elements.isEmpty()) {
            throw new IllegalArgumentException("List cannot be null or empty");
        }
        this.elements = new ArrayList<>(elements);
        this.currentIndex = Math.floorMod(startIndex, elements.size());
    }

    /**
     * Moves the cursor by the specified number of steps (positive or negative) and returns the element at the new position.
     *
     * @param steps number of steps to move (can be negative)
     * @return the element at the new position
     */
    public T move(int steps) {
        currentIndex = Math.floorMod(currentIndex + steps, elements.size());
        return getCurrent();
    }

    public T next() {
        return move(1);
    }

    public T previous() {
        return move(-1);
    }

    public T getCurrent() {
        return elements.get(currentIndex);
    }

    public int getCurrentIndex() {
        return currentIndex;
    }

    public int size() {
        return elements.size();
    }
}
