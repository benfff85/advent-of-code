package com.adventofcode.year2022.day13;

import static java.lang.Boolean.TRUE;
import static java.util.Objects.nonNull;
import java.util.List;
import com.adventofcode.common.AdventOfCodeException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;

public class Packet {

    private static final ObjectMapper objectMapper = new ObjectMapper();
    @Getter
    private final List<Object> packetData;

    public Packet(String packetInput) {

        try {
            packetData = objectMapper.readValue(packetInput, new TypeReference<>() {});
        } catch (Exception e) {
            throw new AdventOfCodeException("Error creating packet", e);
        }
    }

    public int compare(Packet packetComparingTo) {
        return TRUE.equals(customCompare(packetData, packetComparingTo.getPacketData())) ? -1 : 1;
    }


    private Boolean customCompare(Object leftObject, Object rightObject) {

        // both values are integers
        if (leftObject instanceof Integer leftInt && rightObject instanceof Integer rightInt) {
            if (leftInt < rightInt) { // If the left integer is lower than the right integer, the inputs are in the right order
                return true;
            } else if (leftInt > rightInt) { // If the left integer is higher than the right integer, the inputs are not in the right order
                return false;
            } else { // Otherwise, the inputs are the same integer; continue checking the next part of the input
                return null;
            }
        }

        // both values are lists
        if (leftObject instanceof List<?> leftList && rightObject instanceof List<?> rightList) {
            for (int i = 0; i < leftList.size(); i++) {
                if (i >= rightList.size()) { // If the right list runs out of items first, the inputs are not in the right order
                    return false;
                }

                Boolean comparisonValue = customCompare(leftList.get(i), rightList.get(i));
                if (nonNull(comparisonValue)) {
                    return comparisonValue;
                }
            }

            if (leftList.size() < rightList.size()) { // If the left list runs out of items first, the inputs are in the right order
                return true;
            }
            return null; // If we looped through all and didn't determine order, return null
        }

        // exactly one value is an integer
        if (leftObject instanceof List<?> leftList && rightObject instanceof Integer rightInt) {
            return customCompare(leftList, List.of(rightInt));
        } else if (leftObject instanceof Integer leftInt && rightObject instanceof List<?> rightList) {
            return customCompare(List.of(leftInt), rightList);
        }

        throw new AdventOfCodeException("Missed comparison conditions");

    }

}
