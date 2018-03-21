package com.athens.athens2048.core;

import com.athens.athens2048.commands.Command;
import com.athens.athens2048.random.DuoTuple;

public class Turn {
    final public int tileValue;
    final public DuoTuple<Integer, Integer> coordinates;
    final public Command command;

    public Turn(int tileValue, DuoTuple<Integer, Integer>coordinates, Command command){
        this.tileValue = tileValue;
        this.coordinates = coordinates;
        this.command = command;
    }
}
