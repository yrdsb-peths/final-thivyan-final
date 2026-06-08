package com.mygame.battlesimulator;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.JsonValue;

public class BattleManager {
    private JsonValue root;
    private int damage;

    public void generateTeam()
    {

    }

    // Checks which Pokemon is faster, allowing the quicker to move first
    public boolean checkSpeed(Pokemon user, Pokemon opponent)
    {
        return user.getSpeed() > opponent.getSpeed();
    }

    // Calculates the damage by a Pokemon's move
    public String applyMoveDamage(Pokemon attacker, Pokemon defender, Move move, TypeChart moveType)
    {
        String type1 = defender.getType1();
        String type2 = defender.getType2();
        double attackBonus = 1;

        double effectiveness = getEffectiveness(type1, moveType) * getEffectiveness(type2, moveType);

        // Checks if the move meets the condition for Same-Type Attack Bonus (STAB)
        if (attacker.getType1().equals(move.getType()) || attacker.getType2().equals(move.getType()))
        {
            attackBonus = 1.3;
        }

        double modifier = effectiveness * attackBonus;

        if (move.getCategory().equals("Physical")) {
            double baseAttack = attacker.getAttack();
            double movesAttack = move.getPower();
            double defense = defender.getDefense();
            double pureDamage = ((0.84 * ((baseAttack * movesAttack) / defense)) + 2) * modifier;
            // Rounds damage
            damage = (int) pureDamage;
        } else if (move.getCategory().equals("Special")) {
            double baseAttack = attacker.getSpecialAttack();
            double movesAttack = move.getPower();
            double defense = defender.getSpecialDefense();
            double pureDamage = ((0.84 * ((baseAttack * movesAttack) / defense)) + 2) * modifier;
            // Rounds damage
            damage = (int) pureDamage;
        }

        defender.takeDamage(damage);

        // Returns how effective the move was
        if (effectiveness == 0)
        {
            System.out.println("It had no effect...");
            return "It had no effect...";
        }
        else if (effectiveness < 1)
        {
            System.out.println("It's not very effective...");
            return "It's not very effective...";
        }
        else if (effectiveness > 1)
        {
            System.out.println("It's super effective!");
            return "It's super effective!";
        }

        // Neutral effectiveness
        return "";
    }

    // Returns the effectiveness of a move's type against the defending type
    public float getEffectiveness(String type, TypeChart moveType)
    {
        if (type.equals("Normal"))
        {
            return moveType.getNormal();
        }
        else if (type.equals("Fire"))
        {
            return moveType.getFire();
        }
        else if (type.equals("Water"))
        {
            return moveType.getWater();
        }
        else if (type.equals("Grass"))
        {
            return moveType.getGrass();
        }
        else if (type.equals("Electric"))
        {
            return moveType.getElectric();
        }
        else if (type.equals("Ice"))
        {
            return moveType.getIce();
        }
        else if (type.equals("Fighting"))
        {
            return moveType.getFighting();
        }
        else if (type.equals("Poison"))
        {
            return moveType.getPoison();
        }
        else if (type.equals("Ground"))
        {
            return moveType.getGround();
        }
        else if (type.equals("Flying"))
        {
            return moveType.getFlying();
        }
        else if (type.equals("Psychic"))
        {
            return moveType.getPsychic();
        }
        else if (type.equals("Bug"))
        {
            return moveType.getBug();
        }
        else if (type.equals("Rock"))
        {
            return moveType.getRock();
        }
        else if (type.equals("Ghost"))
        {
            return moveType.getGhost();
        }
        else if (type.equals("Dragon"))
        {
            return moveType.getDragon();
        }
        else if (type.equals("Steel"))
        {
            return moveType.getSteel();
        }
        else if (type.equals("Dark"))
        {
            return moveType.getDark();
        }
        else if (type.equals("Fairy"))
        {
            return moveType.getFairy();
        }
        return 1;
    }

}
