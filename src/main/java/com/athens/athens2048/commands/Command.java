package com.athens.athens2048.commands;

import com.athens.athens2048.core.Tile;

public interface Command {
    boolean execute(Tile [][] etiles, boolean updateScore);
    boolean execute();
}
