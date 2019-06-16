package com.marshmallow.robocleaner.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;
import com.marshmallow.robocleaner.exception.OilPatchOutOfAreaException;
import com.marshmallow.robocleaner.model.Instructions;
import com.marshmallow.robocleaner.model.Solution;

import java.util.Arrays;
import java.util.Optional;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;

@RunWith (SpringRunner.class)
public class NavigationServiceTest {

    @Autowired
    private NavigationService navigationService;

    @Test
    public void testBasicUseCase() {
        // Given
        Instructions instructions = Instructions.builder()
                                                .areaSize(Arrays.asList(5, 5))
                                                .startingPosition(Arrays.asList(1, 2))
                                                .oilPatches(Arrays.asList(Arrays.asList(1, 0), Arrays.asList(2, 2), Arrays.asList(2, 3)))
                                                .navigationInstructions("NNESEESWNWW")
                                                .build();

        // When
        Optional<Solution> found = navigationService.navigate(instructions);

        // Then
        assertTrue(found.isPresent());
        Solution solution = found.get();
        assertEquals(1, solution.getOilPatchesCleaned());
        assertEquals(Arrays.asList(1, 3), solution.getFinalPosition());
    }

    @Test (expected = OilPatchOutOfAreaException.class)
    public void testOilPatchOutsideOfArea() {
        // Given
        Instructions instructions = Instructions.builder()
                                                .areaSize(Arrays.asList(5, 5))
                                                .startingPosition(Arrays.asList(1, 2))
                                                .oilPatches(Arrays.asList(Arrays.asList(1, 5)))
                                                .navigationInstructions("NNESEESWNWW")
                                                .build();

        // When
        navigationService.navigate(instructions);
    }

    @Test
    public void testRectangularArea() {
        // Given
        Instructions instructions = Instructions.builder()
                                                .areaSize(Arrays.asList(5, 7))
                                                .startingPosition(Arrays.asList(1, 2))
                                                .oilPatches(Arrays.asList(Arrays.asList(1, 6), Arrays.asList(2, 6), Arrays.asList(2, 3)))
                                                .navigationInstructions("NNNNESEESWNWW")
                                                .build();

        // When
        Optional<Solution> found = navigationService.navigate(instructions);

        // Then
        assertTrue(found.isPresent());
        Solution solution = found.get();
        assertEquals(2, solution.getOilPatchesCleaned());
        assertEquals(Arrays.asList(1, 5), solution.getFinalPosition());
    }

    @TestConfiguration
    static class NavigationServiceTestConfiguration {
        @Bean
        public NavigationService navigationService() {
            return new NavigationService();
        }
    }
}
