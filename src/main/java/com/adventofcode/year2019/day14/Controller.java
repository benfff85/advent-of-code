package com.adventofcode.year2019.day14;

import org.springframework.stereotype.Component;
import com.adventofcode.common.*;
import lombok.extern.slf4j.Slf4j;


@Slf4j
@Component("controller-day-14")
public class Controller extends SolutionController {

    private final ReactionRegistry reactionRegistry;
    private final ChemicalInventory chemicalInventory;

    // TODO Remove constructor
    public Controller(InputHelper inputHelper, ReactionRegistry reactionRegistry, ChemicalInventory chemicalInventory) {
        super(inputHelper, "puzzle-input/2019/day-14.txt");
        this.reactionRegistry = reactionRegistry;
        this.chemicalInventory = chemicalInventory;
    }

    public DailyAnswer execute() {

        reactionRegistry.init(puzzleInput);
        long fuel = 0;

        try {
            // Some arbitrary large number that will cause ORE to be exhausted and an exception thrown
            while (fuel <= 1000000000) {
                reactionRegistry.executeReaction("FUEL", 1L);
                fuel++;
                chemicalInventory.useChemical("FUEL", 1L);

                if (fuel == 1) {
                    answer.setPart1(Config.INITIAL_ORE - chemicalInventory.getChemicalQuantity("ORE"));
                    log.info("Ore required to generate one fuel: {}", answer.getPart1());
                }

            }
        } catch (Exception e) {
            answer.setPart2(fuel);
            log.info("Total fuel created with one trillion ORE: {}", answer.getPart2());
        }

        return answer;
    }

}
