package com.athens.athens2048.core;


public class Tile {
    /**
     * Represents a single tile on the Board.
     */

    private int number;

    public Tile(int number) {
        this.number = number;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }
}
