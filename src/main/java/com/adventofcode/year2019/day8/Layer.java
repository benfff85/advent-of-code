package com.adventofcode.year2019.day8;

import java.util.ArrayList;
import java.util.List;
import com.google.common.collect.HashMultiset;
import com.google.common.collect.Multiset;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Layer {

    private final Integer width;
    private final Integer height;
    private final Integer pixelCount;
    @EqualsAndHashCode.Include
    private final List<List<Integer>> grid;
    private final Multiset<Integer> frequency = HashMultiset.create();

    public Layer(Integer height, Integer width, List<Integer> input) {
        this.width = width;
        this.height = height;
        pixelCount = width * height;
        grid = new ArrayList<>(height);
        for (int i = 0; i < height; i++) {
            grid.add(new ArrayList<>(width));
        }

        List<Integer> row;
        Integer color;
        for (int i = 0; i < height; i++) {
            row = grid.get(i);
            for (int j = 0; j < width; j++) {
                color = input.get((i * width) + j);
                row.add(color);
                frequency.add(color);
            }
        }

    }

    public Integer getCountOf(Integer integer) {
        return frequency.count(integer);
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        List<Integer> row;
        for (int i = 0; i < height; i++) {
            row = grid.get(i);
            for (int j = 0; j < width; j++) {
                sb.append(row.get(j));
            }
            sb.append("\n");
        }
        return sb.toString();
    }

}
