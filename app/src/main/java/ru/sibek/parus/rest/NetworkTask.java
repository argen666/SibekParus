package ru.sibek.parus.rest;

import android.content.ContentProviderClient;
import android.content.ContentValues;
import android.content.SyncResult;
import android.os.RemoteException;
import android.util.Log;

import java.lang.reflect.Method;

import ru.sibek.parus.mappers.Invoices;
import ru.sibek.parus.sqlite.InvoiceProvider;

/**
 * Created by Developer on 31.10.2014.
 */
public class NetworkTask {

    private ContentProviderClient provider=null;
    private SyncResult syncResult=null;

    public NetworkTask(ContentProviderClient provider, SyncResult syncResult) {
        this.provider=provider;
        this.syncResult=syncResult;
    }


    public void getData(String invoiceID, Object... params) throws RemoteException {
        Parus service = ParusService.getService();
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
            if (params.length>1){
                ret= m.invoke(service,params[1]);
            }
            else{
                ret= m.invoke(service);
            }
            //inv = service.listInv("59945");
        } catch (Exception e) {
            Log.d("MyParusExp", e.toString());
        }

        /*syncResult.stats.numUpdates += provider.u
                .update(InvoiceProvider.URI, channelValues, FeedProvider.Columns._ID + "=?", new String[]{feedId});*/
        /*if (invoiceID!=null) {
            syncResult.stats.numDeletes += provider
                    .delete(InvoiceProvider.URI, InvoiceProvider.Columns._ID + "=?", new String[]{invoiceID});
        }*/
        syncResult.stats.numUpdates += provider
                .bulkInsert(InvoiceProvider.URI, ((Invoices)ret).toContentValues());
       // return ret;
    }
}
