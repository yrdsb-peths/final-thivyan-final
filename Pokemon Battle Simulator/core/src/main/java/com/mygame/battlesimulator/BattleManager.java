package com.mygame.battlesimulator;

public class BattleManager {
    private int damage;

    public int calculateDamage(Pokemon attacker, Pokemon defender, Move move) {

        if (move.getCategory().equals("Physical")) {
            double baseAttack = attacker.getAttack();
            double movesAttack = move.getPower();
            double defense = defender.getDefense();
            double pureDamage = (0.84 * ((baseAttack * movesAttack) / defense)) + 2;
            // Rounds damage
            damage = (int) pureDamage;
        } else if (move.getCategory().equals("Special")) {
            double baseAttack = attacker.getSpecialAttack();
            double movesAttack = move.getPower();
            double defense = defender.getSpecialDefense();
            double pureDamage = (0.84 * ((baseAttack * movesAttack) / defense)) + 2;
            // Rounds damage
            damage = (int) pureDamage;
        }

        return damage;
    }

    
}
