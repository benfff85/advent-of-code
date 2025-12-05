package com.adventofcode.year2025.day5;

import java.util.List;
import org.springframework.stereotype.Component;
import com.adventofcode.common.DailyAnswer;
import com.adventofcode.common.SolutionController;
import lombok.extern.slf4j.Slf4j;


@Slf4j
@Component("controller-2025-5")
public class Controller extends SolutionController {

    public DailyAnswer execute() {

        List<Range> ranges = new java.util.ArrayList<>(puzzleInput.stream().filter(s -> s.contains("-")).map(Range::new).toList());
        log.info("Ranges: {}", ranges);

        List<Long> ingredients = puzzleInput.stream().skip(ranges.size() + 1).map(Long::parseLong).toList();
        log.info("Ingredients: {}", ingredients);

        int countFreshIngredients = 0;
        for (Long ingredient : ingredients) {
            if (isInRange(ranges, ingredient)) {
                countFreshIngredients++;
            }
        }
        answer.setPart1(countFreshIngredients);


        ranges.sort((r1, r2) -> Long.compare(r1.getFirstId(), r2.getFirstId()));
        long totalFreshIngredients = 0;
        long currentStart = ranges.get(0).getFirstId();
        long currentEnd = ranges.get(0).getLastId();
        for (int i = 1; i < ranges.size(); i++) {
            Range nextRange = ranges.get(i);
            if (nextRange.getFirstId() <= currentEnd + 1) {
                currentEnd = Math.max(currentEnd, nextRange.getLastId());
            } else {
                totalFreshIngredients += (currentEnd - currentStart + 1);
                currentStart = nextRange.getFirstId();
                currentEnd = nextRange.getLastId();
            }
        }
        totalFreshIngredients += (currentEnd - currentStart + 1);
        answer.setPart2(totalFreshIngredients);

        return answer;

    }

    private boolean isInRange(List<Range> ranges, Long ingredient) {
        for (Range range : ranges) {
            if (ingredient >= range.getFirstId() && ingredient <= range.getLastId()) {
                return true;
            }
        }
        return false;
    }

}
