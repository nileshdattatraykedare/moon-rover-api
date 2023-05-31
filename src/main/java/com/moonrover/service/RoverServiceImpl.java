package com.moonrover.service;

import com.moonrover.ReportResponse;
import com.moonrover.Rover;
import com.moonrover.Utils;
import org.springframework.stereotype.Service;

@Service
public class RoverServiceImpl implements RoverService {
    private Rover rover;

    @Override
    public void placeRover(int x, int y, Rover.Direction facing) {
        rover = new Rover(x, y, facing);
    }

    @Override
    public void moveRover() {
        int originalX = rover.getX();
        int originalY = rover.getY();
        rover.move();
        int newX = rover.getX();
        int newY = rover.getY();

        if (originalX == newX && originalY == newY) {
            throw new IllegalStateException("Cannot move further. Rover has reached the edge of the tabletop.");
        }
    }

    @Override
    public void turnRoverLeft() {
        rover.turnLeft();
    }

    @Override
    public void turnRoverRight() {
        rover.turnRight();
    }

    @Override
    public ReportResponse getRoverReport() {
        if (isRoverPlaced()) {
            int x = rover.getX();
            int y = rover.getY();
            Rover.Direction facing = rover.getFacing();
            return new ReportResponse(x, y, facing.toString());
        } else {
            throw new IllegalStateException("Rover is not placed on the tabletop.");
        }
    }

    @Override
    public boolean isRoverPlaced() {
        return rover != null;
    }
}

