package com.adventofcode.year2022.day1;

import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Component;
import com.adventofcode.common.DailyAnswer;
import com.adventofcode.common.SolutionController;
import lombok.extern.slf4j.Slf4j;


@Slf4j
@Component("controller-2022-1")
public class Controller extends SolutionController {

    public DailyAnswer execute() {

        List<Elf> elves = new ArrayList<>();
        Elf elf = new Elf();
        for (String string : puzzleInput) {
            if (!string.isEmpty()) {
                elf.addSnack(Integer.parseInt(string));
            } else {
                elves.add(elf);
                elf = new Elf();
            }
        }
        elves.add(elf);

        List<Integer> sumsOfCalories = new ArrayList<>(elves.stream().map(Elf::getTotalCalories).sorted().toList());

        answer.setPart1(sumsOfCalories.get(sumsOfCalories.size() - 1));
        log.info("Max Calories carried by an elf: {}", answer.getPart1());

        answer.setPart2(sumsOfCalories.subList(sumsOfCalories.size() - 3, sumsOfCalories.size()).stream().mapToInt(Integer::intValue).sum());
        log.info("Total calories carried by top three elves: {}", answer.getPart2());

        return answer;
    }

}
