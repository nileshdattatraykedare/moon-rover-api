package com.moonrover.service;

import com.moonrover.ReportResponse;
import com.moonrover.Rover;

public interface RoverService {
    void placeRover(int x, int y, Rover.Direction facing);

    void moveRover();

    void turnRoverLeft();

    void turnRoverRight();
    boolean isRoverPlaced();

    ReportResponse getRoverReport();
}

