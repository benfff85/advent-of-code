package com.adventofcode.year2025;

import com.adventofcode.common.DailyAnswer;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@DisplayName("2025")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
class AdventOfCodeTests {

    private final com.adventofcode.year2025.day1.Controller day1Controller;

    @Test
    void testDay1() {
        DailyAnswer answer = day1Controller.execute();
        assertEquals(1168, answer.getPart1());
        assertEquals(7199, answer.getPart2());
    }

}