package com.athens.athens2048.random;
import com.athens.athens2048.core.Tile;

import java.util.Random;
import java.util.ArrayList;

public class RandomTilePicker{
	private Random rand;
	private RandomSelector randSel;
	private volatile static RandomTilePicker uniqueInstance;

	// RandomPicker constructor
	private RandomTilePicker(){
		this.rand = new Random();
		this.randSel =  new RandomSelector();
	}
	public static RandomTilePicker getInstance(){
	    if(uniqueInstance == null){
	        synchronized (RandomTilePicker.class){
	            if(uniqueInstance == null){
	                uniqueInstance =  new RandomTilePicker();
                }
            }
        }
        return uniqueInstance;
    }


    public DuoTuple<Integer,Integer> update(Tile[][]tiles) {
        int height = tiles.length;
        int width = tiles[0].length;

        ArrayList<DuoTuple<Integer, Integer>> freeTiles = new ArrayList<>();

        // Register empty tiles
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                if (tiles[i][j].getNumber() == 0) {
                    freeTiles.add(new DuoTuple<>(i, j));
                }
            }
        }

        if (freeTiles.size() == 0) return null;
        // Pick a cell at a random index between 0 included and size excluded
        return freeTiles.get(rand.nextInt(freeTiles.size()));
    }

    public int pickRandomTileValue(){
	    return randSel.getRandom();
    }
}