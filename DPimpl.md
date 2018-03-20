# Design pattern implementation <a id="DP"></a>

We chose Singleton and Strategy patterns, because ...

We didn't use decoration pattern, because it would have no use for our game. The most obvious added functionalities of 2048 are:
- possibility to change grid's size
- possibility to play after achieving '2048' tile

In a game where for example we have a spaceship on which we can upgrade it's weapons, we could treat them as decorations:
`gun - shoot()` `laser - shoot()` `rocket - shoot()`

## Singleton

## Strategy
