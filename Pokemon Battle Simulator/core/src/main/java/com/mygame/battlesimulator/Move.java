package com.mygame.battlesimulator;


import com.badlogic.gdx.utils.JsonValue;

// Handles the information associated with each move
public class Move {
    private String name;
    private String type;
    private String category;
    private int power;
    private int accuracy;
    private int points;

    // Constructs a move object with its own stats
    public Move(String name, JsonValue data)
    {
        this.name = name;
        this.type = data.getString("type");
        this.category = data.getString("category");

        this.power = data.getInt("power");
        this.accuracy = data.getInt("accuracy");
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

    public String getName() {
        return name;
    }
}
