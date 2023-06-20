package com.example.practicaapi.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class ListaPokemon {
    @SerializedName("results")
    @Expose
    private ArrayList<Pokemon> listaPokemon;

    public ListaPokemon () {
        listaPokemon = new ArrayList<>();
    }


    public ArrayList<Pokemon> getListaPokemon () {
        return listaPokemon;
    }

}
