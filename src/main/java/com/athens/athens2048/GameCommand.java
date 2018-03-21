package com.athens.athens2048;

public abstract class GameCommand {
    protected Game game;
    protected Tile [][] tiles;
    public void initialize(Tile [][] tiles, Game game){
        this.tiles = tiles;
        this.game = game;

    }
}
