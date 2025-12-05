package com.adventofcode.year2019.day5;

import static com.adventofcode.year2019.day5.ParameterMode.*;
import java.math.BigInteger;
import java.util.*;
import com.adventofcode.common.AdventOfCodeException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class IntComputer {

    IntComputerContext context;

    public BigInteger process(List<BigInteger> instructions, Queue<BigInteger> input) {
        return process(IntComputerContext.builder()
                .instructions(instructions)
                .instructionIndex(0)
                .inputs(input)
                .outputs(new LinkedList<>())
                .isRunning(true)
                .relativeBase(0)
                .build())
                        .getOutputs().pollLast();
    }

    public IntComputerContext process(IntComputerContext inputContext) {
        this.context = inputContext;
        List<BigInteger> instructions = context.getInstructions();
        padInstructions(instructions, 4000);

        Opcode opcode;
        int i = context.getInstructionIndex();
        while (i < instructions.size()) {
            log.debug("{}", instructions);
            opcode = new Opcode(instructions.get(i));

            if (OperationType.ADD.equals(opcode.getOperationType())) {
                processAdd(i, opcode);
                i += 4;
            } else if (OperationType.MULTIPLY.equals(opcode.getOperationType())) {
                processMultiply(i, opcode);
                i += 4;
            } else if (OperationType.INPUT.equals(opcode.getOperationType())) {
                // If we need input but don't have any return and get some input later from the amplifier feedback loop
                if (context.getInputs().isEmpty()) {
                    context.setInstructionIndex(i);
                    return context;
                }
                processInput(i, opcode);
                i += 2;
            } else if (OperationType.OUTPUT.equals(opcode.getOperationType())) {
                processOutput(i, opcode);
                i += 2;
            } else if (OperationType.JUMP_IF_TRUE.equals(opcode.getOperationType())) {
                // If first param is non-zero set i to second param value
                i = processJumpIfTrue(i, opcode);
            } else if (OperationType.JUMP_IF_FALSE.equals(opcode.getOperationType())) {
                // If first param is zero set i to second param value
                i = processJumpIfFalse(i, opcode);
            } else if (OperationType.LESS_THAN.equals(opcode.getOperationType())) {
                // If the first param is less than the second parameter, it stores 1 in the position given by the third parameter. Otherwise, it stores 0.
                processLessThan(i, opcode);
                i += 4;
            } else if (OperationType.EQUALS.equals(opcode.getOperationType())) {
                // If the first param is equal to the second parameter, it stores 1 in the position given by the third parameter. Otherwise, it stores 0.
                processEquals(i, opcode);
                i += 4;
            } else if (OperationType.RELATIVE_BASE_OFFSET.equals(opcode.getOperationType())) {
                processRelativeBaseOffset(i, opcode);
                i += 2;
            } else if (OperationType.TERMINATE.equals(opcode.getOperationType())) {
                context.setRunning(false);
                context.setInstructionIndex(i);
                return context;
            } else {
                log.error("Unexpected operator {} at index {}", instructions.get(i), i);
            }
        }
        return null;
    }


    private void processAdd(int indexOfOpcode, Opcode opcode) {
        BigInteger a = getParameterValue(opcode.getParameterModes().get(0), indexOfOpcode + 1);
        BigInteger b = getParameterValue(opcode.getParameterModes().get(1), indexOfOpcode + 2);
        writeValue(opcode.getParameterModes().get(2), indexOfOpcode + 3, a.add(b));
    }

    private void processMultiply(int indexOfOpcode, Opcode opcode) {
        BigInteger a = getParameterValue(opcode.getParameterModes().get(0), indexOfOpcode + 1);
        BigInteger b = getParameterValue(opcode.getParameterModes().get(1), indexOfOpcode + 2);
        writeValue(opcode.getParameterModes().get(2), indexOfOpcode + 3, a.multiply(b));

    }

    private void processInput(int indexOfOpcode, Opcode opcode) {
        BigInteger value = context.getInputs().poll();
        Integer a = context.getInstructions().get(indexOfOpcode + 1).intValue();
        log.debug("Adding input value of {} to index {}", value, a);
        writeValue(opcode.getParameterModes().get(0), indexOfOpcode + 1, value);
    }

    private void processOutput(int indexOfOpcode, Opcode opcode) {
        BigInteger a = getParameterValue(opcode.getParameterModes().get(0), indexOfOpcode + 1);
        context.getOutputs().add(a);
        log.debug("Output: {}", a);
    }

    private int processJumpIfTrue(int indexOfOpcode, Opcode opcode) {
        BigInteger a = getParameterValue(opcode.getParameterModes().get(0), indexOfOpcode + 1);
        if (!a.equals(BigInteger.ZERO)) {
            return getParameterValue(opcode.getParameterModes().get(1), indexOfOpcode + 2).intValue();
        }
        return indexOfOpcode + 3;
    }

    private int processJumpIfFalse(int indexOfOpcode, Opcode opcode) {
        BigInteger a = getParameterValue(opcode.getParameterModes().get(0), indexOfOpcode + 1);
        if (a.equals(BigInteger.ZERO)) {
            return getParameterValue(opcode.getParameterModes().get(1), indexOfOpcode + 2).intValue();
        }
        return indexOfOpcode + 3;
    }

    private void processLessThan(int indexOfOpcode, Opcode opcode) {
        BigInteger a = getParameterValue(opcode.getParameterModes().get(0), indexOfOpcode + 1);
        BigInteger b = getParameterValue(opcode.getParameterModes().get(1), indexOfOpcode + 2);
        writeValue(opcode.getParameterModes().get(2), indexOfOpcode + 3, BigInteger.valueOf(a.compareTo(b) < 0 ? 1 : 0));

    }

    private void processEquals(int indexOfOpcode, Opcode opcode) {
        BigInteger a = getParameterValue(opcode.getParameterModes().get(0), indexOfOpcode + 1);
        BigInteger b = getParameterValue(opcode.getParameterModes().get(1), indexOfOpcode + 2);
        writeValue(opcode.getParameterModes().get(2), indexOfOpcode + 3, BigInteger.valueOf(a.equals(b) ? 1 : 0));

    }

    private void processRelativeBaseOffset(int indexOfOpcode, Opcode opcode) {
        context.setRelativeBase(context.getRelativeBase() + getParameterValue(opcode.getParameterModes().get(0), indexOfOpcode + 1).intValue());
    }

    private BigInteger getParameterValue(ParameterMode parameterMode, Integer indexOfParameter) {
        BigInteger value = context.getInstructions().get(indexOfParameter);
        if (POSITION.equals(parameterMode)) {
            return context.getInstructions().get(value.intValue());
        } else if (RELATIVE.equals(parameterMode)) {
            return context.getInstructions().get(context.getRelativeBase() + value.intValue());
        } else {
            return value;
        }
    }

    private void writeValue(ParameterMode parameterMode, Integer indexOfParameter, BigInteger value) {
        if (IMMEDIATE.equals(parameterMode)) {
            throw new AdventOfCodeException("Something is wrong, trying to write to parameter but in immediate mode");
        }

        int indexToWriteTo = 0;
        Integer index = context.getInstructions().get(indexOfParameter).intValue();
        if (POSITION.equals(parameterMode)) {
            indexToWriteTo = index;
        } else if (RELATIVE.equals(parameterMode)) {
            indexToWriteTo = index + context.getRelativeBase();
        }

        context.getInstructions().set(indexToWriteTo, value);
    }


    // TODO not great, would be nice to just dynamically add addresses as needed instead of guessing at size
    private void padInstructions(List<BigInteger> instructions, int countOfMemoryAddressesNeeded) {
        int countToCreate = countOfMemoryAddressesNeeded - instructions.size();
        for (int i = 0; i < countToCreate; i++) {
            context.getInstructions().add(BigInteger.ZERO);
        }
    }


}
