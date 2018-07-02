# Space-Invaders-reborn

Next document show style of work.

Project represent mobile version for our favorite game from Atari 1978 Space Invader. The project in this repository is used for courses Clean Code and Mobile applications. Contributor -> https://github.com/katherine11 . Four main branches: master (release branch), develop (connect branch), Game-Design, Login-implementation. Work scheduler -> trello.

Environment:

- Android Studio 3.1.3
- Android Emulator

Language:

- Java

Structure:

- LoginActivity - connect user to game
- SpaceInvadersActivity - design of the game, connect all needed part together (Invader, PlayerShip, DefenceBrick, Bullet, SpaceInvaderView)
- SoaceInvaderView - will be the view of the game. It will also hold the logic of the game and respond to screen touches as well
- Invader - holds information for attackers
- PlayerShip - control based (from user input) object
- DefenceBrick - freezed object for game logic defence purpose
