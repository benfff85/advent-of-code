package com.adventofcode.year2019.day6;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

import static java.util.Objects.nonNull;

@Data
public class SpaceObject {

    private final String name;
    private SpaceObject parent;
    private List<SpaceObject> children = new ArrayList<>();

    public void addChild(SpaceObject child) {
        children.add(child);
    }

    public String toString() {
        return (nonNull(parent) ? parent.name : null) + ")" + name + "--> [" + children.size() + "]";
    }
}
