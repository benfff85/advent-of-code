package com.adventofcode.year2019.day6;

import java.util.*;
import org.springframework.stereotype.Component;
import com.adventofcode.common.DailyAnswer;
import com.adventofcode.common.SolutionController;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component("controller-day-6")
public class Controller extends SolutionController {

    public DailyAnswer execute() {
        Map<String, SpaceObject> spaceObjects = new HashMap<>();

        for (String orbitMapEntry : puzzleInput) {
            String[] inputRow = orbitMapEntry.split("\\)");
            SpaceObject orbitedObject = createOrFetchSpaceObject(spaceObjects, inputRow[0]);
            SpaceObject orbitingObject = createOrFetchSpaceObject(spaceObjects, inputRow[1]);
            linkParentAndChildSpaceObjects(orbitedObject, orbitingObject);
        }


        int directOrbitCount = 0;
        int indirectOrbitCount = 0;
        int stepsToCenterOfMass;
        for (SpaceObject spaceObject : spaceObjects.values()) {
            stepsToCenterOfMass = getStepsToCenterOfMass(spaceObject);
            if (stepsToCenterOfMass > 0) {
                directOrbitCount++;
                indirectOrbitCount += stepsToCenterOfMass - 1;
            }
        }

        answer.setPart1(indirectOrbitCount + directOrbitCount);
        log.info("Total Orbits: {}", answer.getPart1());


        List<SpaceObject> a = getSpaceObjectsToCenterOfMass(spaceObjects.get("YOU"));
        List<SpaceObject> b = getSpaceObjectsToCenterOfMass(spaceObjects.get("SAN"));
        for (SpaceObject spaceObject : a) {
            if (b.contains(spaceObject)) {
                answer.setPart2(a.indexOf(spaceObject) + b.indexOf(spaceObject));
                log.info("Orbital transfers required: {}", answer.getPart2());
                break;
            }
        }

        return answer;
    }


    private List<SpaceObject> getSpaceObjectsToCenterOfMass(SpaceObject spaceObject) {
        List<SpaceObject> spaceObjects = new ArrayList<>();
        while (!"COM".equals(spaceObject.getName())) {
            spaceObject = spaceObject.getParent();
            spaceObjects.add(spaceObject);
        }
        return spaceObjects;
    }

    private int getStepsToCenterOfMass(SpaceObject spaceObject) {
        int stepCount = 0;
        while (!"COM".equals(spaceObject.getName())) {
            stepCount++;
            spaceObject = spaceObject.getParent();
        }
        return stepCount;
    }

    private SpaceObject createOrFetchSpaceObject(Map<String, SpaceObject> spaceObjects, String name) {
        if (spaceObjects.containsKey(name)) {
            return spaceObjects.get(name);
        }
        SpaceObject spaceObject = new SpaceObject(name);
        spaceObjects.put(name, spaceObject);
        return spaceObject;
    }

    private void linkParentAndChildSpaceObjects(SpaceObject parent, SpaceObject child) {
        child.setParent(parent);
        parent.addChild(child);
    }

}
