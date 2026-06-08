package com.mygame.battlesimulator;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.JsonValue;
import com.badlogic.gdx.Gdx;

public class Pokemon {
    private String name;
    private int health;
    private int currentHealth;
    private int attack;
    private int defense;
    private int specialAttack;
    private int specialDefense;
    private int speed;
    private String type1;
    private String type2;
    private String userSprite;
    private String oppSprite;
    private String icon;
    private String[] moves;
    private int userWidth;
    private int userHeight;
    private int oppWidth;
    private int oppHeight;
    private int userFrames;
    private int oppFrames;
    private float scale;
    private boolean legendary;
    private boolean canMegaEvolve;

    private String status;
    private int attackStage;
    private int defenseStage;
    private int specialAttackStage;
    private int specialDefenseStage;
    private int speedStage;

    public Pokemon(JsonValue data)
    {
        this.name = data.getString("name");

        this.health = data.get("stats").getInt("hp");
        this.currentHealth = this.health;
        this.attack = data.get("stats").getInt("attack");
        this.defense = data.get("stats").getInt("defense");
        this.specialAttack = data.get("stats").getInt("specialAttack");
        this.specialDefense = data.get("stats").getInt("specialDefense");
        this.speed = data.get("stats").getInt("speed");;
        this.type1 = data.getString("type1");
        this.type2 = data.getString("type2");

        //JsonValue animation = data.get("animation");
        this.userSprite = data.get("animation").getString("userSprite");
        this.oppSprite = data.get("animation").getString("oppSprite");
        this.icon = data.get("animation").getString("icon");
        this.userWidth = data.get("animation").getInt("userWidth");
        this.userHeight = data.get("animation").getInt("userHeight");
        this.oppWidth = data.get("animation").getInt("oppWidth");
        this.oppHeight = data.get("animation").getInt("oppHeight");
        this.userFrames = data.get("animation").getInt("userFrames");
        this.oppFrames = data.get("animation").getInt("oppFrames");
        this.scale = data.get("animation").getInt("scale");

        //JsonValue movesData = data.get("moves");
        this.moves = new String[data.get("moves").size];

        for (int i = 0; i < data.get("moves").size; i++)
        {
            this.moves[i] = data.get("moves").getString(i);
        }

        this.legendary = data.getBoolean("legendary");
        this.canMegaEvolve = data.getBoolean("canMegaEvolve");

        this.status = "";
        this.attackStage = 0;
        this.defenseStage = 0;
        this.specialAttackStage = 0;
        this.specialDefenseStage = 0;
        this.speedStage = 0;
    }

    public void takeDamage(int damage)
    {
        currentHealth -= damage;
        if (currentHealth < 0)
        {
            currentHealth = 0;
        }
    }

    public boolean isFainted()
    {
        return currentHealth <= 0;
        //switchPokemon();
    }

    public String getName() {
        return name;
    }

    public int getHealth() {
        return health;
    }

    public int getCurrentHealth() {
        return currentHealth;
    }


    public void setHealth(int health) {
        this.health = health;
    }

    public int getAttack() {
        return attack;
    }

    public int getDefense() {
        return defense;
    }

    public int getSpecialAttack() {
        return specialAttack;
    }

    public int getSpecialDefense() {
        return specialDefense;
    }

    public int getSpeed() {
        return speed;
    }

    public String getType1() {
        return type1;
    }

    public String getType2() {
        return type2;
    }

    public String getUserSprite() {
        return userSprite;
    }

    public String getOppSprite() {
        return oppSprite;
    }

    public String getIcon() {
        return icon;
    }

    public int getUserWidth() {
        return userWidth;
    }

    public int getUserHeight() {
        return userHeight;
    }

    public int getOppWidth() {
        return oppWidth;
    }

    public int getOppHeight() {
        return oppHeight;
    }

    public int getUserFrames() {
        return userFrames;
    }

    public int getOppFrames() {
        return oppFrames;
    }

    public String[] getMoves() {
        return moves;
    }

    public boolean isLegendary()
    {
        return legendary;
    }

    public boolean canMega()
    {
        return canMegaEvolve;
    }

    public float getScale() {
        return scale;
    }

}
