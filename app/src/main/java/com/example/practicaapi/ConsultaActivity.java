package com.example.practicaapi;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class ConsultaActivity extends AppCompatActivity {

    TextView txt_info;
    ImageView img1;
    Button btnVolver;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consulta);
        txt_info = findViewById(R.id.txt_info);
        img1 = findViewById(R.id.imageView);
        btnVolver = findViewById(R.id.btnVolver);
        Bundle extras = getIntent().getExtras();
        String informacion = extras.getString("informacion");
        String imagen = extras.getString("imagen");
        txt_info.setText(informacion);
        Picasso.get().load(imagen).error(R.mipmap.ic_launcher).into(img1);
        btnVolver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ConsultaActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }
}
