package brinkstest.com.pruebalista.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.RemoteViews;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import brinkstest.com.pruebalista.R;
import brinkstest.com.pruebalista.helpers.DownloadImageTask;
import brinkstest.com.pruebalista.models.Aplicacion;

/**
 * Created by cc_ri on 20/10/2017.
 */

public class AplicacionesArrayAdapter extends ArrayAdapter<Aplicacion> {

    List<Aplicacion> aplicaciones = new ArrayList<>();
    List<Aplicacion> TodasAplicaciones = new ArrayList<>();

    public AplicacionesArrayAdapter(Context context, int textViewResourceId, List<Aplicacion> objects) {
        super(context, textViewResourceId, objects);
        TodasAplicaciones = new ArrayList<>();
        aplicaciones = objects;
        TodasAplicaciones.addAll(objects);
    }

    @Override
    public int getCount() {
        return super.getCount();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View v = convertView;
        if(position<aplicaciones.size()) {
            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inflater.inflate(R.layout.adapter_listitemaplicaciones, null);
            TextView textView = (TextView) v.findViewById(R.id.textView);
            ImageView imageView = (ImageView) v.findViewById(R.id.imageView);
            Log.i("CRICUC", String.valueOf(aplicaciones.size()) + " -|- " + String.valueOf(position));
            textView.setText(aplicaciones.get(position).getName().getLabel());
            new DownloadImageTask(imageView)
                    .execute(aplicaciones.get(position).getImages().get(2).getLabel());
        }

        return v;

    }

    @Override
    public Filter getFilter() {

        Filter myFilter = new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                String cadena = String.valueOf(constraint).toUpperCase();
                FilterResults filterResults = new FilterResults();
                ArrayList<Aplicacion> tempList=new ArrayList<Aplicacion>();
                //constraint is the result from text you want to filter against.
                //objects is your data set you will filter from
                aplicaciones.clear();
                aplicaciones.addAll(TodasAplicaciones);
                if(cadena != null && aplicaciones!=null) {
                    Log.i("FILTRO", cadena + " -|- " + String.valueOf(aplicaciones.size())+ " -|- " + String.valueOf(TodasAplicaciones.size()));
                    int length=aplicaciones.size();
                    for (Iterator<Aplicacion> i = aplicaciones.iterator(); i.hasNext();) {
                        Aplicacion item = i.next();
                        Log.i("ITERADOR", item.getName().getLabel() + " -|- " + item.getName().getLabel().toUpperCase().contains(cadena));
                        if(item.getName().getLabel().toUpperCase().contains(cadena)) {
                            tempList.add(item);
                        }
                    }
                    //following two lines is very important
                    //as publish result can only take FilterResults objects
                    filterResults.values = tempList;
                    filterResults.count = tempList.size();
                }
                return filterResults;
            }

            @SuppressWarnings("unchecked")
            @Override
            protected void publishResults(CharSequence contraint, FilterResults results) {

                List<Aplicacion> filtrado = (ArrayList<Aplicacion>) results.values;
                List<Aplicacion> borrar = new ArrayList<Aplicacion>();

                for (Iterator<Aplicacion> i = aplicaciones.iterator(); i.hasNext();) {
                    Aplicacion item = i.next();
                    if(!filtrado.contains(item)){
                        borrar.add(item);
                    }
                }

                aplicaciones.removeAll(borrar);

                Log.i("Flag",String.valueOf(aplicaciones.size()));
                if (results.count > 0) {
                    notifyDataSetChanged();
                } else {
                    notifyDataSetInvalidated();
                }
            }
        };

        return myFilter;
    }

}
