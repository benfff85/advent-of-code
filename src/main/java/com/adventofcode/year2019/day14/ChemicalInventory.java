package com.adventofcode.year2019.day14;

import java.util.HashMap;
import java.util.Map;
import org.springframework.stereotype.Component;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class ChemicalInventory {

    private final Map<String, Long> chemicals = new HashMap<>();

    public void addChemical(String chemical, Long quantity) {
        chemicals.put(chemical, chemicals.get(chemical) + quantity);
    }

    public void useChemical(String chemical, Long quantity) {
        chemicals.put(chemical, chemicals.get(chemical) - quantity);
    }

    public Long getChemicalQuantity(String chemical) {
        return chemicals.get(chemical);
    }

    public void initChemical(String s) {
        chemicals.put(s, 0L);
    }
}
