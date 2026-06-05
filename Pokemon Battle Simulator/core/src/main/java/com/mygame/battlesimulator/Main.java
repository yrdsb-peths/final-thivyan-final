package com.mygame.battlesimulator;

//import com.badlogic.gdx.ApplicationAdapter;
//import com.badlogic.gdx.graphics.Texture;
//import com.badlogic.gdx.graphics.g2d.SpriteBatch;
//import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
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
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.*;


/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class Main extends ApplicationAdapter {

    private OrthographicCamera camera;
    private Vector3 mousePosition = new Vector3();
    private SpriteBatch batch;
    private ShapeRenderer shapeRenderer;
    private PokemonRenderer playerRenderer;
    private PokemonRenderer oppRenderer;

    private Texture sheet;
    private Animation<TextureRegion> animation;
    private float stateTime;
    private BitmapFont font;

    private BattleManager battle;
    private Team playerTeam;
    private Team oppTeam;

    private float displayedPlayerHp;
    private float displayedOppHp;

    private String battleState;
    private String battleText;
    private String visibleBattleText;
    private float typingTimer;
    private int lettersShown;

    @Override
    public void create() {

//
//        // CREATE ANIMATION
//        animation = new Animation<>(0.05f, frames);
//
//
//        animation.setPlayMode(Animation.PlayMode.LOOP);
//
//
//        stateTime = 0f;

//        shapeRenderer = new ShapeRenderer();
//        batch = new SpriteBatch();

        camera = new OrthographicCamera();
        camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/pokemon-ds-font.ttf"));


        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();


        parameter.size = 32;


        font = generator.generateFont(parameter);


        generator.dispose();

        PokemonDatabase database = new PokemonDatabase();


        Pokemon[] team = new Pokemon[6];
        //team[0] = database.getRandomMega();
        team[0] = database.getPokemon("reshiram");
        team[1] = database.getRandomLegendary();
        for (int i = 2; i < 6; i++) {
            team[i] = database.getRandomPokemon();
            if (team[i] == team[0] || team[i] == team[1] || team[i] == team[2] || team[i] == team[3] || team[i] == team[4] || team[i] == team[5]) {
                team[i] = database.getRandomPokemon();
            }
        }

        playerTeam = new Team(team);
        for (int i = 0; i < 6; i++) {
            System.out.println(playerTeam.getPokemon(i).getName());
        }

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
        for (int i = 0; i < 6; i++) {
            System.out.println(oppTeam.getPokemon(i).getName());
        }


            //System.out.println()
        battle = new BattleManager();
        batch = new SpriteBatch();
        playerRenderer = new PokemonRenderer(playerTeam.getCurrentPokemon(), true);
        oppRenderer = new PokemonRenderer(oppTeam.getCurrentPokemon(), false);

        shapeRenderer = new ShapeRenderer();

        displayedPlayerHp = playerTeam.getCurrentPokemon().getCurrentHealth();
        displayedOppHp = oppTeam.getCurrentPokemon().getCurrentHealth();
        //System.out.println("awrnoawt");
        battleState = "CHOOSING";
    }


    @Override
    public void render() {
        camera.update();
        batch.setProjectionMatrix(camera.combined);
        shapeRenderer.setProjectionMatrix(camera.combined);

        ScreenUtils.clear(0.15f, 0.15f, 0.2f, 1f);
        displayedPlayerHp += (playerTeam.getCurrentPokemon().getCurrentHealth()-displayedPlayerHp)*0.08f;
        displayedOppHp += (oppTeam.getCurrentPokemon().getCurrentHealth()-displayedOppHp)*0.08f;
        float delta = Gdx.graphics.getDeltaTime();

        playerRenderer.update(delta);
        oppRenderer.update(delta);

        batch.begin();

        playerRenderer.draw(batch, 110, 80);
        oppRenderer.draw(batch, 370, 250);

        batch.end();

        // health bar
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        drawHp(playerTeam.getCurrentPokemon(), displayedPlayerHp, 400, 200);
        drawHp(oppTeam.getCurrentPokemon(), displayedOppHp, 80, 400);
        shapeRenderer.end();


        if (battleState.equals("CHOOSING")) {
            JsonReader reader = new JsonReader();


            JsonValue root = reader.parse(Gdx.files.internal("pokemon/data/pokemon.json"));
            JsonValue movesRoot = reader.parse(Gdx.files.internal("pokemon/data/moves.Json"));

            //System.out.println(reshiram.getName());

            shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);

            BattleScreen battleScreen = new BattleScreen();
            //String moveType = "Dragon";
            //battleScreen.findColour("Dragon");

            String[] moves = playerTeam.getCurrentPokemon().getMoves();

            int screenWidth = Gdx.graphics.getWidth();
            int buttonWidth = 180;
            int buttonHeight = 45;

            int startX = 40;
            int startY = 35;

            int gapX = 20;
            int gapY = 15;


            for (int i = 0; i < 4; i++) {
                int x = startX + (i % 2) * (buttonWidth + gapX);
                int y = startY + (i / 2) * (buttonHeight + gapY);


                //            if (i < 2)
                //            {
                //                 x = 50 + i * (270);
                //                 y = 50;
                //            }
                //            else
                //            {
                //                x = 50 + ((i - 2) * 270);
                //                y = 140;
                //            }

                String moveName = moves[i];
                JsonValue movesData = movesRoot.get(moveName);
                String moveType = movesData.getString("type");

                shapeRenderer.setColor(Color.valueOf(battleScreen.findColour(moveType)));


                shapeRenderer.rect(x, y, buttonWidth, buttonHeight);
            }


            shapeRenderer.end();

            batch.begin();

            for (int i = 0; i < 4; i++) {

                int x = startX + (i % 2) * (buttonWidth + gapX);
                int y = startY + (i / 2) * (buttonHeight + gapY);
                //            int x = 50 + (i % 2) * 270;
                //
                //            int y = 50 + (i / 2) * 90;

                font.draw(batch, moves[i], x + 12, y + 30);
            }

            batch.end();

            if (Gdx.input.justTouched()) {

                //            int mouseX = Gdx.input.getX();
                //            int mouseY = Gdx.graphics.getHeight() - Gdx.input.getY();
                mousePosition.set(Gdx.input.getX(), Gdx.input.getY(), 0);

                camera.unproject(mousePosition);
                float mouseX = mousePosition.x;
                float mouseY = mousePosition.y;
                //startX = screenWidth/2 - 200;
//                System.out.println("mouse x: " + mouseX);
//                System.out.println("mouse y: " + mouseY);

                for (int i = 0; i < 4; i++) {
                    int x = startX + (i % 2) * (buttonWidth + gapX);
                    int y = startY + (i / 2) * (buttonHeight + gapY);

                    if (mouseX >= x && mouseX <= x + buttonWidth && mouseY >= y && mouseY <= y + buttonHeight) {
                        String moveName = moves[i];
                        JsonValue moveData = movesRoot.get(moveName);
                        Move move = new Move(moveName, moveData);

                        battle.applyMoveDamage(playerTeam.getCurrentPokemon(), oppTeam.getCurrentPokemon(), move);

                        battleText = playerTeam.getCurrentPokemon().getName() + " used " + move.getName() + "!       ";
                        visibleBattleText = "";
                        lettersShown = 0;
                        typingTimer = 0f;
                        battleState = "FIGHTING";
                    }
                }
            }

            if (oppTeam.getCurrentPokemon().isFainted()) {
                System.out.println(oppTeam.getCurrentPokemon().getName() + " fainted");
                boolean switched = oppTeam.findAvailablePokemon();
                if (switched) {
                    oppRenderer.dispose();

                    oppRenderer = new PokemonRenderer(oppTeam.getCurrentPokemon(), false);
                } else {
                    System.out.println("no more pokemon");

                }
            }
        }
        else
        {
            typingTimer += Gdx.graphics.getDeltaTime();

            if (lettersShown < battleText.length() && typingTimer >= 0.03f)
            {
                lettersShown++;
                visibleBattleText = battleText.substring(0, lettersShown);
                typingTimer = 0f;
            }
            batch.begin();
            font.draw(batch, visibleBattleText, 40, 100);
            batch.end();
            if (visibleBattleText.equals(battleText)) {
                battleState = "CHOOSING";
            }

        }


//        stateTime += Gdx.graphics.getDeltaTime();
//
//
//        Gdx.gl.glClearColor(0.15f, 0.15f, 0.2f, 1f);
//        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
//
//
//        TextureRegion currentFrame = animation.getKeyFrame(stateTime);
//
//
//        batch.begin();
//
//
//        batch.draw(currentFrame, 140, 210);
//
//
//        batch.end();

    }

    public void drawHp (Pokemon pokemon, float displayedHp, float x, float y)
    {
        float maxWidth = 200;
        float height = 18;

        float hpPercent = displayedHp/pokemon.getHealth();

        shapeRenderer.setColor(Color.DARK_GRAY);
        shapeRenderer.rect(x, y, maxWidth, height);

        shapeRenderer.setColor(Color.GREEN);
        shapeRenderer.rect(x, y, maxWidth*hpPercent, height);
    }

    @Override
    public void dispose() {
        playerRenderer.dispose();
        oppRenderer.dispose();
        shapeRenderer.dispose();
        batch.dispose();
        //sheet.dispose();
    }

}
