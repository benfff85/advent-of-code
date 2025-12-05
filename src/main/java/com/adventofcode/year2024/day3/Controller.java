package com.adventofcode.year2024.day3;

import java.util.List;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.springframework.stereotype.Component;
import com.adventofcode.common.DailyAnswer;
import com.adventofcode.common.SolutionController;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component("controller-2024-3")
public class Controller extends SolutionController {

    public DailyAnswer execute() {

        TreeMap<Integer, String> matches = parse("mul\\((\\d+),(\\d+)\\)", puzzleInput);
        answer.setPart1(matches.values()
                .stream()
                .map(MultiplyCommand::new)
                .mapToInt(MultiplyCommand::getProduct)
                .sum());
        log.info("Part 1: {}", answer.getPart1());


        TreeMap<Integer, String> doIndices = parse("do\\(\\)", puzzleInput);
        doIndices.put(0, null);
        TreeMap<Integer, String> dontIndices = parse("don't\\(\\)", puzzleInput);
        dontIndices.put(Integer.MIN_VALUE, null);

        answer.setPart2(matches.entrySet()
                .stream()
                .filter(p -> !isCorrupt(p.getKey(), doIndices, dontIndices))
                .map(s -> new MultiplyCommand(s.getValue()))
                .mapToInt(MultiplyCommand::getProduct)
                .sum());
        log.info("Part 2: {}", answer.getPart2());

        return answer;
    }

    private boolean isCorrupt(Integer left, TreeMap<Integer, String> doIndices, TreeMap<Integer, String> dontIndices) {
        return dontIndices.lowerKey(left) > doIndices.lowerKey(left);
    }

    private TreeMap<Integer, String> parse(String regex, List<String> puzzleInput) {

        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(puzzleInput.stream().reduce((String::concat)).orElseThrow());
        TreeMap<Integer, String> matches = new TreeMap<>();
        while (matcher.find()) {
            matches.put(matcher.start(), matcher.group());
        }
        return matches;

    }

}
