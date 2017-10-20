package brinkstest.com.pruebalista.services;

import android.util.Log;

import com.google.gson.GsonBuilder;

import java.util.List;

import brinkstest.com.pruebalista.adapters.ItemTypeAdapterFactory;
import brinkstest.com.pruebalista.models.Aplicacion;
import retrofit.RequestInterceptor;
import retrofit.RestAdapter;
import retrofit.converter.GsonConverter;
import retrofit.http.GET;
import rx.Observable;

/**
 * Created by cc_ri on 20/10/2017.
 */

public class WSAplicaciones {

    private WSAplicacionesApi wsAplicacionesApi;
    private static final String WSRest_Aplicaciones_URL = "https://itunes.apple.com/us/rss/toppaidapplications";

    public WSAplicaciones(){

        try {

            RequestInterceptor requestInterceptor = new RequestInterceptor() {
                @Override
                public void intercept(RequestFacade request) {
                    request.addHeader("Accept", "application/json");
                }
            };

            RestAdapter restAdapter = new RestAdapter.Builder()
                    .setEndpoint(WSRest_Aplicaciones_URL)
                    .setRequestInterceptor(requestInterceptor)
                    .setConverter(new GsonConverter(new GsonBuilder()
                            .registerTypeAdapterFactory(new ItemTypeAdapterFactory())
                            .create()))
                    .setLogLevel(RestAdapter.LogLevel.FULL)
                    .build();

            wsAplicacionesApi = restAdapter.create(WSAplicacionesApi.class);

        }catch (Exception e){
            Log.i("Error en WSAplicaciones", e.toString());
            e.printStackTrace();
        }

    }

    public WSAplicacionesApi getApi() {

        return wsAplicacionesApi;
    }

    public interface WSAplicacionesApi {

        @GET("/limit=20/json")
        public Observable<List<Aplicacion>>
        getAplicaciones();
    }

}
