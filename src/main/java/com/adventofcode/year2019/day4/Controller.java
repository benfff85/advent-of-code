package com.adventofcode.year2019.day4;

import static java.lang.Integer.parseInt;
import static java.lang.Math.max;
import org.springframework.stereotype.Component;
import com.adventofcode.common.DailyAnswer;
import com.adventofcode.common.SolutionController;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component("controller-day-4")
public class Controller extends SolutionController {

    private boolean isInitialMappingApplied = false;

    public DailyAnswer execute() {
        int lowerBound = parseInt(puzzleInput.get(0).split("-")[0]);
        int upperBound = parseInt(puzzleInput.get(0).split("-")[1]);

        short initialA = (short) (lowerBound / 100000);
        short initialB = (short) ((lowerBound / 10000) % 10);
        short initialC = (short) ((lowerBound / 1000) % 10);
        short initialD = (short) ((lowerBound / 100) % 10);
        short initialE = (short) ((lowerBound / 10) % 10);
        short initialF = (short) (lowerBound % 10);


        int passwordCount = 0;
        int passwordCountWithRepeatingDigitsNotPartOfLargerGroup = 0;

        // A reasonable place to use recursion but not needed since stack depth is not dynamic
        outermostLoop: for (short a = calculateLoopStartingValue(initialA, (short) 0); a < 10; a++) {
            for (short b = calculateLoopStartingValue(initialB, a); b < 10; b++) {
                for (short c = calculateLoopStartingValue(initialC, b); c < 10; c++) {
                    for (short d = calculateLoopStartingValue(initialD, c); d < 10; d++) {
                        for (short e = calculateLoopStartingValue(initialE, d); e < 10; e++) {
                            for (short f = calculateLoopStartingValue(initialF, e); f < 10; f++) {
                                // Once initial mapping has been applied, disable the logic and perform standard incrementing
                                if (!isInitialMappingApplied) {
                                    isInitialMappingApplied = true;
                                    log.info("Initial mapping applied!");
                                }

                                if ((parseInt("" + a + b + c + d + e + f + "") > upperBound)) {
                                    break outermostLoop;
                                }


                                if (containsRepeatingDigit(a, b, c, d, e, f)) {
                                    passwordCount++;
                                }

                                if (containsRepeatingDigitsNotPartOfLargerGroup(a, b, c, d, e, f)) {
                                    passwordCountWithRepeatingDigitsNotPartOfLargerGroup++;
                                }

                            }
                        }
                    }
                }
            }
        }
        answer.setPart1(passwordCount);
        log.info("Count of passwords: {}", passwordCount);
        answer.setPart2(passwordCountWithRepeatingDigitsNotPartOfLargerGroup);
        log.info("Count of passwords where repeating digit is not part of larger group: {}", passwordCountWithRepeatingDigitsNotPartOfLargerGroup);
        return answer;
    }

    private boolean containsRepeatingDigit(short a, short b, short c, short d, short e, short f) {
        return a == b || b == c || c == d || d == e || e == f;
    }

    private boolean containsRepeatingDigitsNotPartOfLargerGroup(short a, short b, short c, short d, short e, short f) {
        return (a == b && b != c) ||
                (a != b && b == c && c != d) ||
                (b != c && c == d && d != e) ||
                (c != d && d == e && e != f) ||
                (d != e && e == f);
    }

    private short calculateLoopStartingValue(short initialLoopValue, short wrappingLoopValue) {
        if (!isInitialMappingApplied) {
            return (short) max(initialLoopValue, wrappingLoopValue);
        }
        return wrappingLoopValue;
    }

}
