package com.athens.athens2048.commands;

import com.athens.athens2048.core.Board;


public interface Command {
    //boolean execute(Tile [][] etiles, boolean updateScore);
    boolean execute(Board board, boolean updateScore);
    boolean execute();
}
