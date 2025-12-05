package com.adventofcode.year2019.day5;


import static java.lang.Integer.parseInt;
import static java.lang.String.valueOf;
import static org.apache.commons.lang3.StringUtils.leftPad;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;

@Getter
public class Opcode {

    private final OperationType operationType;
    private final List<ParameterMode> parameterModes;

    public Opcode(BigInteger inputBigInt) {
        int input = inputBigInt.intValue();
        operationType = OperationType.getFromValue(input % 100);

        parameterModes = new ArrayList<>();
        if (OperationType.ADD.equals(operationType) || OperationType.MULTIPLY.equals(operationType) || OperationType.LESS_THAN.equals(operationType) || OperationType.EQUALS.equals(operationType)) {
            String paddedInput = leftPad((input / 100) + "", 3, "0");
            parameterModes.add(ParameterMode.getFromValue(parseInt(valueOf(paddedInput.charAt(2)))));
            parameterModes.add(ParameterMode.getFromValue(parseInt(valueOf(paddedInput.charAt(1)))));
            parameterModes.add(ParameterMode.getFromValue(parseInt(valueOf(paddedInput.charAt(0)))));
        }
        if (OperationType.JUMP_IF_TRUE.equals(operationType) || OperationType.JUMP_IF_FALSE.equals(operationType)) {
            String paddedInput = leftPad((input / 100) + "", 2, "0");
            parameterModes.add(ParameterMode.getFromValue(parseInt(valueOf(paddedInput.charAt(1)))));
            parameterModes.add(ParameterMode.getFromValue(parseInt(valueOf(paddedInput.charAt(0)))));
        }
        if (OperationType.INPUT.equals(operationType) || OperationType.OUTPUT.equals(operationType) || OperationType.RELATIVE_BASE_OFFSET.equals(operationType)) {
            String paddedInput = leftPad((input / 100) + "", 1, "0");
            parameterModes.add(ParameterMode.getFromValue(parseInt(valueOf(paddedInput.charAt(0)))));
        }


    }

}
