package com.adventofcode.year2019.day14;

import java.util.*;
import org.springframework.stereotype.Component;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class ReactionRegistry {

    private final Map<String, Reaction> reactions = new HashMap<>();
    private final ChemicalInventory chemicalInventory;

    public ReactionRegistry(ChemicalInventory chemicalInventory) {
        this.chemicalInventory = chemicalInventory;
    }

    public void init(List<String> input) {

        String outputChemical;
        long outputChemicalQuantity;
        for (String s : input) {
            outputChemical = s.split(" => ")[1].split(" ")[1];
            chemicalInventory.initChemical(outputChemical);
            outputChemicalQuantity = Integer.parseInt(s.split(" => ")[1].split(" ")[0]);

            Map<String, Integer> inputChemicals = new HashMap<>();
            String[] inputChemStrings = s.split(" => ")[0].split(",");
            for (String inputChemString : inputChemStrings) {
                inputChemicals.put(inputChemString.trim().split(" ")[1], Integer.parseInt(inputChemString.trim().split(" ")[0]));
            }
            reactions.put(outputChemical, new Reaction(inputChemicals, outputChemical, outputChemicalQuantity));
        }

        chemicalInventory.initChemical("ORE");
        chemicalInventory.addChemical("ORE", Config.INITIAL_ORE);

    }

    // TODO Currently not optimized, possible use for Memoization but current runtime is only about 100s
    public void executeReaction(String desiredChemical, Long desiredAmount) {

        // If we already have what we want just return
        long amountOfChemicalNeeded = desiredAmount - chemicalInventory.getChemicalQuantity(desiredChemical);
        if (amountOfChemicalNeeded <= 0) {
            return;
        }

        // If we don't have the desired amount, check how many times we need to execute the reaction to get it
        Reaction r = reactions.get(desiredChemical);
        long numberOfReactionsNeeded = Math.ceilDiv(amountOfChemicalNeeded, r.outputChemicalQuantity());

        // Get required chemicals
        Map<String, Integer> inputs = r.inputChemicals();
        for (Map.Entry<String, Integer> input : inputs.entrySet()) {
            executeReaction(input.getKey(), input.getValue() * numberOfReactionsNeeded);
            chemicalInventory.useChemical(input.getKey(), input.getValue() * numberOfReactionsNeeded);
        }

        chemicalInventory.addChemical(desiredChemical, r.outputChemicalQuantity() * numberOfReactionsNeeded);

    }

}
