package com.adventofcode.year2019.day5;

import java.math.BigInteger;
import java.util.*;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class IntComputerContext {

    private List<BigInteger> instructions;
    private Queue<BigInteger> inputs;
    private Deque<BigInteger> outputs;
    private Integer instructionIndex;
    private boolean isRunning;
    private Integer relativeBase;

}
