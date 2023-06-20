package com.example.practicaapi.network;

import android.util.Log;
import android.widget.Toast;

import com.example.practicaapi.interfaces.Peticiones;
import com.example.practicaapi.model.ListaPokemon;
import com.example.practicaapi.model.ModeloRetorno;
import com.example.practicaapi.model.Pokedex;
import com.example.practicaapi.model.ListaPokemon;
import com.example.practicaapi.model.Pokemon;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ConsultasApi {

    public static String url = "https://pokeapi.co/api/v2/";
    private static Retrofit retrofit;
    private ModeloRetorno modeloRetorno = new ModeloRetorno();
    private ListaPokemon listaPokemon = new ListaPokemon();

    public void respuesta(String id) {

        retrofit = new Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create(new GsonBuilder().serializeNulls().create()))
                .build();
        Peticiones consApi = retrofit.create(Peticiones.class);

        Call<Pokedex> call = consApi.consultar(id);

        call.enqueue(new Callback<Pokedex>() {
            @Override
            public void onResponse(Call<Pokedex> call, Response<Pokedex> response) {
                try {
                    if (response.isSuccessful()) {
                        Pokedex pokedex = response.body();
                        assert pokedex != null;
                        modeloRetorno.setId(pokedex.getId());
                        modeloRetorno.setName(Character.toUpperCase(pokedex.getName().charAt(0)) + pokedex.getName().substring(1));
                        modeloRetorno.setHeight(pokedex.getHeight() / 10.0);
                        modeloRetorno.setWeight(pokedex.getWeight() / 10.0);
                        modeloRetorno.setFront_default(pokedex.getSprites().getFront_default());
                    } else {
                        Log.d("RESPUESTA FALLIDA", "ERROR: " + call);
                    }
                } catch (Exception e) {
                    Log.d("EXCEPTION", "ERROR: " + e);
                }
            }

            @Override
            public void onFailure(Call<Pokedex> call, Throwable t) {
                Log.d("FALLO", "ERROR: " + call + t);
            }
        });
    }

    public void lista(int offset) {
        retrofit = new Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        Peticiones consApi = retrofit.create(Peticiones.class);
        Call<ListaPokemon> call = consApi.getLista(offset);
        call.enqueue(new Callback<ListaPokemon>() {
            @Override
            public void onResponse(Call<ListaPokemon> call, Response<ListaPokemon> response) {
                if (response.isSuccessful()) {
                    listaPokemon = response.body();
                } else {
                    Log.d("RESPUESTA FALLIDA", "ERROR: " + call);
                }
            }

            @Override
            public void onFailure(Call<ListaPokemon> call, Throwable t) {
                Log.d("FALLO", "ERROR: " + call + t);
            }
        });

    }

    public ModeloRetorno getModeloRetorno () {
        return modeloRetorno;
    }

    public ListaPokemon getListaPokemon() {
        return listaPokemon;
    }
}
