package com.mygame.battlesimulator;

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
    private String opponentSprite;
    private String moves;
    private int width;
    private int height;
    private int frontFrames;
    private int backFrames;


    public void constructor(name, health, attack, defense, specialAttack, specialDefense, speed, type1, type2, userSprite, opponentSprite, moves, width, height, frontFrames, backFrames)
    {
        this.name = name;
        this.health = health;
        this.attack = attack;
        this.defense = defense;
        this.specialAttack = specialAttack;
        this.specialDefense = specialDefense;
        this.speed = speed;
        this.type1 = type1;
        this.type2 = type2;
        this.userSprite = userSprite;
        this.opponentSprite = opponentSprite;
        this.moves = moves;
        this.width = width;
        this.height = height;
        this.frontFrames = frontFrames;
        this.backFrames = backFrames;
    }
    

}
