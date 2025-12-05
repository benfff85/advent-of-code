package com.adventofcode.year2022.day11;

import static java.lang.Integer.parseInt;
import java.math.BigInteger;
import java.util.*;
import lombok.Getter;
import lombok.Setter;

public abstract class Monkey {

    private final List<Item> items;
    private final BigInteger modValue;
    private final Integer monkey1;
    private final Integer monkey2;
    @Setter
    private WorryReductionType worryReductionType = WorryReductionType.DIVIDE_BY_THREE;
    @Getter
    private Integer inspectionCount = 0;


    protected Monkey(String input, Integer modValue, Integer monkey1, Integer monkey2) {
        items = new ArrayList<>(Arrays.stream(input.split(", ")).map(i -> Item.builder().worryLevel(BigInteger.valueOf(parseInt(i))).build()).toList());
        this.modValue = BigInteger.valueOf(modValue);
        this.monkey1 = monkey1;
        this.monkey2 = monkey2;
    }

    public Queue<Item> processTurn() {
        Queue<Item> itemQueue = new LinkedList<>();
        for (Item item : items) {
            item.setWorryLevel(getNewWorryLevel(item.getWorryLevel()));
            inspectionCount++;
            reduceWorry(item);
            item.setMonkeyTarget(getMonkeyTarget(item.getWorryLevel()));
            itemQueue.add(item);
        }
        items.clear();
        return itemQueue;
    }

    private void reduceWorry(Item item) {
        if (worryReductionType.equals(WorryReductionType.DIVIDE_BY_THREE)) {
            item.setWorryLevel(item.getWorryLevel().divide(BigInteger.valueOf(3)));
        } else if (worryReductionType.equals(WorryReductionType.MOD_BY_GCM)) {
            // Hard coded greatest common multiple of mod values across monkeys
            item.setWorryLevel(item.getWorryLevel().mod(BigInteger.valueOf(9699690)));
        }
    }

    public void addItem(Item item) {
        items.add(item);
    }

    public abstract BigInteger getNewWorryLevel(BigInteger worryLevel);

    public Integer getMonkeyTarget(BigInteger worryLevel) {
        return worryLevel.mod(modValue).equals(BigInteger.ZERO) ? monkey1 : monkey2;
    }

}
