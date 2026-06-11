package com.mygame.battlesimulator;

//import com.badlogic.gdx.ApplicationAdapter;
//import com.badlogic.gdx.graphics.Texture;
//import com.badlogic.gdx.graphics.g2d.SpriteBatch;
//import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.*;


/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class Main extends ApplicationAdapter {

    // Set up battle space and input
    private OrthographicCamera camera;
    private Vector3 mousePosition = new Vector3();
    private SpriteBatch batch;
    private Texture background;
    private TextureRegion[][] backgrounds;
    private ShapeRenderer shapeRenderer;
    private PokemonDatabase database;
    private PokemonRenderer playerRenderer;
    private PokemonRenderer oppRenderer;
    private PokemonRenderer iconRenderer;

    // Load the textures and fonts
    private Texture sheet;
    private Texture titleBackground;
    private Texture greenArrow;
    private Texture redArrow;
    private Animation<TextureRegion> animation;
    private float stateTime;
    private BitmapFont font;

    // Create the teams and battle
    private BattleManager battle;
    private Team playerTeam;
    private Team oppTeam;

    // Load pokemon/move/type details
    private JsonValue root;
    private JsonValue movesRoot;
    private JsonValue typesRoot;
    private Move userMove;
    private String userMoveName;
    private String oppMoveName;
    private TypeChart userType;

    // Display health bar
    private float displayedPlayerHp;
    private float displayedOppHp;

    // Choose background
    private int backgroundX;
    private int backgroundY;

    // Display text after a move is used
    private String battleState;
    private String battleText;
    private String effectiveText;
    private String userEffectiveness;
    private String oppEffectiveness;
    private String visibleBattleText;
    private String visibleEffectiveText;
    private float typingTimer;
    private int lettersShown;
    private boolean animationReset;

    // Keep track of turns
    private boolean playerMoveDone;
    private boolean oppMoveDone;
    private boolean firstTurn;
    private boolean megaUsed;

    // Music
    private Music battleMusic;
    private Music hitEffect;

    @Override
    public void create() {
        camera = new OrthographicCamera();
        camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        // Sets the custom font
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/pokemon-ds-font.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 32;
        parameter.borderWidth = 1;
        parameter.borderColor = Color.BLACK;
        font = generator.generateFont(parameter);
        generator.dispose();

        // Creates the player and opponent teams
        database = new PokemonDatabase();
        createPlayerTeam();
        createOppTeam();

        // Sets up the battle sprites
        battle = new BattleManager();
        batch = new SpriteBatch();
        playerRenderer = new PokemonRenderer(playerTeam.getCurrentPokemon(), true);
        oppRenderer = new PokemonRenderer(oppTeam.getCurrentPokemon(), false);
        shapeRenderer = new ShapeRenderer();
        //Texture greenArrow = new Texture("ui/greenArrows.png");
        //Texture redArrow = new Texture("ui/redArrow.png");

        // Gets the Pokemon/moves/types information from the respective JSON files
        JsonReader reader = new JsonReader();
        root = reader.parse(Gdx.files.internal("pokemon/data/pokemon.json"));
        movesRoot = reader.parse(Gdx.files.internal("pokemon/data/moves.Json"));
        typesRoot = reader.parse(Gdx.files.internal("pokemon/data/types.Json"));

        //
        displayedPlayerHp = playerTeam.getCurrentPokemon().getCurrentHealth();
        displayedOppHp = oppTeam.getCurrentPokemon().getCurrentHealth();
        battleState = "TITLE";

        hitEffect = Gdx.audio.newMusic(Gdx.files.internal("sounds/hit.mp3"));
        hitEffect.setVolume(0.8f);

        battleMusic = Gdx.audio.newMusic(Gdx.files.internal("sounds/champion.mp3"));
        battleMusic.setLooping(true);
        battleMusic.setVolume(0.2f);
        battleMusic.play();

        titleBackground = new Texture(Gdx.files.internal("ui/titleBackground.png"));
        backgroundX = MathUtils.random(0,2);
        backgroundY = MathUtils.random(0,2);
        background = new Texture ("ui/backgrounds.jpg");
        background.setFilter(Texture.TextureFilter.Nearest, Texture.TextureFilter.Nearest);
        backgrounds = TextureRegion.split(background, 504, 393);
    }


    @Override
    public void render() {
        camera.update();
        batch.setProjectionMatrix(camera.combined);
        shapeRenderer.setProjectionMatrix(camera.combined);

        ScreenUtils.clear(0.15f, 0.15f, 0.2f, 1f);

        if (!battleState.equals("GAME OVER") && !battleState.equals("TITLE")) {
            drawBackground();
            displayBattleBar();
        }

        if (battleState.equals("TITLE"))
        {
            drawTitleScreen();
        }
        else if (battleState.equals("PLAYER")) {
            playerMoveDone = true;
            oppMoveDone = true;
            getInput();
        }

        else if (battleState.equals("SPEED"))
        {
            chooseTurn();
        }
        else if (battleState.equals("PLAYER_ATTACK"))
        {
            if (!playerMoveDone)
            {
                getMove();
                playerMoveDone = true;
            }

            displayBattleText(true);
        }
        else if (battleState.equals("OPP_ATTACK"))
        {
            if (!oppMoveDone)
            {
                processOpponentTurn();
                oppMoveDone = true;
            }

            displayBattleText(false);
        }
        else if (battleState.equals("SWITCHING"))
        {
            switchPokemon();
        }
        else if (battleState.equals("GAME OVER"))
        {
            batch.begin();
            font.draw(batch, "Click to play again", 240, 250);
            batch.end();
            battleMusic.pause();

            if (Gdx.input.justTouched())
            {
                battleMusic.play();
                restartBattle();
            }
        }
    }


    private void drawTitleScreen()
    {
        batch.begin();
        batch.draw(titleBackground, 0, 0, 650, 500);
        font.draw(batch, "Pokemon Battle", 80, 380);
        font.draw(batch, "Simulator", 113, 350);
        font.draw(batch, "Click anywhere to start", 33, 280);
        batch.end();

        if (Gdx.input.justTouched())
        {
            battleState = "PLAYER";
        }
    }

    private void getInput()
    {
        String[] moves = playerTeam.getCurrentPokemon().getMoves();
        int buttonWidth = 180;
        int buttonHeight = 45;

        int startX = 40;
        int startY = 35;

        int gapX = 20;
        int gapY = 15;

        drawMoves(moves, buttonWidth, buttonHeight, gapX, gapY, startX, startY);

        batch.begin();

        for (int i = 0; i < 4; i++)
        {

            int x = startX + (i % 2) * (buttonWidth + gapX);
            int y = startY + (i / 2) * (buttonHeight + gapY);
            font.draw(batch, moves[i], x + 12, y + 30);
        }

        batch.end();

        if (Gdx.input.justTouched())
        {
            mousePosition.set(Gdx.input.getX(), Gdx.input.getY(), 0);

            camera.unproject(mousePosition);
            float mouseX = mousePosition.x;
            float mouseY = mousePosition.y;

            if (mouseX >= 475 && mouseX <= 555 && mouseY >= 75 && mouseY <= 100)
            {
                battleState = "SWITCHING";
            }

            for (int i = 0; i < 4; i++)
            {
                int x = startX + (i % 2) * (buttonWidth + gapX);
                int y = startY + (i / 2) * (buttonHeight + gapY);

                if (mouseX >= x && mouseX <= x + buttonWidth && mouseY >= y && mouseY <= y + buttonHeight)
                {
                    userMoveName = moves[i];
                    firstTurn = true;
                    playerMoveDone = false;
                    oppMoveDone = false;
                    battleState = "SPEED";
                }
            }
        }
    }

    private void chooseTurn()
    {
        if (battle.checkSpeed(playerTeam.getCurrentPokemon(), oppTeam.getCurrentPokemon()))
        {

            if (!playerMoveDone)
            {
                getMove();
                playerMoveDone = true;
            }

            displayBattleText(true);

        }
        else
        {

            if (!oppMoveDone)
            {
                processOpponentTurn();
                oppMoveDone = true;
            }

            displayBattleText(false);
        }
    }

    private void getMove()
    {
        JsonValue moveData = movesRoot.get(userMoveName);
        userMove = new Move(userMoveName, moveData);
        JsonValue typesData = typesRoot.get(userMove.getType());
        TypeChart type = new TypeChart(userMove.getType(), typesData);

        effectiveText = battle.applyMoveDamage(playerTeam.getCurrentPokemon(), oppTeam.getCurrentPokemon(), userMove, type);
        hitEffect.play();
        battleText = playerTeam.getCurrentPokemon().getName() + " used " + userMove.getName() + "!       ";
        visibleBattleText = "";
        visibleEffectiveText = "";
        lettersShown = 0;
        typingTimer = 0f;
        animationReset = true;
    }

    private void megaEvolve()
    {
        Pokemon current = playerTeam.getCurrentPokemon();

        if (megaUsed)
        {
            return;
        }

        if (!current.canMega())
        {
            return;
        }

        //Pokemon mega = database.get
    }

    private void drawTeamPokeballs(boolean user)
    {
        if (user) {
            for (int i = 0; i < 6; i++) {
                Texture pokeball = new Texture("ui/pokeballs.png");
                if (playerTeam.getPokemon(i).isFainted()) {
                    batch.setColor(Color.BLACK);
                }
                batch.draw(pokeball, 400 + 25 * i, 150, 20, 20);
                batch.setColor(Color.WHITE);
            }
        }
        else
        {
            for (int i = 0; i < 6; i++) {
                Texture pokeball = new Texture("ui/pokeballs.png");
                if (oppTeam.getPokemon(i).isFainted()) {
                    batch.setColor(Color.BLACK);
                }
                batch.draw(pokeball, 100 + 25 * i, 350, 20, 20);
                batch.setColor(Color.WHITE);
            }
        }
    }

    private void createPlayerTeam()
    {
        Pokemon[] team = new Pokemon[6];
        team[0] = database.getRandomMega();
        team[1] = database.getRandomLegendary();
        for (int i = 2; i < 6; i++) {
            team[i] = database.getRandomPokemon();
            if (team[i] == team[0] || team[i] == team[1] || team[i] == team[2] || team[i] == team[3] || team[i] == team[4] || team[i] == team[5]) {
                team[i] = database.getRandomPokemon();
            }
        }

        playerTeam = new Team(team);
    }

    private void createOppTeam()
    {
        Pokemon[] team2 = new Pokemon[6];
        team2[0] = database.getRandomMega();
        team2[1] = database.getRandomLegendary();
        for (int i = 2; i < 6; i++) {
            team2[i] = database.getRandomPokemon();
            if (team2[i] == team2[0] || team2[i] == team2[1] || team2[i] == team2[2] || team2[i] == team2[3] || team2[i] == team2[4] || team2[i] == team2[5]) {
                team2[i] = database.getRandomPokemon();
            }
        }

        oppTeam = new Team(team2);
    }

    private void drawMoves(String[] moves, int buttonWidth, int buttonHeight, int gapX, int gapY, int startX, int startY)
    {
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);

        for (int i = 0; i < 4; i++)
        {
            int x = startX + (i % 2) * (buttonWidth + gapX);
            int y = startY + (i / 2) * (buttonHeight + gapY);

            String moveName = moves[i];
            JsonValue movesData = movesRoot.get(moveName);
            String moveType = movesData.getString("type");

            shapeRenderer.setColor(Color.valueOf(findColour(moveType)));
            shapeRenderer.rect(x, y, buttonWidth, buttonHeight);
            //float effectiveness = battle.getEffectiveness(playerTeam.getCurrentPokemon().getType1(), moveType) * battle.getEffectiveness(playerTeam.getCurrentPokemon().getType2(), moveType);

            //if ()
        }

        shapeRenderer.end();
    }

    private void drawSuperEffective(int x, int y)
    {
        batch.begin();
        batch.draw(greenArrow, x - 10, y + 10);
        batch.end();

    }

    private void drawNotEffective()
    {

    }

    private void drawBackground()
    {
        batch.begin();
        batch.draw(backgrounds[backgroundX][backgroundY], 0, 0, 650, 500);
        batch.end();
    }


    // Displays health bars, names, levels, and types
    private void displayBattleBar()
    {
        displayedPlayerHp += (playerTeam.getCurrentPokemon().getCurrentHealth() - displayedPlayerHp) * 0.08f;
        displayedOppHp += (oppTeam.getCurrentPokemon().getCurrentHealth() - displayedOppHp) * 0.08f;
        float delta = Gdx.graphics.getDeltaTime();

        playerRenderer.update(delta);
        oppRenderer.update(delta);

        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);

        // Creates black bar
        Gdx.gl.glEnable(GL20.GL_BLEND);
        shapeRenderer.setColor(0f ,0f ,0f, 0.5f);
        shapeRenderer.rect(0, 30, Gdx.graphics.getWidth(), 115);

        // health bar
        drawHp(playerTeam.getCurrentPokemon(), displayedPlayerHp, 400, 200);
        drawHp(oppTeam.getCurrentPokemon(), displayedOppHp, 80, 400);

        shapeRenderer.end();
        Gdx.gl.glDisable(GL20.GL_BLEND);

        batch.begin();

        Texture playerType1 = new Texture("ui/" + playerTeam.getCurrentPokemon().getType1() + ".png");
        Texture playerType2 = new Texture("ui/" + playerTeam.getCurrentPokemon().getType2() + ".png");

        Texture oppType1 = new Texture("ui/" + oppTeam.getCurrentPokemon().getType1() + ".png");
        Texture oppType2 = new Texture("ui/" + oppTeam.getCurrentPokemon().getType2() + ".png");

        // Draws player Pokemon's types
        batch.draw(playerType1, 395, 175, 59, 20);
        batch.draw(playerType2, 455, 175, 59, 20);

        // Draws opponent Pomemon's types
        batch.draw(oppType1, 80, 374, 59, 20);
        batch.draw(oppType2, 140, 374, 59, 20);

        // draw pokeballs
        drawTeamPokeballs(true);
        drawTeamPokeballs(false);

        playerRenderer.draw(batch, 110, 80, true);
        font.draw(batch, playerTeam.getCurrentPokemon().getName(), 400, 250);
        font.draw(batch,  "   Lv. 100", 500, 250);
        font.draw(batch, playerTeam.getCurrentPokemon().getCurrentHealth() + "/" + playerTeam.getCurrentPokemon().getHealth(), 520, 195);

        oppRenderer.draw(batch, 370, 250, false);
        font.draw(batch, oppTeam.getCurrentPokemon().getName(), 80, 450);
        font.draw(batch, "   Lv. 100", 180, 450);

        font.draw(batch, "Switch", 485, 100);

        batch.end();


    }

    private void displayBattleText(boolean user)
    {
        if (!visibleBattleText.equals(battleText))
        {
            typingTimer += Gdx.graphics.getDeltaTime();

            if (lettersShown < battleText.length() && typingTimer >= 0.03f) {
                lettersShown++;
                visibleBattleText = battleText.substring(0, lettersShown);
                typingTimer = 0f;
            }

            batch.begin();
            font.draw(batch, visibleBattleText, 40, 100);
            batch.end();
        }
        else
        {
            if (user)
            {
                if (animationReset)
                {
                    lettersShown = 0;
                    typingTimer = 0f;
                    animationReset = false;
                }
                displayEffectiveness(true);
            }
            else if (!user)
            {
                if (animationReset)
                {
                    lettersShown = 0;
                    typingTimer = 0f;
                    animationReset = false;
                }
                displayEffectiveness(false);
            }
        }
    }

    private void switchPokemon()
    {
        Gdx.gl.glEnable(GL20.GL_BLEND);
        shapeRenderer.setAutoShapeType(true);
        shapeRenderer.begin();
        shapeRenderer.set(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(0f, 0f, 0f, 0.9f);
        shapeRenderer.rect(60, 10, 520, 450);
        shapeRenderer.set(ShapeRenderer.ShapeType.Line);
        shapeRenderer.setColor(Color.CLEAR_WHITE);
        shapeRenderer.rect(60, 10, 520, 450);
        shapeRenderer.set(ShapeRenderer.ShapeType.Filled);

        batch.begin();
        for (int i = 0; i < 6; i++)
        {
            Texture icon = new Texture(playerTeam.getPokemon(i).getIcon());
            if (playerTeam.getPokemon(i).isFainted())
            {
                batch.setColor(Color.BLACK);
            }
            batch.draw(icon, 90, 400 - 70*i, 40, 40);
            batch.setColor(Color.WHITE);
            font.draw(batch, playerTeam.getPokemon(i).getName(), 140, 420 - 70*i);
            drawHp(playerTeam.getPokemon(i), playerTeam.getPokemon(i).getCurrentHealth(), 350, 400 - 70*i);
//                shapeRenderer.set(ShapeRenderer.ShapeType.Line);
//                shapeRenderer.rect(85, 397 - 70*i, 470, 45);

        }

        font.draw(batch, "Back", 485, 450);

        if (Gdx.input.justTouched())
        {
            mousePosition.set(Gdx.input.getX(), Gdx.input.getY(), 0);

            camera.unproject(mousePosition);
            float mouseX = mousePosition.x;
            float mouseY = mousePosition.y;

            if (mouseX >= 475 && mouseX <= 555 && mouseY >= 420 && mouseY <= 460)
            {
                battleState = "PLAYER";
            }

            for (int i = 0; i < 6; i++)
            {
                if (mouseX >= 85 && mouseX <= 555 && mouseY >= 397 - 70*i && mouseY <= 420 - 70*i)
                {
                    // Switches to first pokemon
                    if (!playerTeam.getPokemon(i).isFainted())
                    {
                        playerTeam.switchPokemon(i);
                        playerRenderer.dispose();
                        playerRenderer = new PokemonRenderer(playerTeam.getCurrentPokemon(), true);
                        battleState = "PLAYER";
                    }
                }
            }
        }

        batch.end();
        shapeRenderer.end();
        Gdx.gl.glDisable(GL20.GL_BLEND);
    }

    private void displayEffectiveness(boolean user)
    {
        if (!visibleEffectiveText.equals(effectiveText))
        {
            typingTimer += Gdx.graphics.getDeltaTime();


            if (lettersShown < effectiveText.length() && typingTimer >= 0.03f)
            {
                lettersShown++;
                visibleEffectiveText = effectiveText.substring(0, lettersShown);
                typingTimer = 0f;
            }


            batch.begin();
            font.draw(batch, visibleEffectiveText, 40, 100);
            batch.end();
        }
        else
        {
            if (firstTurn)
            {
                if (user)
                {
                    oppMoveDone = false;
                    battleState = "OPP_ATTACK";
                    checkOppFainted();
                    firstTurn = false;
                }
                else if (!user)
                {
                    playerMoveDone = false;
                    battleState = "PLAYER_ATTACK";
                    checkUserFainted();
                    firstTurn = false;
                }
            }
            else
            {
                checkUserFainted();
                checkOppFainted();

                firstTurn = true;
                playerMoveDone = true;
                oppMoveDone = true;

                visibleBattleText = "";
                visibleEffectiveText = "";
                lettersShown = 0;
                typingTimer = 0f;

                battleState = "PLAYER";
            }
        }


    }

    private void processOpponentTurn()
    {
        String[] moves = oppTeam.getCurrentPokemon().getMoves();
        String moveName = moves[MathUtils.random(0, 3)];
        JsonValue moveData = movesRoot.get(moveName);
        Move move = new Move(moveName, moveData);
        JsonValue typesData = typesRoot.get(move.getType());
        TypeChart type = new TypeChart(move.getType(), typesData);

        //oppEffectiveness =
        effectiveText = battle.applyMoveDamage(oppTeam.getCurrentPokemon(), playerTeam.getCurrentPokemon(), move, type);
        hitEffect.play();
        battleText = oppTeam.getCurrentPokemon().getName() + " used " + move.getName() + "!       ";
        visibleBattleText = "";
        visibleEffectiveText = "";
        lettersShown = 0;
        typingTimer = 0f;
        //battleState = "SPEED";
        //firstTurn = true;
        animationReset = true;
    }

    private void checkOppFainted()
    {
        if (oppTeam.getCurrentPokemon().isFainted()) {
            System.out.println(oppTeam.getCurrentPokemon().getName() + " fainted");
            boolean switched = oppTeam.findAvailablePokemon();
            if (switched) {
                oppRenderer.dispose();

                oppRenderer = new PokemonRenderer(oppTeam.getCurrentPokemon(), false);
                battleState = "PLAYER";
            } else {
                System.out.println("no more pokemon");
                battleState = "GAME OVER";
            }
        }
    }

    private void checkUserFainted()
    {
        if (playerTeam.getCurrentPokemon().isFainted()) {
            System.out.println(playerTeam.getCurrentPokemon().getName() + " fainted");
            boolean switched = playerTeam.findAvailablePokemon();
            if (switched) {
                playerRenderer.dispose();

                playerRenderer = new PokemonRenderer(playerTeam.getCurrentPokemon(), true);
                battleState = "PLAYER";
            } else {
                System.out.println("no more pokemon");
                battleState = "GAME OVER";
            }
        }
    }

    private void restartBattle()
    {
        createPlayerTeam();
        createOppTeam();

        //System.out.println()
        battle = new BattleManager();
        batch = new SpriteBatch();
        playerRenderer = new PokemonRenderer(playerTeam.getCurrentPokemon(), true);
        oppRenderer = new PokemonRenderer(oppTeam.getCurrentPokemon(), false);

        shapeRenderer = new ShapeRenderer();

        displayedPlayerHp = playerTeam.getCurrentPokemon().getCurrentHealth();
        displayedOppHp = oppTeam.getCurrentPokemon().getCurrentHealth();

        // Creates new background
        backgroundX = MathUtils.random(0,2);
        backgroundY = MathUtils.random(0,2);

        battleState = "TITLE";
    }

    private void drawHp (Pokemon pokemon, float displayedHp, float x, float y) {
        float maxWidth = 200;
        float height = 18;

        float hpPercent = displayedHp / pokemon.getHealth();
        float hpWidth = maxWidth * hpPercent;

        if (hpWidth > maxWidth)
        {
            hpWidth = maxWidth;
        }

        shapeRenderer.setColor(Color.DARK_GRAY);
        shapeRenderer.rect(x, y, maxWidth, height);

        shapeRenderer.setColor(getHealthColour(hpPercent));
        shapeRenderer.rect(x, y, hpWidth, height);
    }

    private Color getHealthColour(float hpPercent)
    {
        if (hpPercent > 0.5)
        {
            return Color.valueOf("69DC12");
        }
        else if (hpPercent >= 0.2)
        {
            return Color.valueOf("FFDE00");
        }
        else
        {
            return Color.valueOf("FF0000");
        }

    }

    private String findColour(String type)
    {
        if (type.equals("Normal")){
            return "aab09f";
        }
        else if (type.equals("Fire"))
        {
            return "ea7a3c";
        }
        else if (type.equals("Water"))
        {
            return "539ae2";
        }
        else if (type.equals("Electric"))
        {
            return "e5c531";
        }
        else if (type.equals("Grass"))
        {
            return "71c558";
        }
        else if (type.equals("Ice"))
        {
            return "70cbd4";
        }
        else if (type.equals("Fighting"))
        {
            return "cb5f48";
        }
        else if (type.equals("Poison"))
        {
            return "b468b7";
        }
        else if (type.equals("Ground"))
        {
            return "cc9f4f";
        }
        else if (type.equals("Flying"))
        {
            return "7da6de";
        }
        else if (type.equals("Psychic"))
        {
            return "e5709b";
        }
        else if (type.equals("Bug"))
        {
            return "94bc4a";
        }
        else if (type.equals("Rock"))
        {
            return "b2a061";
        }
        else if (type.equals("Ghost"))
        {
            return "846ab6";
        }
        else if (type.equals("Dragon"))
        {
            return "053976";
        }
        else if (type.equals("Dark"))
        {
            return "736c75";
        }
        else if (type.equals("Steel"))
        {
            return "89a1b0";
        }
        else if (type.equals("Fairy"))
        {
            return "e397d1";
        }
        return null;
    }

    @Override
    public void dispose() {
        background.dispose();
        hitEffect.dispose();
        battleMusic.dispose();
        playerRenderer.dispose();
        oppRenderer.dispose();
        shapeRenderer.dispose();
        batch.dispose();
        //sheet.dispose();
    }

}
