package com.marshmallow.robocleaner.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import com.marshmallow.robocleaner.model.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

@Service
@Slf4j
public class NavigationService {

    public Optional<Solution> navigate(Instructions instructions) {
        AreaSize areaSize = new AreaSize(instructions.getAreaSize());
        Area area = new Area(areaSize);
        Position startingPosition = new Position(instructions.getStartingPosition(), areaSize);

        List<List<Integer>> oilPatches = instructions.getOilPatches();
        IntStream.range(0, oilPatches.size())
                 .forEach(index -> area.applyOilPatch(oilPatches.get(index), index));

        log.info("Running the Robo Cleaner...");
        RoboCleaner roboCleaner = new RoboCleaner(area, startingPosition);
        Solution solution = roboCleaner.clean(instructions.getNavigationInstructions());

        if (solution == null) {
            log.error("Error encountered during the navigation. No solution found.");
            return Optional.empty();
        }

        log.info("Robo Cleaner finished the navigation successfully.");
        return Optional.of(solution);
    }
}
