package com.adventofcode.year2019.day16;

import static java.lang.Math.abs;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;
import org.springframework.stereotype.Component;
import com.adventofcode.common.DailyAnswer;
import com.adventofcode.common.SolutionController;
import lombok.extern.slf4j.Slf4j;


@Slf4j
@Component("controller-day-16")
public class Controller extends SolutionController {

    public DailyAnswer execute() {
        List<Byte> inputSignal = inputHelper.parseStringToSingleDigitIntList(puzzleInput.get(0));
        List<Byte> output = processPhases(inputSignal, 100);
        answer.setPart1(output.subList(0, 8));
        log.info("Part 1: {}", answer.getPart1());

        int indexOfAnswer = Integer.parseInt(inputSignal.subList(0, 7).toString().replace(" ", "").replace(",", "").replace("[", "").replace("]", ""));
        List<Byte> inputSignalPart2 = new ArrayList<>(inputSignal.size() * 10000);
        for (int i = 0; i < 10000; i++) {
            inputSignalPart2.addAll(inputSignal);
        }
        List<Byte> outputPart2 = processPhases(inputSignalPart2, 100);
        answer.setPart2(outputPart2.subList(indexOfAnswer, indexOfAnswer + 8));
        log.info("Part 2: {}", answer.getPart2());

        return answer;
    }

    private List<Byte> processPhases(List<Byte> input, Integer phaseCount) {

        int signalSize = input.size();
        List<Byte> inputSignal = new ArrayList<>(signalSize);
        List<Byte> outputSignal = new ArrayList<>(signalSize);
        inputSignal.addAll(input);

        IntStream.rangeClosed(0, signalSize - 1).boxed().forEach(e -> outputSignal.add(e, (byte) 0));

        // Loop through phases
        for (int phase = 0; phase < phaseCount; phase++) {

            // Clear out output list
            IntStream.rangeClosed(0, signalSize - 1).boxed().parallel().forEach(e -> outputSignal.set(e, (byte) 0));

            // Loop through digits in input signal
            IntStream.rangeClosed(0, signalSize - 1).boxed().parallel().forEach(e -> processElement(signalSize, inputSignal, outputSignal, e));

            // Copy output to input for next iteration
            IntStream.rangeClosed(0, signalSize - 1).boxed().parallel().forEach(e -> inputSignal.set(e, outputSignal.get(e)));

            log.info("Done Phase: {}", phase);
        }

        return outputSignal;
    }

    private void processElement(int signalSize, List<Byte> inputSignal, List<Byte> outputSignal, int e) {
        int blockSize = e + 1;
        int blockGroupSize = (4 * blockSize);
        int halfBlockGroupSize = blockGroupSize / 2;
        int phaseSum = 0;

        // Loop through blocks starting points
        for (int startingIndexOfOnes = e; startingIndexOfOnes < signalSize; startingIndexOfOnes += blockGroupSize) {
            phaseSum += processBlockGroup(signalSize, inputSignal, blockSize, halfBlockGroupSize, startingIndexOfOnes);
        }

        addCalculatedElement(outputSignal, phaseSum, e);

    }

    private int processBlockGroup(int signalSize, List<Byte> inputSignal, int blockSize, int halfBlockGroupSize, int startingIndexOfOnes) {
        int blockGroupSum = 0;
        blockGroupSum += processBlock(signalSize, inputSignal, blockSize, startingIndexOfOnes);
        blockGroupSum -= processBlock(signalSize, inputSignal, blockSize, startingIndexOfOnes + halfBlockGroupSize);
        return blockGroupSum;
    }

    private int processBlock(int signalSize, List<Byte> inputSignal, int blockSize, int startingIndex) {
        int blockSum = 0;
        for (int j = 0; j < blockSize && (startingIndex + j) < signalSize; j++) {
            blockSum += inputSignal.get(startingIndex + j);
        }
        return blockSum;
    }

    private void addCalculatedElement(List<Byte> outputSignal, int sum, int e) {
        outputSignal.set(e, (byte) (abs(sum) % 10));
    }

}
