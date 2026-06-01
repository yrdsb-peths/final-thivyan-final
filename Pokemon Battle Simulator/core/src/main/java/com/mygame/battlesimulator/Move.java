package com.mygame.battlesimulator;


import com.badlogic.gdx.utils.JsonValue;

public class Move {
    //private String name;
    private String type;
    private String category;
    private int power;
    private int accuracy;
    private int points;


    public Move(JsonValue data)
    {
        this.type = data.getString("type");
        this.category = data.getString("category");

        this.power = data.getInt("hp");
        this.accuracy = data.getInt("attack");
        this.points = data.getInt("pp");
    }

    public String getType() {
        return type;
    }

    public String getCategory() {
        return category;
    }

    public int getPower() {
        return power;
    }

    public int getAccuracy() {
        return accuracy;
    }

    public int getPoints() {
        return points;
    }
}
