# PROJECT

My game "Pokemon Battle Simulator" is a fan-made Pokemon game that focuses on the battling aspect between teams of Pokemon.
- Once the user clicks the title screen, randomly generated teams of 6 are made for the player and the opponent.
- Each Pokemon has its own unique stats, moveset (four moves), and typing (up to two types).
- Each move has its own power stat, but the damage also scales on the type effectiveness and STAB (Same Type Attack Bonus)
- Type effectiveness scales on how the type of the attacking move matches up against each type of the opposing Pokemon
- Opponent moves are randomly chosen.
- The Pokemon with the higher speed stat will attack first
- The user can also switch their Pokemon at the start of every turn.
- The goal is to defeat the opponent's six Pokemon before the opponent can defeat the player
- Once victorious or defeated, the player can click on the screen to restart

# CREDITS

Code
- Making large JSON files: ChatGPT
- Overall aid on how to use LibGDX with IntelliJIdea: ChatGPT

- Pokemon: Nintendo, Game Freak, Creature Inc.
- Pokemon Sprite Sheets: Pokemon Database (https://pokemondb.net/sprites/)
- Pokemon Type Images: Bulbagarden (https://archives.bulbagarden.net/w/index.php?title=Category:Type_icons&fileuntil=PoisonIC+SMD.png#mw-category-media)
- Pokemon Font: Fontstruct (https://fontstruct.com/fontstructions/show/1027411/pok_mon_black_white_black_2_and_white_2_tex)
- Pokeball image: Kindpng (https://www.kindpng.com/free/pokeballs/)
- Title Screen background: Reddit (https://www.reddit.com/r/pokemon/comments/2ho8av/my_collection_of_pokemon_wallpapers_gathered_over/)
- Battle Screen backgrounds: DevianArt (https://www.deviantart.com/snivy101/art/Pokemon-B2W2-Battle-Backgrounds-319706977)


# Pokemon

A [libGDX](https://libgdx.com/) project generated with [gdx-liftoff](https://github.com/libgdx/gdx-liftoff).

This project was generated with a template including simple application launchers and an `ApplicationAdapter` extension that draws libGDX logo.

## Platforms

- `core`: Main module with the application logic shared by all platforms.
- `lwjgl3`: Primary desktop platform using LWJGL3; was called 'desktop' in older docs.

## Gradle

This project uses [Gradle](https://gradle.org/) to manage dependencies.
The Gradle wrapper was included, so you can run Gradle tasks using `gradlew.bat` or `./gradlew` commands.
Useful Gradle tasks and flags:

- `--continue`: when using this flag, errors will not stop the tasks from running.
- `--daemon`: thanks to this flag, Gradle daemon will be used to run chosen tasks.
- `--offline`: when using this flag, cached dependency archives will be used.
- `--refresh-dependencies`: this flag forces validation of all dependencies. Useful for snapshot versions.
- `build`: builds sources and archives of every project.
- `cleanEclipse`: removes Eclipse project data.
- `cleanIdea`: removes IntelliJ project data.
- `clean`: removes `build` folders, which store compiled classes and built archives.
- `eclipse`: generates Eclipse project data.
- `idea`: generates IntelliJ project data.
- `lwjgl3:jar`: builds application's runnable jar, which can be found at `lwjgl3/build/libs`.
- `lwjgl3:run`: starts the application.
- `test`: runs unit tests (if any).

Note that most tasks that are not specific to a single project can be run with `name:` prefix, where the `name` should be replaced with the ID of a specific project.
For example, `core:clean` removes `build` folder only from the `core` project.
