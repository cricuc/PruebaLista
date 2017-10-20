package brinkstest.com.pruebalista.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import brinkstest.com.pruebalista.R;
import brinkstest.com.pruebalista.helpers.DownloadImageTask;
import brinkstest.com.pruebalista.models.Aplicacion;

/**
 * Created by cc_ri on 20/10/2017.
 */

public class AplicacionesArrayAdapter extends ArrayAdapter<Aplicacion> {

    List<Aplicacion> aplicaciones = null;

    public AplicacionesArrayAdapter(Context context, int textViewResourceId, List<Aplicacion> objects) {
        super(context, textViewResourceId, objects);
        aplicaciones = objects;
    }

    @Override
    public int getCount() {
        return super.getCount();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View v = convertView;
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        v = inflater.inflate(R.layout.adapter_listitemaplicaciones, null);
        TextView textView = (TextView) v.findViewById(R.id.textView);
        ImageView imageView = (ImageView) v.findViewById(R.id.imageView);
        textView.setText(aplicaciones.get(position).getName().getLabel());
        new DownloadImageTask(imageView)
                .execute(aplicaciones.get(position).getImages().get(2).getLabel());
        return v;

    }

    @Override
    public Filter getFilter() {

        Filter myFilter = new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                FilterResults filterResults = new FilterResults();
                ArrayList<Aplicacion> tempList=new ArrayList<Aplicacion>();
                //constraint is the result from text you want to filter against.
                //objects is your data set you will filter from
                if(constraint != null && aplicaciones!=null) {
                    int length=aplicaciones.size();
                    int i=0;
                    while(i<length){
                        Aplicacion item=aplicaciones.get(i);
                        //do whatever you wanna do here
                        //adding result set output array

                        tempList.add(item);

                        i++;
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
                aplicaciones = (ArrayList<Aplicacion>) results.values;
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
