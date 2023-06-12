package com.example.practicaapi;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.practicaapi.interfaces.RecyclerViewInterface;
import com.example.practicaapi.model.ListaPokemon;
import com.example.practicaapi.model.Pokemon;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class AdaptadorPokemon extends RecyclerView.Adapter<AdaptadorPokemon.ViewHolder> {

    private final RecyclerViewInterface recyclerViewInterface;

    private ArrayList<Pokemon> listaPokemon;
    private static Context context;
    private final String url = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/";
    private int offset;
    public static class ViewHolder extends RecyclerView.ViewHolder {

        private TextView textView;
        private ImageView imageView;

        public ViewHolder(View view, RecyclerViewInterface recyclerViewInterface) {
            super(view);
            // Definir click listener

            textView = view.findViewById(R.id.tvPokemon);
            imageView = view.findViewById(R.id.ivPokemon);

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (recyclerViewInterface != null) {
                        int pos = getAdapterPosition();

                        if (pos != RecyclerView.NO_POSITION) {
                            recyclerViewInterface.onItemClick(pos);
                        }
                    }
                }
            });

        }

        public String getName() {
            return textView.getText().toString();
        }

    }


    /**
     * Inicializar los datos del adaptador
     */
    public AdaptadorPokemon(Context context, ListaPokemon listaPokemon, int offset, RecyclerViewInterface recyclerViewInterface) {
        this.recyclerViewInterface = recyclerViewInterface;
        this.context = context;
        this.offset = offset;
        this.listaPokemon = listaPokemon.getListaPokemon();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.pokemon_row, parent, false);
        return new ViewHolder(view, recyclerViewInterface);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Pokemon pokemon = listaPokemon.get(position);
        holder.textView.setText(pokemon.getName());
        int i = position + 1 + offset;
        if (i > 1010) {
            i += 8990;
        }
        Picasso.get().load(url + (i) + ".png").error(R.mipmap.ic_launcher).into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return listaPokemon.size();
    }

}

