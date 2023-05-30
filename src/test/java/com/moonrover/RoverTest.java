package com.moonrover;
import com.moonrover.Rover.Direction;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class RoverTest {
    private Rover rover;

    @BeforeEach
    public void setUp() {
        rover = new Rover();
    }

    @Test
    public void testDefaultInitialization() {
        assertEquals(0, rover.getX());
        assertEquals(0, rover.getY());
        assertEquals(Direction.NORTH, rover.getFacing());
    }

    @Test
    public void testPlaceRover() {
        rover.place(2, 3, Direction.EAST);

        assertEquals(2, rover.getX());
        assertEquals(3, rover.getY());
        assertEquals(Direction.EAST, rover.getFacing());
    }

    @Test
    public void testMoveRover() {
        rover.place(2, 3, Direction.NORTH);
        rover.move();

        assertEquals(2, rover.getX());
        assertEquals(4, rover.getY());
        assertEquals(Direction.NORTH, rover.getFacing());
    }

    @Test
    public void testMoveRoverOutsideTabletopBounds() {
        rover.place(4, 4, Direction.EAST);
        rover.move();

        // Since the rover is already at the tabletop edge, it should not move
        assertEquals(4, rover.getX());
        assertEquals(4, rover.getY());
        assertEquals(Direction.EAST, rover.getFacing());
    }

    @Test
    public void testTurnRoverLeft() {
        rover.place(2, 3, Direction.NORTH);
        rover.turnLeft();

        assertEquals(2, rover.getX());
        assertEquals(3, rover.getY());
        assertEquals(Direction.WEST, rover.getFacing());
    }

    @Test
    public void testTurnRoverRight() {
        rover.place(2, 3, Direction.SOUTH);
        rover.turnRight();

        assertEquals(2, rover.getX());
        assertEquals(3, rover.getY());
        assertEquals(Direction.WEST, rover.getFacing());
    }
}

