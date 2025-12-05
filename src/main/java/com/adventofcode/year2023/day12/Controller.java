package com.adventofcode.year2023.day12;

import java.util.HashMap;
import java.util.List;
import org.springframework.stereotype.Component;
import com.adventofcode.common.DailyAnswer;
import com.adventofcode.common.SolutionController;
import lombok.extern.slf4j.Slf4j;


@Slf4j
@Component("controller-2023-12")
public class Controller extends SolutionController {

    public DailyAnswer execute() {

        List<SpringRow> springs = puzzleInput.stream().map(s -> new SpringRow(s, false)).toList();

        answer.setPart1(springs.stream().mapToLong(SpringRow::getCombinationsCount).sum());
        log.info("Part 1: {}", answer.getPart1());

        springs = puzzleInput.stream().map(s -> new SpringRow(s, true)).toList();

        answer.setPart2(springs.stream().mapToLong(this::countPatterns).sum());
        log.info("Part 2: {}", answer.getPart2());

        return answer;

    }

    private long countPatterns(SpringRow sprintRow) {
        return countPatterns(0, 0, 0, sprintRow, new HashMap<>());
    }

    private long countPatterns(int i, int j, int r, SpringRow springRow, HashMap<List<Integer>, Long> memo) {
        int springCount = springRow.getSprings().size();
        int brokenSpringGroupCount = springRow.getKnownDamagedSpringGroupings().size();

        if (j == springCount) {
            if (i == j && brokenSpringGroupCount == r) {
                return 1;
            }
            if (brokenSpringGroupCount - 1 == r && j - i == springRow.getKnownDamagedSpringGroupings().get(r)) {
                return 1;
            }
            return 0;
        }
        if (i >= springCount || j >= springCount || r > brokenSpringGroupCount) {
            return 0;
        }

        List<Integer> pos = List.of(i, j, r);
        if (memo.containsKey(pos)) {
            return memo.get(pos);
        }

        SpringState c = springRow.getSprings().get(j);
        if (SpringState.BROKEN.equals(c)) {
            long result = countPatterns(i, j + 1, r, springRow, memo);
            memo.put(pos, result);
            return result;
        }
        if (SpringState.OPERATIONAL.equals(c)) {
            if (i != j) {
                if (r == brokenSpringGroupCount || springRow.getKnownDamagedSpringGroupings().get(r) != j - i) {
                    return 0;
                }
                i = j;
                r++;
            }
            long result = countPatterns(i + 1, j + 1, r, springRow, memo);
            memo.put(pos, result);
            return result;
        }

        long result = countPatterns(i, j + 1, r, springRow, memo);
        if (i != j) {
            if (r == brokenSpringGroupCount || springRow.getKnownDamagedSpringGroupings().get(r) != j - i) {
                memo.put(pos, result);
                return result;
            }
            i = j;
            r++;
        }
        result += countPatterns(i + 1, j + 1, r, springRow, memo);
        memo.put(pos, result);
        return result;
    }



}
