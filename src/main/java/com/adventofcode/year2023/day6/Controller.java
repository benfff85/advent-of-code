package com.adventofcode.year2023.day6;

import static java.lang.Long.parseLong;
import static org.apache.commons.lang3.StringUtils.deleteWhitespace;
import static org.apache.commons.lang3.StringUtils.normalizeSpace;
import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;
import org.springframework.stereotype.Component;
import com.adventofcode.common.DailyAnswer;
import com.adventofcode.common.SolutionController;
import lombok.extern.slf4j.Slf4j;


@Slf4j
@Component("controller-2023-6")
public class Controller extends SolutionController {

    public DailyAnswer execute() {


        List<Long> times = Arrays.stream(normalizeSpace(puzzleInput.get(0)).split(" ")).skip(1).mapToLong(Long::parseLong).boxed().toList();
        List<Long> recordDistances = Arrays.stream(normalizeSpace(puzzleInput.get(1)).split(" ")).skip(1).mapToLong(Long::parseLong).boxed().toList();
        List<Race> races = IntStream.range(0, times.size()).mapToObj(i -> new Race(times.get(i), recordDistances.get(i))).toList();
        int x = races.stream().map(r -> r.getWinningOptions().size()).mapToInt(Integer::intValue).reduce(1, (a, b) -> a * b);

        answer.setPart1(x);
        log.info("Part 1: {}", answer.getPart1());

        Race race = new Race(parseLong(deleteWhitespace(puzzleInput.get(0).split(":")[1])), parseLong(deleteWhitespace(puzzleInput.get(1).split(":")[1])));
        answer.setPart2(race.getWinningOptions().size());
        log.info("Part 2: {}", answer.getPart2());

        return answer;
    }

}
