package com.adventofcode.year2022.day20;

import java.util.LinkedList;
import java.util.List;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class LoopingList {

    private final List<Node> list = new LinkedList<>();
    private final Integer listSize;

    public LoopingList(List<Node> inputNodes) {
        list.addAll(inputNodes);
        listSize = list.size();
    }

    public void slide(Node node, Long value) {
        int startingIndex = list.indexOf(node);
        list.remove(startingIndex);
        list.add((int) calculateShiftedIndex(startingIndex, value), node);
    }


    private long calculateShiftedIndex(int startingIndex, Long value) {
        long shiftedIndex = startingIndex + (value % (listSize - 1));
        if (shiftedIndex >= listSize) {
            shiftedIndex = (shiftedIndex % listSize) + 1;
        } else if (shiftedIndex < 0) {
            shiftedIndex = (listSize - 1) + (shiftedIndex % listSize);
        }
        return shiftedIndex;
    }

    public Node getNodeGivenOffset(Long value, Long offset) {

        long startingIndex = list.indexOf(list.stream().filter(n -> n.getValue() == value).findFirst().orElseThrow());

        long shiftedIndex = startingIndex + offset;
        if (shiftedIndex >= listSize) {
            shiftedIndex = (shiftedIndex % listSize);
        } else if (shiftedIndex < 0) {
            shiftedIndex = listSize + (shiftedIndex % listSize);
        }

        return list.get((int) shiftedIndex);
    }
}
