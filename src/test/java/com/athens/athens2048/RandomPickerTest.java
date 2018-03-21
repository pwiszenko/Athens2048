package com.athens.athens2048;

import com.athens.athens2048.core.Tile;
import com.athens.athens2048.random.DuoTuple;
import com.athens.athens2048.random.RandomTilePicker;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

public class RandomPickerTest extends TestCase
{
    private final int HEIGHT = 4;
    private final int WIDTH = 4;
    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public RandomPickerTest( String testName )
    {
        super( testName );
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite()
    {
        return new TestSuite( RandomPickerTest.class );
    }

    /**
     * Rigourous Test :-)
     */
    public void testRandomPicker()
    {
        Tile[][]tiles = new Tile[HEIGHT][WIDTH];
        tiles[0][0] = new Tile(2);
        tiles[0][1] = new Tile(2);
        tiles[0][2] = new Tile(4);
        tiles[0][3] = new Tile(4);
        tiles[1][0] = new Tile(1);
        tiles[1][1] = new Tile(1);
        tiles[1][2] = new Tile(2);
        tiles[1][3] = new Tile(2);
        tiles[2][0] = new Tile(2);
        tiles[2][1] = new Tile(2);
        tiles[2][2] = new Tile(4);
        tiles[2][3] = new Tile(0);
        tiles[3][0] = new Tile(0);
        tiles[3][1] = new Tile(0);
        tiles[3][2] = new Tile(1);
        tiles[3][3] = new Tile(2);

        RandomTilePicker picker = RandomTilePicker.getInstance();
        DuoTuple<Integer, Integer> duo = picker.update(tiles);
        System.out.println("Picked " + duo);

        for(int i = 0;  i < 5; i++){
            System.out.println("Randomly picked : " + String.valueOf(picker.pickRandomTileValue()));
        }
    }
}
