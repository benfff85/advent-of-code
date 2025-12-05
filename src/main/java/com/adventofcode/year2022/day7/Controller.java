package com.adventofcode.year2022.day7;

import static java.lang.Integer.parseInt;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Component;
import com.adventofcode.common.DailyAnswer;
import com.adventofcode.common.SolutionController;
import lombok.extern.slf4j.Slf4j;


@Slf4j
@Component("controller-2022-7")
public class Controller extends SolutionController {

    public DailyAnswer execute() {

        List<Directory> fileSystem = new ArrayList<>();
        Directory fileSystemRoot = new Directory("/", null);
        fileSystem.add(fileSystemRoot);
        Directory pwd = fileSystemRoot;

        for (String line : puzzleInput) {
            if (line.startsWith("$")) {
                // If "cd" set current working directory, assume that it exists
                if (line.startsWith("$ cd")) {
                    if (line.startsWith("$ cd /")) {
                        pwd = fileSystemRoot;
                    } else if (line.startsWith("$ cd ..")) {
                        pwd = pwd.getParentDir();
                    } else {
                        pwd = pwd.getChildDir(line.split(" ")[2]);
                    }
                }
                // If "ls" do we really need to take actions
            } else if (line.startsWith("dir")) {
                Directory dir = new Directory(line.split(" ")[1], pwd);
                fileSystem.add(dir);
                pwd.addChildDir(dir);
            } else {
                File file = new File(line.split(" ")[1], parseInt(line.split(" ")[0]));
                pwd.addFile(file);
            }
        }

        answer.setPart1(fileSystem.stream().map(Directory::getSize).filter(size -> size <= 100000).mapToLong(Long::longValue).sum());
        log.info("P1: {}", answer.getPart1());

        Long additionalSpaceNeeded = 30000000 - (70000000 - fileSystemRoot.getSize());
        answer.setPart2(fileSystem.stream().map(Directory::getSize).filter(size -> size > additionalSpaceNeeded).sorted().findFirst().orElse(0L));
        log.info("P2: {}", answer.getPart1());

        return answer;
    }


}
