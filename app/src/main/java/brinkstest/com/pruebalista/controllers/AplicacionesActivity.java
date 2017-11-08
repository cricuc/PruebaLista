package brinkstest.com.pruebalista.controllers;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

import brinkstest.com.pruebalista.R;
import brinkstest.com.pruebalista.adapters.AplicacionesArrayAdapter;
import brinkstest.com.pruebalista.models.Aplicacion;
import brinkstest.com.pruebalista.services.WSAplicaciones;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class AplicacionesActivity extends AppCompatActivity {

    Button btnONombre;
    Button btnOFecha;
    Context context = this;
    WSAplicaciones wsaplicaciones;
    List<String> glCategorias = new ArrayList<String>();
    List<Aplicacion> glTodasApliaciones = new ArrayList<Aplicacion>();
    List<Aplicacion> glAplicacionesFiltradas = new ArrayList<Aplicacion>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aplicaciones);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Top 20 Aplicaciones");
        setSupportActionBar(toolbar);

        ConsultarTopAplicaciones();

        btnONombre = (Button)findViewById(R.id.btnONombre);
        btnONombre.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                OrdenarPorNombre();
            }
        });

        btnOFecha = (Button)findViewById(R.id.btnOFecha);
        btnOFecha.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                OrdenarPorFecha();
            }
        });

        ((ListView)findViewById(R.id.lvCategorias)).setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position,
                                    long id) {

                String item = (String) parent.getItemAtPosition(position);

                Intent intent = new Intent(AplicacionesActivity.this, DetalleCategoriaActivity.class);
                intent.putExtra("Categoria", item);
                startActivity(intent);
            }
        });

        ((ListView)findViewById(R.id.lvAplicaciones)).setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position,
                                    long id) {

                Aplicacion item = (Aplicacion) parent.getItemAtPosition(position);

                Intent intent = new Intent(AplicacionesActivity.this, DetalleAplicacionActivity.class);
                intent.putExtra("Nombre", item.getName().getLabel());
                intent.putExtra("Categoria", item.getCategory().getAttributes().getLabel());
                intent.putExtra("Imagen", item.getImages().get(2).getLabel());
                intent.putExtra("Fecha", item.getReleaseDate().getLabel());
                intent.putExtra("Summary", item.getSummary().getLabel());
                startActivity(intent);
            }
        });

        ((EditText)findViewById(R.id.inputSearch)).addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence cs, int arg1, int arg2, int arg3) {
                // When user changed the Text
                glAplicacionesFiltradas = new ArrayList<Aplicacion>();
                glAplicacionesFiltradas.addAll(glTodasApliaciones);
                Log.i("Entrooo", String.valueOf(glAplicacionesFiltradas.size()));
                ((AplicacionesArrayAdapter)((ListView)findViewById(R.id.lvAplicaciones)).getAdapter()).getFilter().filter(cs);

            }

            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
                                          int arg3) {
                // TODO Auto-generated method stub

            }

            @Override
            public void afterTextChanged(Editable arg0) {
                // TODO Auto-generated method stub
            }
        });

    }

    private void ConsultarTopAplicaciones() {

        if(wsaplicaciones == null) {
            wsaplicaciones = new WSAplicaciones();
        }

        wsaplicaciones.getApi()
                .getAplicaciones()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<Aplicacion>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.i("ErrorenConsultarTopApps", e.toString());
                        e.printStackTrace();
                    }

                    @Override
                    public void onNext(List<Aplicacion> aplicaciones) {

                        glTodasApliaciones = aplicaciones;
                        glAplicacionesFiltradas.addAll(aplicaciones);

                        ArrayAdapter adapterCategorias = new ArrayAdapter(context,
                                android.R.layout.simple_list_item_1, ExtraerCategorias(glTodasApliaciones));

                        ((ListView)findViewById(R.id.lvCategorias)).setAdapter(adapterCategorias);

                        AplicacionesArrayAdapter adapterAplicaciones = new AplicacionesArrayAdapter(context,
                                android.R.layout.simple_list_item_1, glAplicacionesFiltradas);

                        ((ListView)findViewById(R.id.lvAplicaciones)).setAdapter(adapterAplicaciones);

                    }
                });
    }

    public List<String> ExtraerCategorias(List<Aplicacion> aplicaciones){

        glCategorias = new ArrayList<String>();

        for (Iterator<Aplicacion> i = aplicaciones.iterator(); i.hasNext();) {
            Aplicacion aplicacion = i.next();
            glCategorias.add(aplicacion.getCategory().getAttributes().getLabel());
        }
        glCategorias = glCategorias.stream().distinct().collect(Collectors.<String>toList());

        return glCategorias;

    }

    public void OrdenarPorNombre(){

        Collections.sort(glCategorias);

        ArrayAdapter adapterCategorias = new ArrayAdapter(context,
                android.R.layout.simple_list_item_1, glCategorias);

        Log.i("OrdenarPorNombre", String.valueOf(glAplicacionesFiltradas.size()));

        ((ListView)findViewById(R.id.lvCategorias)).setAdapter(adapterCategorias);

        ((ListView)findViewById(R.id.lvCategorias)).deferNotifyDataSetChanged();

        Collections.sort(glAplicacionesFiltradas,new Comparator<Aplicacion>(){
            public int compare(Aplicacion o1, Aplicacion o2){
                return o1.getName().getLabel().toUpperCase().compareTo(o2.getName().getLabel().toUpperCase());
            }
        });

        AplicacionesArrayAdapter adapterAplicaciones = new AplicacionesArrayAdapter(context,
                android.R.layout.simple_list_item_1, glAplicacionesFiltradas);

        ((ListView)findViewById(R.id.lvAplicaciones)).setAdapter(adapterAplicaciones);

        ((ListView)findViewById(R.id.lvAplicaciones)).deferNotifyDataSetChanged();

        ((EditText)findViewById(R.id.inputSearch)).setText(((EditText)findViewById(R.id.inputSearch)).getText());

    }

    public void OrdenarPorFecha(){

        Collections.sort(glAplicacionesFiltradas,new Comparator<Aplicacion>(){
            public int compare(Aplicacion o1, Aplicacion o2){
                return o2.getReleaseDate().getLabel().compareTo(o1.getReleaseDate().getLabel());
            }
        });

        AplicacionesArrayAdapter adapterAplicaciones = new AplicacionesArrayAdapter(context,
                android.R.layout.simple_list_item_1, glAplicacionesFiltradas);

        ((ListView)findViewById(R.id.lvAplicaciones)).setAdapter(adapterAplicaciones);

        ((ListView)findViewById(R.id.lvAplicaciones)).deferNotifyDataSetChanged();

        ((EditText)findViewById(R.id.inputSearch)).setText(((EditText)findViewById(R.id.inputSearch)).getText());

    }


}
