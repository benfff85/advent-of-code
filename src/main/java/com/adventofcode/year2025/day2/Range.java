package com.adventofcode.year2025.day2;

import lombok.Data;

@Data
public class Range {

    private final long firstId;
    private final long lastId;

    public Range(String input) {
        String[] ids = input.split("-");
        this.firstId = Long.parseLong(ids[0]);
        this.lastId = Long.parseLong(ids[1]);
    }

}