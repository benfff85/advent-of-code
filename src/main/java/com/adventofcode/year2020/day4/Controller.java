package com.adventofcode.year2020.day4;

import com.adventofcode.common.DailyAnswer;
import com.adventofcode.common.InputHelper;
import com.adventofcode.common.SolutionController;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

import static org.apache.commons.lang3.StringUtils.isNotEmpty;

@Slf4j
@Component("controller-2020-4")
public class Controller extends SolutionController {

    public Controller(InputHelper inputHelper) {
        super(inputHelper, "puzzle-input/2020/day-4.txt");
    }

    public DailyAnswer execute() {

        List<Passport> passports = new ArrayList<>();
        StringBuilder fullPassportText = new StringBuilder();
        for (String string : puzzleInput) {
            if (isNotEmpty(string)) {
                fullPassportText.append(string).append(" ");
            } else {
                passports.add(new Passport(fullPassportText.append(" ").toString()));
                fullPassportText.setLength(0);
            }
        }
        passports.add(new Passport(fullPassportText.append(" ").toString()));

        answer.setPart1(passports.stream().filter(Passport::isValidPart1).count());
        log.info("Part 1: {}", answer.getPart1());

        answer.setPart2(passports.stream().filter(Passport::isValidPart1).filter(Passport::isValidPart2).count());
        log.info("Part 2: {}", answer.getPart2());

        return answer;
    }

}
