package ru.sibek.parus;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit.RestAdapter;
import retrofit.converter.GsonConverter;

/**
 * Created by Developer on 09.10.2014.
 */
public class ParusService {

    private static String API_URL = "http://192.168.16.13:8080/ords";
    private static Gson gson=null;
    private static Parus instance=null;

    private ParusService() {
    }

    public static Parus getService() {

        if (gson==null)
        {
        gson = new GsonBuilder()
                        .registerTypeAdapter(Invoices.class, new JSONDeserializer<Invoices>())
                        .create();
        }
        if (instance==null) {
            instance = new RestAdapter.Builder()
                    .setConverter(new GsonConverter(gson))
                    .setEndpoint(API_URL)
                    .build()
                    .create(Parus.class);
        }
        return instance;
    }
}
