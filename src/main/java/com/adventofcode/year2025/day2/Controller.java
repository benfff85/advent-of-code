package com.adventofcode.year2025.day2;

import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Component;

import com.adventofcode.common.DailyAnswer;
import com.adventofcode.common.InputHelper;
import com.adventofcode.common.SolutionController;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component("controller-2025-2")
public class Controller extends SolutionController {

    public Controller(InputHelper inputHelper) {
        super(inputHelper, "puzzle-input/2025/day-2.txt");
    }

    public DailyAnswer execute() {

        List<Range> ranges = Arrays.stream(puzzleInput.getFirst().split(",")).map(Range::new).toList();
        log.info("Ranges: {}", ranges);

        long invalidSum1 = 0;
        long invalidMultiOccuranceSum1 = 0;

        for (Range range : ranges) {

            for (long id = range.getFirstId(); id <= range.getLastId(); id++) {

                if (isInvalid(id)) {
                    invalidSum1 += id;
                }

                if (isInvalidMultiOccurance(id)) {
                    invalidMultiOccuranceSum1 += id;
                }

            }
        }

        answer.setPart1(invalidSum1);
        answer.setPart2(invalidMultiOccuranceSum1);

        return answer;
    }

    private boolean isInvalid(long id) {
        String idStr = Long.toString(id);
        if (idStr.length() % 2 != 0) {
            return false;
        }
        int halfLength = idStr.length() / 2;
        for (int i = 0; i < halfLength; i++) {
            if (idStr.charAt(i) != idStr.charAt(i + halfLength)) {
                return false;
            }
        }
        return true;
    }

    private boolean isInvalidMultiOccurance(long id) {
        String idStr = Long.toString(id);
        int len = idStr.length();
        if (len < 2) {
            return false;
        }

        for (int patternLen = 1; patternLen <= len / 2; patternLen++) {
            if (len % patternLen == 0) {
                boolean matches = true;
                for (int i = patternLen; i < len; i++) {
                    if (idStr.charAt(i) != idStr.charAt(i % patternLen)) {
                        matches = false;
                        break;
                    }
                }
                if (matches) {
                    return true;
                }
            }
        }
        return false;
    }

}
