package com.mygame.battlesimulator;

//import com.badlogic.gdx.ApplicationAdapter;
//import com.badlogic.gdx.graphics.Texture;
//import com.badlogic.gdx.graphics.g2d.SpriteBatch;
//import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.utils.*;


/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class Main extends ApplicationAdapter {
//    private SpriteBatch batch;
//    private Texture image;
//
//    @Override
//    public void create() {
//        batch = new SpriteBatch();
//        image = new Texture("Venusaur.png");
//    }
//
//    @Override
//    public void render() {
//        ScreenUtils.clear(0.15f, 0.15f, 0.2f, 1f);
//        batch.begin();
//        batch.draw(image, 140, 210);
//        batch.end();
//    }
//
//    @Override
//    public void dispose() {
//        batch.dispose();
//        image.dispose();
//    }
    private SpriteBatch batch;
    private ShapeRenderer shapeRenderer;

    private Texture sheet;
    private Animation<TextureRegion> animation;


    private float stateTime;
    private BitmapFont font;

    @Override
    public void create() {

//        JsonReader reader = new JsonReader();
//
//
//        JsonValue root = reader.parse(Gdx.files.internal("Pokemon/data/pokemon.json"));
//
//
//        Pokemon rayquaza = new Pokemon(root.get("rayquaza"));
//
//
//        System.out.println(rayquaza.getName());
//        System.out.println(rayquaza.getHealth());
//        System.out.println(rayquaza.getAttack());
//        System.out.println(rayquaza.getType1());


//
//
//        // LOAD SPRITE SHEET
//        sheet = new Texture(rayquaza.getOppSprite());
//        int width = rayquaza.getWidth();
//        int height = rayquaza.getHeight();
//        int frameCount = rayquaza.getOppFrames();
//
//
//        // CUT THE SHEET INTO FRAMES
//        TextureRegion[][] temp = TextureRegion.split(sheet, width, height);
//
//        // venusaur 86x71, rayquaza 101x106 front: 110, 97, 100 x 89 reshiram back 108 x 83 front
//        // STORE FRAMES
//        Array<TextureRegion> frames = new Array<>();
//
//
//        //int frameCount = 225;
//
//
//        for (int row = 0; row < temp.length; row++) {
//
//
//            for (int col = 0; col < temp[row].length; col++) {
//
//
//                if (frames.size >= frameCount) {
//                    break;
//                }
//
//
//                frames.add(temp[row][col]);
//            }
//        }
//
//
//        // CREATE ANIMATION
//        animation = new Animation<>(0.05f, frames);
//
//
//        animation.setPlayMode(Animation.PlayMode.LOOP);
//
//
//        stateTime = 0f;

        //shapeRenderer = new ShapeRenderer();
        //batch = new SpriteBatch();
//        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/pokemon-ds-font.ttf"));
//
//
//        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
//
//
//        parameter.size = 32;
//
//
//        font = generator.generateFont(parameter);
//
//
//        generator.dispose();

        PokemonDatabase database = new PokemonDatabase();

        for (int x = 0; x < 2; x++) {
            System.out.println("Team " + x);
            Pokemon[] team = new Pokemon[6];
            team[0] = database.getRandomMega();
            team[1] = database.getRandomLegendary();
            for (int i = 2; i < 6; i++) {
                team[i] = database.getRandomPokemon();
                if (team[i] == team[0] || team[i] == team[1] || team[i] == team[2] || team[i] == team[3] || team[i] == team[4] || team[i] == team[5]) {
                    team[i] = database.getRandomPokemon();
                }
            }

            Team playerTeam = new Team(team);
            for (int i = 0; i < 6; i++) {
                System.out.println(playerTeam.getPokemon(i).getName());
            }
            //System.out.println()
        }
        //System.out.println("awrnoawt");
    }


    @Override
    public void render() {
//        ScreenUtils.clear(0.15f, 0.15f, 0.2f, 1f);
//        JsonReader reader = new JsonReader();
//
//
//        JsonValue root = reader.parse(Gdx.files.internal("pokemon/data/pokemon.json"));
//        JsonValue movesRoot = reader.parse(Gdx.files.internal("pokemon/data/moves.Json"));
//
//        Pokemon reshiram = new Pokemon(root.get("greninja"));
//
//
//        //System.out.println(reshiram.getName());
//
//        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
//
//        BattleScreen battleScreen = new BattleScreen();
//        //String moveType = "Dragon";
//        //battleScreen.findColour("Dragon");
//
//        for (int i = 0; i < 4; i++)
//        {
//            int x;
//            int y;
//
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
//
//            String moveName = reshiram.getMoves()[i];
//            JsonValue moves = movesRoot.get(moveName);
//            String moveType = moves.getString("type");
//
//            shapeRenderer.setColor(Color.valueOf(battleScreen.findColour(moveType)));
//
//
//            shapeRenderer.rect(x, y, 250, 70);
//        }
//
//
//        shapeRenderer.end();
//
//        batch.begin();
//
//        for (int i = 0; i < 4; i++) {
//
//            String moveName = reshiram.getMoves()[i];
//
//            int x = 50 + (i % 2) * 270;
//
//            int y = 50 + (i / 2) * 90;
//
//            font.draw(batch, moveName, x + 20, y + 45);
//        }
//
//        batch.end();

        //int damage = battle.calculateDamage();
        //System.out.println();


//        stateTime += Gdx.graphics.getDeltaTime();
//
//
//        Gdx.gl.glClearColor(0.15f, 0.15f, 0.2f, 1f);
//        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
//
//
//        TextureRegion currentFrame =
//            animation.getKeyFrame(stateTime);
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


    @Override
    public void dispose() {

        shapeRenderer.dispose();
        batch.dispose();
        sheet.dispose();
    }

}
