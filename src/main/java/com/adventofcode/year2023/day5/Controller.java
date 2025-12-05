package com.adventofcode.year2023.day5;

import java.util.*;
import java.util.stream.IntStream;
import java.util.stream.LongStream;
import org.springframework.stereotype.Component;
import com.adventofcode.common.DailyAnswer;
import com.adventofcode.common.SolutionController;
import lombok.extern.slf4j.Slf4j;


@Slf4j
@Component("controller-2023-5")
public class Controller extends SolutionController {

    public DailyAnswer execute() {

        List<Long> seeds = Arrays.stream(puzzleInput.getFirst().split(" ")).skip(1).mapToLong(Long::parseLong).boxed().toList();

        List<RangedMap> maps = generateMapsFromInput();

        long min = seeds.stream()
                .mapToLong(seed -> mapSeedToLocation(seed, maps))
                .min()
                .orElseThrow();
        answer.setPart1(min);
        log.info("Part 1: {}", answer.getPart1());


        min = IntStream.range(0, seeds.size())
                .filter(index -> index % 2 == 0)
                .mapToLong(index -> LongStream.range(seeds.get(index), seeds.get(index) + seeds.get(index + 1))
                        .parallel()
                        .map(seedValue -> mapSeedToLocation(seedValue, maps))
                        .min().orElseThrow())
                .min()
                .orElseThrow();
        answer.setPart2(min);
        log.info("Part 2: {}", answer.getPart2());

        return answer;
    }

    private Long mapSeedToLocation(long seed, List<RangedMap> maps) {
        long location = seed;
        for (RangedMap map : maps) {
            location = map.get(location);
        }
        return location;
    }

    private List<RangedMap> generateMapsFromInput() {
        List<RangedMap> maps = new ArrayList<>();
        RangedMap map;
        for (int i = 3; i < puzzleInput.size(); i += map.size() + 2) {
            map = generateMapFromInput(i);
            maps.add(map);
        }
        return maps;
    }

    private RangedMap generateMapFromInput(int i) {
        RangedMap map = new RangedMap();
        puzzleInput.stream()
                .skip(i)
                .takeWhile(s -> !s.isEmpty())
                .forEach(s -> {
                    String[] split = s.split(" ");
                    map.put(Long.parseLong(split[1]), Long.parseLong(split[0]), Long.parseLong(split[2]));
                });
        return map;
    }

}
