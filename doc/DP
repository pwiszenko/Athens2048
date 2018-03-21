# Design pattern implementation <a id="DP"></a>

This game uses Singleton, Strategy, Observator and Command patterns.

*Decoration pattern* wasn't used, because it would have no use for our game. The most obvious added functionalities of 2048 are:
- possibility to change grid's size
- possibility to play after achieving '2048' tile

For which decoration pattern has no use.
In a Asteroids-like game weapon upgrades can be programmed as decorations:
```
gun - shoot()
laser - shoot()
rocket - shoot()
```

Besides Decoration pattern, we also didn't implement *state pattern*: the game has only two states, what means that implementing the pattern is not worth it. It is worktime vs. effect unefficient.

## Singleton<a id="Singleton"></a>

Singleton pattern was used in `RandomPicker` class, we don't need more than one instance of the class, and that's the main reason why this pattern was used here.

## Strategy<a id="Strategy"></a>

Strategy pattern was used for GUI design: the player can choose two colour themes. Applying strategy pattern allows adding extra themes on the go, for example for holidays, by writing a relatively small amount of code.



The new theme changes only defined parts of the UI, keeping already programmed features.

## Observator<a id="Observator"></a>

This pattern was used mainly to make classes more independent, which allows easier further upgrades / improvements.

## Command<a id="Command"></a>

Command pattern allows adding logging game events, and reconstruct every movement from the start, by merging logged events. A replay/playback feature could be added in the future.
