package com.athens.athens2048.random;
public class DuoTuple<X, Y> {
	public final X x;
	public final Y y;
	public DuoTuple(X x, Y y) {
    	this.x = x;
    	this.y = y;
  	}
  	public String toString(){
  		return "{" +String.valueOf(x) +", "+ String.valueOf(y)+"}";
  	}
}