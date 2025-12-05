package com.adventofcode.year2022.day3;

import java.util.*;
import org.apache.commons.collections4.CollectionUtils;

public class Sack {

    List<String> compartment1 = new ArrayList<>();
    List<String> compartment2 = new ArrayList<>();

    public Sack(String input) {
        List<String> chars = List.of(input.split(""));
        compartment1.addAll(chars.subList(0, chars.size() / 2));
        compartment2.addAll(chars.subList(chars.size() / 2, chars.size()));
    }

    public Set<String> getTypesInBothCompartments() {
        return new HashSet<>(CollectionUtils.intersection(compartment1, compartment2));
    }

    public Integer getPriorityOfTypeInBothCompartments() {
        return getTypesInBothCompartments().stream().map(TypeUtil::getPriority).mapToInt(Integer::intValue).sum();
    }

    public Set<String> getAllUniqueTypes() {
        Set<String> set = new HashSet<>();
        set.addAll(compartment1);
        set.addAll(compartment2);
        return set;
    }


}
