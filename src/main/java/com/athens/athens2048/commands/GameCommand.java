package com.athens.athens2048.commands;

import com.athens.athens2048.core.Board;
import com.athens.athens2048.core.Direction;
import com.athens.athens2048.core.Game;


public abstract class GameCommand implements Command{
    protected Board board;

    public void initialize(Board board){
        this.board = board;

    }

    public boolean execute(){
        return execute(board, true);
    }

    public abstract Direction getDirection();
}
