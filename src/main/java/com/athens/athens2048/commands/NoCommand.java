package com.athens.athens2048.commands;


import com.athens.athens2048.core.Game;
import com.athens.athens2048.core.Tile;


public class NoCommand extends GameCommand implements Command {
    public NoCommand(Tile[][] tiles, Game game){
        initialize(tiles, game);
    }

    public boolean execute(Tile[][] etiles, boolean updateScore){
        return false;
    }

}
