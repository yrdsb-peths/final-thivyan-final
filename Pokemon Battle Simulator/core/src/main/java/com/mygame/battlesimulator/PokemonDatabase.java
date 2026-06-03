package com.mygame.battlesimulator;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.JsonValue;


public class PokemonDatabase {
    private JsonValue root;

    private Array<String> allPokemon;
    private Array<String> legendaryPokemon;
    private Array<String> megaPokemon;
    private Array<String> normalPokemon;

    public PokemonDatabase() {
        allPokemon = new Array<>();
        legendaryPokemon = new Array<>();
        megaPokemon = new Array<>();
        normalPokemon = new Array<>();

        loadPokemon();
    }

    private void loadPokemon() {
        JsonReader reader = new JsonReader();
        root = reader.parse(Gdx.files.internal("Pokemon/data/pokemon.json"));

        for (JsonValue pokemonData = root.child; pokemonData != null; pokemonData = pokemonData.next) {
            String id = pokemonData.name;

            allPokemon.add(id);

            boolean legendary = pokemonData.getBoolean("legendary", false);
            boolean canMegaEvolve = pokemonData.getBoolean("canMegaEvolve", false);

            if (legendary) {
                legendaryPokemon.add(id);
            } else {
                normalPokemon.add(id);
            }

            if (canMegaEvolve) {
                megaPokemon.add(id);
            }
        }
    }

    public Pokemon getPokemon(String id) {
        return new Pokemon(root.get(id));
    }

    public Pokemon getRandomPokemon() {
        String id = allPokemon.get(MathUtils.random(allPokemon.size - 1));
        return getPokemon(id);
    }

    public Pokemon getRandomLegendary() {
        String id = legendaryPokemon.get(MathUtils.random(legendaryPokemon.size - 1));
        return getPokemon(id);
    }

    public Pokemon getRandomMega() {
        String id = megaPokemon.get(MathUtils.random(megaPokemon.size - 1));
        return getPokemon(id);
    }

    public Pokemon getRandomNormal() {
        String id = normalPokemon.get(MathUtils.random(normalPokemon.size - 1));
        return getPokemon(id);
    }
}

