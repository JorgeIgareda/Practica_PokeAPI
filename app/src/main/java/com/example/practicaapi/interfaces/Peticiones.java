package com.example.practicaapi.interfaces;

import com.example.practicaapi.model.ListaPokemon;
import com.example.practicaapi.model.Pokemon;
import com.example.practicaapi.model.Pokedex;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface Peticiones {
    @GET("pokemon/{id}")
    Call<Pokedex> consultar(@Path("id") String id);

    @GET("pokemon/?limit=50")
    Call<ListaPokemon> getLista(@Query("offset") int offset);
}
