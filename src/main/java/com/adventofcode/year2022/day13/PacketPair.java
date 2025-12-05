package com.adventofcode.year2022.day13;

import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;
import lombok.Getter;


public record PacketPair(@Getter Packet leftPacket, @Getter Packet rightPacket) {

    public Boolean isInCorrectOrder() {
        return leftPacket.compare(rightPacket) > 0 ? FALSE : TRUE;
    }

}
