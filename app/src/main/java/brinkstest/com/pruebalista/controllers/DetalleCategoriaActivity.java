package brinkstest.com.pruebalista.controllers;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import brinkstest.com.pruebalista.R;

public class DetalleCategoriaActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_categoria);
        getSupportActionBar().setTitle("Detalle de categoría");

        String nombreCat = getIntent().getStringExtra("Categoria");

        ((TextView)findViewById(R.id.tvNombre)).setText(nombreCat);

    }
}
