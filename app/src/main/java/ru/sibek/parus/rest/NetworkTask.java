package ru.sibek.parus.rest;

import android.content.ContentProviderClient;
import android.content.SyncResult;
import android.os.RemoteException;
import android.util.Log;

import java.lang.reflect.Method;

import ru.sibek.parus.mappers.Invoices;
import ru.sibek.parus.mappers.InvoicesSpec;
import ru.sibek.parus.sqlite.InvoiceProvider;
import ru.sibek.parus.sqlite.InvoiceSpecProvider;

/**
 * Created by Developer on 31.10.2014.
 */
public class NetworkTask {

    private ContentProviderClient provider = null;
    private SyncResult syncResult = null;

    public NetworkTask(ContentProviderClient provider, SyncResult syncResult) {
        this.provider = provider;
        this.syncResult = syncResult;
    }


    public Object getData(String invoiceID, String tag, Object... params) throws RemoteException {
        Parus service = ParusService.getService();
        Method m = null;
        Object ret = null;
        Class[] paramTypes = new Class[params.length - 1];
        for (short i = 1; i < params.length; i++) {
            paramTypes[i - 1] = params[i].getClass();
        }
        try {
            m = Parus.class.getDeclaredMethod((String) params[0], paramTypes);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }

        try {
            if (params.length > 1) {
                ret = m.invoke(service, (String) params[1]);
                Log.d("PARAM>>>>>", (String) params[1]);
            } else {
                ret = m.invoke(service);
            }
            //inv = service.listInv("59945");
        } catch (Exception e) {
            // TODO: тут может случаться говно...
            e.printStackTrace();
            Log.d(Log.DEBUG + "MyParusExp", e.toString());

        }

        /*syncResult.stats.numUpdates += provider.u
                .update(InvoiceProvider.URI, channelValues, FeedProvider.Columns._ID + "=?", new String[]{feedId});*/
        /*if (invoiceID!=null) {
            syncResult.stats.numDeletes += provider
                    .delete(InvoiceProvider.URI, InvoiceProvider.Columns._ID + "=?", new String[]{invoiceID});
        }*/
        if (ret!=null)
        {


        switch (tag) {
            case "FULL_INSERT": {
                syncResult.stats.numUpdates += provider
                        .bulkInsert(InvoiceProvider.URI, ((Invoices) ret).toContentValues());
                break;
            }
            case "UPDATE_INVOICE": {
                syncResult.stats.numUpdates += provider
                        .update(InvoiceProvider.URI, ((Invoices) ret).toContentValues()[0], InvoiceProvider.Columns._ID + "=?", new String[]{invoiceID});
                Log.d("UPDATE_INVOICE_SIZE>>>>>>>>>>>>>", ((Invoices) ret).toString());

                break;
            }

            case "UPDATE_SPEC": {
                Log.d("UPDATE_SPEC_SIZE>>>>>>>>>>>>>", ((InvoicesSpec) ret).toString());
                syncResult.stats.numDeletes += provider
                        .delete(InvoiceSpecProvider.URI, InvoiceSpecProvider.Columns.INVOICE_ID + "=?", new String[]{invoiceID});

                syncResult.stats.numUpdates += provider
                        .bulkInsert(InvoiceSpecProvider.URI, ((InvoicesSpec) ret).toContentValues(invoiceID));

/*
                syncResult.stats.numUpdates += provider
                        .update(InvoiceSpecProvider.URI, ((InvoicesSpec) ret).toContentValues(invoiceID)[0], InvoiceSpecProvider.Columns.INVOICE_ID + "=?", new String[]{invoiceID});*/
                break;
            }
        }

    }


        return ret;
    }
}
