package com.mygame.battlesimulator;

import com.badlogic.gdx.utils.JsonValue;

public class TypeChart {
    private String name;
    private float normal;
    private float fire;
    private float water;
    private float grass;
    private float electric;
    private float ice;
    private float fighting;
    private float poison;
    private float ground;
    private float flying;
    private float psychic;
    private float bug;
    private float rock;
    private float ghost;
    private float dragon;
    private float dark;
    private float steel;
    private float fairy;

    // Constructs a move's type and how effective it is against each other type
    public TypeChart(String name, JsonValue data)
    {
        this.name = name;
        this.normal = data.getFloat("Normal", 1);
        this.fire = data.getFloat("Fire", 1);
        this.water = data.getFloat("Water", 1);
        this.grass = data.getFloat("Grass", 1);
        this.electric = data.getFloat("Electric", 1);
        this.ice = data.getFloat("Ice", 1);
        this.fighting = data.getFloat("Fighting", 1);
        this.poison = data.getFloat("Poison", 1);
        this.ground = data.getFloat("Ground", 1);
        this.flying = data.getFloat("Flying", 1);
        this.psychic = data.getFloat("Psychic", 1);
        this.bug = data.getFloat("Bug", 1);
        this.rock = data.getFloat("Rock", 1);
        this.ghost = data.getFloat("Ghost", 1);
        this.dragon = data.getFloat("Dragon", 1);
        this.dark = data.getFloat("Dark", 1);
        this.steel = data.getFloat("Steel", 1);
        this.fairy = data.getFloat("Fairy", 1);
    }

    // Getter methods to get the effectiveness of a move's type against each Pokemon type

    public float getNormal() {
        return normal;
    }

    public float getFire() {
        return fire;
    }

    public float getWater() {
        return water;
    }

    public float getGrass() {
        return grass;
    }

    public float getElectric() {
        return electric;
    }

    public float getIce() {
        return ice;
    }

    public float getFighting() {
        return fighting;
    }

    public float getPoison() {
        return poison;
    }

    public float getGround() {
        return ground;
    }

    public float getFlying() {
        return flying;
    }

    public float getPsychic() {
        return psychic;
    }

    public float getBug() {
        return bug;
    }

    public float getRock() {
        return rock;
    }

    public float getGhost() {
        return ghost;
    }

    public float getDragon() {
        return dragon;
    }

    public float getDark() {
        return dark;
    }

    public float getSteel() {
        return steel;
    }

    public float getFairy() {
        return fairy;
    }

    public String getName() {
        return name;
    }
}
