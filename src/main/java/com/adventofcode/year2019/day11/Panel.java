package com.adventofcode.year2019.day11;

import static com.adventofcode.year2019.day11.Color.BLACK;
import java.awt.Point;
import com.adventofcode.common.grid.PrintableGridElement;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class Panel implements PrintableGridElement {

    private final Point coordinate;
    private com.adventofcode.year2019.day11.Color color = BLACK;
    private boolean hasBeenPainted = false;

    public Panel(Point point, com.adventofcode.year2019.day11.Color color) {
        this.coordinate = point;
        this.color = color;
    }

    public void paint(Color colorToPaint) {
        if (!colorToPaint.equals(color)) {
            color = colorToPaint;
            hasBeenPainted = true;
        }
    }

    @Override
    public String print() {
        return color.print();
    }
}
