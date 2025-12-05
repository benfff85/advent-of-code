package com.adventofcode.year2023.day6;

import java.util.ArrayList;
import java.util.List;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Data
public class Race {

    private Long time;
    private Long distanceRecord;
    private List<Long> winningOptions = new ArrayList<>();

    public Race(Long time, Long distanceRecord) {
        this.time = time;
        this.distanceRecord = distanceRecord;
        calculateWinningOptions();
    }

    private void calculateWinningOptions() {
        for (long i = 0; i < time; i++) {
            if (i * (time - i) > distanceRecord) {
                winningOptions.add(i);
            }
        }
    }

}
