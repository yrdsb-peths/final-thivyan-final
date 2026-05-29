package com.mygame.battlesimulator;

//import com.badlogic.gdx.ApplicationAdapter;
//import com.badlogic.gdx.graphics.Texture;
//import com.badlogic.gdx.graphics.g2d.SpriteBatch;
//import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.JsonValue;



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


    private Texture sheet;
    private Animation<TextureRegion> animation;


    private float stateTime;


    @Override
    public void create() {

        JsonReader reader = new JsonReader();


        JsonValue root = reader.parse(Gdx.files.internal("Pokemon/data/pokemon.json"));


        Pokemon rayquaza = new Pokemon(root.get("rayquaza"));


        System.out.println(rayquaza.getName());
        System.out.println(rayquaza.getHealth());
        System.out.println(rayquaza.getAttack());
        System.out.println(rayquaza.getType1());

        batch = new SpriteBatch();


        // LOAD SPRITE SHEET
        sheet = new Texture(rayquaza.getOppSprite());
        int width = rayquaza.getWidth();
        int height = rayquaza.getHeight();
        int frameCount = rayquaza.getOppFrames();


        // CUT THE SHEET INTO FRAMES
        TextureRegion[][] temp = TextureRegion.split(sheet, width, height);

        // venusaur 86x71, rayquaza 101x106 front: 110, 97, 100 x 89 reshiram back 108 x 83 front
        // STORE FRAMES
        Array<TextureRegion> frames = new Array<>();


        //int frameCount = 225;


        for (int row = 0; row < temp.length; row++) {


            for (int col = 0; col < temp[row].length; col++) {


                if (frames.size >= frameCount) {
                    break;
                }


                frames.add(temp[row][col]);
            }
        }


        // CREATE ANIMATION
        animation = new Animation<>(0.05f, frames);


        animation.setPlayMode(Animation.PlayMode.LOOP);


        stateTime = 0f;

    }


    @Override
    public void render() {


        stateTime += Gdx.graphics.getDeltaTime();


        Gdx.gl.glClearColor(0.15f, 0.15f, 0.2f, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);


        TextureRegion currentFrame =
            animation.getKeyFrame(stateTime);


        batch.begin();


        batch.draw(currentFrame, 140, 210);


        batch.end();

    }


    @Override
    public void dispose() {


        batch.dispose();
        sheet.dispose();
    }

}
