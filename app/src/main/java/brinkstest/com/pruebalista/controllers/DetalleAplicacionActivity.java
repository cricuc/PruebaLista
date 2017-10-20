package brinkstest.com.pruebalista.controllers;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import brinkstest.com.pruebalista.R;
import brinkstest.com.pruebalista.helpers.DownloadImageTask;

public class DetalleAplicacionActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_aplicacion);
        getSupportActionBar().setTitle("Detalle de aplicación");

        String nombre = getIntent().getStringExtra("Nombre");
        String categoria = getIntent().getStringExtra("Categoria");
        String imagen = getIntent().getStringExtra("Imagen");
        String fecha = getIntent().getStringExtra("Fecha");
        String summary = getIntent().getStringExtra("Summary");

        ((TextView)findViewById(R.id.tvNombre)).setText(nombre);
        ((TextView)findViewById(R.id.tvCategoria)).setText(categoria);
        ((TextView)findViewById(R.id.tvFecha)).setText(fecha);
        ((EditText)findViewById(R.id.tvDescripcion)).setText(summary);

        ImageView imageView = (ImageView) findViewById(R.id.ivIcono);
        new DownloadImageTask(imageView)
                .execute(imagen);

    }
}
