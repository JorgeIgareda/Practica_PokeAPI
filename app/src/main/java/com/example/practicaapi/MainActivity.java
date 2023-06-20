package com.example.practicaapi;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.practicaapi.adapters.AdaptadorPokemon;
import com.example.practicaapi.interfaces.RecyclerViewInterface;
import com.example.practicaapi.model.ListaPokemon;
import com.example.practicaapi.model.ModeloRetorno;
import com.example.practicaapi.network.ConsultasApi;
import com.google.android.material.textfield.TextInputEditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class MainActivity extends AppCompatActivity implements RecyclerViewInterface {

    private int offset;
    private RecyclerView rvPokemon;
    private int posicionSeleccionada = RecyclerView.NO_POSITION;
    public ListaPokemon listaPokemon;
    private AdaptadorPokemon adaptadorPokemon;

    public Button btnConsultar;
    public Button btnVer50;
    public Button btnVer50Anteriores;
    public Button btnVer50Siguientes;
    public TextInputEditText txtConsulta;
    public String respuesta = "", imagen = "";
    public ModeloRetorno pokedex = new ModeloRetorno();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnConsultar = findViewById(R.id.btnConsultar);
        txtConsulta = findViewById(R.id.inConsulta);

        // Controla lo que hace la aplicación cuando el usuario pulsa
        // el botón "CONSULTAR" (debe mostrar los datos y el sprite frontal
        // del pokémon solicitado).
        btnConsultar.setOnClickListener(view -> {
            if (txtConsulta.getText() != null) {
                consultaPokemon(txtConsulta.getText().toString().toLowerCase().trim());
            }
        });

        rvPokemon = findViewById(R.id.item_list);
        rvPokemon.setLayoutManager(new GridLayoutManager(getApplicationContext(), 2));

        btnVer50 = findViewById(R.id.btnVer50);
        btnVer50Anteriores = findViewById(R.id.btnVer50anteriores);
        btnVer50Siguientes = findViewById(R.id.btnVer50siguientes);

        // Controla lo que hace la aplicación cuando el usuario pulsa
        // el botón "VER 50 PRIMEROS" (debe mostrar el nombre e imagen
        // de los 50 primeros pokémon).
        btnVer50.setOnClickListener(view -> {
            offset = 0;
            muestraLista(offset);
            btnVer50Siguientes.setVisibility(View.VISIBLE);
        });

        btnVer50Siguientes.setOnClickListener(view -> {
            offset += 50;
            muestraLista(offset);
            if (offset == 50) {
                btnVer50Anteriores.setVisibility(View.VISIBLE);
            } else if (offset == 1250) {
                btnVer50Siguientes.setVisibility(View.INVISIBLE);
            }
        });

        btnVer50Anteriores.setOnClickListener(view -> {
            offset -= 50;
            muestraLista(offset);
            if (offset == 0) {
                btnVer50Anteriores.setVisibility(View.INVISIBLE);
            } else if (offset == 1200) {
                btnVer50Siguientes.setVisibility(View.VISIBLE);
            }
        });


    }

    /**
     * Método auxiliar que facilita el uso de Toast.makeText
     *
     * @param mensaje mensaje que quiero mostrar en Toast
     */
    public void muestraToast(String mensaje) {
        Toast.makeText(this, mensaje, Toast.LENGTH_SHORT).show();
    }

    /**
     * Método que define lo que realiza la aplicación cuando el usuario
     * hace click en un elemento del RecyclerView
     *
     * @param position posición del adaptador
     */
    @Override
    public void onItemClick(int position) {

        // En caso de que no sea el primer Pokémon al que da click
        // hago transparente el fondo del anterior
        if (posicionSeleccionada != RecyclerView.NO_POSITION) {
            RecyclerView.ViewHolder anterior = rvPokemon.findViewHolderForAdapterPosition(posicionSeleccionada);
            if (anterior != null) {
                View vistaAnterior = anterior.itemView;
                vistaAnterior.setBackgroundColor(Color.TRANSPARENT);
            }
        }

        // Cambio el color de fondo del Pokémon al que el usuario da click por amarillo
        // Después de eso, muestra los datos del pokémon seleccionado.
        AdaptadorPokemon.ViewHolder actual = (AdaptadorPokemon.ViewHolder) rvPokemon.findViewHolderForAdapterPosition(position);
        if (actual != null) {
            View v = actual.itemView;
            v.setBackgroundColor(Color.YELLOW);
            consultaPokemon(actual.getName());
        }

        posicionSeleccionada = position; // Actualizo el valor de la ultima posición seleccionada
    }

    /**
     * Consulta en la API los datos de un pokémon y los muestra en una nueva pantalla
     *
     * @param nombre nombre del Pokémon que se quiere consultar
     */
    public void consultaPokemon(String nombre) {

        ConsultasApi rg = new ConsultasApi();
        try {
            rg.respuesta(nombre);
            muestraToast("Procesando...");
            final Handler handler = new Handler();
            handler.postDelayed(() -> {
                pokedex = rg.getModeloRetorno();
                if (pokedex.getId() != null) {
                    respuesta = "ID: " + pokedex.getId() + "\n" +
                            "Nombre: " + pokedex.getName() + "\n" +
                            "Altura: " + pokedex.getHeight() + " m \n" +
                            "Peso: " + pokedex.getWeight() + " kg";
                    imagen = pokedex.getFront_default();
                    Intent intent = new Intent(MainActivity.this, ConsultaActivity.class);

                    intent.putExtra("informacion", respuesta);
                    intent.putExtra("imagen", imagen);
                    startActivity(intent);
                } else {
                    muestraToast("Error: No existe ese pokémon.");
                }
            }, 1000);
        } catch (Exception e) {
            muestraToast("Error: " + e);
        }

    }

    /**
     * Consulta en la API el nombre e imagen de los 50 primeros pokémon a partir de un offset
     *
     * @param offset offset de la lista
     */
    public void muestraLista(int offset) {
        ConsultasApi rg = new ConsultasApi();
        try {
            rg.lista(offset);
            muestraToast("Procesando...");
            final Handler handler = new Handler();
            handler.postDelayed(() -> {
                listaPokemon = rg.getListaPokemon();
                adaptadorPokemon = new AdaptadorPokemon(getApplicationContext(), listaPokemon, offset, MainActivity.this);
                rvPokemon.setAdapter(adaptadorPokemon);
            }, 1000);
        } catch (Exception e) {
            muestraToast("Error: " + e);
            Log.d("ERROR", "ERROR: " + e);
        }
    }
}