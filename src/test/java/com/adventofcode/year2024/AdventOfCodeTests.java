package com.adventofcode.year2024;


import com.adventofcode.common.DailyAnswer;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;


@SpringBootTest
@DisplayName("2024")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
class AdventOfCodeTests {


    private final com.adventofcode.year2024.day1.Controller day1Controller;
    private final com.adventofcode.year2024.day2.Controller day2Controller;
    private final com.adventofcode.year2024.day3.Controller day3Controller;
    private final com.adventofcode.year2024.day4.Controller day4Controller;
    private final com.adventofcode.year2024.day5.Controller day5Controller;
    private final com.adventofcode.year2024.day6.Controller day6Controller;
    private final com.adventofcode.year2024.day7.Controller day7Controller;
    private final com.adventofcode.year2024.day8.Controller day8Controller;
    private final com.adventofcode.year2024.day9.Controller day9Controller;
    private final com.adventofcode.year2024.day10.Controller day10Controller;


    @Test
    void testDay1() {
        DailyAnswer answer = day1Controller.execute();
        assertEquals(1660292, answer.getPart1());
        assertEquals(22776016, answer.getPart2());
    }

    @Test
    void testDay2() {
        DailyAnswer answer = day2Controller.execute();
        assertEquals(463L, answer.getPart1());
        assertEquals(514L, answer.getPart2());
    }

    @Test
    void testDay3() {
        DailyAnswer answer = day3Controller.execute();
        assertEquals(163931492, answer.getPart1());
        assertEquals(76911921, answer.getPart2());
    }

    @Test
    void testDay4() {
        DailyAnswer answer = day4Controller.execute();
        assertEquals(2378, answer.getPart1());
        assertEquals(1796, answer.getPart2());
    }

    @Test
    void testDay5() {
        DailyAnswer answer = day5Controller.execute();
        assertEquals(5955, answer.getPart1());
        assertEquals(4030, answer.getPart2());
    }

    @Test
    void testDay6() {
        DailyAnswer answer = day6Controller.execute();
        assertEquals(4711, answer.getPart1());
        assertEquals(1562, answer.getPart2());
    }

    @Test
    void testDay7() {
        DailyAnswer answer = day7Controller.execute();
        assertEquals(21572148763543L, answer.getPart1());
        assertEquals(581941094529163L, answer.getPart2());
    }

    @Test
    void testDay8() {
        DailyAnswer answer = day8Controller.execute();
        assertEquals(381L, answer.getPart1());
        assertEquals(1184L, answer.getPart2());
    }

    @Test
    void testDay9() {
        DailyAnswer answer = day9Controller.execute();
        assertEquals(6390180901651L, answer.getPart1());
        assertEquals(6412390114238L, answer.getPart2());
    }

    @Test
    void testDay10() {
        DailyAnswer answer = day10Controller.execute();
        assertEquals(593, answer.getPart1());
        assertEquals(1192, answer.getPart2());
    }

}
