package com.adventofcode.year2022.day4;

import static java.lang.Integer.parseInt;
import static org.apache.commons.collections4.CollectionUtils.containsAll;
import static org.apache.commons.collections4.CollectionUtils.containsAny;
import static org.apache.commons.collections4.IterableUtils.toList;
import java.util.List;
import org.apache.commons.math3.util.IntegerSequence;

public class Pair {

    private final List<Integer> sectionList1;
    private final List<Integer> sectionList2;

    public Pair(String input) {
        sectionList1 = toList(new IntegerSequence.Range(parseInt(input.split(",")[0].split("-")[0]), parseInt(input.split(",")[0].split("-")[1]), 1));
        sectionList2 = toList(new IntegerSequence.Range(parseInt(input.split(",")[1].split("-")[0]), parseInt(input.split(",")[1].split("-")[1]), 1));
    }

    public boolean isSubset() {
        return containsAll(sectionList1, sectionList2) || containsAll(sectionList2, sectionList1);
    }

    public boolean isOverlap() {
        return containsAny(sectionList1, sectionList2) || containsAny(sectionList2, sectionList1);
    }

}
