package com.adventofcode.year2019.day12;

import static java.lang.Math.abs;
import static org.apache.commons.math3.util.ArithmeticUtils.lcm;
import java.util.List;
import org.springframework.stereotype.Component;
import com.adventofcode.common.DailyAnswer;
import com.adventofcode.common.SolutionController;
import lombok.extern.slf4j.Slf4j;


@Slf4j
@Component("controller-day-12")
public class Controller extends SolutionController {

    public DailyAnswer execute() {

        List<Moon> moons = puzzleInput.stream().map(Moon::new).toList();
        for (int i = 0; i < 1000; i++) {
            updateVelocities(moons);
            applyVelocity(moons);
        }
        answer.setPart1(getTotalEnergy(moons));
        log.info("Total energy after 1000 steps: {}", answer.getPart1());


        moons = puzzleInput.stream().map(Moon::new).toList();
        List<Vector> initialVectorsX = getVectors(moons, Axis.X);
        List<Vector> initialVectorsY = getVectors(moons, Axis.Y);
        List<Vector> initialVectorsZ = getVectors(moons, Axis.Z);
        long stepsTillCycleX = 0;
        long stepsTillCycleY = 0;
        long stepsTillCycleZ = 0;
        long stepCount = 0;
        while (stepsTillCycleX == 0 || stepsTillCycleY == 0 || stepsTillCycleZ == 0) {
            updateVelocities(moons);
            applyVelocity(moons);
            stepCount++;
            if (stepsTillCycleX == 0 && initialVectorsX.equals(getVectors(moons, Axis.X))) {
                stepsTillCycleX = stepCount;
            }
            if (stepsTillCycleY == 0 && initialVectorsY.equals(getVectors(moons, Axis.Y))) {
                stepsTillCycleY = stepCount;
            }
            if (stepsTillCycleZ == 0 && initialVectorsZ.equals(getVectors(moons, Axis.Z))) {
                stepsTillCycleZ = stepCount;
            }
        }
        answer.setPart2(lcm(lcm(stepsTillCycleX, stepsTillCycleY), stepsTillCycleZ));
        log.info("Steps required to return to original position: {}", answer.getPart2());

        return answer;
    }

    private List<Vector> getVectors(List<Moon> moons, Axis axis) {
        return moons.stream().map(moon -> moon.getVector(axis)).toList();
    }

    private void updateVelocities(List<Moon> moons) {

        Moon moon1;
        Moon moon2;
        for (int i = 0; i < moons.size(); i++) {
            moon1 = moons.get(i);
            for (int j = i + 1; j < moons.size(); j++) {
                moon2 = moons.get(j);
                if (moon1.getCurrentCoordinate().getX() < moon2.getCurrentCoordinate().getX()) {
                    moon1.getCurrentVelocity().setX(moon1.getCurrentVelocity().getX() + 1);
                    moon2.getCurrentVelocity().setX(moon2.getCurrentVelocity().getX() - 1);
                } else if (moon1.getCurrentCoordinate().getX() > moon2.getCurrentCoordinate().getX()) {
                    moon1.getCurrentVelocity().setX(moon1.getCurrentVelocity().getX() - 1);
                    moon2.getCurrentVelocity().setX(moon2.getCurrentVelocity().getX() + 1);
                }

                if (moon1.getCurrentCoordinate().getY() < moon2.getCurrentCoordinate().getY()) {
                    moon1.getCurrentVelocity().setY(moon1.getCurrentVelocity().getY() + 1);
                    moon2.getCurrentVelocity().setY(moon2.getCurrentVelocity().getY() - 1);
                } else if (moon1.getCurrentCoordinate().getY() > moon2.getCurrentCoordinate().getY()) {
                    moon1.getCurrentVelocity().setY(moon1.getCurrentVelocity().getY() - 1);
                    moon2.getCurrentVelocity().setY(moon2.getCurrentVelocity().getY() + 1);
                }

                if (moon1.getCurrentCoordinate().getZ() < moon2.getCurrentCoordinate().getZ()) {
                    moon1.getCurrentVelocity().setZ(moon1.getCurrentVelocity().getZ() + 1);
                    moon2.getCurrentVelocity().setZ(moon2.getCurrentVelocity().getZ() - 1);
                } else if (moon1.getCurrentCoordinate().getZ() > moon2.getCurrentCoordinate().getZ()) {
                    moon1.getCurrentVelocity().setZ(moon1.getCurrentVelocity().getZ() - 1);
                    moon2.getCurrentVelocity().setZ(moon2.getCurrentVelocity().getZ() + 1);
                }

            }
        }

    }

    private void applyVelocity(List<Moon> moons) {
        Coordinate coordinate;
        Velocity velocity;
        for (Moon moon : moons) {
            coordinate = moon.getCurrentCoordinate();
            velocity = moon.getCurrentVelocity();
            coordinate.setX(coordinate.getX() + velocity.getX());
            coordinate.setY(coordinate.getY() + velocity.getY());
            coordinate.setZ(coordinate.getZ() + velocity.getZ());
        }
    }

    private Integer getTotalEnergy(List<Moon> moons) {
        int totalEnergy = 0;
        Coordinate coordinate;
        Velocity velocity;
        for (Moon moon : moons) {
            coordinate = moon.getCurrentCoordinate();
            velocity = moon.getCurrentVelocity();
            totalEnergy += ((abs(coordinate.getX()) + abs(coordinate.getY()) + abs(coordinate.getZ())) * (abs(velocity.getX()) + abs(velocity.getY()) + abs(velocity.getZ())));
        }
        return totalEnergy;
    }

}
