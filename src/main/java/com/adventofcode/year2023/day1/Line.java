package com.adventofcode.year2023.day1;

import org.apache.commons.lang3.StringUtils;
import com.adventofcode.common.AdventOfCodeException;
import lombok.Data;

@Data
public class Line {

    Integer value;

    public Line(String input, boolean shouldConvertTextToDigit) {

        StringBuilder newString = new StringBuilder();
        for (int i = 0; i < input.length(); i++) {
            if (shouldConvertTextToDigit) {
                newString.append(injectNumerics(input, "one", i, "1"));
                newString.append(injectNumerics(input, "two", i, "2"));
                newString.append(injectNumerics(input, "three", i, "3"));
                newString.append(injectNumerics(input, "four", i, "4"));
                newString.append(injectNumerics(input, "five", i, "5"));
                newString.append(injectNumerics(input, "six", i, "6"));
                newString.append(injectNumerics(input, "seven", i, "7"));
                newString.append(injectNumerics(input, "eight", i, "8"));
                newString.append(injectNumerics(input, "nine", i, "9"));
            }

            newString.append(input.charAt(i));

        }

        value = (getFirstDigit(newString.toString()) * 10) + getFirstDigit(StringUtils.reverse(newString.toString()));

    }

    private int getFirstDigit(String string) {
        for (int i = 0; i < string.length(); i++) {
            if (StringUtils.isNumeric(string.charAt(i) + "")) {
                return Integer.parseInt(string.charAt(i) + "");
            }
        }
        throw new AdventOfCodeException("No digit found in string: " + string);
    }

    private String injectNumerics(String input, String numString, int beginIndex, String number) {
        return input.startsWith(numString, beginIndex) ? number : "";
    }
}
