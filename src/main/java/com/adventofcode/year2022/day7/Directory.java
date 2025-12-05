package com.adventofcode.year2022.day7;

import static java.util.Objects.nonNull;
import java.util.ArrayList;
import java.util.List;
import lombok.Data;
import lombok.ToString;

@Data
public class Directory {

    @ToString.Include
    private final String name;
    private final List<File> files = new ArrayList<>();
    private final List<Directory> childDirs = new ArrayList<>();
    private final Directory parentDir;
    private Long size = 0L;

    public void addChildDir(Directory directory) {
        childDirs.add(directory);
    }

    public void addFile(File file) {
        files.add(file);
        addToSize(file.size());
    }

    public void addToSize(Integer fileSize) {
        size += fileSize;
        if (nonNull(parentDir)) {
            parentDir.addToSize(fileSize);
        }
    }

    public Directory getChildDir(String s) {
        for (Directory directory : childDirs) {
            if (s.equals(directory.name)) {
                return directory;
            }
        }
        return null;
    }
}
