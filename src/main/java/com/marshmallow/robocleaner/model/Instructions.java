package com.marshmallow.robocleaner.model;

import lombok.Builder;
import lombok.Value;
import com.marshmallow.robocleaner.validation.AreaSize;
import com.marshmallow.robocleaner.validation.NavigationInstructions;
import com.marshmallow.robocleaner.validation.OilPatches;
import com.marshmallow.robocleaner.validation.Pair;

import java.util.List;

@Value
@Builder
public class Instructions {

    @AreaSize
    private List<Integer> areaSize;

    @Pair
    private List<Integer> startingPosition;

    @OilPatches
    private List<List<Integer>> oilPatches;

    @NavigationInstructions
    private String navigationInstructions;
}
