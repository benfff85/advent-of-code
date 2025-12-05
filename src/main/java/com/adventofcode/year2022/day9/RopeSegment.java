package com.adventofcode.year2022.day9;

import static com.adventofcode.common.grid.SurroundingType.ALL;
import java.awt.Point;
import com.adventofcode.common.grid.PointUtil;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class RopeSegment {

    @Getter
    private final String name;
    @Getter
    private Point head = new Point(0, 0);
    @Getter
    private Point tail = new Point(0, 0);

    public RopeSegment(String name) {
        this.name = name;
    }

    public void move(Point newHead) {
        head = newHead;

        if (!PointUtil.getSurroundingPoints(head, ALL).contains(tail) && !head.equals(tail)) {
            // Need to move tail
            if (head.x == tail.x) {
                if (head.y == tail.y + 2) {
                    // Up
                    tail = new Point(tail.x, tail.y + 1);
                } else {
                    // Down
                    tail = new Point(tail.x, tail.y - 1);
                }
            } else if (head.y == tail.y) {
                if (head.x == tail.x + 2) {
                    // Right
                    tail = new Point(tail.x + 1, tail.y);
                } else {
                    // Left
                    tail = new Point(tail.x - 1, tail.y);
                }
            } else if (head.x > tail.x) {
                if (head.y > tail.y) {
                    // Up Right
                    tail = new Point(tail.x + 1, tail.y + 1);
                } else {
                    // Down Right
                    tail = new Point(tail.x + 1, tail.y - 1);
                }
            } else if (head.y > tail.y) {
                // Up Left
                tail = new Point(tail.x - 1, tail.y + 1);
            } else {
                // Down Left
                tail = new Point(tail.x - 1, tail.y - 1);
            }

        }
    }
}
