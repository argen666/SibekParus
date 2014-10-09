package ru.sibek.parus;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Developer on 07.10.2014.
 */
public class JSONDeserializer<T> implements JsonDeserializer<T>
{
    @Override
    public T deserialize(JsonElement je, Type type, JsonDeserializationContext jdc)
            throws JsonParseException
    {
        Invoices obj = new Gson().fromJson(je, type);
        for (Invoices.Item item:obj.getItems())
        {
            try {
               Date d=((new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'")).parse(item.getDdocDate()));
               item.setDdocDate(new SimpleDateFormat("dd.MM.yyyy").format(d));
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        Log.d("MyDes", "Start...");

        return (T)obj;

    }
}