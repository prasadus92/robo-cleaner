package com.marshmallow.robocleaner.model;

import lombok.Data;
import com.marshmallow.robocleaner.validation.Validatable;

import java.util.List;

/**
 * Class that represent a point in the Area
 */
@Data
public class Position implements Validatable {

    private AreaSize areaSize;
    private Integer x;
    private Integer y;

    public Position(List<Integer> coordinates, AreaSize areaSize) {
        x = coordinates.get(0);
        y = coordinates.get(1);
        this.areaSize = areaSize;
    }

    public void setLocation(Position position) {
        this.x = position.getX();
        this.y = position.getY();
    }

    public void incrementX() {
        x++;
    }

    public void decrementX() {
        x--;
    }

    public void incrementY() {
        y++;
    }

    public void decrementY() {
        y--;
    }

    /**
     * Checks if a position is inside the area.
     */
    @Override
    public boolean isValid() {
        return (areaSize != null) && (x >= 0) && (x < areaSize.getM()) && (y >= 0) && (y < areaSize.getN());
    }
}
