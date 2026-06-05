package com.mygame.battlesimulator;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;

public class PokemonRenderer {
    private Pokemon pokemon;
    private Texture sheet;
    private Animation<TextureRegion> animation;
    private double stateTime;

    public PokemonRenderer(Pokemon pokemon, boolean playerSide)
    {
        this.pokemon = pokemon;

        String spritePath;
        int frameCount;
        TextureRegion[][] grid;

        if (playerSide)
        {
            spritePath = pokemon.getUserSprite();
            frameCount = pokemon.getUserFrames();
        }
        else
        {
            spritePath = pokemon.getOppSprite();
            frameCount = pokemon.getOppFrames();
        }

        sheet = new Texture(spritePath);

        sheet.setFilter(Texture.TextureFilter.Nearest, Texture.TextureFilter.Nearest);

        if (playerSide)
        {
            grid = TextureRegion.split(sheet, pokemon.getUserWidth(), pokemon.getUserHeight());
        }
        else
        {
            grid = TextureRegion.split(sheet, pokemon.getOppWidth(), pokemon.getOppHeight());
        }


        Array<TextureRegion> frames = new Array<>();

        for (int row = 0; row < grid.length; row++)
        {
            for (int col = 0; col < grid[row].length; col++)
            {
                if (frames.size >= frameCount)
                {
                    break;
                }
                frames.add(grid[row][col]);
            }
        }

        animation = new Animation<>(0.05f, frames);

        animation.setPlayMode(Animation.PlayMode.LOOP);
    }

    public void update(double delta)
    {
        stateTime += delta;
    }

    public void draw(SpriteBatch batch, double x, double y, boolean playerSide)
    {
        TextureRegion currentFrame = animation.getKeyFrame((float) stateTime);
        if (playerSide)
        {
            batch.draw(currentFrame, (float) x, (float) y, pokemon.getUserWidth() * pokemon.getScale(), pokemon.getUserHeight() * pokemon.getScale());
        }
        else
        {
            batch.draw(currentFrame, (float) x, (float) y, pokemon.getOppWidth() * pokemon.getScale(), pokemon.getOppHeight() * pokemon.getScale());
        }

    }

    public void dispose()
    {
        sheet.dispose();
    }
}
