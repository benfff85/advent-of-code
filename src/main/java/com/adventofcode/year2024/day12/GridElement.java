package com.adventofcode.year2024.day12;

import com.adventofcode.common.grid.ConstructableGridElement;
import lombok.Getter;

@Getter
public enum GridElement implements ConstructableGridElement<GridElement> {

    A("A"), B("B"), C("C"), D("D"), E("E"), F("F"), G("G"),
    H("H"), I("I"), J("J"), K("K"), L("L"), M("M"), N("N"),
    O("O"), P("P"), Q("Q"), R("R"), S("S"), T("T"), U("U"),
    V("V"), W("W"), X("X"), Y("Y"), Z("Z");

    private final String string;

    GridElement(String string) {
        this.string = string;
    }

    @Override
    public String print() {
        return string;
    }

}
