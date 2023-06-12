package com.example.practicaapi.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class ListaPokemon {
    @SerializedName("results")
    @Expose
    private ArrayList<Pokemon> listaPokemon;

    public ListaPokemon () {
        listaPokemon = new ArrayList<Pokemon>();
    }

    public void setListaPokemon(ArrayList<Pokemon> listaPokemon){
        this.listaPokemon = listaPokemon;
    }

    public ArrayList<Pokemon> getListaPokemon () {
        return listaPokemon;
    }

    public void addPokemon(Pokemon pokemon){
        listaPokemon.add(pokemon);
    }

    public void addPokemon(String name, String url){
        Pokemon pokemon = new Pokemon();
        pokemon.setName(name);
        pokemon.setUrl(url);
        listaPokemon.add(pokemon);
    }
}
