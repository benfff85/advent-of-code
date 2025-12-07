package com.adventofcode.year2025.day6;

import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Component;
import com.adventofcode.common.DailyAnswer;
import com.adventofcode.common.SolutionController;
import lombok.extern.slf4j.Slf4j;


@Slf4j
@Component("controller-2025-6")
public class Controller extends SolutionController {

    public DailyAnswer execute() {


        List<Problem> problems = new ArrayList<>();

        int lineLength = puzzleInput.getFirst().length();
        int leftIndex = 0;
        for (int rightIndex = 1; rightIndex < lineLength; rightIndex++) {
            if (isColumnAllSpaces(rightIndex)) {
                problems.add(initializeProblem(leftIndex, rightIndex));
                leftIndex = rightIndex + 1;
                rightIndex++;
            }
        }
        problems.add(initializeProblem(leftIndex, lineLength));

        answer.setPart1(problems.stream().mapToLong(Problem::evaluatePart1).sum());
        answer.setPart2(problems.stream().mapToLong(Problem::evaluatePart2).sum());
        return answer;
    }



    private boolean isColumnAllSpaces(int rightIndex) {
        return puzzleInput.stream().filter(row -> row.charAt(rightIndex) != ' ').count() == 0;
    }

    private Problem initializeProblem(int leftIndex, int rightIndex) {
        Problem problem = new Problem();

        puzzleInput.subList(0, puzzleInput.size() - 1)
                .stream()
                .map(row -> row.substring(leftIndex, rightIndex))
                .forEach(problem::addNumber);
        problem.setOperator(puzzleInput.getLast().substring(leftIndex, rightIndex).trim());

        return problem;
    }

}
