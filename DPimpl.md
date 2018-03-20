# Design pattern implementation <a id="DP"></a>

We chose Singleton and Strategy patterns, because ...

We didn't use decoration pattern, because it would have no use for our game. The most obvious added functionalities of 2048 are:
- possibility to change grid's size
- possibility to play after achieving '2048' tile

In a game where for example we have a spaceship on which we can upgrade it's weapons, we could treat them as decorations:
`gun - shoot()` `laser - shoot()` `rocket - shoot()`

## Singleton

Singleton pattern was used in `RandomPicker` class.

## Strategy

Strategy pattern was used for GUI design: the player can choose two colour themes. Applying strategy pattern allows adding extra  themes on the go, for example for holidays, by writing a relatively small amount of code.
```java
new public int main();
example of code with Christmas trees
```

The new theme changes only defined parts of the UI, keeping already programmed features.
