package com.adventofcode.year2022.day13;

import static java.lang.Boolean.TRUE;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Component;
import com.adventofcode.common.DailyAnswer;
import com.adventofcode.common.SolutionController;
import lombok.extern.slf4j.Slf4j;


@Slf4j
@Component("controller-2022-13")
public class Controller extends SolutionController {

    public DailyAnswer execute() {

        List<PacketPair> packetPairs = new ArrayList<>();
        for (int i = 0; i < puzzleInput.size(); i += 3) {
            packetPairs.add(new PacketPair(new Packet(puzzleInput.get(i)), new Packet(puzzleInput.get(i + 1))));
        }

        int sum = 0;
        for (int i = 0; i < packetPairs.size(); i++) {
            if (TRUE.equals(packetPairs.get(i).isInCorrectOrder())) {
                sum += (i + 1);
            }
        }

        answer.setPart1(sum);
        log.info("P1: {}", answer.getPart1());

        List<Packet> packets = new ArrayList<>();
        for (PacketPair packetPair : packetPairs) {
            packets.add(packetPair.leftPacket());
            packets.add(packetPair.rightPacket());
        }
        Packet p1 = new Packet("[[2]]");
        Packet p2 = new Packet("[[6]]");
        packets.add(p1);
        packets.add(p2);

        packets.sort(Packet::compare);

        answer.setPart2((packets.indexOf(p1) + 1) * (packets.indexOf(p2) + 1));
        log.info("P2: {}", answer.getPart2());

        return answer;

    }
}
