package com.adventofcode.year2022.day18;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;
import java.util.*;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class LavaDroplet {

    private static final int SIZE = 25;
    private final List<List<List<Cube>>> droplet = new ArrayList<>(SIZE);
    private final Set<Cube> allCubes = new HashSet<>();
    private final Set<Cube> processedCubes = new HashSet<>();
    private final Deque<Cube> cubesToProcess = new LinkedList<>();
    private int contactPointCount = 0;

    public LavaDroplet() {

        for (int x = 0; x < SIZE; x++) {
            // For every x add a list for y
            droplet.add(x, new ArrayList<>(SIZE));
            for (int y = 0; y < SIZE; y++) {
                // For every y add a list for z
                droplet.get(x).add(y, new ArrayList<>(SIZE));
                for (int z = 0; z < SIZE; z++) {
                    droplet.get(x).get(y).add(null);
                }
            }
        }
    }

    public void addCube(Cube cube) {
        allCubes.add(cube);
        setCube(cube);
    }

    public int calculateSurfaceArea() {
        int surfaceArea = 0;

        for (Cube cube : allCubes) {
            if (isNull(getCube(cube.getX() - 1, cube.getY(), cube.getZ()))) {
                surfaceArea++;
            }
            if (isNull(getCube(cube.getX() + 1, cube.getY(), cube.getZ()))) {
                surfaceArea++;
            }
            if (isNull(getCube(cube.getX(), cube.getY() - 1, cube.getZ()))) {
                surfaceArea++;
            }
            if (isNull(getCube(cube.getX(), cube.getY() + 1, cube.getZ()))) {
                surfaceArea++;
            }
            if (isNull(getCube(cube.getX(), cube.getY(), cube.getZ() - 1))) {
                surfaceArea++;
            }
            if (isNull(getCube(cube.getX(), cube.getY(), cube.getZ() + 1))) {
                surfaceArea++;
            }
        }

        return surfaceArea;

    }

    public Cube getCube(int x, int y, int z) {
        return droplet.get(x).get(y).get(z);
    }

    public void setCube(Cube cube) {
        droplet.get(cube.getX()).get(cube.getY()).set(cube.getZ(), cube);
    }

    public int calculateContiguousSurfaceArea() {
        cubesToProcess.push(new Cube(0, 0, 0));
        getContactPointsCount();
        return contactPointCount;
    }

    private void getContactPointsCount() {
        Cube cube;

        while (!cubesToProcess.isEmpty()) {
            cube = cubesToProcess.pop();
            processedCubes.add(cube);

            // Check all six directions
            checkAdjacent(cube, -1, 0, 0); // left
            checkAdjacent(cube, 1, 0, 0); // right
            checkAdjacent(cube, 0, -1, 0); // down
            checkAdjacent(cube, 0, 1, 0); // up
            checkAdjacent(cube, 0, 0, -1); // forward
            checkAdjacent(cube, 0, 0, 1); // backward

        }
    }

    private void checkAdjacent(Cube cube, int dx, int dy, int dz) {
        int newX = cube.getX() + dx;
        int newY = cube.getY() + dy;
        int newZ = cube.getZ() + dz;

        // Check bounds
        if (newX >= 0 && newX < SIZE - 1 && newY >= 0 && newY < SIZE - 1 && newZ >= 0 && newZ < SIZE - 1) {
            Cube adjacentCube = new Cube(newX, newY, newZ);

            // If it exists as a cube (lava) add it as a contact point
            if (nonNull(getCube(newX, newY, newZ))) {
                contactPointCount++;
            }
            // Otherwise check if not already processed, process it
            else if (!processedCubes.contains(adjacentCube) && !cubesToProcess.contains(adjacentCube)) {
                cubesToProcess.push(adjacentCube);
            }
        }
    }

}
