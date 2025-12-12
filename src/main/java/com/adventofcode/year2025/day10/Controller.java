package com.adventofcode.year2025.day10;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.springframework.stereotype.Component;
import com.adventofcode.common.DailyAnswer;
import com.adventofcode.common.SolutionController;
import com.microsoft.z3.*;
import lombok.extern.slf4j.Slf4j;


@Slf4j
@Component("controller-2025-10")
public class Controller extends SolutionController {

    public DailyAnswer execute() {
        List<Machine> machines = parseInput();
        long totalPresses = machines.stream().mapToLong(this::solveMachine).sum();

        answer.setPart1(totalPresses);
        log.info("Part 1: {}", answer.getPart1());

        long totalPressesPart2 = machines.stream().mapToLong(this::solveMachinePart2).sum();
        answer.setPart2(totalPressesPart2);
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

    private long solveMachinePart2(Machine machine) {
        try (Context ctx = new Context()) {
            Optimize optimize = ctx.mkOptimize();

            List<IntExpr> buttonPresses = new ArrayList<>();
            for (int i = 0; i < machine.getButtons().size(); i++) {
                IntExpr x = ctx.mkIntConst("x_" + i);
                buttonPresses.add(x);
                optimizeAdd(optimize, ctx.mkGe(x, ctx.mkInt(0)));
            }

            // For each joltage counter
            for (int c = 0; c < machine.getJoltageRequirements().size(); c++) {
                ArithExpr<IntSort> sum = ctx.mkInt(0);
                for (int b = 0; b < machine.getButtons().size(); b++) {
                    List<Integer> buttonEffects = machine.getButtons().get(b);
                    // Check if button b affects counter c
                    // Button definition: list of INDICES it affects.
                    // Joltage mode: "button ... indicates which counters it affects"
                    // It seems the indices in the input (e.g. (1,3)) refer to indices of counters as well.
                    // "where 0 means the first counter, 1 means the second counter"
                    // So if button has index 'c', it adds 1 to counter 'c'.

                    if (buttonEffects.contains(c)) {
                        sum = mkAdd(ctx, sum, buttonPresses.get(b));
                    }
                }
                optimizeAdd(optimize, ctx.mkEq(sum, ctx.mkInt(machine.getJoltageRequirements().get(c))));
            }

            ArithExpr<IntSort> totalPresses = ctx.mkInt(0);
            for (IntExpr x : buttonPresses) {
                totalPresses = mkAdd(ctx, totalPresses, x);
            }

            optimize.MkMinimize(totalPresses);

            if (optimizeCheck(optimize) == Status.SATISFIABLE) {
                // Evaluate the total presses from the model
                // Since we minimized totalPresses, we can just get the value of that expression or sum individual values.
                // Let's sum individual values to be safe and explicit.
                long total = 0;
                for (IntExpr x : buttonPresses) {
                    // evaluate returns Expr, converting to string and parsing likely safe for integer model
                    // but strict parsing is better. Z3 integer values are usually simple decimals in toString.
                    total += Long.parseLong(optimize.getModel().evaluate(x, false).toString());
                }
                return total;
            } else {
                throw new RuntimeException("Unsatisfiable joltage requirements for machine: " + machine);
            }
        }
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

    @SafeVarargs
    private final ArithExpr<IntSort> mkAdd(Context ctx, ArithExpr<IntSort>... args) {
        return ctx.mkAdd(args);
    }

    @SafeVarargs
    private final void optimizeAdd(Optimize optimize, Expr<BoolSort>... constraints) {
        optimize.Add(constraints);
    }

    @SafeVarargs
    private final Status optimizeCheck(Optimize optimize, Expr<BoolSort>... assumptions) {
        return optimize.Check(assumptions);
    }
}
