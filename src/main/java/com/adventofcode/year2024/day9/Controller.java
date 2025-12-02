package com.adventofcode.year2024.day9;

import com.adventofcode.common.DailyAnswer;
import com.adventofcode.common.InputHelper;
import com.adventofcode.common.SolutionController;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.iterators.LoopingIterator;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.*;

import static java.util.Objects.nonNull;

@Slf4j
@Component("controller-2024-9")
public class Controller extends SolutionController {

    public Controller(InputHelper inputHelper) {
        super(inputHelper, "puzzle-input/2024/day-9.txt");
    }

    public DailyAnswer execute() {

        List<Integer> disk = initDisk(puzzleInput.getFirst());
        compressDisk(disk);
        Long checksum = calculateChecksum(disk);

        answer.setPart1(checksum);

        disk = initDisk(puzzleInput.getFirst());
        compressDiskWithoutFragmentation(disk);
        checksum = calculateChecksum(disk);

        answer.setPart2(checksum);
        log.info("Part 2: {}", answer.getPart2());

        return answer;

    }

    private void compressDiskWithoutFragmentation(List<Integer> disk) {
        List<Integer> processedFileIds = new ArrayList<>();
        Map<Integer, Integer> frequencyMap = CollectionUtils.getCardinalityMap(disk);
        for (int i = disk.size() - 1; i >= 0; i--) {

            Integer fileId = disk.get(i);

            if (fileId == null || processedFileIds.contains(fileId)) {
                continue;
            }

            long fileSize = frequencyMap.get(fileId);
            Integer z = getFirstOccurrenceOfSequence(disk, null, fileSize);
            if (nonNull(z) && z < i) {
                for (int j = 0; j < fileSize; j++) {
                    disk.set(z + j, fileId);
                    disk.set(i - j, null);
                }
            }

            processedFileIds.add(fileId);

        }

    }

    private Integer getFirstOccurrenceOfSequence(List<Integer> list, Integer value, long count) {
        for (int i = 0; i < list.size(); i++) {
            boolean found = true;
            for (int j = 0; j < count; j++) {
                if (!Objects.equals(list.get(i + j), value)) {
                    found = false;
                    break;
                }
            }

            if (found) {
                return i;
            }

        }
        return null;
    }

    private List<Integer> initDisk(String input) {
        LoopingIterator<String> typeIterator = new LoopingIterator<>(List.of("file", "free"));
        List<Integer> disk = new ArrayList<>();
        int fileId = 0;
        for (Integer i : Arrays.stream(input.split("")).map(Integer::parseInt).toList()) {
            if (typeIterator.next().equals("file")) {
                for (int j = 0; j < i; j++) {
                    disk.add(fileId);
                }
                fileId++;
            } else {
                for (int j = 0; j < i; j++) {
                    disk.add(null);
                }
            }

        }
        return disk;
    }

    private void compressDisk(List<Integer> disk) {
        int backwardsIndex = disk.size() - 1;
        for (int i = 0; i < backwardsIndex; i++) {
            if (disk.get(i) == null) {

                while (disk.get(backwardsIndex) == null) {
                    backwardsIndex--;
                }
                if (i < backwardsIndex) { // edge case
                    disk.set(i, disk.get(backwardsIndex));
                    disk.set(backwardsIndex, null);
                }

            }
        }
    }

    private static Long calculateChecksum(List<Integer> disk) {
        long checksum = 0L;
        for (int i = 0; i < disk.size(); i++) {
            if (disk.get(i) != null) {
                checksum += ((long) i * disk.get(i));
            }
        }
        return checksum;
    }

}
