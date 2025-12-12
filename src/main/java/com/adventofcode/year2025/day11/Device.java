package com.adventofcode.year2025.day11;

import java.util.HashSet;
import java.util.Set;
import lombok.*;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString(onlyExplicitlyIncluded = true)
public class Device {

    @EqualsAndHashCode.Include
    @ToString.Include
    private final String name;

    private final Set<Device> outputs = new HashSet<>();

    public Device(String name) {
        this.name = name;
    }
}
