package com.athens.athens2048.commands;

import com.athens.athens2048.core.Board;
import com.athens.athens2048.core.Direction;
import com.athens.athens2048.core.Game;


public class NoCommand extends GameCommand implements Command {

    public NoCommand(Board board, Game game){
        initialize(board, game);
    }

    public boolean execute(Board board, boolean updateScore){
        return false;
    }

    public Direction getDirection(){
        return Direction.TOP;
    }

}
