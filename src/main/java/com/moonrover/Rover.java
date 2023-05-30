package com.moonrover;

public class Rover {
    private int x;
    private int y;
    private Direction facing;

    public Rover() {
        this.x = 0;
        this.y = 0;
        this.facing = Direction.NORTH;
    }

    public Rover(int x, int y, Direction facing) {
        this.x = x;
        this.y = y;
        this.facing = facing;
    }

    public void place(int x, int y, Direction facing) {
        this.x = x;
        this.y = y;
        this.facing = facing;
    }

    public void move() {
        int newX = x;
        int newY = y;

        switch (facing) {
            case NORTH:
                newY++;
                break;
            case EAST:
                newX++;
                break;
            case SOUTH:
                newY--;
                break;
            case WEST:
                newX--;
                break;
        }

        if (Utils.isWithinTabletopBounds(newX, newY,5, 5)) {
            x = newX;
            y = newY;
        }
    }

    public void turnLeft() {
        facing = facing.left();
    }

    public void turnRight() {
        facing = facing.right();
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public Direction getFacing() {
        return facing;
    }

    public enum Direction {
        NORTH,
        EAST,
        SOUTH,
        WEST;

        public Direction left() {
            int newIndex = (this.ordinal() + 3) % 4;
            return Direction.values()[newIndex];
        }

        public Direction right() {
            int newIndex = (this.ordinal() + 1) % 4;
            return Direction.values()[newIndex];
        }
    }
}

