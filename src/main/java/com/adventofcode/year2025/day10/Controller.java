package com.adventofcode.year2025.day10;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.springframework.stereotype.Component;
import com.adventofcode.common.DailyAnswer;
import com.adventofcode.common.SolutionController;
import lombok.extern.slf4j.Slf4j;


@Slf4j
@Component("controller-2025-10")
public class Controller extends SolutionController {

    public DailyAnswer execute() {
        List<Machine> machines = parseInput();
        long totalPresses = machines.stream().mapToLong(this::solveMachine).sum();

        answer.setPart1(totalPresses);
        log.info("Part 1: {}", answer.getPart1());

        answer.setPart2(null);
        log.info("Part 2: {}", answer.getPart2());

        return answer;
    }

    private long solveMachine(Machine machine) {
        int n = machine.getTargetState().size();
        List<Boolean> startState = new ArrayList<>(Collections.nCopies(n, false));

        Queue<State> queue = new LinkedList<>();
        queue.add(new State(startState, 0));

        Set<List<Boolean>> visited = new HashSet<>();
        visited.add(startState);

        while (!queue.isEmpty()) {
            State current = queue.poll();

            if (current.lights.equals(machine.getTargetState())) {
                return current.presses;
            }

            for (List<Integer> button : machine.getButtons()) {
                List<Boolean> nextLights = new ArrayList<>(current.lights);
                for (int index : button) {
                    if (index >= 0 && index < n) {
                        nextLights.set(index, !nextLights.get(index));
                    }
                }

                if (visited.add(nextLights)) {
                    queue.add(new State(nextLights, current.presses + 1));
                }
            }
        }

        throw new RuntimeException("Could not solve machine: " + machine);
    }

    private List<Machine> parseInput() {
        return puzzleInput.stream().map(this::parseLine).toList();
    }

    private Machine parseLine(String line) {
        // Example: [.##.] (3) (1,3) (2) (2,3) (0,2) (0,1) {3,5,4,7}

        // Parse target state
        String targetStr = line.substring(line.indexOf('[') + 1, line.indexOf(']'));
        List<Boolean> targetState = new ArrayList<>();
        for (char c : targetStr.toCharArray()) {
            targetState.add(c == '#');
        }

        // Parse buttons
        List<List<Integer>> buttons = new ArrayList<>();
        Pattern buttonPattern = Pattern.compile("\\(([^)]+)\\)");
        Matcher buttonMatcher = buttonPattern.matcher(line);
        while (buttonMatcher.find()) {
            String content = buttonMatcher.group(1);
            List<Integer> button = Arrays.stream(content.split(","))
                    .map(String::trim)
                    .map(Integer::parseInt)
                    .toList();
            buttons.add(button);
        }

        // Parse joltage (optional/ignored for now but good to have)
        String joltageStr = line.substring(line.indexOf('{') + 1, line.indexOf('}'));
        List<Integer> joltage = Arrays.stream(joltageStr.split(","))
                .map(String::trim)
                .map(Integer::parseInt)
                .toList();

        return Machine.builder()
                .targetState(targetState)
                .buttons(buttons)
                .joltageRequirements(joltage)
                .build();
    }

    private record State(List<Boolean> lights, int presses) {
    }
}
