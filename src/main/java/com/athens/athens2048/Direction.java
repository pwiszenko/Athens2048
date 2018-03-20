package com.athens.athens2048;


public enum Direction {
    TOP,
    RIGHT,
    BOTTOM,
    LEFT;

    public static boolean isHorizontal(Direction direction) {
        return direction == LEFT || direction == RIGHT;
    }
}
