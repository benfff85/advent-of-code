package com.adventofcode.year2019.day5;

public enum ParameterMode {

    POSITION(0), IMMEDIATE(1), RELATIVE(2);

    private final Integer value;

    ParameterMode(Integer value) {
        this.value = value;
    }

    public static ParameterMode getFromValue(Integer value) {
        ParameterMode[] parameterModes = ParameterMode.values();
        for (ParameterMode parameterMode : parameterModes) {
            if (parameterMode.value.equals(value)) {
                return parameterMode;
            }
        }
        return null;
    }

}
