package com.moonrover;

public class ApiConstants {
    // Success Messages
    public static final String ROVER_PLACED_SUCCESSFULLY = "Rover placed successfully.";
    public static final String ROVER_MOVED_SUCCESSFULLY = "Rover moved successfully.";
    public static final String ROVER_TURNED_LEFT_SUCCESSFULLY = "Rover turned left successfully.";
    public static final String ROVER_TURNED_RIGHT_SUCCESSFULLY = "Rover turned right successfully.";

    // Error Messages
    public static final String INVALID_COORDINATES = "Invalid coordinates. Rover cannot be placed outside the tabletop.";
    public static final String ERR_INVALID_COORDINATES = "InvalidPlacement";
    public static final String CANNOT_MOVE_FURTHER = "Rover cannot move further. It is at the edge of the tabletop.";
    public static final String ERR_CANNOT_MOVE_FURTHER = "CannotMoveFurther";
    public static final String ROVER_NOT_PLACED = "Rover is not placed yet.";
    public static final String ERR_ROVER_NOT_PLACED = "RoverNotPlaced";
    public static final String INVALID_DIRECTION = "Invalid turn direction.";
    public static final String ERR_INVALID_DIRECTION = "InvalidDirection";

    public static final String INVALID_DIRECTION_VALUE = "Invalid Direction value choose from (SOUTH, NORTH, EAST, WEST)";
}
