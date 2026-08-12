# RPGGame

A console-based (CUI) RPG game written in Java.

## Concept

The CUI displays the player's name, current node location, and dungeon difficulty, with a main menu:

```
[1]: Travel
[2]: Node
[3]: Dungeon
[4]: Stats/Inventory
```

Both players and enemies (including bosses) share three core stats: Strength, Magic, and Agility.

There is a single, infinite dungeon (a loop the player exits by typing 'x'). The dungeon's active enemy pool is determined by the player's current node, and each node has its own boss.

Battle outcomes are threshold-based: each enemy has its own Strength/Magic/Agility thresholds, and the closer a player's matching stat is to an enemy's threshold, the higher the player's win chance (starting around 75% at parity and increasing beyond it). A stat of 0 on an enemy means immunity to that stat, not automatic player advantage. Dodge chance further affects win probability. Equipped items (weapons, rings, amulets, etc.) grant stat bonuses. All stats combine into an overall "Power Level" for both players and enemies.

## Project Structure

- `data/` - Text files containing item, enemy, node, node location, and dialogue template definitions, plus player save data.
- `images/` - ASCII art assets for the console UI.
- `lib/` - External JARs, if needed (e.g. JUnit, for the `test` target in `build.xml`).
- `nbproject/` - NetBeans project metadata.
- `src/main/` - Application source code, under the `main` package (and its subpackages, matching folder structure), organized into:
  - `config/` - Dependency wiring (`main.config`)
  - `core/` - Main game loop and orchestration (`main.core`)
  - `cui/` - Console rendering and input parsing (`main.cui`)
  - `domain/` - Player, world (node/location), combat (enemy/boss/dungeon/dialogue), and item models (`main.domain.*`)
  - `services/` - Combat, dialogue, and travel business logic (`main.services.*`)
  - `persistence/` - File-based repositories and I/O utilities (`main.persistence`)
  - `util/` - Shared helper utilities (`main.util`)
  - `exceptions/` - Custom checked exceptions for game/data/input errors (`main.exceptions`)
  - `resources/ascii/` - ASCII art resources
- `test/` - Unit tests, its own source root (so `test/services/CombatServiceTest.java` is package `services`, not `main.services`).

## Status

This is a scaffolded project structure reflecting the current game design. No mechanics are implemented yet.

## Object-Oriented Design

- **Abstraction**: `Item` is abstract; service behavior is defined via interfaces (`CombatService`, `TravelService`, `DialogueService`) separate from their implementations.
- **Encapsulation**: domain state (e.g. `Player`, `Stats`, `Enemy`) is intended to be accessed only through its own methods, not exposed fields.
- **Inheritance**: `Weapon extends Item`; `Boss extends Enemy`.
- **Polymorphism**: code that works with an `Enemy` (e.g. combat resolution) works unchanged when handed a `Boss`, which overrides enemy-specific behavior.
- **Exceptions**: `GameException` is the base checked exception, with `DataLoadException`, `SaveDataException`, `ItemNotFoundException`, and `InvalidCommandException` covering the main failure points (bad data files, save/load issues, missing references, invalid CUI input).

## Battle Sequencing

Battle outcomes are resolved first (win chance, dodge, weapon ability activation), then narrated: each resolved step becomes a `BattleAction`, matched to a dialogue template by keyword (e.g. `dodge`, `heavy_attack`) based on the stat-ratio difference between player and enemy (Agility differences produce dodge/slow-move lines, Strength/Magic differences produce attack-weight lines). `CombatSequencer` plays these actions out on a background thread, printing one line roughly every second via `CombatRenderer`, rather than dumping the whole sequence at once. Weapon ability activations (e.g. a temporary +8 Strength) are rolled per battle and narrated the same way. Players are still prompted to fight or run whenever they enter the dungeon or a boss encounter.

## Build

This project builds with Ant (`build.xml`), targeting JDK 25. `App.java` and its subpackages live under `src/main/` on disk, which maps to `package main;` and `package main.<subfolder>;` in code - so the Ant source root is `src` (not `src/main`), letting the `main` folder itself act as the top-level package. Tests are their own separate source root at `test/`, so `test/services/...` is package `services`, without a `main.` prefix.

Open the folder directly in NetBeans 28 as a Java project with an existing Ant script (File > New Project > Java with Existing Ant Script, pointing at `build.xml`) - no manual setup needed beyond that. From the command line:

```
ant compile      # compile to build/classes
ant jar          # package into dist/RPGGame.jar
ant run          # run the application
ant test         # run tests (requires a JUnit jar under lib/)
ant clean        # remove build/ and dist/
```

`manifest.mf` sets the runnable jar's entry point (`Main-Class: main.App`) and is used by the `jar` target.
