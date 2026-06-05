package com.mygame.battlesimulator;


import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class BattleScreen {
    //private Pokemon
    private double displayedPlayerHp;
    private double displayedOppHp;
    private SpriteBatch batch;
    private Texture background;

    private void drawBackground()
    {
        batch.draw(background, 0, 0, 1280, 720);
    }



    public String findColour(String type)
    {
        if (type.equals("Normal")){
            return "aab09f";
        }
        else if (type.equals("Fire"))
        {
            return "ea7a3c";
        }
        else if (type.equals("Water"))
        {
            return "539ae2";
        }
        else if (type.equals("Electric"))
        {
            return "e5c531";
        }
        else if (type.equals("Grass"))
        {
            return "71c558";
        }
        else if (type.equals("Ice"))
        {
            return "70cbd4";
        }
        else if (type.equals("Fighting"))
        {
            return "cb5f48";
        }
        else if (type.equals("Poison"))
        {
            return "b468b7";
        }
        else if (type.equals("Ground"))
        {
            return "cc9f4f";
        }
        else if (type.equals("Flying"))
        {
            return "7da6de";
        }
        else if (type.equals("Psychic"))
        {
            return "e5709b";
        }
        else if (type.equals("Bug"))
        {
            return "94bc4a";
        }
        else if (type.equals("Rock"))
        {
            return "b2a061";
        }
        else if (type.equals("Ghost"))
        {
            return "846ab6";
        }
        else if (type.equals("Dragon"))
        {
            return "053976";
        }
        else if (type.equals("Dark"))
        {
            return "736c75";
        }
        else if (type.equals("Steel"))
        {
            return "89a1b0";
        }
        else if (type.equals("Fairy"))
        {
            return "e397d1";
        }
        return null;
    }


}
