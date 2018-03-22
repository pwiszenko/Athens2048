# Design pattern implementation <a id="DP"></a>

This game uses Singleton, Strategy, Observator, Command and MVC patterns.

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

This pattern was used to notify any display class (such as the AppFrame class) that no other move is possible,
it is a game over.


## Command<a id="Command"></a>

Command pattern is replacing a huge switch() structure in our code. It alos allows us the record
every single move of the player and every single spawned number.

Using this recording, we added the possibility to replay our play since the begining over and over.
Pressing `r` rewind game to the beginning, pressing `e` jump to the most recent move

We also used it to implement undo/redo feature.
Pressing `u` will undo last move, pressing `y` will redo last undone move.


## MVC<a id="MVC"></a>

Model view controller pattern allowed to separate responsibility of tasks in the game: movement management, GUI display, computations of changes in the board following user's input.
