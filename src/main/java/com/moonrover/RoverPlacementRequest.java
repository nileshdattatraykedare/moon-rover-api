package com.moonrover;

public class RoverPlacementRequest {
    private int x;
    private int y;
    private String facing;

    public RoverPlacementRequest(int x, int y, String facing) {
        this.x = x;
        this.y=y;
        this.facing=facing;
    }

    public int getX() {
        return x;
    }
    public int getY() {
        return y;
    }

    public String getFacing() {
        return facing;
    }

    // Constructors, getters, and setters
}
