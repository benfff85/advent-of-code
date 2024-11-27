package com.adventofcode.year2023.day5;

import lombok.extern.slf4j.Slf4j;

import java.util.Map;
import java.util.NavigableMap;
import java.util.TreeMap;

import static java.util.Objects.nonNull;

@Slf4j
public class RangedMap {

    private final NavigableMap<Long, Range> ranges = new TreeMap<>();

    public void put(Long sourceStart, Long destinationStart, Long rangeLength) {
        Range range = new Range(sourceStart, (sourceStart + rangeLength) - 1, destinationStart - sourceStart);
        ranges.put(sourceStart, range);
    }

    public int size() {
        return ranges.size();
    }

    public Long get(Long source) {
        Map.Entry<Long, Range> entry = ranges.floorEntry(source);
        if (nonNull(entry) && source <= entry.getValue().sourceEnd()) {
            return source + entry.getValue().offsetToDestination();
        }
        return source;
    }

}