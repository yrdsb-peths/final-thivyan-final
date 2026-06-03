package com.mygame.battlesimulator;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.JsonValue;


public class PokemonDatabase {
    private Array<Pokemon> allPokemon;
    private Array<Pokemon> legendaryPokemon;
    private Array<Pokemon> megaPokemon;
    private Array<Pokemon> normalPokemon;

    public PokemonDatabase()
    {
        allPokemon = new Array<>();
        legendaryPokemon = new Array<>();
        megaPokemon = new Array<>();
        normalPokemon = new Array<>();

        loadPokemon();
    }

    private void loadPokemon() {
        JsonReader reader = new JsonReader();
        JsonValue root = reader.parse(Gdx.files.internal("Pokemon/data/pokemon.json"));

        for (JsonValue pokemonData = root.child; pokemonData != null; pokemonData = pokemonData.next)
        {
            Pokemon pokemon = new Pokemon(pokemonData);

            allPokemon.add(pokemon);

            if (pokemon.isLegendary())
            {
                legendaryPokemon.add(pokemon);
            }
            else
            {
                normalPokemon.add(pokemon);
            }

            if (pokemon.canMega())
            {
                megaPokemon.add(pokemon);
            }
        }
    }

    public Pokemon getRandomPokemon()
    {
        return allPokemon.get(MathUtils.random(allPokemon.size - 1));
    }

    public Pokemon getRandomLegendary()
    {
        return legendaryPokemon.get(MathUtils.random(legendaryPokemon.size - 1));
    }

    public Pokemon getRandomMega()
    {
        return megaPokemon.get(MathUtils.random(megaPokemon.size - 1));
    }

    public Pokemon getRandomNormal()
    {
        return normalPokemon.get(MathUtils.random(normalPokemon.size - 1));
    }

    public Pokemon getPokemon(String name)
    {
        for (Pokemon pokemon: allPokemon)
        {
            if (pokemon.getName().equals(name))
            {
                return pokemon;
            }
        }

        return null;
    }
}
