# Design pattern implementation <a id="DP"></a>

We chose Singleton and Strategy patterns. We didn't use decoration pattern, because it would have no use for our game. Indeed, the only elements we have in the game are the board and the tiles. We could not think of a case where decorators could help to enhance it. Some of the possible new features of 2048 could be:
- possibility to change grid's size
- possibility to play after achieving '2048' tile

In a game where for example we have a spaceship on which we can upgrade it's weapons, we could treat them as decorations:
`gun - shoot()` `laser - shoot()` `rocket - shoot()`

## Singleton

Singleton pattern was used in `RandomPicker` class. The class takes care of randomly picking an empty tile in the game in order to make spawn a new number in it. In this case we don't need/want several instances of the same class.

## Strategy

Strategy pattern was used for GUI design: the player can choose two colour themes. Applying strategy pattern allows adding extra  themes on the runtime.
We could also use this implementation to apply temporary themes like a Christmas theme.
