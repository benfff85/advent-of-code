package com.adventofcode.year2025.day5;

import lombok.Data;

@Data
public class Range {

    private long firstId;
    private long lastId;

    public Range(String input) {
        String[] ids = input.split("-");
        this.firstId = Long.parseLong(ids[0]);
        this.lastId = Long.parseLong(ids[1]);
    }

}
