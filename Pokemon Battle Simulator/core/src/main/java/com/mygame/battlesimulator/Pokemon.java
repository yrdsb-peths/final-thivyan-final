package com.mygame.battlesimulator;

import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.JsonValue;
import com.badlogic.gdx.Gdx;

public class Pokemon {
    private String name;
    private int health;
    private int attack;
    private int defense;
    private int specialAttack;
    private int specialDefense;
    private int speed;
    private String type1;
    private String type2;
    private String userSprite;
    private String oppSprite;
    private String[] moves;
    private int width;
    private int height;
    private int userFrames;
    private int oppFrames;

//    public Pokemon(String name, int health, int attack, int defense, int specialAttack, int specialDefense, int speed, String type1, String type2, String userSprite, String oppSprite, String[] moves, int width, int height, int userFrames, int oppFrames)
//    {
//        this.name = name;
//        this.health = health;
//        this.attack = attack;
//        this.defense = defense;
//        this.specialAttack = specialAttack;
//        this.specialDefense = specialDefense;
//        this.speed = speed;
//        this.type1 = type1;
//        this.type2 = type2;
//        this.userSprite = userSprite;
//        this.oppSprite = oppSprite;
//        this.moves = moves;
//        this.width = width;
//        this.height = height;
//        this.userFrames = userFrames;
//        this.oppFrames = oppFrames;
//    }

    public Pokemon(JsonValue data)
    {
        this.name = data.getString("name");
        
        this.health = data.get("stats").getInt("hp");
        this.attack = data.get("stats").getInt("attack");
        this.defense = data.get("stats").getInt("defense");
        this.specialAttack = data.get("stats").getInt("specialAttack");
        this.specialDefense = data.get("stats").getInt("specialDefense");
        this.speed = data.get("stats").getInt("speed");;
        this.type1 = data.get("stats").getString("type1");
        this.type2 = data.get("stats").getString("type2");

        //JsonValue animation = data.get("animation");
        this.userSprite = data.get("animation").getString("userSprite");
        this.oppSprite = data.get("animation").getString("oppSprite");
        this.width = data.get("animation").getInt("width");;
        this.height = data.get("animation").getInt("height");;
        this.userFrames = data.get("animation").getInt("userFrames");;
        this.oppFrames = data.get("animation").getInt("oppFrames");;

        //JsonValue movesData = data.get("moves");
        this.moves = new String[data.get("moves").size];

        for (int i = 0; i < data.get("moves").size; i++)
        {

        }

    }


}
