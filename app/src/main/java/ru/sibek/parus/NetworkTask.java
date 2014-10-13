package ru.sibek.parus;

import android.os.AsyncTask;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.lang.reflect.Method;

import retrofit.RestAdapter;
import retrofit.converter.GsonConverter;

class NetworkTask extends AsyncTask<Object, Void, Object> {

    private static NetworkTask instance=null;
   /* private NetworkTask() {
    }*/

    public static NetworkTask getInstance()
    {
        //if (instance==null){
            instance = new NetworkTask();
       // }
       return instance;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        Log.d("MyParus", "NetworkTask start...");
    }

    @Override
    protected Object doInBackground(Object... params) {

        Parus service = ParusService.getService();
//66
        Method m =null;
        Object ret=null;
        Class[] paramTypes=new Class[params.length-1];
        for (short i=1;i<params.length;i++)
        {
            paramTypes[i-1]=params[i].getClass();
        }
        try {
            m =  Parus.class.getDeclaredMethod((String)params[0],paramTypes);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }



        try {
           ret= m.invoke(service,params[1]);
            //inv = service.listInv("59945");
        } catch (Exception e) {
            Log.d("MyParusExp", e.toString());
        }
        return ret;
    }

    @Override
    protected void onPostExecute(Object result) {
        super.onPostExecute(result);
    }
}
