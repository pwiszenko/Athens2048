package com.athens.athens2048.random;

import java.util.ArrayList;
import java.util.Random;



public class RandomSelector {

    private  class Item {

        public final int value;
        public final int relativeProb;

        public Item( int value, int relativeProb) {
            this.value = value;
            this.relativeProb = relativeProb;
        }
    }

    ArrayList<Item> items = new ArrayList<Item>();
    Random rand = new Random();
    int totalSum = 0;

    public RandomSelector() {
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