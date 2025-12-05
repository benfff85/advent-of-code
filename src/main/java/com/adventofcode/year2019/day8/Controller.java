package com.adventofcode.year2019.day8;

import java.util.*;
import org.springframework.stereotype.Component;
import com.adventofcode.common.DailyAnswer;
import com.adventofcode.common.SolutionController;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component("controller-day-8")
public class Controller extends SolutionController {

    public DailyAnswer execute() {

        int layerSize = 25 * 6;
        List<Layer> layers = new ArrayList<>();
        for (int i = 0; i < puzzleInput.get(0).length(); i += layerSize) {
            layers.add(new Layer(6, 25, Arrays.stream(puzzleInput.get(0).substring(i, i + layerSize).split("")).map(Integer::parseInt).toList()));
        }

        int fewestZeroDigits = Integer.MAX_VALUE;
        int countOfOneTimesCountOfTwo = 0;
        for (Layer layer : layers) {
            if (layer.getCountOf(0) < fewestZeroDigits) {
                fewestZeroDigits = layer.getCountOf(0);
                countOfOneTimesCountOfTwo = layer.getCountOf(1) * layer.getCountOf(2);
            }
        }
        answer.setPart1(countOfOneTimesCountOfTwo);
        log.info("Count of ones multiplied by count of twos: {}", countOfOneTimesCountOfTwo);

        Layer flattenedLayer = flattenLayers(layers);
        answer.setPart2(flattenedLayer);
        log.info("Flattened layer: \n{}", answer.getPart2());

        return answer;
    }

    private Layer flattenLayers(List<Layer> layers) {
        List<Integer> blanks = new ArrayList<>();
        for (int i = 0; i < 6 * 25; i++) {
            blanks.add(2);
        }

        Layer flattenedLayer = new Layer(6, 25, blanks);

        List<Integer> row;
        List<Integer> flattenedLayerRow;
        for (Layer layer : layers) {

            for (int i = 0; i < 6; i++) {
                row = layer.getGrid().get(i);
                flattenedLayerRow = flattenedLayer.getGrid().get(i);
                for (int j = 0; j < 25; j++) {
                    if (flattenedLayerRow.get(j) == 2) {
                        flattenedLayerRow.set(j, row.get(j));
                    }
                }
            }

        }

        return flattenedLayer;
    }

}
