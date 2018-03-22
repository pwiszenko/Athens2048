package com.athens.athens2048.random;

import java.util.ArrayList;
import java.util.Random;


public class RandomSelector {

    private  class Item {

        final int value;
        final int relativeProb;

        Item( int value, int relativeProb) {
            this.value = value;
            this.relativeProb = relativeProb;
        }
    }

    private ArrayList<Item> items = new ArrayList<>();
    private Random rand = new Random();
    private int totalSum = 0;

    RandomSelector() {
        items.add(new Item(2, 90));
        items.add(new Item(4, 10));
        for(Item item : items) {
            totalSum = totalSum + item.relativeProb;
        }
    }

    public int getRandom() {

        int index = rand.nextInt(totalSum);
        int sum = 0;
        int i=0;
        while(sum < index ) {
            sum = sum + items.get(i++).relativeProb;
        }
        return items.get(Math.max(0,i-1)).value;
    }
}