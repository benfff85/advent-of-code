package com.adventofcode.year2025;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import com.adventofcode.common.DailyAnswer;
import lombok.RequiredArgsConstructor;

@SpringBootTest
@DisplayName("2025")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
class AdventOfCodeTests {

    private final com.adventofcode.year2025.day1.Controller day1Controller;
    private final com.adventofcode.year2025.day2.Controller day2Controller;
    private final com.adventofcode.year2025.day3.Controller day3Controller;
    private final com.adventofcode.year2025.day4.Controller day4Controller;
    private final com.adventofcode.year2025.day5.Controller day5Controller;
    private final com.adventofcode.year2025.day6.Controller day6Controller;
    private final com.adventofcode.year2025.day7.Controller day7Controller;

    @Test
    void testDay1() {
        DailyAnswer answer = day1Controller.execute();
        assertEquals(1168, answer.getPart1());
        assertEquals(7199, answer.getPart2());
    }

    @Test
    void testDay2() {
        DailyAnswer answer = day2Controller.execute();
        assertEquals(32976912643L, answer.getPart1());
        assertEquals(54446379122L, answer.getPart2());
    }

    @Test
    void testDay3() {
        DailyAnswer answer = day3Controller.execute();
        assertEquals(17074L, answer.getPart1());
        assertEquals(169512729575727L, answer.getPart2());
    }

    @Test
    void testDay4() {
        DailyAnswer answer = day4Controller.execute();
        assertEquals(1419, answer.getPart1());
        assertEquals(8739, answer.getPart2());
    }

    @Test
    void testDay5() {
        DailyAnswer answer = day5Controller.execute();
        assertEquals(798, answer.getPart1());
        assertEquals(366181852921027L, answer.getPart2());
    }

    @Test
    void testDay6() {
        DailyAnswer answer = day6Controller.execute();
        assertEquals(5316572080628L, answer.getPart1());
        assertEquals(11299263623062L, answer.getPart2());
    }

    @Test
    void testDay7() {
        DailyAnswer answer = day7Controller.execute();
        assertEquals(1507L, answer.getPart1());
        assertEquals(1537373473728L, answer.getPart2());
    }

}
