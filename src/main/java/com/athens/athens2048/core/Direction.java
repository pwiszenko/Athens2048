package com.athens.athens2048.core;


public enum Direction {
    TOP(0),
    RIGHT(1),
    BOTTOM(2),
    LEFT(3);
    public final int value;

    Direction(int value)
    {
        this.value = value;
    }
    int getValue(){
        return value;
    }

    public static boolean isHorizontal(Direction direction) {
        return direction == LEFT || direction == RIGHT;
    }
    public static int directionCount = 4;
}
