package com.adventofcode.common;


import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import jakarta.annotation.PostConstruct;

public abstract class SolutionController {
    @Autowired
    protected InputHelper inputHelper;
    protected List<String> puzzleInput;
    protected DailyAnswer answer = new DailyAnswer();

    protected SolutionController(InputHelper inputHelper, String puzzleInputFileName) {
        this.inputHelper = inputHelper;
        puzzleInput = inputHelper.read(puzzleInputFileName).stream().toList();
    }

    protected SolutionController() {}

    @PostConstruct
    public void init() {
        if (puzzleInput == null) {
            String[] split = this.getClass().getPackageName().split("\\.");
            String year = split[split.length - 2].replace("year", "");
            String day = split[split.length - 1].replace("day", "");
            puzzleInput = inputHelper.read("puzzle-input/" + year + "/day-" + day + ".txt");
        }
    }

    public abstract DailyAnswer execute();

}
