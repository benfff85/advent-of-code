package com.adventofcode.year2022;


import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import com.adventofcode.common.DailyAnswer;
import lombok.RequiredArgsConstructor;


@SpringBootTest
@DisplayName("2022")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
class AdventOfCodeTests {

    private final com.adventofcode.year2022.day1.Controller day1Controller;
    private final com.adventofcode.year2022.day2.Controller day2Controller;
    private final com.adventofcode.year2022.day3.Controller day3Controller;
    private final com.adventofcode.year2022.day4.Controller day4Controller;
    private final com.adventofcode.year2022.day5.Controller day5Controller;
    private final com.adventofcode.year2022.day6.Controller day6Controller;
    private final com.adventofcode.year2022.day7.Controller day7Controller;
    private final com.adventofcode.year2022.day8.Controller day8Controller;
    private final com.adventofcode.year2022.day9.Controller day9Controller;
    private final com.adventofcode.year2022.day10.Controller day10Controller;
    private final com.adventofcode.year2022.day11.Controller day11Controller;
    private final com.adventofcode.year2022.day12.Controller day12Controller;
    private final com.adventofcode.year2022.day13.Controller day13Controller;
    private final com.adventofcode.year2022.day14.Controller day14Controller;
    private final com.adventofcode.year2022.day15.Controller day15Controller;
    private final com.adventofcode.year2022.day16.Controller day16Controller;
    private final com.adventofcode.year2022.day17.Controller day17Controller;
    private final com.adventofcode.year2022.day18.Controller day18Controller;
    private final com.adventofcode.year2022.day19.Controller day19Controller;
    private final com.adventofcode.year2022.day20.Controller day20Controller;
    private final com.adventofcode.year2022.day21.Controller day21Controller;

    @Test
    void testDay1() {
        DailyAnswer answer = day1Controller.execute();
        assertEquals(69206, answer.getPart1());
        assertEquals(197400, answer.getPart2());
    }

    @Test
    void testDay2() {
        DailyAnswer answer = day2Controller.execute();
        assertEquals(14827, answer.getPart1());
        assertEquals(13889, answer.getPart2());
    }

    @Test
    void testDay3() {
        DailyAnswer answer = day3Controller.execute();
        assertEquals(7967, answer.getPart1());
        assertEquals(2716, answer.getPart2());
    }

    @Test
    void testDay4() {
        DailyAnswer answer = day4Controller.execute();
        assertEquals(526L, answer.getPart1());
        assertEquals(886L, answer.getPart2());
    }

    @Test
    void testDay5() {
        DailyAnswer answer = day5Controller.execute();
        assertEquals("GRTSWNJHH", answer.getPart1());
        assertEquals("QLFQDBBHM", answer.getPart2());
    }

    @Test
    void testDay6() {
        DailyAnswer answer = day6Controller.execute();
        assertEquals(1198, answer.getPart1());
        assertEquals(3120, answer.getPart2());
    }

    @Test
    void testDay7() {
        DailyAnswer answer = day7Controller.execute();
        assertEquals(1501149L, answer.getPart1());
        assertEquals(10096985L, answer.getPart2());
    }

    @Test
    void testDay8() {
        DailyAnswer answer = day8Controller.execute();
        assertEquals(1560, answer.getPart1());
        assertEquals(252000, answer.getPart2());
    }

    @Test
    void testDay9() {
        DailyAnswer answer = day9Controller.execute();
        assertEquals(6190, answer.getPart1());
        assertEquals(2516, answer.getPart2());
    }

    @Test
    void testDay10() {
        DailyAnswer answer = day10Controller.execute();
        assertEquals(17020, answer.getPart1());
        assertEquals("""
                ###..#....####.####.####.#.....##..####.
                #..#.#....#.......#.#....#....#..#.#....
                #..#.#....###....#..###..#....#....###..
                ###..#....#.....#...#....#....#.##.#....
                #.#..#....#....#....#....#....#..#.#....
                #..#.####.####.####.#....####..###.####.
                """, answer.getPart2());
    }

    @Test
    void testDay11() {
        DailyAnswer answer = day11Controller.execute();
        assertEquals(90294L, answer.getPart1());
        assertEquals(18170818354L, answer.getPart2());
    }

    @Test
    void testDay12() {
        DailyAnswer answer = day12Controller.execute();
        assertEquals(420, answer.getPart1());
        assertEquals(414, answer.getPart2());
    }

    @Test
    void testDay13() {
        DailyAnswer answer = day13Controller.execute();
        assertEquals(4821, answer.getPart1());
        assertEquals(21890, answer.getPart2());
    }

    @Test
    void testDay14() {
        DailyAnswer answer = day14Controller.execute();
        assertEquals(618L, answer.getPart1());
        assertEquals(26358L, answer.getPart2());
    }

    @Test
    void testDay15() {
        DailyAnswer answer = day15Controller.execute();
        assertEquals(5144286, answer.getPart1());
        assertEquals(10229191267339L, answer.getPart2());
    }

    @Test
    void testDay16() {
        DailyAnswer answer = day16Controller.execute();
        assertEquals(1647, answer.getPart1());
        assertEquals(2169, answer.getPart2());
    }

    @Test
    void testDay17() {
        DailyAnswer answer = day17Controller.execute();
        assertEquals(3100, answer.getPart1());
        // assertEquals(1540634005751, answer.getPart2());
    }

    @Test
    void testDay18() {
        DailyAnswer answer = day18Controller.execute();
        assertEquals(3576, answer.getPart1());
        assertEquals(2066, answer.getPart2());
    }

    @Test
    @Disabled("Runtime currently 18min")
    void testDay19() {
        DailyAnswer answer = day19Controller.execute();
        assertEquals(2341, answer.getPart1());
        assertEquals(3689, answer.getPart2());
    }

    @Test
    void testDay20() {
        DailyAnswer answer = day20Controller.execute();
        assertEquals(3700L, answer.getPart1());
        assertEquals(10626948369382L, answer.getPart2());
    }

    @Test
    void testDay21() {
        DailyAnswer answer = day21Controller.execute();
        assertEquals(121868120894282L, answer.getPart1());
        assertEquals(3582317956029L, answer.getPart2());
    }

}
