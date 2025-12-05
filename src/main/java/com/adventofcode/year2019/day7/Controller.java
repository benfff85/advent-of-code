package com.adventofcode.year2019.day7;

import java.math.BigInteger;
import java.util.*;
import org.springframework.stereotype.Component;
import com.adventofcode.common.DailyAnswer;
import com.adventofcode.common.SolutionController;
import com.adventofcode.year2019.day5.IntComputerContext;
import com.google.common.collect.Collections2;
import com.google.common.collect.Iterables;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component("controller-day-7")
public class Controller extends SolutionController {

    public DailyAnswer execute() {
        List<BigInteger> input = inputHelper.parseStringToBigIntList(puzzleInput.get(0));

        BigInteger maxSignal = BigInteger.ZERO;
        List<Amplifier> amplifiers;
        Collection<List<BigInteger>> phaseSettingPermutations = Collections2.permutations(List.of(BigInteger.valueOf(0), BigInteger.valueOf(1), BigInteger.valueOf(2), BigInteger.valueOf(3), BigInteger.valueOf(4)));
        BigInteger output;
        for (List<BigInteger> phaseSetting : phaseSettingPermutations) {
            amplifiers = initAmplifiers(input);
            output = amplifiers.get(0).run(new LinkedList<>(List.of(phaseSetting.get(0), BigInteger.valueOf(0)))).getOutputs().pop();
            output = amplifiers.get(1).run(new LinkedList<>(List.of(phaseSetting.get(1), output))).getOutputs().pop();
            output = amplifiers.get(2).run(new LinkedList<>(List.of(phaseSetting.get(2), output))).getOutputs().pop();
            output = amplifiers.get(3).run(new LinkedList<>(List.of(phaseSetting.get(3), output))).getOutputs().pop();
            output = amplifiers.get(4).run(new LinkedList<>(List.of(phaseSetting.get(4), output))).getOutputs().pop();

            if (output.compareTo(maxSignal) > 0) {
                maxSignal = output;
            }
        }
        answer.setPart1(maxSignal);
        log.info("Max signal {}", answer.getPart1());


        BigInteger signal;
        maxSignal = BigInteger.ZERO;
        phaseSettingPermutations = Collections2.permutations(List.of(BigInteger.valueOf(5), BigInteger.valueOf(6), BigInteger.valueOf(7), BigInteger.valueOf(8), BigInteger.valueOf(9)));
        IntComputerContext outputContext;
        for (List<BigInteger> phaseSetting : phaseSettingPermutations) {
            amplifiers = initAmplifiers(input);

            // Initial loop iteration with phase inputs
            outputContext = amplifiers.get(0).run(new LinkedList<>(List.of(phaseSetting.get(0), BigInteger.valueOf(0))));
            outputContext = amplifiers.get(1).run(generateQueueOfPhaseAndPriorOutputs(phaseSetting.get(1), outputContext.getOutputs()));
            outputContext = amplifiers.get(2).run(generateQueueOfPhaseAndPriorOutputs(phaseSetting.get(2), outputContext.getOutputs()));
            outputContext = amplifiers.get(3).run(generateQueueOfPhaseAndPriorOutputs(phaseSetting.get(3), outputContext.getOutputs()));
            outputContext = amplifiers.get(4).run(generateQueueOfPhaseAndPriorOutputs(phaseSetting.get(4), outputContext.getOutputs()));

            // It doesn't seem to be the case, but we must be careful amplifier E has not already stopped
            for (Amplifier amplifier : Iterables.cycle(amplifiers)) {
                outputContext = amplifier.run(generateQueueOfPriorOutputs(outputContext.getOutputs()));
                if ("e".equals(amplifier.getName()) && !outputContext.isRunning()) {
                    signal = outputContext.getOutputs().removeLast();
                    if (signal.compareTo(maxSignal) > 0) {
                        maxSignal = signal;
                    }
                    break;
                }
            }
        }
        answer.setPart2(maxSignal);
        log.info("Max signal {}", answer.getPart2());

        return answer;
    }

    private void addOutputsToQueue(Queue<BigInteger> inputs, Deque<BigInteger> outputs) {
        while (!outputs.isEmpty()) {
            inputs.add(outputs.remove());
        }
    }

    private Queue<BigInteger> generateQueueOfPriorOutputs(Deque<BigInteger> outputs) {
        Queue<BigInteger> queue = new LinkedList<>();
        addOutputsToQueue(queue, outputs);
        return queue;
    }

    private Queue<BigInteger> generateQueueOfPhaseAndPriorOutputs(BigInteger phase, Deque<BigInteger> outputs) {
        Queue<BigInteger> queue = new LinkedList<>();
        queue.add(phase);
        addOutputsToQueue(queue, outputs);
        return queue;
    }

    private List<Amplifier> initAmplifiers(List<BigInteger> instructions) {
        List<Amplifier> amplifiers = new LinkedList<>();
        amplifiers.add(new Amplifier("a", instructions));
        amplifiers.add(new Amplifier("b", instructions));
        amplifiers.add(new Amplifier("c", instructions));
        amplifiers.add(new Amplifier("d", instructions));
        amplifiers.add(new Amplifier("e", instructions));
        return amplifiers;
    }


}
