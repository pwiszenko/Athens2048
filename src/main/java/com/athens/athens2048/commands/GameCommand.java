package com.athens.athens2048.commands;

import com.athens.athens2048.core.Game;
import com.athens.athens2048.core.Tile;

public abstract class GameCommand implements Command{
    protected Game game;
    protected Tile[][] tiles;
    public void initialize(Tile [][] tiles, Game game){
        this.tiles = tiles;
        this.game = game;

    }
    public boolean execute(){
        return execute(tiles, true);
    }
}
