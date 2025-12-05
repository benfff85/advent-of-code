package com.adventofcode.year2022.day11;

import java.math.BigInteger;
import java.util.*;
import org.springframework.stereotype.Component;
import com.adventofcode.common.DailyAnswer;
import com.adventofcode.common.SolutionController;
import lombok.extern.slf4j.Slf4j;


@Slf4j
@Component("controller-2022-11")
public class Controller extends SolutionController {

    public DailyAnswer execute() {

        List<Monkey> monkeys = initMonkeys();
        processRounds(monkeys, 20);
        answer.setPart1(getProductOfInteractionCountOf2MostActiveMonkeys(monkeys));
        log.info("P1: {}", answer.getPart1());

        monkeys = initMonkeys();
        monkeys.forEach(m -> m.setWorryReductionType(WorryReductionType.MOD_BY_GCM));
        processRounds(monkeys, 10000);
        answer.setPart2(getProductOfInteractionCountOf2MostActiveMonkeys(monkeys));
        log.info("P2: {}", answer.getPart2());

        return answer;

    }

    private void processRounds(List<Monkey> monkeys, Integer roundCount) {
        Queue<Item> output;
        for (int i = 0; i < roundCount; i++) {
            for (Monkey monkey : monkeys) {
                output = monkey.processTurn();
                for (Item item : output) {
                    monkeys.get(item.getMonkeyTarget()).addItem(item);
                }
            }
        }
    }

    private Long getProductOfInteractionCountOf2MostActiveMonkeys(List<Monkey> monkeys) {
        return monkeys.stream()
                .map(Monkey::getInspectionCount)
                .sorted()
                .skip(monkeys.size() - 2L)
                .map(Integer::longValue)
                .reduce((x, y) -> x * y)
                .orElse(0L);
    }

    private List<Monkey> initMonkeys() {
        List<Monkey> monkeys = new ArrayList<>(8);

        monkeys.add(new Monkey("66, 59, 64, 51", 2, 1, 4) {
            public BigInteger getNewWorryLevel(BigInteger worryLevel) {
                return worryLevel.multiply(BigInteger.valueOf(3));
            }
        });

        monkeys.add(new Monkey("67, 61", 7, 3, 5) {
            public BigInteger getNewWorryLevel(BigInteger worryLevel) {
                return worryLevel.multiply(BigInteger.valueOf(19));
            }
        });

        monkeys.add(new Monkey("86, 93, 80, 70, 71, 81, 56", 11, 4, 0) {
            public BigInteger getNewWorryLevel(BigInteger worryLevel) {
                return worryLevel.add(BigInteger.valueOf(2));
            }
        });

        monkeys.add(new Monkey("94", 19, 7, 6) {
            public BigInteger getNewWorryLevel(BigInteger worryLevel) {
                return worryLevel.multiply(worryLevel);
            }
        });

        monkeys.add(new Monkey("71, 92, 64", 3, 5, 1) {
            public BigInteger getNewWorryLevel(BigInteger worryLevel) {
                return worryLevel.add(BigInteger.valueOf(8));
            }
        });

        monkeys.add(new Monkey("58, 81, 92, 75, 56", 5, 3, 6) {
            public BigInteger getNewWorryLevel(BigInteger worryLevel) {
                return worryLevel.add(BigInteger.valueOf(6));
            }
        });

        monkeys.add(new Monkey("82, 98, 77, 94, 86, 81", 17, 7, 2) {
            public BigInteger getNewWorryLevel(BigInteger worryLevel) {
                return worryLevel.add(BigInteger.valueOf(7));
            }
        });

        monkeys.add(new Monkey("54, 95, 70, 93, 88, 93, 63, 50", 13, 2, 0) {
            public BigInteger getNewWorryLevel(BigInteger worryLevel) {
                return worryLevel.add(BigInteger.valueOf(4));
            }
        });

        return monkeys;
    }

}
