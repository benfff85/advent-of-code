package com.adventofcode.year2020;


import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import com.adventofcode.common.DailyAnswer;
import lombok.RequiredArgsConstructor;


@SpringBootTest
@DisplayName("2020")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
class AdventOfCodeTests {


    private final com.adventofcode.year2020.day1.Controller day1Controller;
    private final com.adventofcode.year2020.day2.Controller day2Controller;
    private final com.adventofcode.year2020.day3.Controller day3Controller;
    private final com.adventofcode.year2020.day4.Controller day4Controller;
    private final com.adventofcode.year2020.day5.Controller day5Controller;


    @Test
    void testDay1() {
        DailyAnswer answer = day1Controller.execute();
        assertEquals(381699, answer.getPart1());
        assertEquals(111605670, answer.getPart2());
    }

    @Test
    void testDay2() {
        DailyAnswer answer = day2Controller.execute();
        assertEquals(586L, answer.getPart1());
        assertEquals(352L, answer.getPart2());
    }

    @Test
    void testDay3() {
        DailyAnswer answer = day3Controller.execute();
        assertEquals(173L, answer.getPart1());
        assertEquals(4385176320L, answer.getPart2());
    }

    @Test
    void testDay4() {
        DailyAnswer answer = day4Controller.execute();
        assertEquals(230L, answer.getPart1());
        assertEquals(156L, answer.getPart2());
    }

    @Test
    void testDay5() {
        DailyAnswer answer = day5Controller.execute();
        assertEquals(938, answer.getPart1());
        assertEquals(696, answer.getPart2());
    }

}
