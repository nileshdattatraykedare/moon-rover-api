package com.moonrover;

public class Utils {
    public static boolean isWithinTabletopBounds(int x, int y, int maxX, int maxY) {
        return x >= 0 && x < maxX && y >= 0 && y < maxY;
    }
}
