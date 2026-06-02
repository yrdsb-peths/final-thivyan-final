package com.mygame.battlesimulator;

public class Team {
    private Pokemon[] pokemon;
    private int currentIndex;

    // Creates a Pokemon team based on a list of names;
    public Team(Pokemon[] pokemon)
    {
        this.pokemon = pokemon;
        this.currentIndex = 0;
    }

    // Gets the current pokemon
    public Pokemon getActivepokemon()
    {
        return pokemon[currentIndex];
    }

    // Gets the pokemon at a specific index
    public Pokemon getPokemon(int index)
    {
        return pokemon[index];
    }

    // Switches pokemon based on what the user selects
    public void switchPokemon(int newIndex)
    {
        currentIndex = newIndex;
    }

    public boolean allFainted()
    {
        //
    }
}
