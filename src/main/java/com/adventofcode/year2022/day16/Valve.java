package com.adventofcode.year2022.day16;

import java.util.*;
import lombok.Getter;

@Getter
public class Valve {

    private final String name;
    private final Integer flowRate;
    private final List<String> connectedValveNames;
    private final List<Valve> connectedValves = new ArrayList<>();
    private boolean isOpen = false;

    public Valve(String input) {
        name = input.split(" ")[1];
        flowRate = Integer.parseInt(input.split("=")[1].split(";")[0]);
        connectedValveNames = Arrays.stream(input.replace("valve ", "valves ").split("valves ")[1].split(", ")).toList();
    }

    public void linkValves(Map<String, Valve> valves) {
        for (String valveName : connectedValveNames) {
            connectedValves.add(valves.get(valveName));
        }
    }

    public void open() {
        isOpen = true;
    }

    public void close() {
        isOpen = false;
    }

}
