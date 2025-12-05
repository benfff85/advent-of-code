package com.adventofcode.year2023.day15;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;
import org.springframework.stereotype.Component;
import com.adventofcode.common.DailyAnswer;
import com.adventofcode.common.SolutionController;
import lombok.extern.slf4j.Slf4j;


@Slf4j
@Component("controller-2023-15")
public class Controller extends SolutionController {

    public DailyAnswer execute() {

        answer.setPart1(Arrays.stream(puzzleInput.getFirst().split(",")).mapToInt(this::hash).sum());
        log.info("Part 1: {}", answer.getPart1());

        Map<Integer, List<Lens>> boxes = new HashMap<>();
        IntStream.range(0, 256).forEach(i -> boxes.put(i, new LinkedList<>()));

        for (String s : puzzleInput.getFirst().split(",")) {

            Lens lens = new Lens(s);

            List<Lens> box = boxes.get(hash(lens.getLabel()));

            if (lens.isShouldAdd()) {
                if (box.contains(lens)) {
                    int index = box.indexOf(lens);
                    box.remove(lens);
                    box.add(index, lens);
                } else {
                    box.add(lens);
                }
            } else {
                box.remove(lens);
            }

        }

        AtomicInteger sum = new AtomicInteger();
        for (Map.Entry<Integer, List<Lens>> box : boxes.entrySet()) {
            IntStream.range(0, box.getValue().size()).forEach(i -> sum.addAndGet((box.getKey() + 1) * (i + 1) * box.getValue().get(i).getFocalLength()));
        }

        answer.setPart2(sum.get());
        log.info("Part 2: {}", answer.getPart2());

        return answer;

    }

    private int hash(String s) {

        AtomicInteger value = new AtomicInteger();
        s.chars().forEach(a -> {
            value.addAndGet(a);
            value.updateAndGet(v -> v * 17);
            value.updateAndGet(v -> v % 256);
        });

        return value.get();
    }


}
